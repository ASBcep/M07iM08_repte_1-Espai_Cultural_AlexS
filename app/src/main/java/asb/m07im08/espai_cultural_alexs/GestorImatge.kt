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
    fun eliminarImatges(id: String, context: Context): Boolean {
        var eliminades = false
        val imgElementPathPNGhr = context.filesDir.toString() + "/img/" + id + "hr" + ".png"
        val imgElementPathJPGhr = context.filesDir.toString() + "/img/" + id + "hr" + ".jpg"
        val imgElementPathPNGsr = context.filesDir.toString() + "/img/" + id + "sr" + ".png"
        val imgElementPathJPGsr = context.filesDir.toString() + "/img/" + id + "sr" + ".jpg"
        var arxiuImatge = File("")
        for (i in 1..4){
            if (i==1){
                arxiuImatge = File(imgElementPathPNGhr)
            } else if (i==2){
                arxiuImatge = File(imgElementPathJPGhr)
            } else if (i==3){
                arxiuImatge = File(imgElementPathPNGsr)
            } else if (i==4){
                arxiuImatge = File(imgElementPathJPGsr)
            }
            if (arxiuImatge.exists()) {
                eliminades = arxiuImatge.delete()
            }
        }
        return eliminades
    }
}