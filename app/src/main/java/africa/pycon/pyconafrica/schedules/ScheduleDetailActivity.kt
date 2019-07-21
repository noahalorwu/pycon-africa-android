package africa.pycon.pyconafrica.schedules

import africa.pycon.pyconafrica.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_schedule_detail.*
import kotlinx.android.synthetic.main.speaker_profile.*
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
        supportActionBar?.title = "Details"
        tvTitle.text = title
        tvDesc.text = talkDesc
        tvLoc.text = talkLoc
        tvStart.text = startTime
        tvEnd.text = endTime
        sProfileView.text = speakerName

        sProfileView.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.speaker_profile, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            sProfile?.text = speakerProfile
            sName?.text = speakerName
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}
