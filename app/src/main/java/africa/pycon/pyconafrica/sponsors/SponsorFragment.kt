package africa.pycon.pyconafrica.sponsors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.toast
import android.content.Intent
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot

class SponsorFragment : Fragment() {
    lateinit var mrecylerview: RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var mprogress: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sponsor, container, false)
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        ref = FirebaseDatabase.getInstance().reference.child("Sponsors")
        mrecylerview = view.findViewById(R.id.reyclerview)
        mprogress = view.findViewById(R.id.load_sponsors)
        mrecylerview.layoutManager = LinearLayoutManager(parentFragment?.context)
        mrecylerview.addItemDecoration(DividerItemDecoration(activity!!,
            DividerItemDecoration.VERTICAL))
        mrecylerview.setHasFixedSize(true)
        ref.keepSynced(true)
        fireBaseData()
        return view
    }

    fun fireBaseData() {
        val option = FirebaseRecyclerOptions.Builder<SponsorViewModel>()
            .setQuery(ref, SponsorViewModel::class.java)
            .setLifecycleOwner(this)
            .build()

        val fireBaseAdapter = object : FirebaseRecyclerAdapter<SponsorViewModel,
                SponsorViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SponsorViewHolder {
                val itemView = LayoutInflater.from(activity)
                    .inflate(R.layout.sponsors_layout,
                    parent, false)

                return SponsorViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: SponsorViewHolder,
                position: Int,
                model: SponsorViewModel
            ) { val placeid = getRef(position).key.toString()
                ref.child(placeid).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        context?.toast("Error loading")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        mprogress.visibility = if (itemCount == 0) View.VISIBLE
                        else View.GONE
                        holder.sponsorName.text = model.sponsorName
                        holder.sponsorsLevel.text = model.sponsorPackage
                        context?.let {
                            Glide.with(it)
                                .load(model.sponsorLogo)
                                .into(holder.sponsorLogo) }
                        holder.itemView.setOnClickListener {
                            val intent = Intent(context, SponsorDetailActivity::class.java)
                            intent.putExtra("sponsorName", model.sponsorName)
                            intent.putExtra("sponsorPackage", model.sponsorPackage)
                            intent.putExtra("sponsorLogo", model.sponsorLogo)
                            context?.startActivity(intent)
                        } }
                })
            }
        }
        mrecylerview.adapter = fireBaseAdapter
        fireBaseAdapter.startListening()
    }
    class SponsorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sponsorName: TextView = itemView.findViewById(R.id.sponsor_name)
        var sponsorsLevel: TextView = itemView.findViewById(R.id.sponsor_level)
        var sponsorLogo: ImageView = itemView.findViewById(R.id.display_img)
    }
}