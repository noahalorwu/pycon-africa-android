package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.toast
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import devs.mulham.horizontalcalendar.HorizontalCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
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
    lateinit var mEvent: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_schedules, container,
            false)
        mRecylerview = view.findViewById(R.id.schedules_recycler)
        mRef = FirebaseDatabase.getInstance().reference
        mEvent = view.findViewById(R.id.no_event)
        mRecylerview.layoutManager = LinearLayoutManager(parentFragment?.context)
        mRecylerview.addItemDecoration(DividerItemDecoration(activity!!,
            DividerItemDecoration.VERTICAL))
        mRecylerview.setHasFixedSize(true)
        mRef.keepSynced(true)
        val startDate: Calendar = Calendar.getInstance()
        startDate.set(2019,Calendar.AUGUST, 6)
        val endDate: Calendar = Calendar.getInstance()
        endDate.set(2019,Calendar.AUGUST, 10)
        val hc: HorizontalCalendar = HorizontalCalendar.Builder(view, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build()
        hc.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                val mDate = SimpleDateFormat("dd-MM-yyyy").format(date?.time)
                val mSchedules = mRef.child("Schedules").child(mDate)
                val option = FirebaseRecyclerOptions.Builder<SchedulesViewModel>()
                    .setQuery(mSchedules, SchedulesViewModel::class.java)
                    .setLifecycleOwner(parentFragment)
                    .build()

                val fireBaseAdapter = object : FirebaseRecyclerAdapter<SchedulesViewModel,
                        ScheduleViewHolder>(option) {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): ScheduleViewHolder {
                        val itemView = LayoutInflater.from(activity)
                            .inflate(R.layout.schedules_layout, parent,
                                false)

                        return ScheduleViewHolder(itemView)
                    }

                    override fun onBindViewHolder(
                        holder: ScheduleViewHolder,
                        position: Int,
                        model: SchedulesViewModel
                    ) {
                        val placeid = getRef(position).key.toString()
                        mRef.child(placeid).addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                context?.toast("Error loading")
                            }
                            override fun onDataChange(p0: DataSnapshot) {
                                holder.tStartTime.text = model.startTime.toString()
                                holder.tEndTime.text = model.endTime.toString()
                                holder.tTitle.text = model.talkTitle.toString()
                                holder.tLocation.text = model.talkLocation.toString()
                                holder.itemView.setOnClickListener {
                                    val intent = Intent(context, ScheduleDetailActivity::class.java)
                                    intent.putExtra("startTime", model.startTime)
                                    intent.putExtra("endTime", model.endTime)
                                    intent.putExtra("talkTitle", model.talkTitle)
                                    intent.putExtra("talkDescription", model.talkDescription)
                                    intent.putExtra("talkLocation", model.talkLocation)
                                    intent.putExtra("speakerName", model.speakerName)
                                    intent.putExtra("speakerProfile", model.speakerProfile)
                                    intent.putExtra("speakerTwitter", model.speakerTwitter)
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
        return view
        }
    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tStartTime: TextView = itemView.findViewById(R.id.start_time)
        var tEndTime: TextView = itemView.findViewById(R.id.end_time)
        var tTitle: TextView = itemView.findViewById(R.id.talk_title)
        var tLocation: TextView = itemView.findViewById(R.id.talk_location)
    }
}