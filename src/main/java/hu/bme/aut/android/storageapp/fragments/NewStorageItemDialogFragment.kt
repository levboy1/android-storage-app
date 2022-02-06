package hu.bme.aut.android.storageapp.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.storageapp.helpers.Helpers
import hu.bme.aut.android.storageapp.R
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.databinding.DialogNewStorageItemBinding

class NewStorageItemDialogFragment : DialogFragment() {
    interface NewStorageItemDialogListener {
        fun onShoppingItemCreated(newItem: StorageItem)
    }

    private lateinit var listener: NewStorageItemDialogListener
    private lateinit var binding: DialogNewStorageItemBinding
    private var hasImageAttached = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewStorageItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewStorageItemBinding.inflate(LayoutInflater.from(context))

        binding.btAddImage.setOnClickListener { view ->
            val selectImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(selectImageIntent, REQUEST_CODE)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_storage_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onShoppingItemCreated(getStorageItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            val imageUri = data?.data
            binding.ivImage.setImageURI(imageUri)
            hasImageAttached = true
        }
    }

    private fun isValid(): Boolean {
        val isValid = binding.etName.text.isNotEmpty()
                && binding.etCode.text.isNotEmpty()
                && binding.etPrice.text.isNotEmpty()
        return isValid
    }

    private fun getStorageItem(): StorageItem {
        val item = StorageItem(
            name = binding.etName.text.toString(),
            code = binding.etCode.text.toString(),
            description = binding.etDescription.text.toString(),
            price = binding.etPrice.text.toString().toIntOrNull() ?: 0,
            imagePath = null
        )

        if(hasImageAttached) {
            val bitmap = binding.ivImage.drawable.toBitmap()
            val imagePath = saveBitmap(bitmap, item.hashCode().toString())
            item.imagePath = imagePath
        }

        return item
    }

    private fun saveBitmap(bitmap: Bitmap, name: String): String {
        val path = Helpers.saveToInternalStorage(bitmap, requireContext(), name, "jpg")
        return path
    }

    companion object {
        const val TAG = "NewStorageItemDialogFragment"
        private const val REQUEST_CODE = 101
    }
}