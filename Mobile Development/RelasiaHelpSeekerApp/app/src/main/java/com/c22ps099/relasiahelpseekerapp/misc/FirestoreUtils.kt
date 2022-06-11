package com.c22ps099.relasiahelpseekerapp.misc

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

object FirebaseUtils {

    suspend fun uploadPhotos(photosUri: ArrayList<Uri>): List<String> {
        val storageRef = FirebaseStorage.getInstance().getReference("images")
        val photosUrls = ArrayList<String>()
        val uploadedPhotosUriLink = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            (photosUri.indices).map { index ->
                async(Dispatchers.IO) {
                    uploadPhoto(storageRef, photosUri[index])
                }
            }
        }.awaitAll()

        uploadedPhotosUriLink.forEach { photoUriLink -> photosUrls.add(photoUriLink) }
        return photosUrls
    }


    private suspend fun uploadPhoto(storageRef: StorageReference, photoFile: Uri): String {
        val fileName = UUID.randomUUID().toString()

        return storageRef.child(fileName)
            .putFile(photoFile)
            .result.storage.downloadUrl.toString()
    }
}