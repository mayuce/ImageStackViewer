package com.labters.imagestackviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.labters.imagestackviewer.data.ResourceType
import java.io.ByteArrayOutputStream

object ImageViewExtensions {

    private const val ALPHA = 180

    /**
     * Changes the background of the image when resource is ready.
     */
    fun onImageReady(imageView: ImageView, resource: Drawable?) {
        val bitmap = resource?.toBitmap()
        bitmap?.let {
            (imageView.parent as? LinearLayout)?.let {
                Palette.Builder(bitmap).generate { palette ->
                    it.setBackgroundColor(
                        ColorUtils.setAlphaComponent(palette?.getDominantColor(0) ?: 0, ALPHA)
                    )
                }
            }
        }
    }
}

@BindingAdapter(value = ["app:src", "app:isCenterCrop"], requireAll = false)
fun ImageView.srcGlide(data: Any?, isCenterCrop: Boolean?) {

    if (data == null) {
        setImageBitmap(null)
        return
    }

    (data as? ResourceType.Base64Resource)?.let {
        setImageBitmap(it.resource?.toBitmap())
        return
    }

    var options = RequestOptions()

    if (isCenterCrop != false) {
        options = options.centerCrop()
    }

    val resource = (data as? ResourceType.AppResource)?.resource
        ?: (data as? ResourceType.UrlResource)?.resource

    resource?.let {
        Glide
            .with(context)
            .load(resource)
            .apply(options)
            .addListener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    ImageViewExtensions.onImageReady(this@srcGlide, resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

            })
            .into(this)
    }
}

fun String.toBitmap(): Bitmap {
    val decodedBytes: ByteArray = Base64.decode(
        this.substring(this.indexOf(",") + 1),
        Base64.DEFAULT
    )
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun Bitmap.toBase64(format: Bitmap.CompressFormat? = null, quality: Int = 80): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(format ?: Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
}

fun ImageView.getCompressedBase64(quality: Int = 80) =
    drawable.toBitmap().toBase64(quality = quality)