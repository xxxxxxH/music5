package jboster.poshe.one.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.hjq.permissions.XXPermissions

fun AppCompatActivity.huoQuQuanXian(jieguo: (Boolean) -> Unit) {
    XXPermissions.with(this).permission(permissions).request { _, result ->
        jieguo(result)
    }
}

fun AppCompatActivity.xiayigeYemian(clazz: Class<*>, guangbuguandangqianyiemian:Boolean = false){
    startActivity(Intent(this, clazz))
    if (guangbuguandangqianyiemian){
        finish()
    }
}