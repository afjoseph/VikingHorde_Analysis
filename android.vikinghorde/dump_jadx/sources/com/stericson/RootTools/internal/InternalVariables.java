package com.stericson.RootTools.internal;

public class InternalVariables {
    protected static final java.lang.String PS_REGEX = "^\\S+\\s+([0-9]+).*$";
    protected static java.lang.String busyboxVersion;
    protected static boolean found = false;
    protected static java.lang.String getSpaceFor;
    protected static java.lang.String inode = "";
    protected static java.util.ArrayList<com.stericson.RootTools.containers.Mount> mounts;
    protected static boolean nativeToolsReady = false;
    protected static com.stericson.RootTools.containers.Permissions permissions;
    protected static java.lang.String pid_list = "";
    protected static boolean processRunning = false;
    protected static java.util.regex.Pattern psPattern = java.util.regex.Pattern.compile("^\\S+\\s+([0-9]+).*$");
    protected static java.lang.String[] space;
    protected static java.util.ArrayList<com.stericson.RootTools.containers.Symlink> symlinks;
}
