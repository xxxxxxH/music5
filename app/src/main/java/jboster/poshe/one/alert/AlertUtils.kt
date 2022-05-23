package jboster.poshe.one.alert

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import jboster.poshe.one.R
import jboster.poshe.one.utils.ShareUtils


fun AppCompatActivity.getShareAlert():AlertDialog{
    val dialog = AlertDialog.Builder(this).create()
    val v = layoutInflater.inflate(R.layout.layout_share,null)
    dialog.setView(v)
    v.findViewById<TextView>(R.id.shareInFb).setOnClickListener {
        ShareUtils.get().shareWithFb(this,"")
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