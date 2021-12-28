package ro.ubbcluj.cs.matei.individuals.todo.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_item.view.*
import ro.ubbcluj.cs.matei.individuals.R
import ro.ubbcluj.cs.matei.individuals.core.TAG
import ro.ubbcluj.cs.matei.individuals.todo.data.Movie
import ro.ubbcluj.cs.matei.individuals.todo.item.ItemEditFragment

class ItemListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var items = emptyList<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener;

    init {
        onItemClick = View.OnClickListener { view ->
            val item = view.tag as Movie
            fragment.findNavController().navigate(R.id.fragment_item_edit, Bundle().apply {
                putString(ItemEditFragment.ITEM_ID, item._id)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val item = items[position]
        holder.itemView.tag = item
        holder.textView.text = item.title
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.text
    }
}
