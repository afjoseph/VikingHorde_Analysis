package com.stericson.RootTools.internal;

class Installer {
    static final java.lang.String BOGUS_FILE_NAME = "bogus";
    static final java.lang.String LOG_TAG = "RootTools::Installer";
    android.content.Context context;
    java.lang.String filesPath;

    public Installer(android.content.Context context) throws java.io.IOException {
        this.context = context;
        this.filesPath = context.getFilesDir().getCanonicalPath();
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x01a5 A:{Catch:{ all -> 0x01c3 }} */
    /* JADX WARNING: Removed duplicated region for block: B:97:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01b1 A:{SYNTHETIC, Splitter:B:71:0x01b1} */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01c6 A:{SYNTHETIC, Splitter:B:76:0x01c6} */
    /* JADX WARNING: Missing block: B:3:0x0046, code skipped:
            if (getFileSignature(r14).equals(getStreamSignature(r23.context.getResources().openRawResource(r24))) == false) goto L_0x0048;
     */
    protected boolean installBinary(int r24, java.lang.String r25, java.lang.String r26) {
        /*
        r23 = this;
        r14 = new java.io.File;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r0 = r23;
        r7 = r0.filesPath;
        r6 = r6.append(r7);
        r7 = java.io.File.separator;
        r6 = r6.append(r7);
        r0 = r25;
        r6 = r6.append(r0);
        r6 = r6.toString();
        r14.<init>(r6);
        r6 = r14.exists();
        if (r6 == 0) goto L_0x0048;
    L_0x0028:
        r0 = r23;
        r6 = r0.getFileSignature(r14);
        r0 = r23;
        r7 = r0.context;
        r7 = r7.getResources();
        r0 = r24;
        r7 = r7.openRawResource(r0);
        r0 = r23;
        r7 = r0.getStreamSignature(r7);
        r6 = r6.equals(r7);
        if (r6 != 0) goto L_0x010b;
    L_0x0048:
        r6 = "RootTools::Installer";
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r17 = "Installing a new version of binary: ";
        r0 = r17;
        r7 = r7.append(r0);
        r0 = r25;
        r7 = r7.append(r0);
        r7 = r7.toString();
        android.util.Log.e(r6, r7);
        r0 = r23;
        r6 = r0.context;	 Catch:{ FileNotFoundException -> 0x010d, IOException -> 0x016d }
        r7 = "bogus";
        r11 = r6.openFileInput(r7);	 Catch:{ FileNotFoundException -> 0x010d, IOException -> 0x016d }
        r11.close();	 Catch:{ FileNotFoundException -> 0x010d, IOException -> 0x016d }
    L_0x0071:
        r0 = r23;
        r6 = r0.context;
        r6 = r6.getResources();
        r0 = r24;
        r13 = r6.openRawResource(r0);
        r3 = java.nio.channels.Channels.newChannel(r13);
        r15 = 0;
        r16 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x01a0 }
        r0 = r16;
        r0.<init>(r14);	 Catch:{ FileNotFoundException -> 0x01a0 }
        r2 = r16.getChannel();	 Catch:{ FileNotFoundException -> 0x01ee, all -> 0x01ea }
        r4 = 0;
        r6 = r13.available();	 Catch:{ IOException -> 0x017d }
        r0 = (long) r6;	 Catch:{ IOException -> 0x017d }
        r18 = r0;
    L_0x0098:
        r6 = r18 - r4;
        r6 = r2.transferFrom(r3, r4, r6);	 Catch:{ IOException -> 0x017d }
        r4 = r4 + r6;
        r6 = (r4 > r18 ? 1 : (r4 == r18 ? 0 : -1));
        if (r6 < 0) goto L_0x0098;
    L_0x00a3:
        if (r16 == 0) goto L_0x00b2;
    L_0x00a5:
        r16.flush();	 Catch:{ Exception -> 0x01f2 }
        r6 = r16.getFD();	 Catch:{ Exception -> 0x01f2 }
        r6.sync();	 Catch:{ Exception -> 0x01f2 }
        r16.close();	 Catch:{ Exception -> 0x01f2 }
    L_0x00b2:
        r13.close();	 Catch:{ IOException -> 0x01d4 }
        r8 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01e5 }
        r6 = 0;
        r7 = 0;
        r17 = 1;
        r0 = r17;
        r0 = new java.lang.String[r0];	 Catch:{ Exception -> 0x01e5 }
        r17 = r0;
        r20 = 0;
        r21 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01e5 }
        r21.<init>();	 Catch:{ Exception -> 0x01e5 }
        r22 = "chmod ";
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x01e5 }
        r0 = r21;
        r1 = r26;
        r21 = r0.append(r1);	 Catch:{ Exception -> 0x01e5 }
        r22 = " ";
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x01e5 }
        r0 = r23;
        r0 = r0.filesPath;	 Catch:{ Exception -> 0x01e5 }
        r22 = r0;
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x01e5 }
        r22 = java.io.File.separator;	 Catch:{ Exception -> 0x01e5 }
        r21 = r21.append(r22);	 Catch:{ Exception -> 0x01e5 }
        r0 = r21;
        r1 = r25;
        r21 = r0.append(r1);	 Catch:{ Exception -> 0x01e5 }
        r21 = r21.toString();	 Catch:{ Exception -> 0x01e5 }
        r17[r20] = r21;	 Catch:{ Exception -> 0x01e5 }
        r0 = r17;
        r8.<init>(r6, r7, r0);	 Catch:{ Exception -> 0x01e5 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01e5 }
        r6.add(r8);	 Catch:{ Exception -> 0x01e5 }
        r0 = r23;
        r0.commandWait(r8);	 Catch:{ Exception -> 0x01e5 }
    L_0x010b:
        r6 = 1;
    L_0x010c:
        return r6;
    L_0x010d:
        r9 = move-exception;
        r12 = 0;
        r0 = r23;
        r6 = r0.context;	 Catch:{ Exception -> 0x0139 }
        r7 = "bogus";
        r17 = 0;
        r0 = r17;
        r12 = r6.openFileOutput(r7, r0);	 Catch:{ Exception -> 0x0139 }
        r6 = "justcreatedfilesdirectory";
        r6 = r6.getBytes();	 Catch:{ Exception -> 0x0139 }
        r12.write(r6);	 Catch:{ Exception -> 0x0139 }
        if (r12 == 0) goto L_0x0071;
    L_0x0128:
        r12.close();	 Catch:{ IOException -> 0x0136 }
        r0 = r23;
        r6 = r0.context;	 Catch:{ IOException -> 0x0136 }
        r7 = "bogus";
        r6.deleteFile(r7);	 Catch:{ IOException -> 0x0136 }
        goto L_0x0071;
    L_0x0136:
        r6 = move-exception;
        goto L_0x0071;
    L_0x0139:
        r10 = move-exception;
        r6 = com.stericson.RootTools.C1040RootTools.debugMode;	 Catch:{ all -> 0x015b }
        if (r6 == 0) goto L_0x0147;
    L_0x013e:
        r6 = "RootTools::Installer";
        r7 = r10.toString();	 Catch:{ all -> 0x015b }
        android.util.Log.e(r6, r7);	 Catch:{ all -> 0x015b }
    L_0x0147:
        r6 = 0;
        if (r12 == 0) goto L_0x010c;
    L_0x014a:
        r12.close();	 Catch:{ IOException -> 0x0159 }
        r0 = r23;
        r7 = r0.context;	 Catch:{ IOException -> 0x0159 }
        r17 = "bogus";
        r0 = r17;
        r7.deleteFile(r0);	 Catch:{ IOException -> 0x0159 }
        goto L_0x010c;
    L_0x0159:
        r7 = move-exception;
        goto L_0x010c;
    L_0x015b:
        r6 = move-exception;
        if (r12 == 0) goto L_0x016c;
    L_0x015e:
        r12.close();	 Catch:{ IOException -> 0x01f5 }
        r0 = r23;
        r7 = r0.context;	 Catch:{ IOException -> 0x01f5 }
        r17 = "bogus";
        r0 = r17;
        r7.deleteFile(r0);	 Catch:{ IOException -> 0x01f5 }
    L_0x016c:
        throw r6;
    L_0x016d:
        r10 = move-exception;
        r6 = com.stericson.RootTools.C1040RootTools.debugMode;
        if (r6 == 0) goto L_0x017b;
    L_0x0172:
        r6 = "RootTools::Installer";
        r7 = r10.toString();
        android.util.Log.e(r6, r7);
    L_0x017b:
        r6 = 0;
        goto L_0x010c;
    L_0x017d:
        r10 = move-exception;
        r6 = com.stericson.RootTools.C1040RootTools.debugMode;	 Catch:{ FileNotFoundException -> 0x01ee, all -> 0x01ea }
        if (r6 == 0) goto L_0x018b;
    L_0x0182:
        r6 = "RootTools::Installer";
        r7 = r10.toString();	 Catch:{ FileNotFoundException -> 0x01ee, all -> 0x01ea }
        android.util.Log.e(r6, r7);	 Catch:{ FileNotFoundException -> 0x01ee, all -> 0x01ea }
    L_0x018b:
        r6 = 0;
        if (r16 == 0) goto L_0x010c;
    L_0x018e:
        r16.flush();	 Catch:{ Exception -> 0x019d }
        r7 = r16.getFD();	 Catch:{ Exception -> 0x019d }
        r7.sync();	 Catch:{ Exception -> 0x019d }
        r16.close();	 Catch:{ Exception -> 0x019d }
        goto L_0x010c;
    L_0x019d:
        r7 = move-exception;
        goto L_0x010c;
    L_0x01a0:
        r10 = move-exception;
    L_0x01a1:
        r6 = com.stericson.RootTools.C1040RootTools.debugMode;	 Catch:{ all -> 0x01c3 }
        if (r6 == 0) goto L_0x01ae;
    L_0x01a5:
        r6 = "RootTools::Installer";
        r7 = r10.toString();	 Catch:{ all -> 0x01c3 }
        android.util.Log.e(r6, r7);	 Catch:{ all -> 0x01c3 }
    L_0x01ae:
        r6 = 0;
        if (r15 == 0) goto L_0x010c;
    L_0x01b1:
        r15.flush();	 Catch:{ Exception -> 0x01c0 }
        r7 = r15.getFD();	 Catch:{ Exception -> 0x01c0 }
        r7.sync();	 Catch:{ Exception -> 0x01c0 }
        r15.close();	 Catch:{ Exception -> 0x01c0 }
        goto L_0x010c;
    L_0x01c0:
        r7 = move-exception;
        goto L_0x010c;
    L_0x01c3:
        r6 = move-exception;
    L_0x01c4:
        if (r15 == 0) goto L_0x01d3;
    L_0x01c6:
        r15.flush();	 Catch:{ Exception -> 0x01e8 }
        r7 = r15.getFD();	 Catch:{ Exception -> 0x01e8 }
        r7.sync();	 Catch:{ Exception -> 0x01e8 }
        r15.close();	 Catch:{ Exception -> 0x01e8 }
    L_0x01d3:
        throw r6;
    L_0x01d4:
        r10 = move-exception;
        r6 = com.stericson.RootTools.C1040RootTools.debugMode;
        if (r6 == 0) goto L_0x01e2;
    L_0x01d9:
        r6 = "RootTools::Installer";
        r7 = r10.toString();
        android.util.Log.e(r6, r7);
    L_0x01e2:
        r6 = 0;
        goto L_0x010c;
    L_0x01e5:
        r6 = move-exception;
        goto L_0x010b;
    L_0x01e8:
        r7 = move-exception;
        goto L_0x01d3;
    L_0x01ea:
        r6 = move-exception;
        r15 = r16;
        goto L_0x01c4;
    L_0x01ee:
        r10 = move-exception;
        r15 = r16;
        goto L_0x01a1;
    L_0x01f2:
        r6 = move-exception;
        goto L_0x00b2;
    L_0x01f5:
        r7 = move-exception;
        goto L_0x016c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stericson.RootTools.internal.Installer.installBinary(int, java.lang.String, java.lang.String):boolean");
    }

    protected boolean isBinaryInstalled(java.lang.String destName) {
        if (new java.io.File(this.filesPath + java.io.File.separator + destName).exists()) {
            return true;
        }
        return false;
    }

    protected java.lang.String getFileSignature(java.io.File f) {
        java.lang.String signature = "";
        try {
            return getStreamSignature(new java.io.FileInputStream(f));
        } catch (java.io.FileNotFoundException ex) {
            android.util.Log.e("RootTools::Installer", ex.toString());
            return signature;
        }
    }

    protected java.lang.String getStreamSignature(java.io.InputStream is) {
        java.lang.String signature = "";
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            do {
            } while (-1 != new java.security.DigestInputStream(is, md).read(new byte[4096]));
            byte[] digest = md.digest();
            java.lang.StringBuffer sb = new java.lang.StringBuffer();
            for (byte b : digest) {
                sb.append(java.lang.Integer.toHexString(b & 255));
            }
            signature = sb.toString();
            try {
                is.close();
            } catch (java.io.IOException e) {
            }
        } catch (java.io.IOException ex) {
            android.util.Log.e("RootTools::Installer", ex.toString());
            try {
                is.close();
            } catch (java.io.IOException e2) {
            }
        } catch (java.security.NoSuchAlgorithmException ex2) {
            android.util.Log.e("RootTools::Installer", ex2.toString());
            try {
                is.close();
            } catch (java.io.IOException e3) {
            }
        } catch (Throwable th) {
            try {
                is.close();
            } catch (java.io.IOException e4) {
            }
            throw th;
        }
        return signature;
    }

    private void commandWait(com.stericson.RootShell.execution.Command cmd) {
        synchronized (cmd) {
            try {
                if (!cmd.isFinished()) {
                    cmd.wait(2000);
                }
            } catch (java.lang.InterruptedException ex) {
                android.util.Log.e("RootTools::Installer", ex.toString());
            }
        }
    }
}
