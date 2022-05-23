package jboster.poshe.one.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView
import com.anythink.core.api.ATSDK
import com.anythink.core.api.NetTrafficeCallback
import com.anythink.splashad.api.ATSplashAd
import com.anythink.splashad.api.ATSplashAdListener
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkSettings
import com.tencent.mmkv.MMKV
import jboster.poshe.one.BuildConfig
import jboster.poshe.one.R
import kotlin.system.measureTimeMillis

class TouchApplication :Application(){
    companion object{
        lateinit var app: TouchApplication
    }

    val maxSdk by lazy {
        AppLovinSdk.getInstance(
            getString(R.string.lovin_app_key).reversed(),
            AppLovinSdkSettings(this),
            this
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        app = this
    }

    override fun onCreate() {
        super.onCreate()
        initSdk()
    }

    fun getChapingAd(ac: Activity): MaxInterstitialAd {
        return MaxInterstitialAd(
            resources.getString(R.string.lovin_insert_ad_id),
            maxSdk,
            ac
        )
    }

    fun getYuanShengAd(): MaxNativeAdLoader {
        return MaxNativeAdLoader(
            resources.getString(R.string.lovin_native_ad_id),
            maxSdk,
            this
        )
    }

    //banner
    fun getBannerAd(): MaxAdView {
        return MaxAdView(
            resources.getString(R.string.lovin_banner_ad_id),
            maxSdk,
            this
        )
    }

    //open
    fun getKaiPingAd(listener: ATSplashAdListener?): ATSplashAd {
        return ATSplashAd(this, resources.getString(R.string.top_on_open_ad_id), listener)
    }

    fun initSdk() {
        measureTimeMillis {
            MMKV.initialize(app)
            maxSdk.apply {
                mediationProvider = AppLovinMediationProvider.MAX
                initializeSdk()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val processName = getProcessName()
                if (packageName != processName) {
                    WebView.setDataDirectorySuffix(processName)
                }
            }

            ATSDK.checkIsEuTraffic(app, object : NetTrafficeCallback {
                override fun onResultCallback(isEU: Boolean) {
                    if (isEU && ATSDK.getGDPRDataLevel(app) == ATSDK.UNKNOWN) {
                        ATSDK.showGdprAuth(app)
                    }
                }

                override fun onErrorCallback(errorMsg: String) {
                }
            })

            ATSDK.setNetworkLogDebug(BuildConfig.DEBUG)
            ATSDK.integrationChecking(app)
            ATSDK.init(
                app,
                getString(R.string.top_on_app_id),
                getString(R.string.top_on_app_key)
            )
        }.let {

        }
    }
}