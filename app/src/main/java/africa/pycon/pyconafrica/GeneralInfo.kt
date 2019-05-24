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

class GeneralInfo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGeneralInfoBinding>(
            inflater,
            R.layout.fragment_general_info,
            container, false)
        binding.general = this
        return binding.root
    }
    fun openMaps() {
        context?.browseCustomTab(getString(R.string.maps))
    }

    fun showDialog() {
        context?.toast("I am to show a dialog but ...")
    }
}