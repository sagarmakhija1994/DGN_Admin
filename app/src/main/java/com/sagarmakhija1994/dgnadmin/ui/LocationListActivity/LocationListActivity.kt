package com.sagarmakhija1994.dgnadmin.ui.locationListActivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.sagarmakhija1994.dgnadmin.adapter.LocationListCustomAdapter
import com.sagarmakhija1994.dgnadmin.adapter.LocationListItemDataModel
import com.sagarmakhija1994.dgnadmin.databinding.ActivityLocationListBinding
import com.sagarmakhija1994.dgnadmin.util.AppLevelData
import com.sagarmakhija1994.dgnadmin.util.CustomDialogs
import com.sagarmakhija1994.dgnadmin.util.enums.LocationListSelectionTypeEnum
import com.sagarmakhija1994.dgnadmin.util.enums.VideoListSelectionTypeEnum

class LocationListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationListBinding
    private lateinit var customDialogs: CustomDialogs
    private lateinit var darbarItemList: ArrayList<LocationListItemDataModel>
    private lateinit var darbarListCustomAdapter: LocationListCustomAdapter
    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        customDialogs = CustomDialogs()
        darbarItemList = ArrayList()
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.darbarListView.layoutManager = llm
        darbarListCustomAdapter = LocationListCustomAdapter(this, darbarItemList)
        binding.darbarListView.adapter = darbarListCustomAdapter
        darbarListCustomAdapter.notifyDataSetChanged()
        when (AppLevelData.locationSelectionListType) {
            LocationListSelectionTypeEnum.DARBAR -> {
                binding.txtHeader.text = "Darbar"
                loadList("Darbar")
            }
            LocationListSelectionTypeEnum.CLINICS -> {
                binding.txtHeader.text = "Clinics"
                loadList("Clinics")
            }
            LocationListSelectionTypeEnum.DHARAMSHALA -> {
                binding.txtHeader.text = "Dharamshala"
                loadList("Dharamshala")
            }
        }

    }

    private fun loadList(collection:String){
        customDialogs.showLoadingDialog(this)
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                customDialogs.hideLoadingDialog()
                for (document in result) {
                    val order = document.data["order"].toString().toInt()
                    val name = document.data["name"].toString()
                    val address = document.data["address"].toString().replace("\\n","\n")
                    val contact = document.data["contact"].toString().replace("\\n","\n")
                    val images = document.data["images"] as ArrayList<*>
                    val dataModel = LocationListItemDataModel(order,name,address,contact,images)
                    darbarItemList.add(dataModel)

                }
                darbarItemList.sortBy { it.order }
                darbarListCustomAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Unable to connect, Please Try Again.", Toast.LENGTH_LONG).show()
            }
    }
}