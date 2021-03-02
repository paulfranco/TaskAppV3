package co.paulfran.taskappv3

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.taskappv3.R
import co.paulfran.taskappv3.databinding.ActivityItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    val item = Items(name, thisProjectWithItems.project.name,false)
                    thisProjectWithItems.items.add(item)

                    // Add to Room
                    CoroutineScope(Dispatchers.IO). launch {
                        AppData.db.projectDao().insertItem(item)
                    }

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
        val item = thisProjectWithItems.items[index]
        item.completed = !(item.completed)
        val projectName = item.projectName
        val itemName = item.name

        // Update Item in Room
        CoroutineScope(Dispatchers.IO).launch {
            AppData.db.projectDao().updateItem(projectName, itemName, item.completed)
        }
        
        itemsAdapter!!.notifyDataSetChanged()
    }

    override fun itemLongClick(index: Int) {
        val projectName = thisProjectWithItems.project.name
        val itemName = thisProjectWithItems.items[index].name

        // Delete from Room
        CoroutineScope(Dispatchers.IO).launch {
            AppData.db.projectDao().deleteItem(projectName, itemName)
        }

        thisProjectWithItems.items.removeAt(index)
        itemsAdapter!!.notifyItemRemoved(index)
    }
}