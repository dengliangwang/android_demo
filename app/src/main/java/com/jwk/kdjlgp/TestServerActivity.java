package com.jwk.kdjlgp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class TestServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_server);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//            return true;//不执行父类点击事件
//        return super.onKeyDown(keyCode, event);
//    }
}
