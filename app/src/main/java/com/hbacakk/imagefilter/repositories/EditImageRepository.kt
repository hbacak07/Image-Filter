package com.hbacakk.imagefilter.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.hbacakk.imagefilter.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
}