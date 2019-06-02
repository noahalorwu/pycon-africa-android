package africa.pycon.pyconafrica.extensions

import africa.pycon.pyconafrica.R
import africa.pycon.pyconafrica.models.Todo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todos_item_layout.view.*


class TodosAdapter internal constructor(private val todosList: ArrayList<Todo>, private val context: Context?) :
    RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {

    private var listener: OnClickListener? = null

    fun setListener(clickListener: OnClickListener) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TodosAdapter.TodosViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            africa.pycon.pyconafrica.R.layout.todos_item_layout, parent, false)
        return TodosViewHolder(inflatedView)
    }

    class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(todoItem: Todo) {
            val title: TextView = itemView.findViewById(R.id.todos_title)
            val dateTime: TextView = itemView.findViewById(R.id.todos_date_time)
            val editButton: ImageView = itemView.findViewById(R.id.edit_todo)
            val deleteButton: ImageView = itemView.findViewById(R.id.delete_todo)
            title.text = todoItem.title
            dateTime.text = todoItem.timestamp
        }
    }

    override fun getItemCount(): Int {
        return todosList.size
    }

    override fun onBindViewHolder(holder: TodosAdapter.TodosViewHolder, position: Int) {
        val todoItem = todosList[position]
        holder.bindItems(todoItem)

        holder.itemView.todoLinearLayout.setOnClickListener({ if (listener != null) {
            listener!!.onItemClick(todoItem, position)
        } })

        holder.itemView.edit_todo.setOnClickListener({ if (listener != null) {
            listener!!.onItemEdit(todoItem)
        } })

        holder.itemView.delete_todo.setOnClickListener({ if (listener != null) {
            listener!!.onItemDelete(todoItem)
        } })

    }

    interface OnClickListener {
        fun onItemClick(todo: Todo, position: Int)
        fun onItemDelete(todo: Todo)
        fun onItemEdit(todo: Todo)
    }
}