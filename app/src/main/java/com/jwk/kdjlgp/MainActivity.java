package com.jwk.kdjlgp;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ujhgl.lohsy.ljsomsh.HYCenter;
import com.ujhgl.lohsy.ljsomsh.HYError;
import com.ujhgl.lohsy.ljsomsh.HYGameUser;
import com.ujhgl.lohsy.ljsomsh.HYInitDelegate;
import com.ujhgl.lohsy.ljsomsh.HYLog;
import com.ujhgl.lohsy.ljsomsh.HYLoginDelegate;
import com.ujhgl.lohsy.ljsomsh.HYLogoutDelegate;
import com.ujhgl.lohsy.ljsomsh.HYShare;
import com.ujhgl.lohsy.ljsomsh.HYShareDelegate;
import com.ujhgl.lohsy.ljsomsh.HYShareType;
import com.ujhgl.lohsy.ljsomsh.HYUser;
import com.ujhgl.lohsy.ljsomsh.gamecontrol.HYGameControlDelegate;

import java.util.HashMap;


public class MainActivity
        extends Activity
        implements HYInitDelegate,
        HYLoginDelegate,
        HYLogoutDelegate,
        HYShareDelegate, HYGameControlDelegate,
        ActivityCompat.OnRequestPermissionsResultCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        final HYCenter aPlatform = HYCenter.shared();
        aPlatform.setInitDelegate(this);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // listeners
        aPlatform.setLoginDelegate(this);
        aPlatform.setLogoutDelegate(this);

        // GameControl
       // aPlatform.setmGameControlListener(this);

        aPlatform.setShareDelegate(this);

        //HYCenter.shared().requestPermissions(this);


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

                if (!aPlatform.userHasLogged())
                {
                    aPlatform.login(MainActivity.this);
                }
            }
        });
        mLogin = aLogin;

        // request products
        Button aRP = (Button)findViewById(R.id.mosdk_demo_id_rp);
        aRP.setEnabled(aPlatform.userHasLogged());
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
        aUC.setEnabled(aPlatform.userHasLogged());
        aUC.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.userHasLogged())
                {
                    aPlatform.presentUserCenter(MainActivity.this);
                }
            }
        });
        mUC = aUC;

        // fb
        Button aFB = (Button)findViewById(R.id.mosdk_demo_id_fb);
        aFB.setEnabled(aPlatform.userHasLogged());
        aFB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                if (aPlatform.userHasLogged())
                {
                    HYCenter aPlatform	= HYCenter.shared();
                    HYUser aUser			= aPlatform.getUser();
                    HYShare aShare			= aPlatform.getShare(HYShareType.Facebook);
                    String aLocale			= aPlatform.getLocale();
                    String aExtra = "aExtra";//自定义字段，服务器发货会传给游戏服务器

                    String gameRole = "role";
                    String gameServer = "1";

                    //若传自定义字段请使用 MOGameUser aGameUser = new MOGameUser(gameRole, gameServer, aLocale,aExtra);
                    HYGameUser aGameUser = new HYGameUser(gameRole, gameServer, aLocale);

                    aShare.showFB(MainActivity.this,aUser,aGameUser, null);

                }
            }
        });
        mFB = aFB;

        //常见问题
        Button faqBtn = (Button)findViewById(R.id.mosdk_demo_id_faq);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HYCenter.shared().showHelpCenter(MainActivity.this);
            }
        });

        //发起会话
        Button cvtBtn = (Button)findViewById(R.id.mosdk_demo_id_conversation);
        cvtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HYCenter.shared().startConversation(MainActivity.this);
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

        final HYCenter aPlatform = HYCenter.shared();
        if (aPlatform.onActivityResult(this, aRequestCode, aResultCode, aData))
            return;

        super.onActivityResult(aRequestCode, aResultCode, aData);
    }



    @Override
    public void onStop()
    {
        super.onStop();

        HYCenter platform = HYCenter.shared();
        platform.dismisFloatWindow(this);
        platform.inactive(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();


        HYCenter platform = HYCenter.shared();
        if (platform.userHasLogged())
        {
            String aLocale			= platform.getLocale();

            HYGameUser aGameUser = new HYGameUser("1000", "1", aLocale);
            platform.presentFloatWindow(this,aGameUser);
        }
        HYCenter.shared().active(this);
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
    public void initSuccess(HYCenter platform)
    {

        mInited = true;

        HYLog.info("Demo initSuccess");

        mLogin.setEnabled(true);

        if (!platform.userHasLogged())
        {
            platform.automaticLogin(this);
        }



    }

    @Override
    public void initFailure(HYError error)
    {

        mInited = false;

        HYLog.info("Demo initFailure: %s", error);
    }

    public void loginCancelled()
    {

        finish();
        System.exit(0);
    }

    public void loginSuccess(HYUser aUser)
    {
        mLogin.setEnabled(false);
        mRP.setEnabled(true);
        mUC.setEnabled(true);
        mFB.setEnabled(true);

        //用户唯一标识 aUser.getID();

        HYCenter aPlatform	= HYCenter.shared();
        String aLocale			= aPlatform.getLocale();

        String gameRole = "1000";
        String gameServerId = "1";
        HYGameUser aGameUser = new HYGameUser(gameRole, gameServerId, aLocale);
        HYCenter.shared().presentFloatWindow(this,aGameUser);
    }

    public void loginFailure(HYError aError)
    {

        HYLog.info("Demo loginFailure: %s", aError);
    }

    public void logoutSuccess(String aUser)
    {

        HYLog.info("Demo logoutSuccess: %s", aUser);
    }

    public void logoutFailure(HYError aError)
    {

        HYLog.info("Demo logoutFailure: %s", aError);
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




    @Override
    public void enterServerWithUserInfor(HYUser HYUser, int i) {

        //上传服务器标识和角色名
        String aServer = "10";//游戏服标识
        String aRole = "Vayne";//角色名
        HashMap<String,String> aPlayerInfor = new HashMap<String, String>();
        aPlayerInfor.put("server",aServer);
        aPlayerInfor.put("role",aRole);
        HYCenter.shared().submitPlayerInfo(aPlayerInfor);

        if ( i == 0){
            //显示测试服
            Intent intent = new Intent(this,TestServerActivity.class);
            startActivity(intent);

        }
        else  if (i == 0){
            //显示游戏服
            Intent intent = new Intent(this,OfficialActivity.class);
            startActivity(intent);
        }
    }
}
