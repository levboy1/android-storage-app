package hu.bme.aut.android.storageapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.storageapp.adapter.StorageAdapter
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.data.StorageItemDatabase
import hu.bme.aut.android.storageapp.databinding.ActivityMainBinding
import hu.bme.aut.android.storageapp.fragments.DeleteStorageItemDialogFragment
import hu.bme.aut.android.storageapp.fragments.NewStorageItemDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), StorageAdapter.ShoppingItemClickListener,
    NewStorageItemDialogFragment.NewStorageItemDialogListener,
    DeleteStorageItemDialogFragment.DeleteStorageItemDialogListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: StorageItemDatabase
    private lateinit var adapter: StorageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = StorageItemDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewStorageItemDialogFragment().show(
                supportFragmentManager,
                NewStorageItemDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = StorageAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.storageItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: StorageItem) {
        thread {
            database.storageItemDao().update(item)
            Log.d("MainActivity", "StorageItem update was successful")
        }
    }

    override fun onItemDeleteClicked(item: StorageItem) {
        DeleteStorageItemDialogFragment(item).show(
            supportFragmentManager,
            DeleteStorageItemDialogFragment.TAG
        )
    }

    override fun onItemDetailsClicked(item: StorageItem) {
        val showDetailsIntent = Intent()
        showDetailsIntent.setClass(this@MainActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA, item)
        startActivity(showDetailsIntent)
    }

    override fun onDeleteStorageItem(item: StorageItem) {
        thread {
            database.storageItemDao().deleteItem(item)

            runOnUiThread {
                adapter.removeItem(item)
            }
        }
    }

    override fun onShoppingItemCreated(newItem: StorageItem) {
        thread {
            val insertId = database.storageItemDao().insert(newItem)
            newItem.id = insertId

            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}