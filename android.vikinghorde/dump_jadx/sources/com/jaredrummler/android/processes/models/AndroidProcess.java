package com.jaredrummler.android.processes.models;

public class AndroidProcess implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.AndroidProcess> CREATOR = new com.jaredrummler.android.processes.models.AndroidProcess.C10211();
    public final java.lang.String name;
    public final int pid;

    /* renamed from: com.jaredrummler.android.processes.models.AndroidProcess$1 */
    static class C10211 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.AndroidProcess> {
        C10211() {
        }

        public com.jaredrummler.android.processes.models.AndroidProcess createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.AndroidProcess(source);
        }

        public com.jaredrummler.android.processes.models.AndroidProcess[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.AndroidProcess[size];
        }
    }

    static java.lang.String getProcessName(int pid) throws java.io.IOException {
        java.lang.String cmdline = null;
        try {
            cmdline = com.jaredrummler.android.processes.models.ProcFile.readFile(java.lang.String.format("/proc/%d/cmdline", new java.lang.Object[]{java.lang.Integer.valueOf(pid)})).trim();
        } catch (java.io.IOException e) {
        }
        if (android.text.TextUtils.isEmpty(cmdline)) {
            return com.jaredrummler.android.processes.models.Stat.get(pid).getComm();
        }
        return cmdline;
    }

    public AndroidProcess(int pid) throws java.io.IOException {
        this.pid = pid;
        this.name = getProcessName(pid);
    }

    public java.lang.String read(java.lang.String filename) throws java.io.IOException {
        return com.jaredrummler.android.processes.models.ProcFile.readFile(java.lang.String.format("/proc/%d/%s", new java.lang.Object[]{java.lang.Integer.valueOf(this.pid), filename}));
    }

    public java.lang.String attr_current() throws java.io.IOException {
        return read("attr/current");
    }

    public java.lang.String cmdline() throws java.io.IOException {
        return read("cmdline");
    }

    public com.jaredrummler.android.processes.models.Cgroup cgroup() throws java.io.IOException {
        return com.jaredrummler.android.processes.models.Cgroup.get(this.pid);
    }

    public int oom_score() throws java.io.IOException {
        return java.lang.Integer.parseInt(read("oom_score"));
    }

    public int oom_adj() throws java.io.IOException {
        return java.lang.Integer.parseInt(read("oom_adj"));
    }

    public int oom_score_adj() throws java.io.IOException {
        return java.lang.Integer.parseInt(read("oom_score_adj"));
    }

    public com.jaredrummler.android.processes.models.Stat stat() throws java.io.IOException {
        return com.jaredrummler.android.processes.models.Stat.get(this.pid);
    }

    public com.jaredrummler.android.processes.models.Statm statm() throws java.io.IOException {
        return com.jaredrummler.android.processes.models.Statm.get(this.pid);
    }

    public com.jaredrummler.android.processes.models.Status status() throws java.io.IOException {
        return com.jaredrummler.android.processes.models.Status.get(this.pid);
    }

    public java.lang.String wchan() throws java.io.IOException {
        return read("wchan");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.pid);
    }

    protected AndroidProcess(android.os.Parcel in) {
        this.name = in.readString();
        this.pid = in.readInt();
    }
}
