package jboster.poshe.one.zujian

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.ui.BendiYemian

class ZhuyeBottom :LinearLayout{
    lateinit var singer:TextView
    lateinit var local:TextView
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) : View {
        val v = LayoutInflater.from(context).inflate(R.layout.zhuye_bottom, this, true)
        singer = v.findViewById(R.id.singer)
        singer.setOnClickListener { (context as Outta).showChaping() }
        local = v.findViewById(R.id.local)
        local.setOnClickListener {
            (context as Outta).showChaping()
            context.startActivity(Intent(context, BendiYemian::class.java))
        }
        return v
    }
}