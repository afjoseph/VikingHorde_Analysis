package com.p003fa.p004c;

/* renamed from: com.fa.c.StartService */
public class StartService extends android.app.Service {
    public void onCreate() {
    }

    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
        startForeground(1, new android.app.Notification());
        stopSelf();
        return 2;
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
        stopForeground(true);
    }
}
