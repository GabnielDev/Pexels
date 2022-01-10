package com.example.pexels.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pexels.adapter.VideoGridAdapter
import com.example.pexels.adapter.VideoListAdapter
import com.example.pexels.data.VideosItem
import com.example.pexels.databinding.FragmentVideoBinding
import com.example.pexels.view.VideoDetailActivity
import com.example.pexels.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : Fragment(), VideoGridAdapter.OnItemClickCallback,
    VideoListAdapter.OnItemClickCallback {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoGridAdapter: VideoGridAdapter
    private lateinit var videoListAdapter: VideoListAdapter
    private lateinit var videoViewModel: VideoViewModel

    private var data: MutableList<VideosItem?> = ArrayList()
    private var page = 1
    private var nextPage = true
    private var isEmpty = true
    private var loading = false

    private var isGrid = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val view = binding.root

        setAdapter()
        setUpViewModel()
        getData()
        setClick()
        searchVideo()

        return view
    }

    private fun getData() {
        getLoading()
        showImageVideo()
    }

    private fun setClick() {
        binding.imgList.setOnClickListener {
            isGrid = false
            binding.rvVideo.apply {
                adapter = videoListAdapter
                layoutManager = LinearLayoutManager(context)
                videoListAdapter.setData(data)
            }
        }

        binding.imgGrid.setOnClickListener {
            isGrid = true
            binding.rvVideo.apply {
                adapter = videoGridAdapter
                layoutManager = GridLayoutManager(context, 2)
                videoGridAdapter.setData(data)
            }
        }
    }

    private fun setUpViewModel() {
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
    }

    private fun getLoading() {
        videoViewModel.getLoading().observe(viewLifecycleOwner, {
            loading = it
            if (loading) binding.progressCircular.visibility = VISIBLE
            else binding.progressCircular.visibility = GONE
        })

        videoViewModel.getStatus().observe(viewLifecycleOwner, {
            if (it >= 400) binding.lineNodata.visibility = GONE
        })

        videoViewModel.getMessage().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = VISIBLE
            binding.rvVideo.visibility = GONE
        })

        videoViewModel.getMessage().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = VISIBLE
            binding.rvVideo.visibility = GONE

            if (it.isNullOrEmpty()) binding.lineNodata.visibility = GONE
        })

        binding.rvVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                if (countItem.minus(1) == linearLayoutManager.findLastVisibleItemPosition()) {
                    if (!loading && nextPage) {
                        page++
                        getData()
                    }
                }
            }
        })
    }

    private fun setAdapter() {
        videoListAdapter = VideoListAdapter(ArrayList(), this)
        binding.rvVideo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoListAdapter
        }

        videoGridAdapter = VideoGridAdapter(ArrayList(), this)
        binding.rvVideo.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = videoGridAdapter
        }
    }


    private fun showImageVideo() {
        videoViewModel.getVideo(page).observe(viewLifecycleOwner, {
            nextPage = !it.isNullOrEmpty()
            if (page == 0) {
                data = it
                isEmpty = it.isEmpty()
                binding.lineNodata.visibility = VISIBLE
                Toast.makeText(context, "$isEmpty", Toast.LENGTH_SHORT).show()
            } else {
                binding.lineNodata.visibility = GONE
                binding.rvVideo.visibility = VISIBLE
                data.addAll(it)
            }
            if (isGrid) videoGridAdapter.setData(data)
            else videoListAdapter.setData(data)
            Log.e("videoData", "$it")
        })
    }

    private fun searchVideo() {
        binding.edtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                try {
                    videoViewModel.getSearchVideo(p0.toString(), page).observe(viewLifecycleOwner, {
                        videoGridAdapter.setData(it)
                        videoListAdapter.setData(it)
                        Log.e("searchVideo", "$it")
                    })
                } catch (e: Exception) {
                    e.message
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                return false
            }


        })
    }

    override fun onItemClick(data: VideosItem) {
        startActivity(
            Intent(context, VideoDetailActivity::class.java)
                .putExtra(VideoDetailActivity.VIDEO, data.videoFiles?.get(1)?.link)
                .putExtra(VideoDetailActivity.AUTHOR, data.user?.name)
        )
    }


}