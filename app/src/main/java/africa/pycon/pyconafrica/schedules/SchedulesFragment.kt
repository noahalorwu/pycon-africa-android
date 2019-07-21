package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.toast
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import devs.mulham.horizontalcalendar.HorizontalCalendar
import java.text.SimpleDateFormat
import java.util.*
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener as HorizontalCalendarListener

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
class SchedulesFragment : Fragment() {
    lateinit var mRecylerview: RecyclerView
    lateinit var mRef: DatabaseReference
    lateinit var mProgress: ProgressBar
    lateinit var mEvent : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val view: View = inflater.inflate(R.layout.fragment_schedules, container, false)
        mRecylerview = view.findViewById(R.id.schedules_recycler)
        mRef = FirebaseDatabase.getInstance().reference
        mEvent = view.findViewById(R.id.no_event)
               // val mCalendar:String = mRef.key.toString()
       // mProgress = view.findViewById(R.id.load_schedules)
        mRecylerview.layoutManager = LinearLayoutManager(parentFragment?.context)
        mRecylerview.setHasFixedSize(true)
       //fireBaseData()
        mRef.keepSynced(true)
        return view
   }
    fun fireBaseData() {
        val option = FirebaseRecyclerOptions.Builder<SchedulesViewModel>()
            .setQuery(mRef, SchedulesViewModel::class.java)
            .setLifecycleOwner(this)
            .build()

        val fireBaseAdapter = object : FirebaseRecyclerAdapter<SchedulesViewModel,
                ScheduleViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
                val itemView = LayoutInflater.from(activity)
                    .inflate(R.layout.schedules_layout,
                        parent, false)

                return ScheduleViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: ScheduleViewHolder,
                position: Int,
                model: SchedulesViewModel
            ) { val placeid = getRef(position).key.toString()
                mRef.child(placeid).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        context?.toast("Error loading")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        holder.tStartTime.text = model.startTime
                        holder.tEndTime.text = model.endTime
                        holder.tTitle.text = model.talkTitle
                        holder.tLocation.text = model.talkLocation
                    }
                })
            }
        }
        mRecylerview.adapter = fireBaseAdapter
        fireBaseAdapter.startListening()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tStartTime: TextView = itemView.findViewById(R.id.start_time)
        var tEndTime: TextView = itemView.findViewById(R.id.end_time)
        var tTitle: TextView = itemView.findViewById(R.id.talk_title)
        var tLocation: TextView = itemView.findViewById(R.id.talk_location)
    }

    override fun onStart() {
        super.onStart()
        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)
        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        val hc : HorizontalCalendar = HorizontalCalendar.Builder(view,R.id.calendarView)
            .range(startDate,endDate)
            .datesNumberOnScreen(5)
            .build()
        hc.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                val mDate = SimpleDateFormat("dd-MM-yyyy").format(date?.time)
                val mSchedules: DatabaseReference = mRef.child("Schedules").child(mDate)
                    val option = FirebaseRecyclerOptions.Builder<SchedulesViewModel>()
                        .setQuery(mSchedules, SchedulesViewModel::class.java)
                        .setLifecycleOwner(parentFragment)
                        .build()

                    val fireBaseAdapter = object : FirebaseRecyclerAdapter<SchedulesViewModel,
                            ScheduleViewHolder>(option) {

                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
                            val itemView = LayoutInflater.from(activity)
                                .inflate(
                                    R.layout.schedules_layout,
                                    parent, false
                                )

                            return ScheduleViewHolder(itemView)
                        }

                        override fun onBindViewHolder(
                            holder: ScheduleViewHolder,
                            position: Int,
                            model: SchedulesViewModel
                        ) {
//                            val select = 2
//                            if(position % select == 0){
//                                holder.itemView.setBackgroundColor(Color.BLUE)
//                            }else{
//                                holder.itemView.setBackgroundColor(Color.BLACK)
//                            }

                            val placeid = getRef(position).key.toString()
                            mRef.child(placeid).addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                    context?.toast("Error loading")
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    holder.tStartTime.text = model.startTime
                                    holder.tEndTime.text = model.endTime
                                    holder.tTitle.text = model.talkTitle
                                    holder.tLocation.text = model.talkLocation
                                    holder.itemView.setOnClickListener{
                                        val intent = Intent(context, ScheduleDetailActivity::class.java)
                                        intent.putExtra("startTime", model.startTime)
                                        intent.putExtra("endTime", model.endTime)
                                        intent.putExtra("talkTitle", model.talkTitle)
                                        intent.putExtra("talkDescription", model.talkDescription)
                                        intent.putExtra("talkLocation", model.talkLocation)
                                        intent.putExtra("speakerName", model.speakerName)
                                        intent.putExtra("speakerProfile", model.speakerProfile)
                                        intent.putExtra("speakerImage", model.speakerImage)
                                        context?.startActivity(intent)
                                    }
                                }
                            })
                        }
                    }
                    mRecylerview.adapter = fireBaseAdapter
                    fireBaseAdapter.startListening()

                }


        }


    }
}

