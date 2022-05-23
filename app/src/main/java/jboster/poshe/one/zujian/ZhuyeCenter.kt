package jboster.poshe.one.zujian

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import jboster.poshe.one.R
import jboster.poshe.one.utils.*


class ZhuyeCenter : LinearLayout {

    lateinit var relax: ImageView
    lateinit var practice: ImageView
    lateinit var hiphop: ImageView
    lateinit var commuting: ImageView
    lateinit var collection: ImageView
    lateinit var more: ImageView

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
        val v = LayoutInflater.from(context).inflate(R.layout.zhuye_centet, this, true)
        relax = v.findViewById(R.id.relax)
        relax.setOnClickListener { startBrowser(context, RELAXURL) }
        practice = v.findViewById(R.id.practice)
        practice.setOnClickListener { startBrowser(context, PRACTICEURL) }
        hiphop = v.findViewById(R.id.hiphop)
        hiphop.setOnClickListener { startBrowser(context, HIPHOPURL) }
        commuting = v.findViewById(R.id.commuting)
        commuting.setOnClickListener { startBrowser(context, COMMUTINGURL) }
        collection = v.findViewById(R.id.collection)
        collection.setOnClickListener { startBrowser(context, COLLECTIONURL) }
        more = v.findViewById(R.id.more)
        more.setOnClickListener { return@setOnClickListener }
        return v
    }

    fun startBrowser(context: Context, url: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url
        context.startActivity(intent)
    }
}