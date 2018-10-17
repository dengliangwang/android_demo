package com.jwk.kdjlgp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.morlia.mosdk.MOConstants;
import com.morlia.mosdk.MOError;
import com.morlia.mosdk.MOGameUser;
import com.morlia.mosdk.MOInitListener;
import com.morlia.mosdk.MOLog;
import com.morlia.mosdk.MOLoginListener;
import com.morlia.mosdk.MOLogoutListener;
import com.morlia.mosdk.MOPlatform;
import com.morlia.mosdk.MOProduct;
import com.morlia.mosdk.MOShare;
import com.morlia.mosdk.MOShareListener;
import com.morlia.mosdk.MOShareType;
import com.morlia.mosdk.MOUser;
import com.morlia.mosdk.floatwindow.FloatActionController;
import com.morlia.mosdk.floatwindow.permission.FloatPermissionManager;
import com.morlia.mosdk.gamecontrol.MOGameControlListener;
import com.morlia.mosdk.morlia.PermissionManager;
import com.tencent.bugly.crashreport.CrashReport;


public class MainActivity
        extends AppCompatActivity
        implements  MOInitListener,
                    MOLoginListener,
                    MOLogoutListener,
                    MOShareListener ,MOGameControlListener,
        ActivityCompat.OnRequestPermissionsResultCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        final MOPlatform aPlatform = MOPlatform.instance();
        aPlatform.setInitListener(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // listeners
        aPlatform.setLoginListener(this);
        aPlatform.setLogoutListener(this);

        // GameControl
        aPlatform.setmGameControlListener(this);

        aPlatform.setShareListener(this);

        //MOPlatform.instance().requestPermissions(this);


        // 显示包名，版本号 build号相关
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = this.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            //包名
            TextView pn = (TextView)findViewById(R.id.mosdk_demo_id_pn);
            pn.setText("包名 " + pkName);
            //版本号
            TextView vn = (TextView)findViewById(R.id.mosdk_demo_id_vn);
            vn.setText("version_name " + versionName);
            //编译号
            TextView vc = (TextView)findViewById(R.id.mosdk_demo_id_vc);
            vc.setText("version_code " + versionCode);

        } catch (Exception e) {

        }


        // login
        Button aLogin = (Button)findViewById(R.id.mosdk_demo_id_login);
        aLogin.setEnabled(true);
        aLogin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

               // CrashReport.testJavaCrash();

                if (!aPlatform.logined())
                {
                    aPlatform.login(MainActivity.this);
                }
            }
        });
        mLogin = aLogin;

        // request products
        Button aRP = (Button)findViewById(R.id.mosdk_demo_id_rp);
        aRP.setEnabled(aPlatform.logined());
        aRP.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Product_List.class);

                startActivity(intent);

            }
        });
        mRP = aRP;



        // user center
        Button aUC = (Button)findViewById(R.id.mosdk_demo_id_uc);
        aUC.setEnabled(aPlatform.logined());
        aUC.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.logined())
                {
                    aPlatform.showUserCenter(MainActivity.this);
                }
            }
        });
        mUC = aUC;

        // fb
        Button aFB = (Button)findViewById(R.id.mosdk_demo_id_fb);
        aFB.setEnabled(aPlatform.logined());
        aFB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.logined())
                {
                    MOPlatform aPlatform	= MOPlatform.instance();
                    MOUser aUser			= aPlatform.getUser();
                    MOShare aShare			= aPlatform.getShare(MOShareType.Facebook);
                    String aLocale			= aPlatform.getLocale();
                    String aExtra = "aExtra";//自定义字段，服务器发货会传给游戏服务器

                    String gameRole = "role";
                    String gameServer = "1";
                    //若传自定义字段请使用 MOGameUser aGameUser = new MOGameUser(gameRole, gameServer, aLocale,aExtra);
                    MOGameUser aGameUser = new MOGameUser(gameRole, gameServer, aLocale);
                    aShare.showSnsView(MainActivity.this,aUser,aGameUser, null);

                }
            }
        });
        mFB = aFB;

        //常见问题
        Button faqBtn = (Button)findViewById(R.id.mosdk_demo_id_faq);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MOPlatform.instance().showFAQs(MainActivity.this);
            }
        });

        //发起会话
        Button cvtBtn = (Button)findViewById(R.id.mosdk_demo_id_conversation);
        cvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MOPlatform.instance().showConversation(MainActivity.this);
            }
        });



    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }

    @Override
    public void onActivityResult(int aRequestCode, int aResultCode, Intent aData)
    {

        final MOPlatform aPlatform = MOPlatform.instance();
        if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
            return;

        super.onActivityResult(aRequestCode, aResultCode, aData);
    }



    @Override
    public void onStop()
    {
        super.onStop();

        MOPlatform platform = MOPlatform.instance();
        platform.dismisFloatWindow(this);
        platform.inactive(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();


        MOPlatform platform = MOPlatform.instance();
        if (platform.logined())
        {
            String aLocale			= platform.getLocale();

            MOGameUser aGameUser = new MOGameUser("1000", "1", aLocale);
            platform.showFloatWindow(this,aGameUser);
        }
        MOPlatform.instance().active(this);
    }

    //请求权限
    public  void requestUerPermission()
    {
        String[] permisions = {};
        int callbackrequeatCode = 109;
        PermissionManager manager = new PermissionManager();
        Activity activity = this;

        /**
         * permisions 权限数组
         * activity 回调Activity，需要实现ActivityCompat.OnRequestPermissionsResultCallback的方法
         * callbackrequeatCode 在activity 的
         *
         * */
        manager.requestMultiplyPermissions(permisions,activity,callbackrequeatCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults)
    {
        // TODO: 授权回调，判断是否拿到用户授权 requestCode 发起授权请求传入的返回状态码，permissions 申请的权限， grantResults 用户授权结果
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }



    @Override
    public void initSuccess(MOPlatform platform)
    {

        mInited = true;

        MOLog.info("Demo initSuccess");

        mLogin.setEnabled(true);



    }

    @Override
    public void initFailure(MOError error)
    {

        mInited = false;

        MOLog.info("Demo initFailure: %s", error);
    }

    public void loginCancelled()
    {

        finish();
        System.exit(0);
    }

    public void loginSuccess(MOUser aUser)
    {
        mLogin.setEnabled(false);
        mRP.setEnabled(true);
        mUC.setEnabled(true);
        mFB.setEnabled(true);

        MOPlatform aPlatform	= MOPlatform.instance();
        String aLocale			= aPlatform.getLocale();

        String gameRole = "1000";
        String gameServerId = "1";
        MOGameUser aGameUser = new MOGameUser(gameRole, gameServerId, aLocale);
       MOPlatform.instance().showFloatWindow(this,aGameUser);
    }

    public void loginFailure(MOError aError)
    {

        MOLog.info("Demo loginFailure: %s", aError);
    }

    public void logoutSuccess(String aUser)
    {

        MOLog.info("Demo logoutSuccess: %s", aUser);
    }

    public void logoutFailure(MOError aError)
    {

        MOLog.info("Demo logoutFailure: %s", aError);
    }


    @Override
    public void onShareSuccess()
    {

    }

    @Override
    public void onShareError()
    {

    }

    @Override
    public void onShareCancel()
    {

    }

    private boolean 	mInited;
    private Button		mLogin;
    private Button		mRP;
    //private Button		mBuy;
    private Button		mUC;
    private Button		mFB;
    private MOProduct[] mProducts;

    @Override
    public void shouldGoToTestServer(MOUser user) {

        //显示测试服
        Intent intent = new Intent(this,TestServerActivity.class);
        startActivity(intent);

    }

    @Override
    public void shouldGoToOfficialServer(MOUser user) {

        //显示游戏服
        Intent intent = new Intent(this,OfficialActivity.class);
        startActivity(intent);

    }
}
