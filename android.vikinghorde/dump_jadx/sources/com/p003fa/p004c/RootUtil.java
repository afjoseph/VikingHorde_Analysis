package com.p003fa.p004c;

/* renamed from: com.fa.c.RootUtil */
public class RootUtil {
    public static boolean isDeviceRooted() {
        return com.p003fa.p004c.RootUtil.checkRootMethod1() || com.p003fa.p004c.RootUtil.checkRootMethod2() || com.p003fa.p004c.RootUtil.checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        java.lang.String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        for (java.lang.String path : new java.lang.String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"}) {
            if (new java.io.File(path).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        java.lang.Process process = null;
        try {
            process = java.lang.Runtime.getRuntime().exec(new java.lang.String[]{"/system/xbin/which", "su"});
            if (new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream())).readLine() == null) {
                if (process != null) {
                    process.destroy();
                }
                return false;
            } else if (process == null) {
                return true;
            } else {
                process.destroy();
                return true;
            }
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
    }
}
