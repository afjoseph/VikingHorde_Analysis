package com.stericson.RootShell;

/* renamed from: com.stericson.RootShell.RootShell */
public class C1029RootShell {
    public static boolean debugMode = false;
    public static int defaultCommandTimeout = 20000;
    public static boolean handlerEnabled = true;
    public static final java.lang.String version = "RootShell v1.3";

    /* renamed from: com.stericson.RootShell.RootShell$LogLevel */
    public enum LogLevel {
        VERBOSE,
        ERROR,
        DEBUG,
        WARN
    }

    public static void closeAllShells() throws java.io.IOException {
        com.stericson.RootShell.execution.Shell.closeAll();
    }

    public static void closeCustomShell() throws java.io.IOException {
        com.stericson.RootShell.execution.Shell.closeCustomShell();
    }

    public static void closeShell(boolean root) throws java.io.IOException {
        if (root) {
            com.stericson.RootShell.execution.Shell.closeRootShell();
        } else {
            com.stericson.RootShell.execution.Shell.closeShell();
        }
    }

    public static boolean exists(java.lang.String file) {
        return com.stericson.RootShell.C1029RootShell.exists(file, false);
    }

    public static boolean exists(java.lang.String file, boolean isDir) {
        final java.util.List<java.lang.String> result = new java.util.ArrayList();
        java.lang.String cmdToExecute = "ls " + (isDir ? "-d " : " ");
        com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, new java.lang.String[]{cmdToExecute + file}) {
            public void commandOutput(int id, java.lang.String line) {
                com.stericson.RootShell.C1029RootShell.log(line);
                result.add(line);
                super.commandOutput(id, line);
            }
        };
        try {
            com.stericson.RootShell.C1029RootShell.getShell(false).add(command);
            com.stericson.RootShell.C1029RootShell.commandWait(com.stericson.RootShell.C1029RootShell.getShell(false), command);
            for (java.lang.String line : result) {
                if (line.trim().equals(file)) {
                    return true;
                }
            }
            result.clear();
            try {
                com.stericson.RootShell.C1029RootShell.getShell(true).add(command);
                com.stericson.RootShell.C1029RootShell.commandWait(com.stericson.RootShell.C1029RootShell.getShell(true), command);
                java.util.List<java.lang.String> final_result = new java.util.ArrayList();
                final_result.addAll(result);
                for (java.lang.String line2 : final_result) {
                    if (line2.trim().equals(file)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Exception e) {
                return false;
            }
        } catch (java.lang.Exception e2) {
            return false;
        }
    }

    public static java.util.List<java.lang.String> findBinary(java.lang.String binaryName) {
        return com.stericson.RootShell.C1029RootShell.findBinary(binaryName, null);
    }

    public static java.util.List<java.lang.String> findBinary(java.lang.String binaryName, java.util.List<java.lang.String> searchPaths) {
        java.lang.String path;
        final java.util.List<java.lang.String> foundPaths = new java.util.ArrayList();
        boolean found = false;
        if (searchPaths == null) {
            searchPaths = com.stericson.RootShell.C1029RootShell.getPath();
        }
        com.stericson.RootShell.C1029RootShell.log("Checking for " + binaryName);
        try {
            for (java.lang.String path2 : searchPaths) {
                if (!path2.endsWith("/")) {
                    path2 = path2 + "/";
                }
                final java.lang.String currentPath = path2;
                final java.lang.String str = binaryName;
                com.stericson.RootShell.execution.Command cc = new com.stericson.RootShell.execution.Command(0, false, new java.lang.String[]{"stat " + path2 + binaryName}) {
                    public void commandOutput(int id, java.lang.String line) {
                        if (line.contains("File: ") && line.contains(str)) {
                            foundPaths.add(currentPath);
                            com.stericson.RootShell.C1029RootShell.log(str + " was found here: " + currentPath);
                        }
                        com.stericson.RootShell.C1029RootShell.log(line);
                        super.commandOutput(id, line);
                    }
                };
                com.stericson.RootShell.C1029RootShell.getShell(false).add(cc);
                com.stericson.RootShell.C1029RootShell.commandWait(com.stericson.RootShell.C1029RootShell.getShell(false), cc);
            }
            found = !foundPaths.isEmpty();
        } catch (java.lang.Exception e) {
            com.stericson.RootShell.C1029RootShell.log(binaryName + " was not found, more information MAY be available with Debugging on.");
        }
        if (!found) {
            com.stericson.RootShell.C1029RootShell.log("Trying second method");
            for (java.lang.String path22 : searchPaths) {
                if (!path22.endsWith("/")) {
                    path22 = path22 + "/";
                }
                if (com.stericson.RootShell.C1029RootShell.exists(path22 + binaryName)) {
                    com.stericson.RootShell.C1029RootShell.log(binaryName + " was found here: " + path22);
                    foundPaths.add(path22);
                } else {
                    com.stericson.RootShell.C1029RootShell.log(binaryName + " was NOT found here: " + path22);
                }
            }
        }
        java.util.Collections.reverse(foundPaths);
        return foundPaths;
    }

    public static com.stericson.RootShell.execution.Shell getCustomShell(java.lang.String shellPath, int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getCustomShell(shellPath, timeout);
    }

    public static java.util.List<java.lang.String> getPath() {
        return java.util.Arrays.asList(java.lang.System.getenv("PATH").split(":"));
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout, com.stericson.RootShell.execution.Shell.ShellContext shellContext, int retry) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        if (root) {
            return com.stericson.RootShell.execution.Shell.startRootShell(timeout, shellContext, retry);
        }
        return com.stericson.RootShell.execution.Shell.startShell(timeout);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout, com.stericson.RootShell.execution.Shell.ShellContext shellContext) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getShell(root, timeout, shellContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, com.stericson.RootShell.execution.Shell.ShellContext shellContext) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getShell(root, 0, shellContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root, int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getShell(root, timeout, com.stericson.RootShell.execution.Shell.defaultContext, 3);
    }

