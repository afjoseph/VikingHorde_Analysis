package com.stericson.RootTools.containers;

public class Permissions {
    java.lang.String group;
    java.lang.String other;
    int permissions;
    java.lang.String symlink;
    java.lang.String type;
    java.lang.String user;

    public java.lang.String getSymlink() {
        return this.symlink;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public int getPermissions() {
        return this.permissions;
    }

    public java.lang.String getUserPermissions() {
        return this.user;
    }

    public java.lang.String getGroupPermissions() {
        return this.group;
    }

    public java.lang.String getOtherPermissions() {
        return this.other;
    }

    public void setSymlink(java.lang.String symlink) {
        this.symlink = symlink;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public void setUserPermissions(java.lang.String user) {
        this.user = user;
    }

    public void setGroupPermissions(java.lang.String group) {
        this.group = group;
    }

    public void setOtherPermissions(java.lang.String other) {
        this.other = other;
    }

    public java.lang.String getUser() {
        return this.user;
    }

    public void setUser(java.lang.String user) {
        this.user = user;
    }

    public java.lang.String getGroup() {
        return this.group;
    }

    public void setGroup(java.lang.String group) {
        this.group = group;
    }

    public java.lang.String getOther() {
        return this.other;
    }

    public void setOther(java.lang.String other) {
        this.other = other;
    }
}
