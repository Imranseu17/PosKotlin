package com.example.pos_kotlin

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object DisplayUtil {

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获得屏幕尺寸
     * @param context
     * @return
     */
    fun getScreenSize(context: Context): Point {
        val point = Point()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getSize(point)
        return point
    }
}
