package com.xzg.www.myzujianhua

import com.alibaba.android.arouter.launcher.ARouter
import com.xzg.www.lib_common.BaseApplication
import com.xzg.www.lib_common.utils.Utils

class MyApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        if (Utils.isAppDebug) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }
}