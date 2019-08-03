package africa.pycon.pyconafrica

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class PyConApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
