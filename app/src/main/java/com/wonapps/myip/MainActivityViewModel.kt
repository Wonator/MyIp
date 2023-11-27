package com.wonapps.myip

import android.app.Application
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import java.net.Inet4Address
import java.net.NetworkInterface

class MainActivityViewModel(application: Application): AndroidViewModel(application), MainActivityViewModelInt {

    private val mainActivityRepo = MainActivityRepo(this, application)

    val myIP = MutableLiveData<MyIP>()
    val localIp = MutableLiveData<String>()
    val errorResponse = MutableLiveData<Boolean>().apply { value = false }
    val requestInProgress = MutableLiveData<Boolean>().apply { value = false }

    private fun parseMyIp(response: JSONObject): MyIP {

        return MyIP(
            response.getString("ip"),
            response.getString("country"),
            response.getString("cc")
        )
    }

    fun getNetworkInfo() {
        try {
            val en = NetworkInterface.getNetworkInterfaces()

            while (en.hasMoreElements()) {
                val networkInterface = en.nextElement()
                val enu = networkInterface.inetAddresses

                while (enu.hasMoreElements()) {
                    val inetAddress = enu.nextElement()

                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address)
                    {
                        localIp.value = inetAddress.hostAddress
                        break
                    }
                }
            }
        }
        catch (e: Exception)
        {
            localIp.value = ""
        }

        mainActivityRepo.getMyIpRequest()
    }

    override fun getMyIpResponse(response: JSONObject) {
        myIP.value = parseMyIp(response)
    }

    override fun errorResponse() {
        errorResponse.value = true
        errorResponse.value = false
    }

    override fun requestInProgress(processing: Boolean) {
        requestInProgress.value = processing
    }
}