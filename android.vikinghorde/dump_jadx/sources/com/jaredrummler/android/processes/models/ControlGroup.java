package com.jaredrummler.android.processes.models;

public class ControlGroup implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.ControlGroup> CREATOR = new com.jaredrummler.android.processes.models.ControlGroup.C10231();
    public final java.lang.String group;
    /* renamed from: id */
    public final int f877id;
    public final java.lang.String subsystems;

    /* renamed from: com.jaredrummler.android.processes.models.ControlGroup$1 */
    static class C10231 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.ControlGroup> {
        C10231() {
        }

        public com.jaredrummler.android.processes.models.ControlGroup createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.ControlGroup(source);
        }

        public com.jaredrummler.android.processes.models.ControlGroup[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.ControlGroup[size];
        }
    }

    protected ControlGroup(java.lang.String line) throws java.lang.NumberFormatException, java.lang.IndexOutOfBoundsException {
        java.lang.String[] fields = line.split(":");
        this.f877id = java.lang.Integer.parseInt(fields[0]);
        this.subsystems = fields[1];
        this.group = fields[2];
    }

    protected ControlGroup(android.os.Parcel in) {
        this.f877id = in.readInt();
        this.subsystems = in.readString();
        this.group = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.f877id);
        dest.writeString(this.subsystems);
        dest.writeString(this.group);
    }

    public java.lang.String toString() {
        return java.lang.String.format("%d:%s:%s", new java.lang.Object[]{java.lang.Integer.valueOf(this.f877id), this.subsystems, this.group});
    }
}
