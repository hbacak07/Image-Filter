package com.hbacakk.imagefilter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hbacakk.imagefilter.R
import com.hbacakk.imagefilter.data.ImageFilter
import com.hbacakk.imagefilter.databinding.ItemContainerFilterBinding
import com.hbacakk.imagefilter.listener.ImageFilterListener

class ImageFilterAdapter(
    private val imageFilters: List<ImageFilter>,
    private val imageFilterListener: ImageFilterListener
) :
    RecyclerView.Adapter<ImageFilterAdapter.imageFilterViewHolder>() {
    private var selectedFilterPosition = 0
    private var previousSelectedPositions = 0

    inner class imageFilterViewHolder(val binding: ItemContainerFilterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageFilterViewHolder {
        val binding =
            ItemContainerFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return imageFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: imageFilterViewHolder, position: Int) {
        with(holder) {
            with(imageFilters[position]) {
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilterName.text = name
                binding.root.setOnClickListener {
                    if (position!=selectedFilterPosition){
                        imageFilterListener.onFilterSelected(this)
                        previousSelectedPositions=selectedFilterPosition
                        selectedFilterPosition=position
                        with(this@ImageFilterAdapter){
                            notifyItemChanged(previousSelectedPositions,Unit)
                            notifyItemChanged(selectedFilterPosition,Unit)
                        }
                    }
                }
            }
            binding.textFilterName.setTextColor(
                ContextCompat.getColor(binding.textFilterName.context,
                if (selectedFilterPosition==position)
                    R.color.primaryDark
                else
                    R.color.primaryText
                )
            )
        }
    }

    override fun getItemCount() = imageFilters.size
}