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
    fun obrirImatge(activity: Activity, highRes: Boolean, nom: String) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra("highRes", highRes)
        intent.putExtra("nom", nom)
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, context: Context) {
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            val highRes = data?.getBooleanExtra("highRes", false) ?: false
            val nom = data?.getStringExtra("nom") ?: ""
            uri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    val file = File(context.filesDir, "img")
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                    val fileExtension = getFileExtension(context, uri)
                    val prefix = if (highRes) "hr" else "sr"
                    val fileNameWithRes = "${nom}_${prefix}.${fileExtension}"
                    val imgElementPath = "${file.absolutePath}/$fileNameWithRes"
                    val outputStream = FileOutputStream(imgElementPath)
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }
    fun onActivityResultOLD(requestCode: Int, resultCode: Int, data: Intent?, context: Context) {
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            val highRes = data?.getBooleanExtra("highRes", false) ?: false
            val nom = data?.getStringExtra("nom") ?: ""
            uri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    val file = File(context.filesDir, "img")
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                    val fileExtension = getFileExtension(context, uri)
                    val fileNameWithRes = "${if (highRes) "hr" else "sr"}$nom.${fileExtension}"
                    val imgElementPath = "${file.absolutePath}/$fileNameWithRes"
                    val outputStream = FileOutputStream(imgElementPath)
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(context.contentResolver.getType(uri))
    }
}