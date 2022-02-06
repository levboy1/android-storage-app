package hu.bme.aut.android.storageapp.helpers

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Helpers {
    companion object {
        fun saveToInternalStorage(bitmap: Bitmap, context: Context, imageName: String, fileFormat: String): String {
            var contextWrapper: ContextWrapper = ContextWrapper(context)
            val directory = contextWrapper.getDir("images", Context.MODE_PRIVATE)
            val path = File(directory, "${imageName}.${fileFormat}")
            val fileOutputStream = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            return directory.absolutePath
        }

        fun loadImageFromStorage(absolutePath: String, imageName: String, fileFormat: String): Bitmap {
            val file = File(absolutePath, "${imageName}.${fileFormat}")
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))

            return bitmap
        }
    }
}