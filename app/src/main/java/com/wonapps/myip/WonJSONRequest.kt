package com.wonapps.myip

import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class WonJSONRequest(
    method: Int,
    url: String?,
    private val jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>?,
    errorListener: Response.ErrorListener?
) :
    JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {

    override fun deliverResponse(response: JSONObject?) {
        val message = "RequestJSON: ${jsonRequest.toString()}" +
                System.lineSeparator() +
                "ResponseJSON: ${response.toString()}"

        Log.d("VolleyCallSuccess", message)
        super.deliverResponse(response)
    }

    override fun deliverError(error: VolleyError?) {
        var volleyError = ""

        if (error != null) {
            volleyError = error.message.toString()
        }

        val message = "RequestJSON: ${jsonRequest.toString()}" +
                System.lineSeparator() +
                "VolleyErrorMessage: $volleyError"

        Log.e("VolleyCallFail", message)

        super.deliverError(error)
    }
}