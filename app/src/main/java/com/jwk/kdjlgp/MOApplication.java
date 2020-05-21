package com.jwk.kdjlgp;

import android.app.Application;

import com.ujhgl.lohsy.ljsomsh.HYCenter;



/**
 * Created by dengliang.wang on 17/11/7.
 */

public class MOApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HYCenter.shared().ApplicationInit(this);

    }

    @Override
    public void onTerminate() {

        HYCenter.shared().applicationDestroy();
        super.onTerminate();
    }
}
