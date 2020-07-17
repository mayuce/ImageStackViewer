package com.labters.imagestackviewer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StackViewerData(
    val imageList: List<ImageData>,
    val dummyImage: ResourceType? = null,
    val selectedItemPosition: Int = 0
) : Parcelable