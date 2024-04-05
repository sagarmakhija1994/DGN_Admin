package com.sagarmakhija1994.dgnadmin.util

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.Toast
import com.sagarmakhija1994.dgnadmin.databinding.DialogAddVideoBinding
import com.sagarmakhija1994.dgnadmin.databinding.DialogDeleteVideoBinding
import com.sagarmakhija1994.dgnadmin.databinding.DialogLoadingBinding
import com.sagarmakhija1994.dgnadmin.util.AppLevelData.Companion.loadingDialog

class CustomDialogs {

    fun showLoadingDialog(activity: Activity?, text: String = "Loading...", isCancelable: Boolean = false):Dialog {
        var isDialogAlreadyShowing = false
        loadingDialog?.let { isDialogAlreadyShowing = it.isShowing }
        if(!isDialogAlreadyShowing){
            loadingDialog = Dialog(activity!!)
            loadingDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            loadingDialog!!.setCancelable(isCancelable)
            val binding = DialogLoadingBinding.inflate(activity.layoutInflater)
            loadingDialog!!.setContentView(binding.root)
            binding.tvMsg.text = text
            loadingDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            loadingDialog?.show()
        }
        return loadingDialog!!
    }

    fun hideLoadingDialog(){
        try{
            loadingDialog?.hide()
        }catch (ignore:Exception){}
    }

    fun addVideoUrlDialog(activity: Activity?, isCancelable: Boolean = false, onAddUrlClick: (String) -> Unit):Dialog {
        val addVideoUrlDialog = Dialog(activity!!)
        addVideoUrlDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        addVideoUrlDialog.setCancelable(isCancelable)
        val binding = DialogAddVideoBinding.inflate(activity.layoutInflater)
        addVideoUrlDialog.setContentView(binding.root)
        binding.btnAddVideo.setOnClickListener {
            val videoUrl = binding.editVideoUrl.text.toString()
            if(videoUrl.trim().isEmpty()){
                Toast.makeText(activity,"Please enter a valid URL.", Toast.LENGTH_LONG).show()
            }else{
                onAddUrlClick(videoUrl)
                addVideoUrlDialog.dismiss()
            }
        }
        binding.btnCancel.setOnClickListener {
            addVideoUrlDialog.dismiss()
        }
        addVideoUrlDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        addVideoUrlDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        addVideoUrlDialog.show()
        return addVideoUrlDialog
    }

    fun deleteVideoUrlDialog(activity: Activity?, isCancelable: Boolean = false, docId:String, onDeleteVideoUrlClick: (String) -> Unit):Dialog {
        val deleteVideoUrlDialog = Dialog(activity!!)
        deleteVideoUrlDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        deleteVideoUrlDialog.setCancelable(isCancelable)
        val binding = DialogDeleteVideoBinding.inflate(activity.layoutInflater)
        deleteVideoUrlDialog.setContentView(binding.root)
        binding.btnDeleteVideoUrl.setOnClickListener {
            onDeleteVideoUrlClick(docId)
            deleteVideoUrlDialog.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            deleteVideoUrlDialog.dismiss()
        }
        deleteVideoUrlDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        deleteVideoUrlDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        deleteVideoUrlDialog.show()
        return deleteVideoUrlDialog
    }
}