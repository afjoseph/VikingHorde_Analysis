package com.stericson.RootTools.internal;

public class Runner extends java.lang.Thread {
    private static final java.lang.String LOG_TAG = "RootTools::Runner";
    java.lang.String binaryName;
    android.content.Context context;
    java.lang.String parameter;

    public Runner(android.content.Context context, java.lang.String binaryName, java.lang.String parameter) {
        this.context = context;
        this.binaryName = binaryName;
        this.parameter = parameter;
    }

    public void run() {
        java.lang.String privateFilesPath = null;
        try {
            privateFilesPath = this.context.getFilesDir().getCanonicalPath();
        } catch (java.io.IOException e) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                android.util.Log.e("RootTools::Runner", "Problem occured while trying to locate private files directory!");
            }
            e.printStackTrace();
        }
        if (privateFilesPath != null) {
            try {
                com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, privateFilesPath + "/" + this.binaryName + " " + this.parameter);
                com.stericson.RootShell.execution.Shell.startRootShell().add(command);
                commandWait(command);
            } catch (java.lang.Exception e2) {
            }
        }
    }

    private void commandWait(com.stericson.RootShell.execution.Command cmd) {
        synchronized (cmd) {
            try {
                if (!cmd.isFinished()) {
                    cmd.wait(2000);
                }
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
