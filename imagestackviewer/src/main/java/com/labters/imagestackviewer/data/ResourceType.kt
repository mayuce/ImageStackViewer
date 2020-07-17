package com.labters.imagestackviewer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Defines resource types
 * You may define new types
 */
sealed class ResourceType : Parcelable {
    /**
     * @param resource is resource which is app context has it
     */
    @Parcelize
    class AppResource(val resource: Int?) : ResourceType(), Parcelable

    /**
     * @param resource is resource which is String types like URL
     */
    @Parcelize
    class UrlResource(val resource: String?) : ResourceType(), Parcelable

    /**
     * @param resource is resource which is Base64
     */
    @Parcelize
    class Base64Resource(val resource: String?) : ResourceType(), Parcelable
}