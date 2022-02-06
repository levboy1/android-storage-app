package hu.bme.aut.android.storageapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.storageapp.R
import hu.bme.aut.android.storageapp.helpers.Helpers
import hu.bme.aut.android.storageapp.data.StorageItem
import hu.bme.aut.android.storageapp.databinding.ItemStorageListBinding

class StorageAdapter(private val listener: ShoppingItemClickListener) :
    RecyclerView.Adapter<StorageAdapter.StorageItemViewHolder>() {

    private val items = mutableListOf<StorageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StorageItemViewHolder(
        ItemStorageListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StorageItemViewHolder, position: Int) {
        val storageItem = items[position]

        if(storageItem.imagePath != null) {
            val bitmap = Helpers.loadImageFromStorage(storageItem.imagePath!!, storageItem.hashCode().toString(), "jpg")
            holder.binding.ivIcon.setImageBitmap(bitmap)
        }
        else {
            holder.binding.ivIcon.setImageResource(R.drawable.image_placeholder)
        }

        holder.binding.tvName.text = storageItem.name
        holder.binding.tvPrice.text = "${storageItem.price} (£)"

        holder.binding.ibRemove.setOnClickListener { buttonView ->
            listener.onItemDeleteClicked(storageItem)
        }

        holder.binding.ibDetails.setOnClickListener { buttonView ->
            listener.onItemDetailsClicked(storageItem)
        }
    }

    fun addItem(item: StorageItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<StorageItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun removeItem(item: StorageItem) {
        items.remove(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface ShoppingItemClickListener {
        fun onItemChanged(item: StorageItem)
        fun onItemDeleteClicked(item: StorageItem)
        fun onItemDetailsClicked(item: StorageItem)
    }

    inner class StorageItemViewHolder(val binding: ItemStorageListBinding) : RecyclerView.ViewHolder(binding.root)
}