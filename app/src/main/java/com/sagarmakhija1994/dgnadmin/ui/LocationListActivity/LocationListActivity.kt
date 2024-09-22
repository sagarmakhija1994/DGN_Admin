package com.sagarmakhija1994.dgnadmin.ui.locationListActivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.sagarmakhija1994.dgnadmin.adapter.ImageSliderAdapter
import com.sagarmakhija1994.dgnadmin.adapter.LocationListCustomAdapter
import com.sagarmakhija1994.dgnadmin.model.LocationListItemDataModel
import com.sagarmakhija1994.dgnadmin.databinding.ActivityLocationListBinding
import com.sagarmakhija1994.dgnadmin.model.SliderDataModel
import com.sagarmakhija1994.dgnadmin.util.AppLevelData
import com.sagarmakhija1994.dgnadmin.util.CustomDialogs
import com.sagarmakhija1994.dgnadmin.util.enums.LocationListSelectionTypeEnum
import java.io.File

class LocationListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationListBinding
    private lateinit var customDialogs: CustomDialogs
    private lateinit var darbarItemList: ArrayList<LocationListItemDataModel>
    private lateinit var darbarListCustomAdapter: LocationListCustomAdapter
    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private lateinit var slidingImageAdapter:ImageSliderAdapter
    private val PICK_IMAGE_CODE = 100
    private var lastItemIDFromServer = 0

    private var requiredPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        customDialogs = CustomDialogs()
        darbarItemList = ArrayList()
        slidingImageAdapter = ImageSliderAdapter(this)
        binding.imageSlider.setSliderAdapter(slidingImageAdapter)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.darbarListView.layoutManager = llm
        darbarListCustomAdapter = LocationListCustomAdapter(this, darbarItemList)
        binding.darbarListView.adapter = darbarListCustomAdapter
        darbarListCustomAdapter.notifyDataSetChanged()
        binding.btnAddLocation.setOnClickListener {
            binding.viewAddLocation.visibility = View.VISIBLE
        }
        binding.viewAddLocationCancel.setOnClickListener {
            binding.viewAddLocation.visibility = View.GONE
        }
        binding.viewAddLocationFinalAdd.setOnClickListener {
            binding.viewAddLocation.visibility = View.GONE
        }
        binding.btnAddImage.setOnClickListener {
            checkPermission()
        }
        binding.btnRemoveSelectedImage.setOnClickListener {
            slidingImageAdapter.deleteItem(slidingImageAdapter.currentPosition)
            slidingImageAdapter.notifyDataSetChanged()
        }

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

        permissionRequestListener()
    }

    private fun loadList(collection:String){
        Log.e("Sagar-X","Load List")
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
                if(darbarItemList.size>0){
                    lastItemIDFromServer = darbarItemList[darbarItemList.size-1].order
                }

                darbarListCustomAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Unable to connect, Please Try Again.", Toast.LENGTH_LONG).show()
                runOnUiThread {  binding.btnAddLocation.visibility = View.GONE }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_CODE && data != null){
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)!!
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val mediaPath = cursor.getString(columnIndex)
            slidingImageAdapter.addItem(SliderDataModel(0,File(mediaPath)))
            slidingImageAdapter.notifyDataSetChanged()
            cursor.close()
        }
    }

    private fun finalAddLocation(){

    }


    private fun permissionRequestListener() {
        try {
            requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//                checkPermission()
            }
        } catch (ignore: Exception) {
        }
    }

    private fun checkPermission() {
        Log.e("Sagar-x","0")
        var permissionStatus = true
        for (i in requiredPermissions.indices) {
            if (ContextCompat.checkSelfPermission(this, requiredPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                Log.e("Sagar-x","1")
                permissionStatus = false
                requestPermissionLauncher.launch(requiredPermissions[i])
            }
        }
        if (permissionStatus) {
            //            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
//            startActivityForResult(intent, PICK_IMAGE_CODE)
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE)
        }
    }
}