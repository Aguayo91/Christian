package com.coppel.preconfirmar.preconfirmar

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication

class SharedViewModel: ViewModel() {


    fun redInternetOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(
                connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d(
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporCelular))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d(
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporWIFI))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.d(
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporEthernet))
                    return true }
            }
        }
        //true PointMobile
        return true
        //false NormalApp
    }

}


class HomeViewModelFactory(private val repositorio: HomeRepositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepositorio::class.java).newInstance(repositorio)
    }
}