    public static com.stericson.RootShell.execution.Shell getShell(boolean root) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return com.stericson.RootShell.C1029RootShell.getShell(root, 0);
    }

    public static boolean isAccessGiven() {
        final java.util.Set<java.lang.String> ID = new java.util.HashSet();
        try {
            com.stericson.RootShell.C1029RootShell.log("Checking for Root access");
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(158, false, new java.lang.String[]{"id"}) {
                public void commandOutput(int id, java.lang.String line) {
                    if (id == 158) {
                        ID.addAll(java.util.Arrays.asList(line.split(" ")));
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            com.stericson.RootShell.C1029RootShell.commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            for (java.lang.String userid : ID) {
                com.stericson.RootShell.C1029RootShell.log(userid);
                if (userid.toLowerCase().contains("uid=0")) {
                    com.stericson.RootShell.C1029RootShell.log("Access Given");
                    return true;
                }
            }
            return false;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isBusyboxAvailable() {
        return com.stericson.RootShell.C1029RootShell.findBinary("busybox").size() > 0;
    }

    public static boolean isRootAvailable() {
        return com.stericson.RootShell.C1029RootShell.findBinary("su").size() > 0;
    }

    public static void log(java.lang.String msg) {
        com.stericson.RootShell.C1029RootShell.log(null, msg, com.stericson.RootShell.C1029RootShell.LogLevel.DEBUG, null);
    }

    public static void log(java.lang.String TAG, java.lang.String msg) {
        com.stericson.RootShell.C1029RootShell.log(TAG, msg, com.stericson.RootShell.C1029RootShell.LogLevel.DEBUG, null);
    }

    public static void log(java.lang.String msg, com.stericson.RootShell.C1029RootShell.LogLevel type, java.lang.Exception e) {
        com.stericson.RootShell.C1029RootShell.log(null, msg, type, e);
    }

    public static boolean islog() {
        return debugMode;
    }

    public static void log(java.lang.String TAG, java.lang.String msg, com.stericson.RootShell.C1029RootShell.LogLevel type, java.lang.Exception e) {
        if (msg != null && !msg.equals("") && debugMode) {
            if (TAG == null) {
                TAG = "RootShell v1.3";
            }
            switch (type) {
                case VERBOSE:
                    android.util.Log.v(TAG, msg);
                    return;
                case ERROR:
                    android.util.Log.e(TAG, msg, e);
                    return;
                case DEBUG:
                    android.util.Log.d(TAG, msg);
                    return;
                case WARN:
                    android.util.Log.w(TAG, msg);
                    return;
                default:
                    return;
            }
        }
    }

    private static void commandWait(com.stericson.RootShell.execution.Shell shell, com.stericson.RootShell.execution.Command cmd) throws java.lang.Exception {
        while (!cmd.isFinished()) {
            com.stericson.RootShell.C1029RootShell.log("RootShell v1.3", shell.getCommandQueuePositionString(cmd));
            com.stericson.RootShell.C1029RootShell.log("RootShell v1.3", "Processed " + cmd.totalOutputProcessed + " of " + cmd.totalOutput + " output from command.");
            synchronized (cmd) {
                try {
                    if (!cmd.isFinished()) {
                        cmd.wait(2000);
                    }
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!(cmd.isExecuting() || cmd.isFinished())) {
                java.lang.Exception e2;
                if (!shell.isExecuting && !shell.isReading) {
                    com.stericson.RootShell.C1029RootShell.log("RootShell v1.3", "Waiting for a command to be executed in a shell that is not executing and not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                } else if (!shell.isExecuting || shell.isReading) {
                    com.stericson.RootShell.C1029RootShell.log("RootShell v1.3", "Waiting for a command to be executed in a shell that is not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                } else {
                    com.stericson.RootShell.C1029RootShell.log("RootShell v1.3", "Waiting for a command to be executed in a shell that is executing but not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                }
            }
        }
    }
}
