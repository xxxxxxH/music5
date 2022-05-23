package jboster.poshe.one.alert

import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import jboster.poshe.one.R
import jboster.poshe.one.base.Outta
import jboster.poshe.one.event.Event
import jboster.poshe.one.utils.ShareUtils
import jboster.poshe.one.zujian.JishiqiItem
import org.greenrobot.eventbus.EventBus


fun AppCompatActivity.getShareAlert(): AlertDialog {
    val dialog = AlertDialog.Builder(this).create()
    val v = layoutInflater.inflate(R.layout.layout_share, null)
    dialog.setView(v)
    v.findViewById<TextView>(R.id.shareInFb).setOnClickListener {
        ShareUtils.get().shareWithFb(this, "")
    }
    v.findViewById<TextView>(R.id.shareInIns).setOnClickListener {
        ShareUtils.get().shareWithIns(this, "")
    }
    v.findViewById<TextView>(R.id.shareInApp).setOnClickListener {
        ShareUtils.get().shareWithNative(this)
    }
    v.findViewById<TextView>(R.id.shareInEmail).setOnClickListener {
        ShareUtils.get().shareWithEmail(this, "")
    }
    return dialog
}

fun AppCompatActivity.getAboutUsAlert(): AlertDialog {
    val dialog = AlertDialog.Builder(this).create()
    val v = layoutInflater.inflate(R.layout.ahout_layout, null)
    dialog.setView(v)
    v.findViewById<TextView>(R.id.confirm).setOnClickListener { dialog.dismiss() }
    v.findViewById<TextView>(R.id.cancel).setOnClickListener { dialog.dismiss() }
    return dialog
}

fun AppCompatActivity.getTimerAlert(): AlertDialog {
    val dialog = AlertDialog.Builder(this).create()
    val v = layoutInflater.inflate(R.layout.timer_layout, null)
    dialog.setView(v)
    val list = v.findViewById<LinearLayout>(R.id.list)

    val item1 = JishiqiItem(this)
    item1.setItemText("off")
    item1.setImage(true)

    val item2 = JishiqiItem(this)
    item2.setItemText("10 min")
    item2.setImage(false)

    val item3 = JishiqiItem(this)
    item3.setItemText("20 min")
    item3.setImage(false)

    val item4 = JishiqiItem(this)
    item4.setItemText("30 min")
    item4.setImage(false)

    val item5 = JishiqiItem(this)
    item5.setItemText("45 min")
    item5.setImage(false)

    val item6 = JishiqiItem(this)
    item6.setItemText("60 min")
    item6.setImage(false)

    val item7 = JishiqiItem(this)
    item7.setItemText("90 min")
    item7.setImage(false)

    list.addView(item1)
    list.addView(item2)
    list.addView(item3)
    list.addView(item4)
    list.addView(item5)
    list.addView(item6)
    list.addView(item7)

    item1.getRoot().setOnClickListener {
        item1.setImage(!item1.getImage())
        item2.setImage(false)
        item3.setImage(false)
        item4.setImage(false)
        item5.setImage(false)
        item6.setImage(false)
        item7.setImage(false)
    }

    item2.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(!item2.getImage())
        item3.setImage(false)
        item4.setImage(false)
        item5.setImage(false)
        item6.setImage(false)
        item7.setImage(false)
    }


    item3.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(false)
        item3.setImage(!item3.getImage())
        item4.setImage(false)
        item5.setImage(false)
        item6.setImage(false)
        item7.setImage(false)
    }

    item4.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(false)
        item3.setImage(false)
        item4.setImage(!item4.getImage())
        item5.setImage(false)
        item6.setImage(false)
        item7.setImage(false)
    }

    item5.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(false)
        item3.setImage(false)
        item4.setImage(false)
        item5.setImage(!item5.getImage())
        item6.setImage(false)
        item7.setImage(false)
    }

    item6.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(false)
        item3.setImage(false)
        item4.setImage(false)
        item5.setImage(false)
        item6.setImage(!item6.getImage())
        item7.setImage(false)
    }


    item7.getRoot().setOnClickListener {
        item1.setImage(false)
        item2.setImage(false)
        item3.setImage(false)
        item4.setImage(false)
        item5.setImage(false)
        item6.setImage(false)
        item7.setImage(!item7.getImage())
    }

    return dialog
}

fun AppCompatActivity.getExitAlert(): AlertDialog {
    val dialog = AlertDialog.Builder(this).create()
    val v = layoutInflater.inflate(R.layout.exit_alert, null)
    dialog.setView(v)
    val adView: FrameLayout = v.findViewById(R.id.adView)
    (this as Outta).showYuanshengAd {
        it?.let {
            adView.removeAllViews()
            adView.addView(it)
        }
    }
    v.findViewById<TextView>(R.id.cancel).setOnClickListener { dialog.dismiss() }
    v.findViewById<TextView>(R.id.confirm).setOnClickListener { EventBus.getDefault().post(Event("tuima")) }
    return dialog
}