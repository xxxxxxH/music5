package jboster.poshe.one.utils

import android.app.ActivityManager
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import jboster.poshe.one.R
import jboster.poshe.one.base.TouchApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun AppCompatActivity.appendBanner() {
    val content = findViewById<ViewGroup>(android.R.id.content)
    val frameLayout = FrameLayout(this)
    val p = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    frameLayout.layoutParams = p

    val linearLayout = LinearLayout(this)
    val p1 = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
    )
    linearLayout.layoutParams = p1

    val banner = TouchApplication.app.getBannerAd()
    "banner $banner".loges()
    lifecycleScope.launch(Dispatchers.IO) {
        delay(3000)
        banner.loadAd()
        withContext(Dispatchers.Main) {
            val p2 =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp2px(this@appendBanner, 50f)
                )
            p2.gravity = Gravity.BOTTOM
            banner.layoutParams = p2
            linearLayout.addView(banner)
            frameLayout.addView(linearLayout)
            content.addView(frameLayout)
        }
    }
}

fun dp2px(context: Context, dp: Float): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density + 0.5f).toInt()
}

fun isInBackground(): Boolean {
    val activityManager =
        TouchApplication.app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcesses = activityManager
        .runningAppProcesses
    for (appProcess in appProcesses) {
        if (appProcess.processName == TouchApplication.app.packageName) {
            return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
    }
    return false
}

fun AppCompatActivity.addOpen(showOpen: (ViewGroup) -> Unit) {
    val content = findViewById<ViewGroup>(android.R.id.content)
    (content.getTag(R.id.open_ad_view_id) as? FrameLayout)?.let {
        showOpen(it)
    } ?: kotlin.run {
        FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            content.addView(this)
            content.setTag(R.id.open_ad_view_id, this)
            showOpen(this)
        }
    }
}