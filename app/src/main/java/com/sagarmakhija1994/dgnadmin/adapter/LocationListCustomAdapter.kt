package com.sagarmakhija1994.dgnadmin.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.sagarmakhija1994.dgnadmin.databinding.LayoutListItemLocationBinding
import com.sagarmakhija1994.dgnadmin.model.LocationListItemDataModel
import com.sagarmakhija1994.dgnadmin.model.SliderDataModel
import java.io.File


class LocationListCustomAdapter(private val activity: Activity, private val data: ArrayList<LocationListItemDataModel>) : RecyclerView.Adapter<LocationListCustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: LayoutListItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutListItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val binding = holder.binding
        val dataAtI = data[i]
        binding.txtName.text = dataAtI.name
        binding.txtAddress.text = dataAtI.address
        binding.txtContact.text = dataAtI.contact

        val adapter = ImageSliderAdapter(activity)
        binding.imageSlider.setSliderAdapter(adapter)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        for ( item in dataAtI.images) {
            val image = storageRef.child(item.toString())
            val imageNameSplit = item.toString().split("/")
            val imageName = imageNameSplit[imageNameSplit.size-1]
            val imageFile = File.createTempFile(imageName, "jpg")
            image.getFile(imageFile).addOnSuccessListener {
                adapter.addItem(SliderDataModel(0,imageFile))
            }
        }
        binding.imageSlider.startAutoCycle()

    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}