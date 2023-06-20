import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebasefininfocom.R
import com.example.loginfirebasefininfocom.model.User

class UserListAdapter(private val items: ArrayList<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = "Name - ${item.name}"
        holder.itemAge.text = "Age - ${item.age}"
        holder.itemCity.text = "City - ${item.city}"
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvName)
        val itemAge: TextView = itemView.findViewById(R.id.tvAge)
        val itemCity: TextView = itemView.findViewById(R.id.tvCity)
    }

    fun sortByAge(){
        items.sortBy {
            it.age
        }

    }

    fun sortByName(){
        items.sortBy {
            it.name
        }

    }
    fun sortByCity(){
        items.sortBy {
            it.city
        }

    }
}
