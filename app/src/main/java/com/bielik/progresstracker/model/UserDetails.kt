package com.bielik.progresstracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    var userName: String? = null,
    var userImage: String? = null
) : Parcelable
