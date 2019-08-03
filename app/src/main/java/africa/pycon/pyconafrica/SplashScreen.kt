package africa.pycon.pyconafrica

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIMEOUT: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Handler().postDelayed({
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }, SPLASH_TIMEOUT)
    }
}
