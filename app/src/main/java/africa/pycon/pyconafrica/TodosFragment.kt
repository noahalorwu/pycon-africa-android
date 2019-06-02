package africa.pycon.pyconafrica

import africa.pycon.pyconafrica.extensions.DBHelper
import africa.pycon.pyconafrica.extensions.TodosAdapter
import africa.pycon.pyconafrica.models.Todo
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodosFragment : Fragment(), TodosAdapter.OnClickListener {

    private var myAdapter: TodosAdapter? = null
    private var dbHelper: DBHelper? = null
    private var todoList = ArrayList<Todo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dbHelper = DBHelper(context)
        val view = inflater.inflate(R.layout.fragment_todos, container, false)
        val addButton = view.findViewById<FloatingActionButton>(R.id.add_todo_fab)
        val todosRecycler = view.findViewById<RecyclerView>(R.id.todos_recyclerview)

        todoList = dbHelper!!.allNotes
        myAdapter = TodosAdapter(todoList, context)
        myAdapter!!.setListener(this)
        todosRecycler.layoutManager = LinearLayoutManager(activity)
        todosRecycler.adapter = myAdapter
        return view
    }

    private fun createNote(todo: Todo) {
        val id = dbHelper!!.insertTodo(todo)
        val new = dbHelper!!.getTodo(id)
        todoList.add(0, new)
        myAdapter?.notifyDataSetChanged()
    }

    override fun onItemDelete(todo: Todo) {
        deleteConfirmation(todo)
    }

    override fun onItemClick(todo: Todo, position: Int) {
    }

    override fun onItemEdit(todo: Todo) {
    }

    private fun deleteConfirmation(todo: Todo) {
        val alertDialog = AlertDialog.Builder(this.context ?: return)
        alertDialog.setTitle("Confirm Delete...")
        alertDialog.setMessage("Are you sure you want to delete this?")
        alertDialog.setIcon(R.drawable.ic_delete)
        alertDialog.setPositiveButton("YES") { dialog, which ->
            dbHelper!!.deleteTodo(todo)
            todoList.remove(todo)
            myAdapter!!.notifyDataSetChanged() // refreshing the list
        }

        alertDialog.setNegativeButton("NO") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    private fun showNoteDialog(shouldUpdate: Boolean, todo: Todo?, position: Int) {
        val view = LayoutInflater.from(applicationContext).inflate(R.layout.add_todo, null)

        val alertDialogView = AlertDialog.Builder(this).create()
        alertDialogView.setView(view)

        val tvHeader = view.findViewById<TextView>(R.id.tvHeader)
        val edTitle = view.findViewById<EditText>(R.id.edTitle)
        val edDesc = view.findViewById<EditText>(R.id.edDesc)
        val btAddUpdate = view.findViewById<Button>(R.id.btAddUpdate)
        val btCancel = view.findViewById<Button>(R.id.btCancel)
        if (shouldUpdate) btAddUpdate.text = "Update" else btAddUpdate.text = "Save"

        if (shouldUpdate && todo != null) {
            edTitle.setText(todo.title)
            edDesc.setText(todo.desc)
        }

        btAddUpdate.setOnClickListener(View.OnClickListener {
            val tName = edTitle.text.toString()
            val descName = edDesc.text.toString()

            if (TextUtils.isEmpty(tName)) {
                Toast.makeText(this, "Enter Your Title!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (TextUtils.isEmpty(descName)) {
                Toast.makeText(this, "Enter Your Description!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            // check if user updating Todos
            if (shouldUpdate && todo != null) {
                updateNote(Todo(tName, descName), position)      // update note by it's id
            } else {
                createNote(Todo(tName, descName))   // create new note
            }
            alertDialogView.dismiss()
        })

        btCancel.setOnClickListener(View.OnClickListener {
            alertDialogView.dismiss()
        })
        tvHeader.text = if (!shouldUpdate) getString(R.string.lbl_new_todo_title) else getString(R.string.lbl_edit_todo_title)

        alertDialogView.setCancelable(false)
        alertDialogView.show()
    }

}