package africa.pycon.pyconafrica.socialmedia

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.extensions.openWebView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.fragment_facebook.*

class Facebook : Fragment() {
    companion object {
        private const val ID_BACK = 1
        private const val ID_RELOAD = 2
        private const val ID_FORWARD = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_facebook, container, false)
        val pycon_web = view.findViewById(R.id.web_facebook) as WebView
        val pb = view.findViewById(R.id.load_page) as ProgressBar
        context?.openWebView(pycon_web, getString(R.string.py_facebook), pb)
        noah(view)
        return view
    }

    fun noah(view: View) {
        val bottomNavigation: MeowBottomNavigation = view.findViewById(R.id.bottomNavigation)
        bottomNavigation.add(MeowBottomNavigation.Model(
            ID_BACK,
            R.drawable.backweb
        ))
        bottomNavigation.add(MeowBottomNavigation.Model(
            ID_RELOAD,
            R.drawable.reload
        ))
        bottomNavigation.add(MeowBottomNavigation.Model(
            ID_FORWARD,
            R.drawable.forwardarrow
        ))
        bottomNavigation.setOnShowListener {
            val name = when (it.id) {
                ID_RELOAD -> "Reload"
                ID_BACK -> "Back"
                ID_FORWARD -> "Forward"
                else -> ""
            }
        }

        bottomNavigation.setOnClickMenuListener {
            val name = when (it.id) {

                ID_RELOAD -> {
                    web_facebook.reload()
                    return@setOnClickMenuListener
                    Toast.makeText(activity, "Reload", Toast.LENGTH_SHORT).show()
                }

                ID_BACK -> {
                    web_facebook.goBack()
                    return@setOnClickMenuListener
                }
                ID_FORWARD -> {
                    if (web_facebook.canGoForward()) {
                        web_facebook.goForward()
                        return@setOnClickMenuListener
                    }
                    Toast.makeText(activity, "Forward", Toast.LENGTH_SHORT).show()
                }
                else -> ""
            }
        }
    }
}