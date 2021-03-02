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
    lateinit var thisProject: Project
    var itemsAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedProject = intent.getIntExtra("projectIndex", 0)
        thisProject = AppData.projects[selectedProject]

        binding.toolbarTitle.text = thisProject.name

        // RecyclerView
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(thisProject, this)
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
                    thisProject.items.add(item)
                    itemsAdapter!!.notifyItemInserted(thisProject.items.count())
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
        thisProject.items[index].completed = !thisProject.items[index].completed
        itemsAdapter!!.notifyDataSetChanged()
    }

    override fun itemLongClick(index: Int) {
        thisProject.items.removeAt(index)
        itemsAdapter!!.notifyItemRemoved(index)
    }
}