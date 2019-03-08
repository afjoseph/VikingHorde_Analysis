package com.stericson.RootTools.containers;

public class Symlink {
    protected final java.io.File file;
    protected final java.io.File symlinkPath;

    public Symlink(java.io.File file, java.io.File path) {
        this.file = file;
        this.symlinkPath = path;
    }

    public java.io.File getFile() {
        return this.file;
    }

    public java.io.File getSymlinkPath() {
        return this.symlinkPath;
    }
}
