package com.sagarmakhija1994.dgnadmin.util

import android.app.Dialog
import com.sagarmakhija1994.dgnadmin.util.enums.LocationListSelectionTypeEnum
import com.sagarmakhija1994.dgnadmin.util.enums.VideoListSelectionTypeEnum

class AppLevelData {
    companion object{
        var adminPin = ""
        var loadingDialog: Dialog? = null
        var videoSelectionListType = VideoListSelectionTypeEnum.LIVE
        var locationSelectionListType = LocationListSelectionTypeEnum.DARBAR
        var currentSelectedVideo = ""
    }
}