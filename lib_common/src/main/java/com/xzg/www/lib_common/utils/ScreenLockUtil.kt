package com.xzg.www.lib_common.utils

import android.app.Activity
import android.app.KeyguardManager
import android.app.KeyguardManager.KeyguardLock
import android.content.Context
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.util.Log

import java.util.HashMap

/**
 * 用于保持屏幕高亮的工具
 */
class ScreenLockUtil private constructor() {

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {
        private val TAG = "ScreenLockUtil"

        private val mWakeLockArray = HashMap<Activity, WakeLock>()
        private val mIsUnlockArray = HashMap<Activity, Boolean>()


        /**
         * 保持屏幕常亮
         *
         * @param activity you know
         */
        fun keepScreenOn(activity: Activity) {
            var wakeLock = mWakeLockArray[activity]
            if (wakeLock == null) {
                val powerManager = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
                wakeLock = powerManager.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.FULL_WAKE_LOCK,
                    activity.javaClass.name
                )
            }

            if (!wakeLock!!.isHeld) {
                wakeLock.acquire()
            }

            mWakeLockArray[activity] = wakeLock

            cancelLockScreen(activity)

            Log.i(TAG, "开启屏幕常亮")
        }


        /**
         * 取消屏幕常亮
         *
         * @param activity you know
         */
        fun cancelKeepScreen(activity: Activity) {
            val wakeLock = mWakeLockArray[activity]
            if (wakeLock != null) {
                if (wakeLock.isHeld) {
                    wakeLock.release()
                }
            }

            Log.i(TAG, "取消屏幕常亮")
        }

        /**
         * 取消锁屏限制
         *
         * @param activity you know
         */
        private fun cancelLockScreen(activity: Activity) {
            val isUnlock = mIsUnlockArray[activity]
            if (isUnlock != null && isUnlock) {
                return
            }
            val mKeyguardManager = activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            val mKeyguardLock = mKeyguardManager.newKeyguardLock(activity.javaClass.name)
            mKeyguardLock.disableKeyguard()

            mIsUnlockArray[activity] = true
        }
    }
}
