package com.pixel_ninja.vet_track.partials.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

fun Uri.toBitmap(context: Context): Bitmap? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this)
    return BitmapFactory.decodeStream(inputStream)
}