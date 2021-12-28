package ro.ubbcluj.cs.matei.individuals.todo.items

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_item_list.*
import ro.ubbcluj.cs.matei.individuals.R
import ro.ubbcluj.cs.matei.individuals.auth.data.AuthRepository
import ro.ubbcluj.cs.matei.individuals.core.TAG

class ItemListFragment : Fragment() {
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var itemsModel: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        if (!AuthRepository.isLoggedIn) {
            findNavController().navigate(R.id.fragment_login)
            return;
        }
        setupItemList()
        fab.setOnClickListener {
            Log.v(TAG, "add new item")
            findNavController().navigate(R.id.fragment_item_edit)
        }
    }

    private fun setupItemList() {
        itemListAdapter = ItemListAdapter(this)
        item_list.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        itemsModel.items.observe(viewLifecycleOwner, { items ->
            Log.v(TAG, "update items")
            itemListAdapter.items = items
        })
        itemsModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        })
        itemsModel.loadingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        itemsModel.refresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        revealAndHideView()
        changeViewPositionByObjectAnimator()
    }

    private fun changeViewPositionByObjectAnimator() {
        ObjectAnimator.ofFloat(view, "translationX", 200f).apply {
            duration = 5000
            start()
        }
    }

    private fun revealAndHideView() {
        button_reveal.setOnClickListener {
            text_title.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f)
                    .setDuration(5000)
                    .setListener(null)
            }
        }
        button_hide.setOnClickListener {
            text_title.apply {
                alpha = 1f
                visibility = View.VISIBLE
                animate()
                    .alpha(0f)
                    .setDuration(5000)
                    .setListener(null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}