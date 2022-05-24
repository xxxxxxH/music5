package jboster.poshe.one.ui

import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.zujian.ZhuTiTop
import jboster.poshe.one.zujian.ZhutiContent
import kotlinx.android.synthetic.main.zhuti_layout.*

class ZhutiYeMian :Outta(R.layout.zhuti_layout){
    override fun start() {
        super.start()
        val top = ZhuTiTop(this)

        val content = ZhutiContent(this)

        contentLl.addView(top)
        contentLl.addView(content)
    }
}