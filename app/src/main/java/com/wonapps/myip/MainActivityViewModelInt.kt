package com.wonapps.myip

import org.json.JSONObject

interface MainActivityViewModelInt {
    fun getMyIpResponse(response:JSONObject) {}
    fun errorResponse() {}
}