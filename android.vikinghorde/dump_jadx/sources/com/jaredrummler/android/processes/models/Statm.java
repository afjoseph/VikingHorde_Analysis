package com.jaredrummler.android.processes.models;

public final class Statm extends com.jaredrummler.android.processes.models.ProcFile {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Statm> CREATOR = new com.jaredrummler.android.processes.models.Statm.C10261();
    public final java.lang.String[] fields;

    /* renamed from: com.jaredrummler.android.processes.models.Statm$1 */
    static class C10261 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Statm> {
        C10261() {
        }

        public com.jaredrummler.android.processes.models.Statm createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.Statm(source, null);
        }

        public com.jaredrummler.android.processes.models.Statm[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.Statm[size];
        }
    }

    /* synthetic */ Statm(android.os.Parcel x0, com.jaredrummler.android.processes.models.Statm.C10261 x1) {
        this(x0);
    }

    public static com.jaredrummler.android.processes.models.Statm get(int pid) throws java.io.IOException {
        return new com.jaredrummler.android.processes.models.Statm(java.lang.String.format("/proc/%d/statm", new java.lang.Object[]{java.lang.Integer.valueOf(pid)}));
    }

    private Statm(java.lang.String path) throws java.io.IOException {
        super(path);
        this.fields = this.content.split("\\s+");
    }

    private Statm(android.os.Parcel in) {
        super(in);
        this.fields = in.createStringArray();
    }

    public long getSize() {
        return java.lang.Long.parseLong(this.fields[0]) * 1024;
    }

    public long getResidentSetSize() {
        return java.lang.Long.parseLong(this.fields[1]) * 1024;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringArray(this.fields);
    }
}
