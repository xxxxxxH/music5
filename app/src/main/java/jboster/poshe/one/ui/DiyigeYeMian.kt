package jboster.poshe.one.ui

import android.view.View
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.event.Event
import jboster.poshe.one.http.huoQuPeiZhi
import jboster.poshe.one.utils.*
import kotlinx.android.synthetic.main.activity_diyige_ye_mian.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DiyigeYeMian : Outta(R.layout.activity_diyige_ye_mian) {
    override fun start() {
        super.start()
        EventBus.getDefault().register(this)
        huoQuQuanXian {
            if (it){
                huoQuPeiZhi({
                    if (login){
                        zhanshikaiping(ZhuYe::class.java){
                            if (showKaiping(die, isQiangzhi = true)){
                                b = true
                                return@zhanshikaiping
                            }else{
                                "进入主页1".loges()
                                xiayigeYemian(ZhuYe::class.java, guangbuguandangqianyiemian = true)
                            }
                        }
                        return@huoQuPeiZhi
                    }
                    if (configEntity.needLogin()){
                        loginBtn.visibility = View.VISIBLE
                        return@huoQuPeiZhi
                    }
                    zhanshikaiping(ZhuYe::class.java){
                        if (showKaiping(die, isQiangzhi = true)){
                            b = true
                            return@zhanshikaiping
                        }else{
                            "进入主页2".loges()
                            xiayigeYemian(ZhuYe::class.java, guangbuguandangqianyiemian = true)
                            return@zhanshikaiping
                        }
                    }
//                    "进入主页3".loges()
//                    xiayigeYemian(ZhuYe::class.java, guangbuguandangqianyiemian = true)
                },{

                })

            }
            loginBtn.setOnClickListener { xiayigeYemian(DengluYemian::class.java, false) }
        }
    }

    override fun chapingjieshu() {
        super.chapingjieshu()
        chapingguanle(ZhuYe::class.java)
    }

    override fun kaipingjieshu() {
        super.kaipingjieshu()
        shanpingguanle(ZhuYe::class.java)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: Event){
        if (e.getMessage()[0] == "guanlema"){
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}