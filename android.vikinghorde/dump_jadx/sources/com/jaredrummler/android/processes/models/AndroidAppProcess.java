package com.jaredrummler.android.processes.models;

public class AndroidAppProcess extends com.jaredrummler.android.processes.models.AndroidProcess {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.AndroidAppProcess> CREATOR = new com.jaredrummler.android.processes.models.AndroidAppProcess.C10201();
    private static final boolean SYS_SUPPORTS_SCHEDGROUPS = new java.io.File("/dev/cpuctl/tasks").exists();
    public final boolean foreground;
    public final int uid;

    /* renamed from: com.jaredrummler.android.processes.models.AndroidAppProcess$1 */
    static class C10201 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.AndroidAppProcess> {
        C10201() {
        }

        public com.jaredrummler.android.processes.models.AndroidAppProcess createFromParcel(android.os.Parcel source) {
            return new com.jaredrummler.android.processes.models.AndroidAppProcess(source);
        }

        public com.jaredrummler.android.processes.models.AndroidAppProcess[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.AndroidAppProcess[size];
        }
    }

    public static final class NotAndroidAppProcessException extends java.lang.Exception {
        public NotAndroidAppProcessException(int pid) {
            super(java.lang.String.format("The process %d does not belong to any application", new java.lang.Object[]{java.lang.Integer.valueOf(pid)}));
        }
    }

    public AndroidAppProcess(int pid) throws java.io.IOException, com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException {
        boolean foreground;
        int uid;
        super(pid);
        if (SYS_SUPPORTS_SCHEDGROUPS) {
            com.jaredrummler.android.processes.models.Cgroup cgroup = cgroup();
            com.jaredrummler.android.processes.models.ControlGroup cpuacct = cgroup.getGroup("cpuacct");
            com.jaredrummler.android.processes.models.ControlGroup cpu = cgroup.getGroup("cpu");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                if (cpu == null || cpuacct == null || !cpuacct.group.contains("pid_")) {
                    throw new com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException(pid);
                }
                foreground = !cpu.group.contains("bg_non_interactive");
                try {
                    uid = java.lang.Integer.parseInt(cpuacct.group.split("/")[1].replace("uid_", ""));
                } catch (java.lang.Exception e) {
                    uid = status().getUid();
                }
                com.jaredrummler.android.processes.ProcessManager.log("name=%s, pid=%d, uid=%d, foreground=%b, cpuacct=%s, cpu=%s", this.name, java.lang.Integer.valueOf(pid), java.lang.Integer.valueOf(uid), java.lang.Boolean.valueOf(foreground), cpuacct.toString(), cpu.toString());
            } else if (cpu == null || cpuacct == null || !cpu.group.contains("apps")) {
                throw new com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException(pid);
            } else {
                foreground = !cpu.group.contains("bg_non_interactive");
                try {
                    uid = java.lang.Integer.parseInt(cpuacct.group.substring(cpuacct.group.lastIndexOf("/") + 1));
                } catch (java.lang.Exception e2) {
                    uid = status().getUid();
                }
                com.jaredrummler.android.processes.ProcessManager.log("name=%s, pid=%d, uid=%d foreground=%b, cpuacct=%s, cpu=%s", this.name, java.lang.Integer.valueOf(pid), java.lang.Integer.valueOf(uid), java.lang.Boolean.valueOf(foreground), cpuacct.toString(), cpu.toString());
            }
        } else if (this.name.startsWith("/") || !new java.io.File("/data/data", getPackageName()).exists()) {
            throw new com.jaredrummler.android.processes.models.AndroidAppProcess.NotAndroidAppProcessException(pid);
        } else {
            com.jaredrummler.android.processes.models.Stat stat = stat();
            com.jaredrummler.android.processes.models.Status status = status();
            foreground = stat.policy() == 0;
            uid = status.getUid();
            com.jaredrummler.android.processes.ProcessManager.log("name=%s, pid=%d, uid=%d foreground=%b", this.name, java.lang.Integer.valueOf(pid), java.lang.Integer.valueOf(uid), java.lang.Boolean.valueOf(foreground));
        }
        this.foreground = foreground;
        this.uid = uid;
    }

    public java.lang.String getPackageName() {
        return this.name.split(":")[0];
    }

    public android.content.pm.PackageInfo getPackageInfo(android.content.Context context, int flags) throws android.content.pm.PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(getPackageName(), flags);
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (this.foreground ? 1 : 0));
        dest.writeInt(this.uid);
    }

    protected AndroidAppProcess(android.os.Parcel in) {
        super(in);
        this.foreground = in.readByte() != (byte) 0;
        this.uid = in.readInt();
    }
}
