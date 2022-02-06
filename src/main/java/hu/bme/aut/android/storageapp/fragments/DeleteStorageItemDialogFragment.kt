package hu.bme.aut.android.storageapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.storageapp.R
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.databinding.DialogDeleteStorageItemBinding

class DeleteStorageItemDialogFragment(private var item: StorageItem) : DialogFragment() {
    interface DeleteStorageItemDialogListener {
        fun onDeleteStorageItem(item: StorageItem)
    }

    private lateinit var listener: DeleteStorageItemDialogListener
    private lateinit var binding: DialogDeleteStorageItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? DeleteStorageItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDeleteStorageItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_storage_item_dialog_title)
            .setView(binding.root)
            .setPositiveButton(R.string.button_yes) { dialogInterface, i ->
                listener.onDeleteStorageItem(item)
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "DeleteStorageItemDialogFragment"
    }
}