package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.toast
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.text.SimpleDateFormat
import java.util.*

class SchedulesFragment : Fragment() {
    lateinit var mRecylerview: RecyclerView
    lateinit var mRef: DatabaseReference
    lateinit var mEvent: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_schedules, container,
            false
        )
        mRecylerview = view.findViewById(R.id.schedules_recycler)
        mRef = FirebaseDatabase.getInstance().reference
        mRecylerview.layoutManager = LinearLayoutManager(parentFragment?.context)
        mRecylerview.addItemDecoration(
            DividerItemDecoration(
                activity!! as Context?,
                DividerItemDecoration.VERTICAL
            )
        )
        mRecylerview.setHasFixedSize(true)
        mRef.keepSynced(true)
        val startDate: Calendar = Calendar.getInstance()
        startDate.set(2019, Calendar.AUGUST, 6)
        val endDate: Calendar = Calendar.getInstance()
        endDate.set(2019, Calendar.AUGUST, 10)
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
                            .inflate(
                                R.layout.schedules_layout, parent,
                                false
                            )

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
                                        .apply {
                                            putExtra("startTime", model.startTime)
                                            putExtra("endTime", model.endTime)
                                            putExtra("talkTitle", model.talkTitle)
                                            putExtra("talkDescription", model.talkDescription)
                                            putExtra("talkLocation", model.talkLocation)
                                            putExtra("speakerName", model.speakerName)
                                            putExtra("speakerProfile", model.speakerProfile)
                                            putExtra("speakerTwitter", model.speakerTwitter)
                                            putExtra("speakerImage", model.speakerImage)
                                        }

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