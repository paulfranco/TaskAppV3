package co.paulfran.taskappv3

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.taskappv3.R
import co.paulfran.taskappv3.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var binding: ActivityItemBinding
    lateinit var thisProjectWithItems: ProjectWithItems
    var itemsAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedProject = intent.getIntExtra("projectIndex", 0)
        thisProjectWithItems = AppData.projects[selectedProject]

        binding.toolbarTitle.text = thisProjectWithItems.project.name

        // RecyclerView
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(thisProjectWithItems, this)
        binding.itemsRecyclerView.adapter = itemsAdapter

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        // ClickListener on Add New Item EditText
        binding.newItemEt.setOnKeyListener { v, keyCode, event ->

            if (keyCode ==KeyEvent.KEYCODE_ENTER) {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    val name: String = binding.newItemEt.text.toString()
                    val item = Item(name, false)
                    thisProjectWithItems.items.add(item)
                    itemsAdapter!!.notifyItemInserted(thisProjectWithItems.items.count())
                    binding.newItemEt.text.clear()

                    // Hide Keyboard
                    val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
            false
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun itemClick(index: Int) {
        thisProjectWithItems.items[index].completed = !thisProjectWithItems.items[index].completed
        itemsAdapter!!.notifyDataSetChanged()
    }

    override fun itemLongClick(index: Int) {
        thisProjectWithItems.items.removeAt(index)
        itemsAdapter!!.notifyItemRemoved(index)
    }
}