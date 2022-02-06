package hu.bme.aut.android.storageapp.data

import androidx.room.*

@Dao
interface StorageItemDao {
    @Query("SELECT * FROM storageitem")
    fun getAll(): List<StorageItem>

    @Insert
    fun insert(items: StorageItem): Long

    @Update
    fun update(items: StorageItem)

    @Delete
    fun deleteItem(items: StorageItem)
}