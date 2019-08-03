package africa.pycon.pyconafrica

import africa.pycon.pyconafrica.extensions.browseCustomTab
import africa.pycon.pyconafrica.extensions.toast
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class GeneralInfo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_general_info, container, false)
        val generalButton = view.findViewById(R.id.generalBtn) as Button
        val mapButton = view.findViewById(R.id.mapBtn) as Button
        generalButton.setOnClickListener {
            showDialog()
        }
        mapButton.setOnClickListener {
            openMaps()
        }
        return view
    }

    fun openMaps() {
        context?.browseCustomTab(getString(R.string.maps))
    }

    private fun copyToClipBoard(wifiKey: String) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Wifi Key", wifiKey)
        clipboard.primaryClip = clipData
    }

    @SuppressLint("InflateParams")
    fun showDialog() {
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.general_info_dialog_layout, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        val copyButton = dialogView?.findViewById(R.id.copy_button) as Button
        val alertDialog = builder.create()
        alertDialog.show()

        copyButton.setOnClickListener {
            copyToClipBoard(wifiKey = getString(R.string.wifi_key))
            alertDialog?.cancel()
            context?.toast("Wifi Key Copied")
        }
    }
}