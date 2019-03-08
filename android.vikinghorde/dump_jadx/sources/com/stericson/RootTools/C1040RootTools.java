package com.stericson.RootTools;

/* renamed from: com.stericson.RootTools.RootTools */
public final class C1040RootTools {
    public static boolean debugMode = false;
    public static int default_Command_Timeout = 20000;
    public static boolean handlerEnabled = true;
    private static com.stericson.RootTools.internal.RootToolsInternalMethods rim = null;
    public static java.lang.String utilPath;

    public static void setRim(com.stericson.RootTools.internal.RootToolsInternalMethods rim) {
        rim = rim;
    }

    private static final com.stericson.RootTools.internal.RootToolsInternalMethods getInternals() {
        if (rim != null) {
            return rim;
        }
        com.stericson.RootTools.internal.RootToolsInternalMethods.getInstance();
        return rim;
    }

    public static boolean checkUtil(java.lang.String util) {
        return com.stericson.RootTools.C1040RootTools.getInternals().checkUtil(util);
    }

    public static void closeAllShells() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.closeAllShells();
    }

    public static void closeCustomShell() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.closeCustomShell();
    }

    public static void closeShell(boolean root) throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.closeShell(root);
    }

    public static boolean copyFile(java.lang.String source, java.lang.String destination, boolean remountAsRw, boolean preserveFileAttributes) {
        return com.stericson.RootTools.C1040RootTools.getInternals().copyFile(source, destination, remountAsRw, preserveFileAttributes);
    }

    public static boolean deleteFileOrDirectory(java.lang.String target, boolean remountAsRw) {
        return com.stericson.RootTools.C1040RootTools.getInternals().deleteFileOrDirectory(target, remountAsRw);
    }

    public static boolean exists(java.lang.String file) {
        return com.stericson.RootTools.C1040RootTools.exists(file, false);
    }

    public static boolean exists(java.lang.String file, boolean isDir) {
        return com.stericson.RootShell.C1029RootShell.exists(file, isDir);
    }

    public static void fixUtil(java.lang.String util, java.lang.String utilPath) {
        com.stericson.RootTools.C1040RootTools.getInternals().fixUtil(util, utilPath);
    }

    public static boolean fixUtils(java.lang.String[] utils) throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getInternals().fixUtils(utils);
    }

    public static java.util.List<java.lang.String> findBinary(java.lang.String binaryName) {
        return com.stericson.RootShell.C1029RootShell.findBinary(binaryName);
    }

    public static java.lang.String getBusyBoxVersion(java.lang.String path) {
        return com.stericson.RootTools.C1040RootTools.getInternals().getBusyBoxVersion(path);
    }

    public static java.lang.String getBusyBoxVersion() {
        return com.stericson.RootTools.C1040RootTools.getBusyBoxVersion("");
    }

    public static java.util.List<java.lang.String> getBusyBoxApplets() throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getBusyBoxApplets("");
    }

    public static java.util.List<java.lang.String> getBusyBoxApplets(java.lang.String path) throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getInternals().getBusyBoxApplets(path);
    }

    public static com.stericson.RootShell.execution.Shell getCustomShell(java.lang.String shellPath, int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getCustomShell(shellPath, timeout);
    }

    public static com.stericson.RootShell.execution.Shell getCustomShell(java.lang.String shellPath) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootTools.C1040RootTools.getCustomShell(shellPath, 10000);
    }

    public static com.stericson.RootTools.containers.Permissions getFilePermissionsSymlinks(java.lang.String file) {
        return com.stericson.RootTools.C1040RootTools.getInternals().getFilePermissionsSymlinks(file);
    }

    public static java.lang.String getInode(java.lang.String file) {
        return com.stericson.RootTools.C1040RootTools.getInternals().getInode(file);
    }

    public static java.util.ArrayList<com.stericson.RootTools.containers.Mount> getMounts() throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getInternals().getMounts();
    }

    public static java.lang.String getMountedAs(java.lang.String path) throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getInternals().getMountedAs(path);
    }

    public static java.util.List<java.lang.String> getPath() {
        return java.util.Arrays.asList(java.lang.System.getenv("PATH").split(":"));
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout, com.stericson.RootShell.execution.Shell.ShellContext shellContext, int retry) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getShell(root, timeout, shellContext, retry);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout, com.stericson.RootShell.execution.Shell.ShellContext shellContext) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootTools.C1040RootTools.getShell(root, timeout, shellContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, com.stericson.RootShell.execution.Shell.ShellContext shellContext) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootTools.C1040RootTools.getShell(root, 0, shellContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootTools.C1040RootTools.getShell(root, timeout, com.stericson.RootShell.execution.Shell.defaultContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootTools.C1040RootTools.getShell(root, 0);
    }

    public static long getSpace(java.lang.String path) {
        return com.stericson.RootTools.C1040RootTools.getInternals().getSpace(path);
    }

    public static java.lang.String getSymlink(java.lang.String file) {
        return com.stericson.RootTools.C1040RootTools.getInternals().getSymlink(file);
    }

    public static java.util.ArrayList<com.stericson.RootTools.containers.Symlink> getSymlinks(java.lang.String path) throws java.lang.Exception {
        return com.stericson.RootTools.C1040RootTools.getInternals().getSymlinks(path);
    }

    public static java.lang.String getWorkingToolbox() {
        return com.stericson.RootTools.C1040RootTools.getInternals().getWorkingToolbox();
    }

    public static boolean hasEnoughSpaceOnSdCard(long updateSize) {
        return com.stericson.RootTools.C1040RootTools.getInternals().hasEnoughSpaceOnSdCard(updateSize);
    }

    public static boolean hasUtil(java.lang.String util, java.lang.String box) {
        return com.stericson.RootTools.C1040RootTools.getInternals().hasUtil(util, box);
    }

    public static boolean installBinary(android.content.Context context, int sourceId, java.lang.String destName, java.lang.String mode) {
        return com.stericson.RootTools.C1040RootTools.getInternals().installBinary(context, sourceId, destName, mode);
    }

    public static boolean installBinary(android.content.Context context, int sourceId, java.lang.String binaryName) {
        return com.stericson.RootTools.C1040RootTools.installBinary(context, sourceId, binaryName, "700");
    }

    public static boolean hasBinary(android.content.Context context, java.lang.String binaryName) {
        return com.stericson.RootTools.C1040RootTools.getInternals().isBinaryAvailable(context, binaryName);
    }

    public static boolean isAppletAvailable(java.lang.String applet, java.lang.String path) {
        return com.stericson.RootTools.C1040RootTools.getInternals().isAppletAvailable(applet, path);
    }

    public static boolean isAppletAvailable(java.lang.String applet) {
        return com.stericson.RootTools.C1040RootTools.isAppletAvailable(applet, "");
    }

    public static boolean isAccessGiven() {
        return com.stericson.RootShell.C1029RootShell.isAccessGiven();
    }

    public static boolean isBusyboxAvailable() {
        return com.stericson.RootShell.C1029RootShell.isBusyboxAvailable();
    }

    public static boolean isNativeToolsReady(int nativeToolsId, android.content.Context context) {
        return com.stericson.RootTools.C1040RootTools.getInternals().isNativeToolsReady(nativeToolsId, context);
    }

    public static boolean isProcessRunning(java.lang.String processName) {
        return com.stericson.RootTools.C1040RootTools.getInternals().isProcessRunning(processName);
    }

    public static boolean isRootAvailable() {
        return com.stericson.RootShell.C1029RootShell.isRootAvailable();
    }

    public static boolean killProcess(java.lang.String processName) {
        return com.stericson.RootTools.C1040RootTools.getInternals().killProcess(processName);
    }

    public static void offerBusyBox(android.app.Activity activity) {
        com.stericson.RootTools.C1040RootTools.getInternals().offerBusyBox(activity);
    }

    public static android.content.Intent offerBusyBox(android.app.Activity activity, int requestCode) {
        return com.stericson.RootTools.C1040RootTools.getInternals().offerBusyBox(activity, requestCode);
    }

    public static void offerSuperUser(android.app.Activity activity) {
        com.stericson.RootTools.C1040RootTools.getInternals().offerSuperUser(activity);
    }

    public static android.content.Intent offerSuperUser(android.app.Activity activity, int requestCode) {
        return com.stericson.RootTools.C1040RootTools.getInternals().offerSuperUser(activity, requestCode);
    }

    public static boolean remount(java.lang.String file, java.lang.String mountType) {
        return new com.stericson.RootTools.internal.Remounter().remount(file, mountType);
    }

    public static void restartAndroid() {
        com.stericson.RootTools.C1040RootTools.log("Restart Android");
        com.stericson.RootTools.C1040RootTools.killProcess("zygote");
    }

    public static void runBinary(android.content.Context context, java.lang.String binaryName, java.lang.String parameter) {
        new com.stericson.RootTools.internal.Runner(context, binaryName, parameter).start();
    }

    public static void runShellCommand(com.stericson.RootShell.execution.Shell shell, com.stericson.RootShell.execution.Command command) throws java.io.IOException {
        shell.add(command);
    }

    public static void log(java.lang.String msg) {
        com.stericson.RootTools.C1040RootTools.log(null, msg, 3, null);
    }

    public static void log(java.lang.String TAG, java.lang.String msg) {
        com.stericson.RootTools.C1040RootTools.log(TAG, msg, 3, null);
    }

    public static void log(java.lang.String msg, int type, java.lang.Exception e) {
        com.stericson.RootTools.C1040RootTools.log(null, msg, type, e);
    }

    public static boolean islog() {
        return debugMode;
    }

    public static void log(java.lang.String TAG, java.lang.String msg, int type, java.lang.Exception e) {
        if (msg != null && !msg.equals("") && debugMode) {
            if (TAG == null) {
                TAG = "RootTools v4.2";
            }
            switch (type) {
                case 1:
                    android.util.Log.v(TAG, msg);
                    return;
                case 2:
                    android.util.Log.e(TAG, msg, e);
                    return;
                case 3:
                    android.util.Log.d(TAG, msg);
                    return;
                default:
                    return;
            }
        }
    }
}
