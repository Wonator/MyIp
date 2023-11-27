package com.wonapps.myip

import android.app.Application
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import org.json.JSONObject

class MainActivityRepo(
    private val mainActivityViewModelInt: MainActivityViewModelInt,
    private val application: Application
) {

    private val myIpUrl = "https://api.myip.com"
    private val timeout = 1000

    fun getMyIpRequest() {
        val mainJson = JSONObject()

        mainActivityViewModelInt.requestInProgress(true)

        val req = WonJSONRequest(
            Request.Method.POST,
            myIpUrl,
            mainJson,
            { response ->
                mainActivityViewModelInt.getMyIpResponse(response)
                mainActivityViewModelInt.requestInProgress(false)
            },
            {

                mainActivityViewModelInt.errorResponse()
                mainActivityViewModelInt.requestInProgress(false)
            })

        req.retryPolicy = DefaultRetryPolicy(
            timeout,
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        MyVolley.getInstance(application.applicationContext).addToRequestQueue(req)
    }
}