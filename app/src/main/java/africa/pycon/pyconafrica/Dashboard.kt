package africa.pycon.pyconafrica

import africa.pycon.pyconafrica.extensions.browseCustomTab
import africa.pycon.pyconafrica.extensions.toast
import africa.pycon.pyconafrica.socialmedia.Facebook
import africa.pycon.pyconafrica.socialmedia.PyAfricaWebsite
import africa.pycon.pyconafrica.sponsors.SponsorFragment
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Fabric.with(this, Crashlytics())
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        // Inflate the General Info fragment onCreate
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GeneralInfo())
            .addToBackStack(null)
            .commit()
        supportActionBar?.title = "General Info"
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.dashboard, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val nv: NavigationView = findViewById(R.id.nav_view)
        val m: Menu = nv.menu

        when (item.itemId) {
            R.id.nav_generalinfo -> {
                supportActionBar?.title = "General Information"
                setFragment(GeneralInfo())
                item.isChecked = true
            }
            R.id.nav_schedules -> {
                supportActionBar?.title = "Schedules"
            }
            R.id.nav_specialevents -> {
                supportActionBar?.title = "Special Events"
                toast("$item + tapped")
            }
            R.id.nav_sponsors -> {
                supportActionBar?.title = "Sponsors"
                setFragment(SponsorFragment())
            }

            R.id.nav_socialmedia -> {
                m.findItem(R.id.nav_generalinfo).isVisible = false
                m.findItem(R.id.nav_schedules).isVisible = false
                m.findItem(R.id.nav_sponsors).isVisible = false
                m.findItem(R.id.nav_socialmedia).isVisible = false
                m.findItem(R.id.nav_specialevents).isVisible = false
                m.findItem(R.id.nav_todo).isVisible = false
                m.findItem(R.id.nav_twitter).isVisible = true
                m.findItem(R.id.nav_pyconafricablog).isVisible = true
                m.findItem(R.id.nav_youtube).isVisible = true
                m.findItem(R.id.nav_facebook).isVisible = true
                m.findItem(R.id.nav_website).isVisible = true
                m.findItem(R.id.nav_back).isVisible = true
                return true
            }

            R.id.nav_todo -> {
                supportActionBar?.title = "To-Do"
                setFragment(TodosFragment())
            }

            R.id.nav_back -> {
                m.findItem(R.id.nav_generalinfo).isVisible = true
                m.findItem(R.id.nav_schedules).isVisible = true
                m.findItem(R.id.nav_sponsors).isVisible = true
                m.findItem(R.id.nav_socialmedia).isVisible = true
                m.findItem(R.id.nav_specialevents).isVisible = true
                m.findItem(R.id.nav_todo).isVisible = true
                m.findItem(R.id.nav_twitter).isVisible = false
                m.findItem(R.id.nav_pyconafricablog).isVisible = false
                m.findItem(R.id.nav_youtube).isVisible = false
                m.findItem(R.id.nav_website).isVisible = false
                m.findItem(R.id.nav_facebook).isVisible = false
                m.findItem(R.id.nav_back).isVisible = false
                return true
            }
            R.id.nav_website -> {
                supportActionBar?.title = "Pycon Africa Website"
                setFragment(PyAfricaWebsite())
            }
            R.id.nav_facebook -> {
                supportActionBar?.title = "Facebook"
                setFragment(Facebook())
            }

            R.id.nav_twitter -> {
                browseCustomTab(getString(R.string.py_twitter))
            }

            R.id.nav_pyconafricablog -> {
                browseCustomTab(getString(R.string.py_blog))
            }

            R.id.nav_youtube -> {
                browseCustomTab(getString(R.string.py_youtube))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}