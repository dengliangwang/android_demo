package com.jwk.kdjlgp;

import android.app.Application;
import com.morlia.mosdk.MOPlatform;

/**
 * Created by dengliang.wang on 17/11/7.
 */

public class MOApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MOPlatform.instance().init(this);

    }

    @Override
    public void onTerminate() {

        MOPlatform.instance().destroy();
        super.onTerminate();
    }
}
