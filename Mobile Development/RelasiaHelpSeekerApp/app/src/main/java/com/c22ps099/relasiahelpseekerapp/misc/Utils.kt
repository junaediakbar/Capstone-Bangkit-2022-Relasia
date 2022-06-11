package com.c22ps099.relasiahelpseekerapp.misc

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.c22ps099.relasiahelpseekerapp.R
import com.google.android.material.snackbar.Snackbar
import java.io.*
import java.util.*

fun visibility(visible: Boolean): Int {
    return if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }

}

fun hideSoftKeyboard(activity: FragmentActivity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(
        activity.currentFocus?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}
fun showSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .show()
}

fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()

    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}

fun uriToFile(selectedImage: Uri, context: Context): File {
    val contentResolver = context.contentResolver
    val tempFile = createTempFile(context.toString())

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream = FileOutputStream(tempFile)
    val buf = ByteArray(1024)
    var len: Int

    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }

    outputStream.close()
    inputStream.close()

    return tempFile
}

fun reduceFileImage(file: File?, step: Int = 5): File? {
    val bitmap = BitmapFactory.decodeFile(file?.path)
    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= step
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}



fun showSuccessDialog(context: Context){
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_form_success)
    dialog.show()
}



