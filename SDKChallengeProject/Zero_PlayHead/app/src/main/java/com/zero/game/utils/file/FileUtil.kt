package com.zero.game.utils.file

import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.view.ViewGroup
import com.zero.game.BaseApp
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat


object FileUtil {


    fun getBitmapByView(viewGroup: ViewGroup): Bitmap? {
        val bitmap: Bitmap = Bitmap.createBitmap(
            viewGroup.width, viewGroup.height,
            Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        viewGroup.draw(canvas)
        return bitmap
    }

    fun saveBitmap(bitmap: Bitmap?, listener: IOnSaveBitmapListener) {
        if (bitmap == null) {
            return
        }
        val paths = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM)
        if (!paths.exists()) {
            paths.mkdirs()
        }


        val file = File(paths, getFileNameDateFormat() + ".jpg")
        try {
            val fileOutputStream = FileOutputStream(file.absolutePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            mediaCenterScanFile(file.absolutePath)
            listener.onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            listener.onError()
        } finally {
            bitmap.recycle()
        }

    }

    fun mediaCenterScanFile(mediaFilePath: String) {
        try {
            MediaScannerConnection.scanFile(
                BaseApp.instance,
                arrayOf(mediaFilePath),
                null,
                null
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getFileNameDateFormat(): String? {
        val timestamp = Timestamp(System.currentTimeMillis())
        return SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(timestamp)
    }

    interface IOnSaveBitmapListener {
        fun onSuccess()
        fun onError()
    }


}