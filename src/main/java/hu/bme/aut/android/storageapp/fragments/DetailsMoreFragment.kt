package hu.bme.aut.android.storageapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.storageapp.R
import hu.bme.aut.android.storageapp.data.StorageItemHolder
import hu.bme.aut.android.storageapp.databinding.FragmentDetailsMoreBinding

class DetailsMoreFragment : Fragment() {
    private lateinit var binding: FragmentDetailsMoreBinding
    private var storageItemHolder: StorageItemHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageItemHolder = if (activity is StorageItemHolder) {
            activity as StorageItemHolder?
        } else {
            throw RuntimeException("Activity must implement StorageItemHolder interface!")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsMoreBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (storageItemHolder?.getStorageItem() != null) {
            showStorageItem()
        }
    }

    private fun showStorageItem() {
        val storageItem = storageItemHolder!!.getStorageItem()!!
        binding.detailTvName.text = storageItem.name
        binding.detailTvCode.text = storageItem.code
        binding.detailTvDescription.text = storageItem.description
        binding.detailTvPrice.text = "${storageItem.price} (£)"
    }
}