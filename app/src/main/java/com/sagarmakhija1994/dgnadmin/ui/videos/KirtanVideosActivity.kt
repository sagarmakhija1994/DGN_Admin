package com.sagarmakhija1994.dgnadmin.ui.videos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sagarmakhija1994.dgnadmin.adapter.KirtanVideosListCustomAdapter
import com.sagarmakhija1994.dgnadmin.databinding.ActivityKirtanVideosBinding
import com.sagarmakhija1994.dgnadmin.model.KirtanVideoItemDataModel
import com.sagarmakhija1994.dgnadmin.util.AppLevelData.Companion.currentSelectedVideo
import com.sagarmakhija1994.dgnadmin.util.AppLevelData.Companion.videoSelectionListType
import com.sagarmakhija1994.dgnadmin.util.CustomDialogs
import com.sagarmakhija1994.dgnadmin.util.enums.VideoListSelectionTypeEnum

class KirtanVideosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKirtanVideosBinding
    private val db = Firebase.firestore

    private lateinit var kirtanVideosList: ArrayList<KirtanVideoItemDataModel>
    private lateinit var kirtanVideosListCustomAdapter: KirtanVideosListCustomAdapter

    private lateinit var customDialogs: CustomDialogs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKirtanVideosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        customDialogs = CustomDialogs()
        customDialogs.showLoadingDialog(this)

        kirtanVideosList = ArrayList()
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.kirtanVideosListView.layoutManager = llm
        kirtanVideosListCustomAdapter = KirtanVideosListCustomAdapter(kirtanVideosList, ::onListItemClick)
        binding.kirtanVideosListView.adapter = kirtanVideosListCustomAdapter
        kirtanVideosListCustomAdapter.notifyDataSetChanged()
        if(videoSelectionListType == VideoListSelectionTypeEnum.LIVE){
            getLiveKirtanVideoList()
        }else{
            getKirtanVideoList()
        }

        binding.btnAddVideo.setOnClickListener {
            customDialogs.addVideoUrlDialog(this,false, ::onAddVideoUrlClick )
        }
    }

    private fun getKirtanVideoList(){
        kirtanVideosList.clear()
        db.collection("KirtanVideos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    kirtanVideosList.add(KirtanVideoItemDataModel(document.id, document.data["name"].toString(), document.data["url"].toString()))
                }
                kirtanVideosList.sortBy { it.id.reversed() }
                kirtanVideosListCustomAdapter.notifyDataSetChanged()
                customDialogs.hideLoadingDialog()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Error While Connecting.",Toast.LENGTH_LONG).show()
                finish()
            }
    }

    private fun getLiveKirtanVideoList(){
        kirtanVideosList.clear()
        db.collection("Live")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    kirtanVideosList.add(KirtanVideoItemDataModel(document.id, document.data["name"].toString(), document.data["url"].toString()))
                }
                kirtanVideosList.sortBy { it.id.reversed() }
                kirtanVideosListCustomAdapter.notifyDataSetChanged()
                customDialogs.hideLoadingDialog()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Error While Connecting.",Toast.LENGTH_LONG).show()
                finish()
            }
    }

    private fun onListItemClick(data:KirtanVideoItemDataModel) {

        customDialogs.deleteVideoUrlDialog(this,false, data.id, ::onDeleteVideoUrlClick )
        //startActivity(Intent(this, PlayerActivity::class.java))
    }

    private fun onDeleteVideoUrlClick(docId:String){
        customDialogs.showLoadingDialog(this)
        var collectionPath = ""
        if(videoSelectionListType == VideoListSelectionTypeEnum.LIVE){
            collectionPath = "Live"
        }else if(videoSelectionListType == VideoListSelectionTypeEnum.VIDEOS){
            collectionPath = "KirtanVideos"
        }
        if(collectionPath.trim().isEmpty()){
            Toast.makeText(this,"Fail to Delete, Please try again.",Toast.LENGTH_LONG).show()
            return
        }
        db.collection(collectionPath).document(docId)
            .delete()
            .addOnSuccessListener {
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Delete Success.",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Fail to Delete, Please try again.",Toast.LENGTH_LONG).show()
            }

    }

    private fun onAddVideoUrlClick(url:String){
        customDialogs.showLoadingDialog(this)
        var collectionPath = ""
        if(videoSelectionListType == VideoListSelectionTypeEnum.LIVE){
            collectionPath = "Live"
        }else if(videoSelectionListType == VideoListSelectionTypeEnum.VIDEOS){
            collectionPath = "KirtanVideos"
        }
        if(collectionPath.trim().isEmpty()){
            Toast.makeText(this,"Fail to Add, Please try again.",Toast.LENGTH_LONG).show()
            return
        }

        val docId = "${System.currentTimeMillis()}"
        val videoMetaData = hashMapOf(
            "name" to docId,
            "url" to url
        )
        db.collection(collectionPath).document(docId)
            .set(videoMetaData)
            .addOnSuccessListener {
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Added Success.",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                customDialogs.hideLoadingDialog()
                Toast.makeText(this,"Fail to Add, Please try again.",Toast.LENGTH_LONG).show()
            }

    }

    override fun onStop() {
        customDialogs.hideLoadingDialog()
        super.onStop()
    }
}