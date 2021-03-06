package com.xzg.www.lib_common

import android.app.Application
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger
import com.xzg.www.lib_common.utils.Utils


/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 *
 *
 */
open class BaseApplication : Application() {


    companion object {
        fun sInstance() = this
    }

    override fun onCreate() {
        super.onCreate()

        Logger.init("pattern").logLevel(LogLevel.FULL)
        Utils.init(this)
    }
}