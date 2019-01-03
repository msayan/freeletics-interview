package com.hololo.app.squarerepo.service.response

import com.google.gson.annotations.SerializedName

data class Permissions(

	@field:SerializedName("pull")
	val pull: Boolean? = null,

	@field:SerializedName("admin")
	val admin: Boolean? = null,

	@field:SerializedName("push")
	val push: Boolean? = null
)