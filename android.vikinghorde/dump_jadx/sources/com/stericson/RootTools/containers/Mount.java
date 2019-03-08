package com.stericson.RootTools.containers;

public class Mount {
    final java.io.File mDevice;
    final java.util.Set<java.lang.String> mFlags;
    final java.io.File mMountPoint;
    final java.lang.String mType;

    public Mount(java.io.File device, java.io.File path, java.lang.String type, java.lang.String flagsStr) {
        this.mDevice = device;
        this.mMountPoint = path;
        this.mType = type;
        this.mFlags = new java.util.LinkedHashSet(java.util.Arrays.asList(flagsStr.split(",")));
    }

    public java.io.File getDevice() {
        return this.mDevice;
    }

    public java.io.File getMountPoint() {
        return this.mMountPoint;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public java.util.Set<java.lang.String> getFlags() {
        return this.mFlags;
    }

    public java.lang.String toString() {
        return java.lang.String.format("%s on %s type %s %s", new java.lang.Object[]{this.mDevice, this.mMountPoint, this.mType, this.mFlags});
    }
}
