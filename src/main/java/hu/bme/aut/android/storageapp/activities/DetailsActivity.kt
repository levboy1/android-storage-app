package hu.bme.aut.android.storageapp.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.storageapp.R
import hu.bme.aut.android.storageapp.adapter.DetailsPagerAdapter
import hu.bme.aut.android.storageapp.data.StorageItemHolder
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity(), StorageItemHolder {
    private lateinit var binding: ActivityDetailsBinding
    private var storageItem: StorageItem? = null

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA = "extra.storage_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageItem = intent.getParcelableExtra(EXTRA)
        supportActionBar?.title = storageItem?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.main)
                1 -> getString(R.string.details)
                else -> ""
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getStorageItem(): StorageItem? {
        return storageItem
    }
}