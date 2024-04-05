package com.sagarmakhija1994.dgnadmin.util

object Utils {
    fun extractYTID(fullUrl:String):String{
        var url = fullUrl
        if(fullUrl.contains("watch?v=")){
            url = url.split("watch?v=")[1]
        }else if(fullUrl.contains("/live/")){
            url = url.split("/live/")[1]
        }else if(fullUrl.contains("youtu.be/")){
            url = url.split("youtu.be/")[1]
        }
        url = url.split("&list=")[0]
        url = url.split("?si=")[0]
        url = url.split("&pp=")[0]
        return url
    }
}