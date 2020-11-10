package com.rollncode.test.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rollncode.test.R
import com.rollncode.test.ui.MainActivity
import com.rollncode.test.ui.main.adapter.PhotosAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        subscribeOnViewModel()
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener { viewModel.fetchAllPhotos() }
        initAdapter()

        viewModel.getAllPhotos()
    }

    private fun initAdapter() {
        val photosAdapter = PhotosAdapter { url -> (activity as? MainActivity)?.openFullSizeImage(url) }
        photosRecycleView.adapter = photosAdapter
    }

    private fun subscribeOnViewModel() {
        viewModel.isInProgress.observe(viewLifecycleOwner, { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
            photosRecycleView.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        })
        viewModel.photosLiveData.observe(viewLifecycleOwner,
            { photos -> (photosRecycleView.adapter as PhotosAdapter).submitData(photos) })
    }

}