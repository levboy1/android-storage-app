package hu.bme.aut.android.storageapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.storageapp.helpers.Helpers
import hu.bme.aut.android.storageapp.data.StorageItemHolder
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.databinding.FragmentDetailsMainBinding

class DetailsMainFragment : Fragment() {
    private lateinit var binding: FragmentDetailsMainBinding
    private var storageItemHolder: StorageItemHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageItemHolder = if (activity is StorageItemHolder) {
            activity as StorageItemHolder?
        } else {
            throw RuntimeException(
                "Activity must implement StorageItemHolder interface!"
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentDetailsMainBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (storageItemHolder!!.getStorageItem() != null) {
            displayStorageItem()
        }
    }

    private fun displayStorageItem() {
        var storageItem: StorageItem?  = storageItemHolder!!.getStorageItem()
        binding.tvMain.text = storageItem!!.name

        if(storageItem.imagePath != null) {
            val bitmap = Helpers.loadImageFromStorage(storageItem.imagePath!!, storageItem.hashCode().toString(), "jpg")
            binding.ivIcon.setImageBitmap(bitmap)
        }
    }
}