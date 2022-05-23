package jboster.poshe.one.utils

import androidx.appcompat.app.AppCompatActivity

var b = false

fun zhanshikaiping(clazz: Class<*>, b: () -> Unit) {
    b()
}

fun AppCompatActivity.chapingguanle(clazz: Class<*>) {
    if (configEntity.displayOpenAdWithInsertAd()) {
        tiaoqizou(clazz)
    }
}

fun AppCompatActivity.shanpingguanle(clazz: Class<*>) {
    tiaoqizou(clazz)
}

fun AppCompatActivity.tiaoqizou(clazz: Class<*>) {
    if (b) {
        b = !b
        xiayigeYemian(clazz, true)
    }
}