package com.labters.imagestackviewer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData(val image: ResourceType? = null) : Parcelable