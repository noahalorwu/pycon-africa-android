package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.browseCustomTab
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_schedule_detail.*
import kotlinx.android.synthetic.main.speaker_profile.view.*

class ScheduleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle: Bundle? = intent.extras
        val startTime = bundle?.get("startTime").toString()
        val endTime = bundle?.get("endTime").toString()
        val title = bundle?.get("talkTitle").toString()
        val talkDesc = bundle?.get("talkDescription").toString()
        val talkLoc = bundle?.get("talkLocation").toString()
        val speakerName = bundle?.get("speakerName").toString()
        val speakerProfile = bundle?.get("speakerProfile").toString()
        val speakerImg = bundle?.get("speakerImage").toString()
        val speakerSocial = bundle?.get("speakerTwitter").toString()
        supportActionBar?.title = "Details"
        tvTitle.text = title
        tvDesc.text = talkDesc
        tvLoc.text = talkLoc
        tvStart.text = startTime
        tvEnd.text = endTime
        sProfileView.text = "View Speakers Profile"

        sProfileView.setOnClickListener {
            val mDialog = LayoutInflater.from(this).inflate(R.layout.speaker_profile, null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(mDialog)
            Glide.with(this).load(speakerImg).into(mDialog.profile_image)
            mDialog.sName.text = speakerName
            mDialog.sProfile.text = speakerProfile
            mDialog.connectSpeaker.setOnClickListener {
                browseCustomTab("https://twitter.com/$speakerSocial")
            }
            val b = dialogBuilder.create()
            b.show()

            mDialog.close_btn.setOnClickListener {
                b.cancel()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
