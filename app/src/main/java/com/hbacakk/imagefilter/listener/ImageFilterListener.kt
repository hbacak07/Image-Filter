package com.hbacakk.imagefilter.listener

import com.hbacakk.imagefilter.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}