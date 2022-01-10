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
import com.example.pexels.adapter.ImageGridAdapter
import com.example.pexels.adapter.ImageListAdapter
import com.example.pexels.data.PhotosItem
import com.example.pexels.databinding.FragmentImageBinding
import com.example.pexels.view.ImageDetailActivity
import com.example.pexels.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : Fragment(), ImageGridAdapter.OnItemClickCallback,
    ImageListAdapter.OnItemClickCallback {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageGridAdapter: ImageGridAdapter
    private lateinit var imageListAdapter: ImageListAdapter
    private lateinit var imageViewModel : ImageViewModel

    private var data: MutableList<PhotosItem?> = ArrayList()
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

        _binding = FragmentImageBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpViewModel()
        setAdapter()
        getData()
        setClick()
        searchImage()


        return view
    }

    private fun getData() {
        getLoading()
        showImage()

    }

    private fun setClick() {
        binding.imgList.setOnClickListener {
            isGrid = false
            binding.rvImage.apply {
                adapter = imageListAdapter
                layoutManager = LinearLayoutManager(context)
                imageListAdapter.setData(data)
            }
        }

        binding.imgGrid.setOnClickListener {
            isGrid = true
            binding.rvImage.apply {
                adapter = imageGridAdapter
                layoutManager = GridLayoutManager(context, 2)
                imageGridAdapter.setData(data)
            }
        }
    }


    private fun setUpViewModel() {
        imageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

    }

    private fun getLoading() {
        imageViewModel.getLoading().observe(viewLifecycleOwner, {
            loading = it
            if (loading) binding.progressCircular.visibility = VISIBLE
            else binding.progressCircular.visibility = GONE
        })

        imageViewModel.getStatus().observe(viewLifecycleOwner, {
            if (it >= 400) binding.lineNodata.visibility = GONE
        })

        imageViewModel.getMessage().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = VISIBLE
            binding.rvImage.visibility = GONE

            if (it.isNullOrEmpty()) binding.lineNodata.visibility = GONE
        })

        binding.rvImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        imageListAdapter = ImageListAdapter(ArrayList(), this)
        binding.rvImage.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = imageListAdapter

        }
        imageGridAdapter = ImageGridAdapter(ArrayList(), this)
        binding.rvImage.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = imageGridAdapter
        }

    }

    private fun showImage() {
        imageViewModel.getImage(page).observe(viewLifecycleOwner, {
            nextPage = !it.isNullOrEmpty()
            if (page == 0) {
                data = it!!
                isEmpty = it!!.isEmpty()
                binding.lineNodata.visibility = VISIBLE
                Toast.makeText(context, "$isEmpty", Toast.LENGTH_SHORT).show()
            } else {
                binding.lineNodata.visibility = GONE
                binding.rvImage.visibility = VISIBLE
                data.addAll(it!!)
            }

            if (isGrid) imageGridAdapter.setData(data)
            else imageListAdapter.setData(data)
            Log.e("imageData", "$it")
        })
    }

    private fun searchImage() {
       binding.edtSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
       androidx.appcompat.widget.SearchView.OnQueryTextListener {
           override fun onQueryTextSubmit(p0: String?): Boolean {
               try {
                   imageViewModel.getSearchImage(p0.toString(), page).observe(viewLifecycleOwner, {
                       imageGridAdapter.setData(it!!)

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

    override fun onItemClick(data: PhotosItem) {
        startActivity(
            Intent(context, ImageDetailActivity::class.java)
                .putExtra(ImageDetailActivity.IMAGE, data.src?.original)
                .putExtra(ImageDetailActivity.AUTHOR, data.photographer)
        )
    }
}