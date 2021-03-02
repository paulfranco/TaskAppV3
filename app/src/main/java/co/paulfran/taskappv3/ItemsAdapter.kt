package co.paulfran.taskappv3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val project: Project, listenerContext: OnItemClickListener): RecyclerView.Adapter<ItemsViewHolder>() {

    private var clickInterface: OnItemClickListener = listenerContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item: Item = project.items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            clickInterface.itemClick(position)
        }

        holder.itemView.setOnLongClickListener {
            clickInterface.itemLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int = project.items.size

}