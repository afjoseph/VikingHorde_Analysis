package com.jaredrummler.android.processes.models;

public class ProcFile extends java.io.File implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.ProcFile> CREATOR = new com.jaredrummler.android.processes.models.ProcFile.C10241();
    public final java.lang.String content;

    /* renamed from: com.jaredrummler.android.processes.models.ProcFile$1 */
    static class C10241 implements android.os.Parcelable.Creator<com.jaredrummler.android.processes.models.ProcFile> {
        C10241() {
        }

        public com.jaredrummler.android.processes.models.ProcFile createFromParcel(android.os.Parcel in) {
            return new com.jaredrummler.android.processes.models.ProcFile(in);
        }

        public com.jaredrummler.android.processes.models.ProcFile[] newArray(int size) {
            return new com.jaredrummler.android.processes.models.ProcFile[size];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0033  */
    protected static java.lang.String readFile(java.lang.String r6) throws java.io.IOException {
        /*
        r3 = 0;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0030 }
        r2.<init>();	 Catch:{ all -> 0x0030 }
        r4 = new java.io.BufferedReader;	 Catch:{ all -> 0x0030 }
        r5 = new java.io.FileReader;	 Catch:{ all -> 0x0030 }
        r5.<init>(r6);	 Catch:{ all -> 0x0030 }
        r4.<init>(r5);	 Catch:{ all -> 0x0030 }
        r0 = r4.readLine();	 Catch:{ all -> 0x0037 }
        r1 = "";
    L_0x0016:
        if (r0 == 0) goto L_0x0026;
    L_0x0018:
        r5 = r2.append(r1);	 Catch:{ all -> 0x0037 }
        r5.append(r0);	 Catch:{ all -> 0x0037 }
        r1 = "\n";
        r0 = r4.readLine();	 Catch:{ all -> 0x0037 }
        goto L_0x0016;
    L_0x0026:
        r5 = r2.toString();	 Catch:{ all -> 0x0037 }
        if (r4 == 0) goto L_0x002f;
    L_0x002c:
        r4.close();
    L_0x002f:
        return r5;
    L_0x0030:
        r5 = move-exception;
    L_0x0031:
        if (r3 == 0) goto L_0x0036;
    L_0x0033:
        r3.close();
    L_0x0036:
        throw r5;
    L_0x0037:
        r5 = move-exception;
        r3 = r4;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jaredrummler.android.processes.models.ProcFile.readFile(java.lang.String):java.lang.String");
    }

    protected ProcFile(java.lang.String path) throws java.io.IOException {
        super(path);
        this.content = readFile(path);
    }

    protected ProcFile(android.os.Parcel in) {
        super(in.readString());
        this.content = in.readString();
    }

    public long length() {
        return (long) this.content.length();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(getAbsolutePath());
        dest.writeString(this.content);
    }
}
