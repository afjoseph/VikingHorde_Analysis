package com.jaredrummler.android.processes.models;

public final class Stat extends com.jaredrummler.android.processes.models.ProcFile {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Stat> CREATOR = new com.jaredrummler.android.processes.models.Stat.C10251();
    private final java.lang.String[] fields;

    /* renamed from: com.jaredrummler.android.processes.models.Stat$1 */
    static class C10251 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Stat> {
        C10251() {
        }

        public com.jaredrummler.android.processes.models.Stat createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.Stat(source, null);
        }

        public com.jaredrummler.android.processes.models.Stat[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.Stat[size];
        }
    }

    /* synthetic */ Stat(android.os.Parcel x0, com.jaredrummler.android.processes.models.Stat.C10251 x1) {
        this(x0);
    }

    public static com.jaredrummler.android.processes.models.Stat get(int pid) throws java.io.IOException {
        return new com.jaredrummler.android.processes.models.Stat(java.lang.String.format("/proc/%d/stat", new java.lang.Object[]{java.lang.Integer.valueOf(pid)}));
    }

    private Stat(java.lang.String path) throws java.io.IOException {
        super(path);
        this.fields = this.content.split("\\s+");
    }

    private Stat(android.os.Parcel in) {
        super(in);
        this.fields = in.createStringArray();
    }

    public int getPid() {
        return java.lang.Integer.parseInt(this.fields[0]);
    }

    public java.lang.String getComm() {
        return this.fields[1].replace("(", "").replace(")", "");
    }

    public char state() {
        return this.fields[2].charAt(0);
    }

    public int ppid() {
        return java.lang.Integer.parseInt(this.fields[3]);
    }

    public int pgrp() {
        return java.lang.Integer.parseInt(this.fields[4]);
    }

    public int session() {
        return java.lang.Integer.parseInt(this.fields[5]);
    }

    public int tty_nr() {
        return java.lang.Integer.parseInt(this.fields[6]);
    }

    public int tpgid() {
        return java.lang.Integer.parseInt(this.fields[7]);
    }

    public int flags() {
        return java.lang.Integer.parseInt(this.fields[8]);
    }

    public long minflt() {
        return java.lang.Long.parseLong(this.fields[9]);
    }

    public long cminflt() {
        return java.lang.Long.parseLong(this.fields[10]);
    }

    public long majflt() {
        return java.lang.Long.parseLong(this.fields[11]);
    }

    public long cmajflt() {
        return java.lang.Long.parseLong(this.fields[12]);
    }

    public long utime() {
        return java.lang.Long.parseLong(this.fields[13]);
    }

    public long stime() {
        return java.lang.Long.parseLong(this.fields[14]);
    }

    public long cutime() {
        return java.lang.Long.parseLong(this.fields[15]);
    }

    public long cstime() {
        return java.lang.Long.parseLong(this.fields[16]);
    }

    public long priority() {
        return java.lang.Long.parseLong(this.fields[17]);
    }

    public int nice() {
        return java.lang.Integer.parseInt(this.fields[18]);
    }

    public long num_threads() {
        return java.lang.Long.parseLong(this.fields[19]);
    }

    public long itrealvalue() {
        return java.lang.Long.parseLong(this.fields[20]);
    }

    public long starttime() {
        return java.lang.Long.parseLong(this.fields[21]);
    }

    public long vsize() {
        return java.lang.Long.parseLong(this.fields[22]);
    }

    public long rss() {
        return java.lang.Long.parseLong(this.fields[23]);
    }

    public long rsslim() {
        return java.lang.Long.parseLong(this.fields[24]);
    }

    public long startcode() {
        return java.lang.Long.parseLong(this.fields[25]);
    }

    public long endcode() {
        return java.lang.Long.parseLong(this.fields[26]);
    }

    public long startstack() {
        return java.lang.Long.parseLong(this.fields[27]);
    }

    public long kstkesp() {
        return java.lang.Long.parseLong(this.fields[28]);
    }

    public long kstkeip() {
        return java.lang.Long.parseLong(this.fields[29]);
    }

    public long signal() {
        return java.lang.Long.parseLong(this.fields[30]);
    }

    public long blocked() {
        return java.lang.Long.parseLong(this.fields[31]);
    }

    public long sigignore() {
        return java.lang.Long.parseLong(this.fields[32]);
    }

    public long sigcatch() {
        return java.lang.Long.parseLong(this.fields[33]);
    }

    public long wchan() {
        return java.lang.Long.parseLong(this.fields[34]);
    }

    public long nswap() {
        return java.lang.Long.parseLong(this.fields[35]);
    }

    public long cnswap() {
        return java.lang.Long.parseLong(this.fields[36]);
    }

    public int exit_signal() {
        return java.lang.Integer.parseInt(this.fields[37]);
    }

    public int processor() {
        return java.lang.Integer.parseInt(this.fields[38]);
    }

    public int rt_priority() {
        return java.lang.Integer.parseInt(this.fields[39]);
    }

    public int policy() {
        return java.lang.Integer.parseInt(this.fields[40]);
    }

    public long delayacct_blkio_ticks() {
        return java.lang.Long.parseLong(this.fields[41]);
    }

    public long guest_time() {
        return java.lang.Long.parseLong(this.fields[42]);
    }

    public long cguest_time() {
        return java.lang.Long.parseLong(this.fields[43]);
    }

    public long start_data() {
        return java.lang.Long.parseLong(this.fields[44]);
    }

    public long end_data() {
        return java.lang.Long.parseLong(this.fields[45]);
    }

    public long start_brk() {
        return java.lang.Long.parseLong(this.fields[46]);
    }

    public long arg_start() {
        return java.lang.Long.parseLong(this.fields[47]);
    }

    public long arg_end() {
        return java.lang.Long.parseLong(this.fields[48]);
    }

    public long env_start() {
        return java.lang.Long.parseLong(this.fields[49]);
    }

    public long env_end() {
        return java.lang.Long.parseLong(this.fields[50]);
    }

    public int exit_code() {
        return java.lang.Integer.parseInt(this.fields[51]);
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(this.fields);
    }
}
