package jboster.poshe.one.zujian

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import jboster.poshe.one.R
import jboster.poshe.one.ooh.BAdapter

class ZhuyeTop : LinearLayout {

    lateinit var menu: ImageView
    lateinit var banner: Banner<Any, BannerAdapter<Any, BAdapter.BannerViewHolder>>

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

    private fun initView(context: Context): View {
        val v = LayoutInflater.from(context).inflate(R.layout.zhuye_top, this, true)
        menu = v.findViewById(R.id.menu)
        banner = v.findViewById(R.id.banner)
        return v
    }

    fun getBannerAndSet():Banner<Any, BannerAdapter<Any, BAdapter.BannerViewHolder>>{
        return banner
    }

    fun getMenuBtn():ImageView{
        return menu
    }
}