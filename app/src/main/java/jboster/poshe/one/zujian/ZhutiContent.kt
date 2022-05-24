package jboster.poshe.one.zujian

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import jboster.poshe.one.R

class ZhutiContent:LinearLayout {
    lateinit var image:ImageView
    lateinit var list:LinearLayout
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

    private fun initView(context: Context) :View{
        val v = LayoutInflater.from(context).inflate(R.layout.theme_bottom, this, true)
        image = v.findViewById(R.id.image)
        list = v.findViewById(R.id.list)

        val data = ArrayList<Int>()
        data.add(R.mipmap.large_1)
        data.add(R.mipmap.large_2)
        data.add(R.mipmap.large_3)
        data.add(R.mipmap.large_4)

        data.forEach {
            val imageView1 = ImageView(context)
            val p1 = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
            p1.weight = 1f
            imageView1.layoutParams = p1
            imageView1.adjustViewBounds = true
            imageView1.setImageResource(it)
            imageView1.setOnClickListener {_->
                image.setImageResource(it)
            }
            list.addView(imageView1)
        }

        return v
    }
}