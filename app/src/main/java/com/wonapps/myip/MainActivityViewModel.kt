package com.wonapps.myip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.json.JSONObject

class MainActivityViewModel(application: Application): AndroidViewModel(application), MainActivityViewModelInt {

    val mainActivityRepo = MainActivityRepo(this)

    override fun getMyIpResponse(response: JSONObject) {
        super.getMyIpResponse(response)
    }

    override fun errorResponse() {
        super.errorResponse()
    }

}