package com.hbacakk.imagefilter.activities.editimage

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.hbacakk.imagefilter.activities.main.MainActivity
import com.hbacakk.imagefilter.adapters.ImageFilterAdapter
import com.hbacakk.imagefilter.data.ImageFilter
import com.hbacakk.imagefilter.databinding.ActivityEditImageBinding
import com.hbacakk.imagefilter.listener.ImageFilterListener
import com.hbacakk.imagefilter.utilities.displayToast
import com.hbacakk.imagefilter.utilities.show
import com.hbacakk.imagefilter.viewmodels.EditImageViewModels
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(),ImageFilterListener {

    private lateinit var binding: ActivityEditImageBinding
    private lateinit var gpuImage:GPUImage

    private lateinit var originalBitmap: Bitmap
    private  val filteredBitmap=MutableLiveData<Bitmap>()

    private val viewModel: EditImageViewModels by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        setupObservers()
        prepareImagePreview()
    }

    private fun prepareImagePreview() {
        gpuImage= GPUImage(applicationContext)
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URL)?.let {imageUri->
            viewModel.prepareImagePreview(imageUri)
        }
    }

    /*private fun disPlayImageViewPreview() {
        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URL)?.let {
            imageUri->
            val inputStream=contentResolver.openInputStream(imageUri)
            val bitmap=BitmapFactory.decodeStream(inputStream)
            binding.imageEditPreview.setImageBitmap(bitmap)
            binding.imageEditPreview.visibility=View.VISIBLE
        }
    }*/

    private fun setListener(){
        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }
        binding.imageEditPreview.setOnLongClickListener {
            binding.imageEditPreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }
        binding.imageEditPreview.setOnClickListener {
            binding.imageEditPreview.setImageBitmap(filteredBitmap.value)
        }
    }
    private fun setupObservers() {
        viewModel.imagePreviewUiState.observe(this) {
            val dataState = it ?: return@observe
            binding.previewProgressBar.visibility =
                if (dataState.isloading) View.VISIBLE else View.GONE
            dataState.bitmap?.let { bitmap ->
                originalBitmap=bitmap
                filteredBitmap.value=bitmap
                with(originalBitmap){
                    gpuImage.setImage(this)
                    binding.imageEditPreview.show()
                    viewModel.loadImageFilters(bitmap)
                }
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        }
        viewModel.imageFiltersUiState.observe(this){
            val imageFiltersDataState=it?:return@observe
            binding.progresBarFilters.visibility=
                if (imageFiltersDataState.isloading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let {
                imageFilters->
                ImageFilterAdapter(imageFilters,this).also {adapter->
                    binding.recylerViewFilter.adapter=adapter
                }
            }?: kotlin.run {
                imageFiltersDataState.error?.let { error->
                    displayToast(error)
                }
            }
        }
        filteredBitmap.observe(this){
            bitmap->
                binding.imageEditPreview.setImageBitmap(bitmap)

        }
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter){
            with(gpuImage){
                setFilter(filter)
                filteredBitmap.value=bitmapWithFilterApplied
            }
        }
    }
}