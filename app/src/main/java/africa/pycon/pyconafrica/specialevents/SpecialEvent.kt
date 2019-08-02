package africa.pycon.pyconafrica.specialevents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.browseCustomTab
import africa.pycon.pyconafrica.extensions.toast
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class SpecialEvent : Fragment() {
    lateinit var mSpecialRecyclerView: RecyclerView
    lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_special_event, container, false)
        ref = FirebaseDatabase.getInstance().reference.child("SpecialEvent")
        mSpecialRecyclerView = view.findViewById(R.id.specialevent_recycler)
        val layManager = LinearLayoutManager(parentFragment?.context)
        layManager.reverseLayout = true
        layManager.stackFromEnd = true
        mSpecialRecyclerView.layoutManager = layManager
        mSpecialRecyclerView.setHasFixedSize(true)
        ref.keepSynced(true)
        fireBaseData()
        return view
    }

        fun fireBaseData() {
        val option = FirebaseRecyclerOptions.Builder<SpecialEventViewModel>()
            .setQuery(ref, SpecialEventViewModel::class.java)
            .setLifecycleOwner(this)
            .build()

        val fireBaseAdapter = object : FirebaseRecyclerAdapter<SpecialEventViewModel,
                SpecialEventViewHolder>(option) {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): SpecialEventViewHolder {
                val itemView = LayoutInflater.from(activity)
                    .inflate(R.layout.special_event_layout,
                        parent, false)
                return SpecialEventViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: SpecialEventViewHolder,
                position: Int,
                model: SpecialEventViewModel
            ) { val placeid = getRef(position).key.toString()
                ref.child(placeid).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        context?.toast("Error loading")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        holder.eTitle.text = model.eventTitle.toString()
                        holder.eDate.text = model.eventDate.toString()
                        holder.eTime.text = model.eventTime.toString()
                        holder.eDesc.text = model.eventDesc.toString()
                        val link = model.eventLocation.toString()
                        holder.eLink.setOnClickListener {
                            context?.browseCustomTab(link)
                        }
                        context?.let {
                            Glide.with(it)
                                .load(model.eventImg)
                                .into(holder.eImage) }
                    }
                })
            }
        }
        mSpecialRecyclerView.adapter = fireBaseAdapter
        fireBaseAdapter.startListening()
    }
    class SpecialEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eTitle: TextView = itemView.findViewById(R.id.event_title)
        var eDate: TextView = itemView.findViewById(R.id.event_date)
        var eTime: TextView = itemView.findViewById(R.id.event_time)
        var eDesc: TextView = itemView.findViewById(R.id.event_desc)
        var eLink: Button = itemView.findViewById(R.id.event_link)
        var eImage: ImageView = itemView.findViewById(R.id.event_header)
    }
}