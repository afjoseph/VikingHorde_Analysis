package com.jaredrummler.android.processes.models;

public final class Cgroup extends com.jaredrummler.android.processes.models.ProcFile {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Cgroup> CREATOR = new com.jaredrummler.android.processes.models.Cgroup.C10221();
    public final java.util.ArrayList<com.jaredrummler.android.processes.models.ControlGroup> groups;

    /* renamed from: com.jaredrummler.android.processes.models.Cgroup$1 */
    static class C10221 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.Cgroup> {
        C10221() {
        }

        public com.jaredrummler.android.processes.models.Cgroup createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.Cgroup(source, null);
        }

        public com.jaredrummler.android.processes.models.Cgroup[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.Cgroup[size];
        }
    }

    /* synthetic */ Cgroup(android.os.Parcel x0, com.jaredrummler.android.processes.models.Cgroup.C10221 x1) {
        this(x0);
    }

    public static com.jaredrummler.android.processes.models.Cgroup get(int pid) throws java.io.IOException {
        return new com.jaredrummler.android.processes.models.Cgroup(java.lang.String.format("/proc/%d/cgroup", new java.lang.Object[]{java.lang.Integer.valueOf(pid)}));
    }

    private Cgroup(java.lang.String path) throws java.io.IOException {
        super(path);
        java.lang.String[] lines = this.content.split("\n");
        this.groups = new java.util.ArrayList();
        for (java.lang.String line : lines) {
            try {
                this.groups.add(new com.jaredrummler.android.processes.models.ControlGroup(line));
            } catch (java.lang.Exception e) {
            }
        }
    }

    private Cgroup(android.os.Parcel in) {
        super(in);
        this.groups = in.createTypedArrayList(com.jaredrummler.android.processes.models.ControlGroup.CREATOR);
    }

    public com.jaredrummler.android.processes.models.ControlGroup getGroup(java.lang.String subsystem) {
        java.util.Iterator it = this.groups.iterator();
        while (it.hasNext()) {
            com.jaredrummler.android.processes.models.ControlGroup group = (com.jaredrummler.android.processes.models.ControlGroup) it.next();
            for (java.lang.String name : group.subsystems.split(",")) {
                if (name.equals(subsystem)) {
                    return group;
                }
            }
        }
        return null;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.groups);
    }
}
