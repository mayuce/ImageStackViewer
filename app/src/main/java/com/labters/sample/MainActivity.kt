package com.labters.sample

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.labters.imagestackviewer.data.ImageData
import com.labters.imagestackviewer.data.ResourceType
import com.labters.imagestackviewer.presentation.StackImageViewer
import com.labters.imagestackviewer.srcGlide

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TEST_URL =
            "https://cdn.vox-cdn.com/thumbor/eVoUeqwkKQ7MFjDCgrPrqJP5ztc=/0x0:2040x1360/1200x800/filters:focal(860x1034:1186x1360)/cdn.vox-cdn.com/uploads/chorus_image/image/59377089/wjoel_180413_1777_android_001.1523625143.jpg"

        private const val TEST_URL_2 =
            "https://cdn.vox-cdn.com/thumbor/Jmd2pjYeYUhw5jKRBDhbUBtoeVM=/0x0:2040x1360/1200x800/filters:focal(857x517:1183x843)/cdn.vox-cdn.com/uploads/chorus_image/image/59667263/wjoel_180413_1777_android_002.0.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.iv_image)
        val imageViewSecond = findViewById<ImageView>(R.id.iv_image_second)
        val imageViewThird = findViewById<ImageView>(R.id.iv_image_third)
        imageView.srcGlide(
            ResourceType.UrlResource(TEST_URL),
            false
        )
        imageViewSecond.srcGlide(
            ResourceType.AppResource(R.drawable.ic_launcher_foreground),
            false
        )
        imageViewThird.srcGlide(
            ResourceType.UrlResource(TEST_URL_2),
            false
        )
        imageView.setOnClickListener {
            StackImageViewer.openStackViewer(
                activity = this,
                list = listOf(
                    ImageData(ResourceType.UrlResource(TEST_URL)),
                    ImageData(ResourceType.AppResource(R.drawable.ic_launcher_foreground)),
                    ImageData(ResourceType.UrlResource(TEST_URL_2))
                ), selectedItemPose = 0, view = imageView
            )
        }

        imageViewSecond.setOnClickListener {
            StackImageViewer.openStackViewer(
                this,
                listOf(
                    ImageData(ResourceType.UrlResource(TEST_URL)),
                    ImageData(ResourceType.AppResource(R.drawable.ic_launcher_foreground)),
                    ImageData(ResourceType.UrlResource(TEST_URL_2))
                ), selectedItemPose = 1, view = imageViewSecond
            )
        }

        imageViewThird.setOnClickListener {
            StackImageViewer.openStackViewer(
                this,
                listOf(
                    ImageData(ResourceType.UrlResource(TEST_URL)),
                    ImageData(ResourceType.AppResource(R.drawable.ic_launcher_foreground)),
                    ImageData(ResourceType.UrlResource(TEST_URL_2))
                ), selectedItemPose = 2, view = imageViewThird
            )
        }
    }
}
