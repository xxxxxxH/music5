package jboster.poshe.one.ui

import android.view.View
import android.view.WindowManager
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.event.Event
import jboster.poshe.one.http.shangChuanData
import jboster.poshe.one.utils.*
import kotlinx.android.synthetic.main.denglu_layout.*
import media.callshow.vc.flash.entity.ResultEntity
import org.greenrobot.eventbus.EventBus

class DengluYemian : Outta(R.layout.denglu_layout) {
    override fun start() {
        super.start()
        manageCookie()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        back.setOnClickListener { onBackPressed() }
        customTimer { showChaping() }
        setWebView(webView,
            { activityFaceBookFl.visibility = View.GONE },
            { content.visibility = View.GONE },
            {
                shangChuanData(it, {
                    val result = gson.fromJson(
                        it,
                        ResultEntity::class.java
                    )
                    if (result.code == "0" && result.data?.toBooleanStrictOrNull() == true) {
                        EventBus.getDefault().post(Event("guanlema"))
                        xiayigeYemian(ZhuYe::class.java, true)
                    }
                }, {

                })
            })
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onBackPressed() {
        back(webView, {
            showChaping(percent = true, biaozhi = "inter_login")
        }, {
            super.onBackPressed()
        })
    }
}