package com.rollncode.test.ui.full

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rollncode.test.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.full_size_fragment.*

private const val IMAGE_URL = "IMAGE_URL"

class FullSizeFragment : Fragment() {

    companion object {
        fun newInstance(imageUrl: String) = FullSizeFragment().apply {
            val args = Bundle()
            args.putString(IMAGE_URL, imageUrl)
            this.arguments = args
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.full_size_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            displayFullSizeImage()
        } else {
            restorePreviousState(savedInstanceState)
        }
    }

    private fun restorePreviousState(savedInstanceState: Bundle) {
        savedInstanceState.getString(IMAGE_URL)?.let { imageUrl ->
            displayImage(imageUrl)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getImageUrl()?.let { imageUrl ->
            outState.putString(IMAGE_URL, imageUrl)
        }
    }

    private fun displayFullSizeImage() {
        getImageUrl()?.let { imageUrl ->
            displayImage(imageUrl)
        }
    }

    private fun displayImage(imageUrl: String) {
        Picasso.get().load(imageUrl).error(R.drawable.ic_launcher_foreground).into(fullImage)
    }

    private fun getImageUrl(): String? = arguments?.getString(IMAGE_URL)
}