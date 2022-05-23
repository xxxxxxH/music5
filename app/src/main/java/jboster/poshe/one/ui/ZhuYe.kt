package jboster.poshe.one.ui

import androidx.core.view.GravityCompat
import com.youth.banner.indicator.CircleIndicator
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.ooh.BAdapter
import jboster.poshe.one.zujian.ZhuyeBottom
import jboster.poshe.one.zujian.ZhuyeCenter
import jboster.poshe.one.zujian.ZhuyeTop
import kotlinx.android.synthetic.main.activity_main.*

class ZhuYe : Outta(R.layout.activity_main) {
    override fun start() {
        super.start()
        val top = ZhuyeTop(this)
        val banner = top.getBannerAndSet()
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
    }
}