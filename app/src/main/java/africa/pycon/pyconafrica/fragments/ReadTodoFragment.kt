package africa.pycon.pyconafrica.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.models.Todo
import kotlinx.android.synthetic.main.fragment_read_todo.*

class ReadTodoFragment(todoItem: Todo) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view =  inflater.inflate(R.layout.fragment_read_todo, container, false)
        content_textview.text = "Notes Content Goes Here"
        return view
    }


}
