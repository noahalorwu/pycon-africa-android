package africa.pycon.pyconafrica.sponsors

import africa.pycon.pyconafrica.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_sponsor_detail.*

class SponsorDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsor_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle: Bundle? = intent.extras
        val sponsor = bundle?.get("sponsorName").toString()
        val sponsorLevel = bundle?.get("sponsorPackage").toString()
        val brandLogo = bundle?.get("sponsorLogo").toString()
        val sponsorDesc = bundle?.get("sponsorDescription").toString()
        supportActionBar?.title = sponsor
        sName.text = sponsor
        sPackage.text = sponsorLevel
        when (sponsorLevel) {
            "Bronze" -> packageColor(sPackage, "#22aadd")
            "Diamond" -> packageColor(sPackage, "#FFD34A")
            "Platinum" -> packageColor(sPackage, "#FF5722")
            "Headline" -> packageColor(sPackage, "#E91E63")
            else -> packageColor(sPackage, "#8BC34A")
        }
        Glide.with(this).load(brandLogo).into(sLogo)
        sDesc.text = sponsorDesc
        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun packageColor(tv: TextView, txtColor: String) {
        tv.setTextColor(txtColor.toColorInt())
    }
}
