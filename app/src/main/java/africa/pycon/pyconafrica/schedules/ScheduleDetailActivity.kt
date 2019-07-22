package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.browseCustomTab
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_schedule_detail.*
import kotlinx.android.synthetic.main.speaker_profile.*
import kotlinx.android.synthetic.main.speaker_profile.view.*
import java.util.zip.Inflater

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

        sProfileView.setOnClickListener{
            val mDialog = LayoutInflater.from(this).inflate(R.layout.speaker_profile, null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(mDialog)
            mDialog.sName.text = speakerName
            mDialog.sProfile.text = speakerProfile
            mDialog.connectSpeaker.setOnClickListener{
                browseCustomTab("https://twitter.com/$speakerSocial")
            }
            dialogBuilder.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}
