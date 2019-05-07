package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ujhgl.lohsy.ljsomsh.PTController;
import com.ujhgl.lohsy.ljsomsh.PTError;
import com.ujhgl.lohsy.ljsomsh.PTGameUser;
import com.ujhgl.lohsy.ljsomsh.PTGoods;
import com.ujhgl.lohsy.ljsomsh.PTInitCallBack;
import com.ujhgl.lohsy.ljsomsh.PTLog;
import com.ujhgl.lohsy.ljsomsh.PTLoginCallBack;
import com.ujhgl.lohsy.ljsomsh.PTLogoutCallBack;
import com.ujhgl.lohsy.ljsomsh.PTShare;
import com.ujhgl.lohsy.ljsomsh.PTShareCallBack;
import com.ujhgl.lohsy.ljsomsh.PTShareType;
import com.ujhgl.lohsy.ljsomsh.PTUser;
import com.ujhgl.lohsy.ljsomsh.gamecontrol.PTGameControlCallBack;


public class MainActivity
        extends Activity
        implements PTInitCallBack,
        PTLoginCallBack,
        PTLogoutCallBack,
        PTShareCallBack,PTGameControlCallBack,
        ActivityCompat.OnRequestPermissionsResultCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        final PTController aPlatform = PTController.instance();
        aPlatform.setInitListener(this);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // listeners
        aPlatform.setLoginListener(this);
        aPlatform.setLogoutListener(this);

        // GameControl
       // aPlatform.setmGameControlListener(this);

        aPlatform.setShareListener(this);

        //PTController.instance().requestPermissions(this);


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
                    PTController aPlatform	= PTController.instance();
                    PTUser aUser			= aPlatform.getUser();
                    PTShare aShare			= aPlatform.getShare(PTShareType.Facebook);
                    String aLocale			= aPlatform.getLocale();
                    String aExtra = "aExtra";//自定义字段，服务器发货会传给游戏服务器

                    String gameRole = "role";
                    String gameServer = "1";
                    //若传自定义字段请使用 PTGameUser aGameUser = new PTGameUser(gameRole, gameServer, aLocale,aExtra);
                    PTGameUser aGameUser = new PTGameUser(gameRole, gameServer, aLocale);
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
                PTController.instance().showFAQs(MainActivity.this);
            }
        });

        //发起会话
        Button cvtBtn = (Button)findViewById(R.id.mosdk_demo_id_conversation);
        cvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PTController.instance().showConversation(MainActivity.this);
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

        final PTController aPlatform = PTController.instance();
        if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
            return;

        super.onActivityResult(aRequestCode, aResultCode, aData);
    }



    @Override
    public void onStop()
    {
        super.onStop();

        PTController platform = PTController.instance();
        platform.dismisFloatWindow(this);
        platform.inactive(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();


        PTController platform = PTController.instance();
        if (platform.logined())
        {
            String aLocale			= platform.getLocale();

            PTGameUser aGameUser = new PTGameUser("1000", "1", aLocale);
            platform.showFloatWindow(this,aGameUser);
        }
        PTController.instance().active(this);
    }

    //请求权限
    public  void requestUerPermission()
    {
        String[] permisions = {};
        int callbackrequeatCode = 109;
//        PT manager = new PTPermissionManager();
//        Activity activity = this;
//
//        /**
//         * permisions 权限数组
//         * activity 回调Activity，需要实现ActivityCompat.OnRequestPermissionsResultCallback的方法
//         * callbackrequeatCode 在activity 的
//         *
//         * */
//        manager.requestMultiplyPermissions(permisions,activity,callbackrequeatCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults)
    {
        // TODO: 授权回调，判断是否拿到用户授权 requestCode 发起授权请求传入的返回状态码，permissions 申请的权限， grantResults 用户授权结果
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }



    @Override
    public void initSuccess(PTController platform)
    {

        mInited = true;

        PTLog.info("Demo initSuccess");

        mLogin.setEnabled(true);

        if (!platform.logined())
        {
            platform.launchAutomaticLoginFlow(this);
        }



    }

    @Override
    public void initFailure(PTError error)
    {

        mInited = false;

        PTLog.info("Demo initFailure: %s", error);
    }

    public void loginCancelled()
    {

        finish();
        System.exit(0);
    }

    public void loginSuccess(PTUser aUser)
    {
        mLogin.setEnabled(false);
        mRP.setEnabled(true);
        mUC.setEnabled(true);
        mFB.setEnabled(true);

        //用户唯一标识 aUser.getID();

        PTController aPlatform	= PTController.instance();
        String aLocale			= aPlatform.getLocale();

        String gameRole = "1000";
        String gameServerId = "1";
        PTGameUser aGameUser = new PTGameUser(gameRole, gameServerId, aLocale);
       PTController.instance().showFloatWindow(this,aGameUser);
    }

    public void loginFailure(PTError aError)
    {

        PTLog.info("Demo loginFailure: %s", aError);
    }

    public void logoutSuccess(String aUser)
    {

        PTLog.info("Demo logoutSuccess: %s", aUser);
    }

    public void logoutFailure(PTError aError)
    {

        PTLog.info("Demo logoutFailure: %s", aError);
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
    private PTGoods[] mProducts;

    @Override
    public void shouldGoToTestServer(PTUser user) {

        //显示测试服
        Intent intent = new Intent(this,TestServerActivity.class);
        startActivity(intent);

    }

    @Override
    public void shouldGoToOfficialServer(PTUser user) {

        //显示游戏服
        Intent intent = new Intent(this,OfficialActivity.class);
        startActivity(intent);

    }
}
