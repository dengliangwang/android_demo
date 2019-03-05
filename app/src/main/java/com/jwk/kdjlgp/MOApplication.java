package com.jwk.kdjlgp;

import android.app.Application;

import com.ujhgl.lohsy.ljsomsh.PTController;


/**
 * Created by dengliang.wang on 17/11/7.
 */

public class MOApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PTController.instance().init(this);

    }

    @Override
    public void onTerminate() {

        PTController.instance().destroy();
        super.onTerminate();
    }
}
