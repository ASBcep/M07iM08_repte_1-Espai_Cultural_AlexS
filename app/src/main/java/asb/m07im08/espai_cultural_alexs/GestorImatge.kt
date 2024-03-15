package asb.m07im08.espai_cultural_alexs

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.File

object GestorImatge {
    fun inserirImatgeSR(imatge: String, context: Context, imageView: ImageView) {
        val imgElementPathPNG = context.filesDir.toString() + "/img/" + "sr" + imatge + ".png"
        val imgElementPathJPG = context.filesDir.toString() + "/img/" + "sr" + imatge + ".jpg"
        val bitmap = if (File(imgElementPathPNG).exists()) {
            BitmapFactory.decodeFile(imgElementPathPNG)
        } else if (File(imgElementPathJPG).exists()) {
            BitmapFactory.decodeFile(imgElementPathJPG)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.noimg_sr)
        }
        imageView.setImageBitmap(bitmap)
    }
    fun inserirImatgeHR(imatge: String, context: Context, imageView: ImageView) {
        val imgElementPathPNG = context.filesDir.toString() + "/img/" + "hr" + imatge + ".png"
        val imgElementPathJPG = context.filesDir.toString() + "/img/" + "hr" + imatge + ".jpg"
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