package com.lopatin.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import java.text.DateFormat
import java.util.*

/**
 * делаем фильтр по которому будем принимать событие
 * и регистрируем приемник
 * при динамическом подписании не нужно добавлять intent-filter в манифест
 * некоторые intent-filter в поздних версиях можно делать только динамически, не прописывая в манифесте
 * если не прописать в манифесте - на старых приложениях тоже работает
 * если  intent-filter прописан в манифесте, то в теории срабатывает интент даже при выключенном приложении (на практике не смог проверить)
 * FirstBroadcastReceiver подключаем динамически, SecondBroadcastReceiver прописываем в манифесте
 *
 * методы connectionDefaultNetworkCallback и connectionNetworkCallback обрабатывают "прослушивание" событий изменения интернета
 * registerDefaultNetworkCallback и registerNetworkCallback соответсвенно, (первый обрабатывает и wi-fi, второй - нет)
 * которые пришли на смену deprecated CONNECTIVITY_ACTION
 */
class MainActivity : AppCompatActivity() {

    private val broadcastReceiver: BroadcastReceiver = FirstBroadcastReceiver()
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var prefs: SharedPreferences
    private val PREF_FILE_NAME = "broadSharePref"
    private val PREF_STRING_KEY = "infoString"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        prefs = getSharedPreferences(
            PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }


    override fun onStart() {
        super.onStart()
        showHistoryButton.setOnClickListener {
            startActivity(
                intentFor<InfoPrefsActivity>()
            )
        }
        connectionNetworkCallback()
        connectionDefaultNetworkCallback()

        //делаем фильтр по которому будем принимать событие
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        // регистрируем приемник
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            Log.d("logBroadcast", "e : $e")
        }
        super.onDestroy()
    }

    /**
     * registerNetworkCallback не принимает сигналов от wi-fi
     */
    private fun connectionNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder =
                NetworkRequest.Builder()

            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

            val networkRequest = builder.build()
            connectivityManager.registerNetworkCallback(networkRequest,
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network?) {
                        super.onAvailable(network)
                        Log.d("logBroadcast", "Network Available")
                    }

                    override fun onLost(network: Network?) {
                        super.onLost(network)
                        Log.d("logBroadcast", "Connection lost")
                    }

                    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                        super.onBlockedStatusChanged(network, blocked)
                        Log.d("logBroadcast", "Connection onBlockedStatusChanged")
                    }

                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities
                    ) {
                        super.onCapabilitiesChanged(network, networkCapabilities)
                        Log.d("logBroadcast", "Connection onCapabilitiesChanged")
                    }

                    override fun onLinkPropertiesChanged(
                        network: Network,
                        linkProperties: LinkProperties
                    ) {
                        super.onLinkPropertiesChanged(network, linkProperties)
                        Log.d("logBroadcast", "Connection onLinkPropertiesChanged")
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        Log.d("logBroadcast", "Connection onUnavailable")
                    }

                    override fun onLosing(network: Network, maxMsToLive: Int) {
                        super.onLosing(network, maxMsToLive)
                        Log.d("logBroadcast", "Connection onLosing")
                    }
                })
        }
    }

    /**
     * registerDefaultNetworkCallback принимает сигналов от wi-fi
     */
    private fun connectionDefaultNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    super.onAvailable(network)
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onAvailable")
                    Log.d("logBroadcast", "onAvailable network ${network.toString()}")
                }

                override fun onLost(network: Network?) {
                    super.onLost(network)
                    val oldStr = prefs.getString(PREF_STRING_KEY, "")
                    val oldStrNotNull = oldStr ?: ""
                    val newSb = StringBuilder(oldStrNotNull)
                    val df = DateFormat.getTimeInstance()
//                    df.setTimeZone(TimeZone.getTimeZone("gmt"))
                    val gmtTime = df.format(Date())
                    newSb.apply {
                        append("$gmtTime ")
                        append("onLost ")
                        append("network ${network} \n")
                    }
                    prefs.edit().putString(PREF_STRING_KEY, newSb.toString()).apply()
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onLost")
                    Log.d("logBroadcast", "onLost network ${network.toString()}")
                }

                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    super.onBlockedStatusChanged(network, blocked)
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onBlockedStatusChanged")
                    Log.d(
                        "logBroadcast",
                        "onBlockedStatusChanged network ${network.toString()} blocked $blocked"
                    )
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    val oldStr = prefs.getString(PREF_STRING_KEY, "")
                    val oldStrNotNull = oldStr ?: ""
                    val newSb = StringBuilder(oldStrNotNull)

                    val df = DateFormat.getTimeInstance()
