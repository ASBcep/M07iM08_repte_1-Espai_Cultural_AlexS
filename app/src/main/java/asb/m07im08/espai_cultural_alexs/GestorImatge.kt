package asb.m07im08.espai_cultural_alexs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult
import java.io.File
import java.io.FileOutputStream


object GestorImatge {
    private const val PICK_IMAGE_REQUEST_CODE = 1

    fun inserirImatgeSR(id: String, context: Context, imageView: ImageView) {
        val imgElementPathPNG = context.filesDir.toString() + "/img/" + id + "sr" + ".png"
        val imgElementPathJPG = context.filesDir.toString() + "/img/" + id + "sr" + ".jpg"
        val bitmap = if (File(imgElementPathPNG).exists()) {
            BitmapFactory.decodeFile(imgElementPathPNG)
        } else if (File(imgElementPathJPG).exists()) {
            BitmapFactory.decodeFile(imgElementPathJPG)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.noimg_sr)
        }
        imageView.setImageBitmap(bitmap)
    }
    fun inserirImatgeHR(id: String, context: Context, imageView: ImageView) {
        val imgElementPathPNG = context.filesDir.toString() + "/img/" + id + "hr" + ".png"
        val imgElementPathJPG = context.filesDir.toString() + "/img/" + id + "hr" + ".jpg"
        val bitmap = if (File(imgElementPathPNG).exists()) {
            BitmapFactory.decodeFile(imgElementPathPNG)
        } else if (File(imgElementPathJPG).exists()) {
            BitmapFactory.decodeFile(imgElementPathJPG)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.noimg_hr)
        }
        imageView.setImageBitmap(bitmap)
    }

}