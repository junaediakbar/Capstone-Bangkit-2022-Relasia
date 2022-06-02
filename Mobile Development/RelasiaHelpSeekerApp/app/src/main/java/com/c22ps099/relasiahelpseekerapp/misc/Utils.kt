package com.c22ps099.relasiahelpseekerapp.misc

import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.Window
import android.widget.Toast
import com.c22ps099.relasiahelpseekerapp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun visibility(visible: Boolean): Int {
    return if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }

}
fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .show()
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}
fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
private const val FILENAME_FORMAT = "yyyy-MMM-dd-[hh:mm:ss]"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())



fun showSuccessDialog(context: Context){
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_form_success)
    dialog.show()
}