//                    df.setTimeZone(TimeZone.getTimeZone("gmt"))
                    val gmtTime = df.format(Date())
                    newSb.apply {
                        append("${gmtTime} ")
                        append("${networkCapabilities.signalStrength} ")
                        append("${networkCapabilities.linkDownstreamBandwidthKbps} ")
                        append("${networkCapabilities.linkUpstreamBandwidthKbps} \n")
                    }
                    prefs.edit().putString(PREF_STRING_KEY, newSb.toString()).apply()

                    val sb = StringBuilder()
                    sb.apply {

                        append("${gmtTime} ")
                        append("${networkCapabilities.signalStrength} ")
                        append("${networkCapabilities.linkDownstreamBandwidthKbps} ")
                        append("${networkCapabilities.linkUpstreamBandwidthKbps}")
                    }
//                    alert("$networkCapabilities", sb) {
//                        yesButton { toast("Oh…") }
//                        noButton {}
//                    }.show()
//                    toast(sb.toString())
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onCapabilitiesChanged")
                    Log.d(
                        "logBroadcast",
                        "onCapabilitiesChanged networkCapabilities ${networkCapabilities}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onCapabilitiesChanged signalStrength ${networkCapabilities.signalStrength}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onCapabilitiesChanged linkDownstreamBandwidthKbps ${networkCapabilities.linkDownstreamBandwidthKbps}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onCapabilitiesChanged linkUpstreamBandwidthKbps ${networkCapabilities.linkUpstreamBandwidthKbps}"
                    )
//                    Log.d("logBroadcast", "onCapabilitiesChanged transportInfo ${networkCapabilities.transportInfo.toString()}")

                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onLinkPropertiesChanged")
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged linkProperties ${linkProperties}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged dnsServers ${linkProperties.dnsServers}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged domains ${linkProperties.domains}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged httpProxy ${linkProperties.httpProxy}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged interfaceName ${linkProperties.interfaceName}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged isPrivateDnsActive ${linkProperties.isPrivateDnsActive}"
                    )
                    Log.d(
                        "logBroadcast",
                        "onLinkPropertiesChanged linkAddresses ${linkProperties.linkAddresses}"
                    )
                    Log.d("logBroadcast", "onLinkPropertiesChanged mtu ${linkProperties.mtu}")
                    Log.d("logBroadcast", "onLinkPropertiesChanged routes ${linkProperties.routes}")
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onUnavailable")
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    val oldStr = prefs.getString(PREF_STRING_KEY, "")
                    val oldStrNotNull = oldStr ?: ""
                    val newSb = StringBuilder(oldStrNotNull)
                    val df = DateFormat.getTimeInstance()
                    val gmtTime = df.format(Date())
                    newSb.apply {
                        append("$gmtTime ")
                        append("onLosing ")
                        append("maxMsToLive $maxMsToLive ")
                        append("network ${network} \n")
                    }
                    prefs.edit().putString(PREF_STRING_KEY, newSb.toString()).apply()
                    Log.d("logBroadcast", "registerDefaultNetworkCallback onLosing")
                    Log.d("logBroadcast", "onLosing network $network")
                    Log.d("logBroadcast", "onLosing maxMsToLive $maxMsToLive")
                }
            })
        }
    }
}
