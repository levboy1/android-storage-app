package hu.bme.aut.android.storageapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "storageitem")
data class StorageItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "code") var code: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "imagePath") var imagePath: String? = null
) : Parcelable {
    @Ignore
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StorageItem

        if (name != other.name) return false
        if (code != other.code) return false
        if (description != other.description) return false
        if (price != other.price) return false

        return true
    }

    @Ignore
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price
        return result
    }
}