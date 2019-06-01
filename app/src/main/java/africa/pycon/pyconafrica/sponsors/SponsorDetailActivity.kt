package africa.pycon.pyconafrica.sponsors

import africa.pycon.pyconafrica.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_sponsor_detail.*

class SponsorDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsor_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle: Bundle? = intent.extras
        val sponsor_name = bundle?.get("sponsorName").toString()
        val sponsor_package = bundle?.get("sponsorPackage").toString()
        val sponsor_logo = bundle?.get("sponsorLogo").toString()
        supportActionBar?.title = sponsor_name
        sName.text = sponsor_name
        sPackage.text = sponsor_package
        Glide.with(this).load(sponsor_logo).into(sLogo)
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> true
            else -> false
        }
    }
}
