package com.jaredrummler.android.processes.models;

public final class Status extends com.jaredrummler.android.processes.models.ProcFile {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Status> CREATOR = new com.jaredrummler.android.processes.models.Status.C10271();

    /* renamed from: com.jaredrummler.android.processes.models.Status$1 */
    static class C10271 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Status> {
        C10271() {
        }

        public com.jaredrummler.android.processes.models.Status createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.Status(source, null);
        }

        public com.jaredrummler.android.processes.models.Status[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.Status[size];
        }
    }

    /* synthetic */ Status(android.os.Parcel x0, com.jaredrummler.android.processes.models.Status.C10271 x1) {
        this(x0);
    }

    public static com.jaredrummler.android.processes.models.Status get(int pid) throws java.io.IOException {
        return new com.jaredrummler.android.processes.models.Status(java.lang.String.format("/proc/%d/status", new java.lang.Object[]{java.lang.Integer.valueOf(pid)}));
    }

    private Status(java.lang.String path) throws java.io.IOException {
        super(path);
    }

    private Status(android.os.Parcel in) {
        super(in);
    }

    public java.lang.String getValue(java.lang.String fieldName) {
        for (java.lang.String line : this.content.split("\n")) {
            if (line.startsWith(fieldName + ":")) {
                return line.split(fieldName + ":")[1].trim();
            }
        }
        return null;
    }

    public int getUid() {
        try {
            return java.lang.Integer.parseInt(getValue("Uid").split("\\s+")[0]);
        } catch (java.lang.Exception e) {
            return -1;
        }
    }

    public int getGid() {
        try {
            return java.lang.Integer.parseInt(getValue("Gid").split("\\s+")[0]);
        } catch (java.lang.Exception e) {
            return -1;
        }
    }
}
