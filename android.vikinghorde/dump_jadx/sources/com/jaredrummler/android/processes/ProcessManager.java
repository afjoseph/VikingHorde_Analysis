package com.jaredrummler.android.processes;

public class ProcessManager {
    public static final java.lang.String TAG = "AndroidProcesses";
    private static boolean loggingEnabled;

    public static final class ProcessComparator implements java.util.Comparator<com.jaredrummler.android.processes.models.AndroidProcess> {
        public int compare(com.jaredrummler.android.processes.models.AndroidProcess p1, com.jaredrummler.android.processes.models.AndroidProcess p2) {
            return p1.name.compareToIgnoreCase(p2.name);
        }
    }

    public static void setLoggingEnabled(boolean enabled) {
        loggingEnabled = enabled;
    }

    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public static void log(java.lang.String message, java.lang.Object... args) {
        if (loggingEnabled) {
            java.lang.String str = "AndroidProcesses";
            if (args.length != 0) {
                message = java.lang.String.format(message, args);
            }
            android.util.Log.d(str, message);
        }
    }

    public static void log(java.lang.Throwable error, java.lang.String message, java.lang.Object... args) {
        if (loggingEnabled) {
            java.lang.String str = "AndroidProcesses";
            if (args.length != 0) {
                message = java.lang.String.format(message, args);
            }
            android.util.Log.d(str, message, error);
        }
    }

    public static java.util.List<com.jaredrummler.android.processes.models.AndroidProcess> getRunningProcesses() {
        java.util.List<com.jaredrummler.android.processes.models.AndroidProcess> processes = new java.util.ArrayList();
        for (java.io.File file : new java.io.File("/proc").listFiles()) {
            if (file.isDirectory()) {
                try {
                    try {
                        processes.add(new com.jaredrummler.android.processes.models.AndroidProcess(java.lang.Integer.parseInt(file.getName())));
                    } catch (java.io.IOException e) {
                        log(e, "Error reading from /proc/%d.", java.lang.Integer.valueOf(pid));
                    }
                } catch (java.lang.NumberFormatException e2) {
                }
            }
        }
        return processes;
    }

    public static java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> getRunningAppProcesses() {
        java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> processes = new java.util.ArrayList();
        for (java.io.File file : new java.io.File("/proc").listFiles()) {
            if (file.isDirectory()) {
                try {
                    try {
                        processes.add(new com.jaredrummler.android.processes.models.AndroidAppProcess(java.lang.Integer.parseInt(file.getName())));
                    } catch (com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException e) {
                    } catch (java.io.IOException e2) {
                        log(e2, "Error reading from /proc/%d.", java.lang.Integer.valueOf(pid));
                    }
                } catch (java.lang.NumberFormatException e3) {
                }
            }
        }
        return processes;
    }

    public static java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> getRunningForegroundApps(android.content.Context ctx) {
        java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> processes = new java.util.ArrayList();
        java.io.File[] files = new java.io.File("/proc").listFiles();
        android.content.pm.PackageManager pm = ctx.getPackageManager();
        for (java.io.File file : files) {
            if (file.isDirectory()) {
                try {
                    try {
                        com.jaredrummler.android.processes.models.AndroidAppProcess process = new com.jaredrummler.android.processes.models.AndroidAppProcess(java.lang.Integer.parseInt(file.getName()));
                        if (process.foreground && !((process.uid >= 1000 && process.uid <= 9999) || process.name.contains(":") || pm.getLaunchIntentForPackage(process.getPackageName()) == null)) {
                            processes.add(process);
                        }
                    } catch (com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException e) {
                    } catch (java.io.IOException e2) {
                        log(e2, "Error reading from /proc/%d.", java.lang.Integer.valueOf(pid));
                    }
                } catch (java.lang.NumberFormatException e3) {
                }
            }
        }
        return processes;
    }

    public static boolean isMyProcessInTheForeground() {
        java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> processes = getRunningAppProcesses();
        int myPid = android.os.Process.myPid();
        for (com.jaredrummler.android.processes.models.AndroidAppProcess process : processes) {
            if (process.pid == myPid && process.foreground) {
                return true;
            }
        }
        return false;
    }

    public static java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcessInfo(android.content.Context ctx) {
        if (android.os.Build.VERSION.SDK_INT < 22) {
            return ((android.app.ActivityManager) ctx.getSystemService("activity")).getRunningAppProcesses();
        }
        java.util.List<com.jaredrummler.android.processes.models.AndroidAppProcess> runningAppProcesses = getRunningAppProcesses();
        java.util.List<android.app.ActivityManager.RunningAppProcessInfo> arrayList = new java.util.ArrayList();
        for (com.jaredrummler.android.processes.models.AndroidAppProcess process : runningAppProcesses) {
            android.app.ActivityManager.RunningAppProcessInfo info = new android.app.ActivityManager.RunningAppProcessInfo(process.name, process.pid, null);
            info.uid = process.uid;
            arrayList.add(info);
        }
        return arrayList;
    }

    private ProcessManager() {
        throw new java.lang.AssertionError("no instances");
    }
}
