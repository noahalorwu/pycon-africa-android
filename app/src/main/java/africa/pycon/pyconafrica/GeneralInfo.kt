package africa.pycon.pyconafrica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import africa.pycon.pyconafrica.databinding.FragmentGeneralInfoBinding
import africa.pycon.pyconafrica.extensions.browseCustomTab
import africa.pycon.pyconafrica.extensions.toast
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Button

class GeneralInfo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGeneralInfoBinding =
            DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_general_info,
            container, false)
        binding.general = this
        return binding.root
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
        val copyButton = dialogView?.findViewById<Button>(R.id.copy_button)
        val alertDialog = builder.create()
        alertDialog.show()

        copyButton?.setOnClickListener {
            copyToClipBoard(wifiKey = getString(R.string.wifi_key))
            alertDialog?.cancel()
            context?.toast("Wifi Key Copied") }
    }
}