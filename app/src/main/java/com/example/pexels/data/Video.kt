package com.example.pexels.data

import com.google.gson.annotations.SerializedName

data class Video(

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("videos")
	val videos: ArrayList<VideosItem?>? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class VideoFilesItem(

	@field:SerializedName("file_type")
	val fileType: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("quality")
	val quality: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class VideosItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("video_files")
	val videoFiles: ArrayList<VideoFilesItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
