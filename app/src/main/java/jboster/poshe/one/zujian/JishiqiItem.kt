package jboster.poshe.one.zujian

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import jboster.poshe.one.R

class JishiqiItem:LinearLayout {
    lateinit var itemSleepText:TextView
    lateinit var itemSleepSelect:ImageView
    lateinit var itemRoot: RelativeLayout
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
        val v = LayoutInflater.from(context).inflate(R.layout.item_sleep, this, true)
        itemRoot = v.findViewById(R.id.itemRoot)
        itemSleepText = v.findViewById(R.id.itemSleepText)
        itemSleepSelect = v.findViewById(R.id.itemSleepSelect)
        return v
    }

    fun setItemText(s:String){
        itemSleepText.text = s
    }

    fun setImage(i:Boolean){
        if (i){
            itemSleepSelect.visibility = View.VISIBLE
        }else{
            itemSleepSelect.visibility = View.GONE
        }
    }

    fun getRoot():RelativeLayout{
        return itemRoot
    }

    fun getImage():Boolean{
        return itemSleepSelect.visibility == View.VISIBLE
    }
}