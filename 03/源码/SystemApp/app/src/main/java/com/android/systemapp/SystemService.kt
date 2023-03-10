package com.android.systemapp

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.os.IBinder
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import java.lang.Thread.sleep


class SystemService : Service() {

    private val TAG = SystemService::class.java.simpleName;

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        // 创建用于 window 显示的context
        val dm = getSystemService(DisplayManager::class.java)
        val defaultDisplay = dm.getDisplay(Display.DEFAULT_DISPLAY)
        val defaultDisplayContext = createDisplayContext(defaultDisplay)
        val ctx = defaultDisplayContext.createWindowContext(TYPE_APPLICATION_OVERLAY, null);

        // 在屏幕上绘制一个像素的view，用于监控开机动画是否播放完毕
        val mWindowManager = ctx.getSystemService(WindowManager::class.java)
        val bounds = mWindowManager.getCurrentWindowMetrics().getBounds();

        val windowSizeTest: View = object : View(ctx) {
            override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
                Log.e(TAG, "system launch")
            }
        }
        val testParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        testParams.width = bounds.width() / 2
        testParams.height = bounds.height() / 2
        testParams.gravity = Gravity.CENTER
        testParams.title = TAG
        mWindowManager.addView(windowSizeTest, testParams)
    }
}