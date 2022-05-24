package jboster.poshe.one.ui

import androidx.core.view.GravityCompat
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import jboster.poshe.one.R
import jboster.poshe.one.alert.getAboutUsAlert
import jboster.poshe.one.alert.getExitAlert
import jboster.poshe.one.alert.getShareAlert
import jboster.poshe.one.alert.getTimerAlert
import jboster.poshe.one.base.Outta
import jboster.poshe.one.event.Event
import jboster.poshe.one.ooh.BAdapter
import jboster.poshe.one.utils.*
import jboster.poshe.one.zujian.ZhuyeBottom
import jboster.poshe.one.zujian.ZhuyeCenter
import jboster.poshe.one.zujian.ZhuyeTop
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nv_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ZhuYe : Outta(R.layout.activity_main) {
    lateinit var banner: Banner<Any, BannerAdapter<Any, BAdapter.BannerViewHolder>>

    private val aboutAlertDialog by lazy {
        getAboutUsAlert()
    }
    private val timerAlertDialog by lazy {
        getTimerAlert()
    }
    private val shareAlertDialog by lazy {
        getShareAlert()
    }
    private val exitAlertDialog by lazy {
        getExitAlert()
    }

    override fun start() {
        super.start()
        "到主页了".loges()
        EventBus.getDefault().register(this)
        val top = ZhuyeTop(this)
        banner = top.getBannerAndSet()
        val array = arrayOf(
            R.mipmap.main2,
            0
        )
        val adapter = BAdapter(array.toMutableList(), this)
        banner.addBannerLifecycleObserver(this)
            .setAdapter(adapter)
            .indicator = CircleIndicator(this)
        top.getMenuBtn().setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val center = ZhuyeCenter(this)

        content.addView(top)
        content.addView(center)

        val bottom = ZhuyeBottom(this)

        bottomLayout.addView(bottom)

        themee.setOnClickListener { xiayigeYemian(ZhutiYeMian::class.java) }

        about.setOnClickListener { aboutAlertDialog.show() }

        sleep.setOnClickListener { timerAlertDialog.show() }

        share.setOnClickListener { shareAlertDialog.show() }

        yinxiao.setOnClickListener { return@setOnClickListener }
    }

    override fun onBackPressed() {
        exitAlertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        banner.start()
    }

    override fun onStop() {
        super.onStop()
        banner.stop()
    }

    override fun onDestroy() {
        banner.destroy()
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: Event) {
        if (e.getMessage()[0] == "tuima") {
            finish()
        }
    }
}