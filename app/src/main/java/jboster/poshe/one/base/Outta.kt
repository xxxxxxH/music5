package jboster.poshe.one.base

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anythink.core.api.ATAdInfo
import com.anythink.core.api.AdError
import com.anythink.splashad.api.ATSplashAd
import com.anythink.splashad.api.ATSplashAdListener
import com.anythink.splashad.api.IATSplashEyeAd
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdView
import jboster.poshe.one.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class Outta(id: Int) : AppCompatActivity(id) {
    private var isBackground = false
    private var openAd: ATSplashAd? = null
    private var insertAd: MaxInterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }

    open fun start() {
        chushihuaguanggao()
        appendBanner()
    }

    open fun kaipingjieshu() {}

    open fun chapingjieshu() {}

    private fun chushihuaguanggao() {
        openAd = TouchApplication.app.getKaiPingAd(kaipingListener())
        openAd?.loadAd()

        insertAd = TouchApplication.app.getChapingAd(this)
        insertAd?.setListener(chapingListener())
        insertAd?.loadAd()
    }

    private fun getOpenAd() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(3000)
            openAd?.onDestory()
            openAd = TouchApplication.app.getKaiPingAd(kaipingListener())
            openAd?.loadAd()
        }
    }

    private fun getInsertAd() {
        lifecycleScope.launch(Dispatchers.Main) {
            insertAd?.destroy()
            insertAd = TouchApplication.app.getChapingAd(this@Outta)
            insertAd!!.setListener(chapingListener())
            insertAd!!.loadAd()
        }
    }

    fun showKaiping(nidie: ViewGroup, isQiangzhi: Boolean = false): Boolean {
        if (configEntity.displayOpenAdWithInsertAd()) {
            return showChaping(isQiangzhi = isQiangzhi)
        } else {
            return showKaipingImpl(nidie)
        }
    }

    private fun showKaipingImpl(nidie: ViewGroup): Boolean {
        if (openAd != null) {
            if (openAd!!.isAdReady) {
                openAd!!.show(this, nidie)
                return true
            }
        }
        return false
    }

    fun showChaping(percent: Boolean = false, isQiangzhi: Boolean = false, biaozhi: String = ""): Boolean {
        if (isQiangzhi) {
            return showChapingImpl(biaozhi)
        } else {
            if ((percent && configEntity.isCanDisplayByPercent()) || (!percent)) {
                if (System.currentTimeMillis() - lastTime > configEntity.insertAdInterval() * 1000) {
                    var result = false
                    if (list.getOrNull(index) == true) {
                        result = showChapingImpl(biaozhi)
                    }
                    index++
                    if (index >= list.size) {
                        index = 0
                    }
                    return result
                }
            }
        }
        return false
    }

    private fun showChapingImpl(biaozhi: String): Boolean {
        if (insertAd != null) {
            "insert is ready = ${insertAd?.isReady}".loges()
            if (insertAd!!.isReady) {
                insertAd!!.showAd(biaozhi)
                return true
            } else {
                getInsertAd()
                return false
            }
        }
        return false
    }


    fun showYuanshengAd(yuansheng:(MaxNativeAdView?)->Unit){
        val ad = TouchApplication.app.getYuanShengAd()
        ad.loadAd()
        ad.setNativeAdListener(object : MaxNativeAdListener(){
            override fun onNativeAdLoaded(p0: MaxNativeAdView?, p1: MaxAd?) {
                super.onNativeAdLoaded(p0, p1)
                "onNativeAdLoaded".loges()
                yuansheng(p0)
            }

            override fun onNativeAdLoadFailed(p0: String?, p1: MaxError?) {
                super.onNativeAdLoadFailed(p0, p1)
                "onNativeAdLoadFailed $p1".loges()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        isBackground = isInBackground()
    }

    override fun onResume() {
        super.onResume()
        if (isBackground) {
            isBackground = false
            addOpen {
                showKaipingImpl(it)
            }
        }
    }

    inner class kaipingListener : ATSplashAdListener {
        override fun onAdLoaded() {
            "openAd onAdLoaded".loges()
        }

        override fun onNoAdError(p0: AdError?) {
            "openAd onNoAdError $p0".loges()
            getOpenAd()
        }

        override fun onAdShow(p0: ATAdInfo?) {
            "openAd onShow".loges()
        }

        override fun onAdClick(p0: ATAdInfo?) {

        }

        override fun onAdDismiss(p0: ATAdInfo?, p1: IATSplashEyeAd?) {
            "openAd onAdDismiss".loges()
            kaipingjieshu()
            getOpenAd()
        }
    }

    inner class chapingListener : MaxAdListener {
        override fun onAdLoaded(ad: MaxAd?) {
            "insertAd onAdLoaded".loges()
        }

        override fun onAdDisplayed(ad: MaxAd?) {

        }

        override fun onAdHidden(ad: MaxAd?) {
            lastTime = System.currentTimeMillis()
            getInsertAd()
            chapingjieshu()
        }

        override fun onAdClicked(ad: MaxAd?) {

        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
            "insertAd onAdLoadFailed $error".loges()
            getInsertAd()
        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
            "insertAd onAdDisplayFailed $error".loges()
            getInsertAd()
        }
    }
}

