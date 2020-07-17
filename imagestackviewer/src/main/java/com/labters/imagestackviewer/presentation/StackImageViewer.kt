package com.labters.imagestackviewer.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.labters.imagestackviewer.R
import com.labters.imagestackviewer.data.ImageData
import com.labters.imagestackviewer.data.ResourceType
import com.labters.imagestackviewer.data.StackViewerData
import com.labters.imagestackviewer.databinding.ActivityStackViewerBinding
import com.labters.imagestackviewer.getCompressedBase64
import com.labters.imagestackviewer.helper.PullToFinishLayout
import com.labters.imagestackviewer.srcGlide

class StackImageViewer : AppCompatActivity(), PullToFinishLayout.Callback {

    companion object {
        private const val KEY_IMAGE_LIST = "ImageList"

        private fun createStack(context: Context, data: StackViewerData) =
            Intent(context, StackImageViewer::class.java).apply {
                putExtra(KEY_IMAGE_LIST, data)
            }

        fun openStackViewer(
            activity: Activity,
            list: List<ImageData>,
            selectedItemPose: Int = 0,
            view: ImageView? = null
        ) {
            createStack(
                activity,
                StackViewerData(
                    list,
                    ResourceType.Base64Resource(view?.getCompressedBase64()),
                    selectedItemPose
                )
            ).run {
                val optionsCompat = view?.let {
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        androidx.core.util.Pair(
                            view,
                            activity.getString(R.string.imageTransition)
                        )
                    )
                }
                activity.startActivity(this, optionsCompat?.toBundle())
            }
        }
    }

    private lateinit var binding: ActivityStackViewerBinding
    private val adapter = StackViewerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stack_viewer)
        parseBundle()
        createUI()
    }

    private fun parseBundle() {
        intent.extras?.getParcelable<StackViewerData>(KEY_IMAGE_LIST)?.let {
            binding.ivImage.srcGlide(it.dummyImage, false)
            adapter.submitList(it.imageList)
            binding.viewPager.adapter = adapter
            binding.viewPager.setCurrentItem(it.selectedItemPosition, false)
        }
    }

    private fun createUI() {
        binding.pullView.setCallback(this)
        binding.ivImage.animate().alpha(0f).setDuration(300L).setStartDelay(300L).start()
        binding.viewPager.animate().alpha(1f).setDuration(100L).setStartDelay(300L).start()
    }

    override fun onPullStart() {

    }

    override fun onPull(progress: Float) {
        binding.background.alpha = 1f - progress
    }

    override fun onPullCancel() {
        binding.background.animate().alpha(1f).start()
    }

    override fun onPullComplete() {
        binding.ivImage.animate().alpha(1f).setDuration(0L).setStartDelay(0L)
            .withEndAction {
                finishActivity()
            }.start()
    }

    override fun onBackPressed() {
        binding.ivImage.animate().alpha(1f).setDuration(0L).setStartDelay(0L)
            .withEndAction {
                finishActivity()
            }.start()
    }

    private fun finishActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
    }
}