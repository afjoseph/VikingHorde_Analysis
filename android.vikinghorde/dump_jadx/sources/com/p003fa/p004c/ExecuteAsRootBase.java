package com.p003fa.p004c;

/* renamed from: com.fa.c.ExecuteAsRootBase */
public abstract class ExecuteAsRootBase {
    protected abstract java.util.ArrayList<java.lang.String> getCommandsToExecute();

    public static boolean canRunRootCommands() {
        try {
            java.lang.Process suProcess = java.lang.Runtime.getRuntime().exec("su");
            java.io.DataOutputStream os = new java.io.DataOutputStream(suProcess.getOutputStream());
            java.io.DataInputStream osRes = new java.io.DataInputStream(suProcess.getInputStream());
            if (os == null || osRes == null) {
                return false;
            }
            boolean retval;
            boolean exitSu;
            os.writeBytes("id\n");
            os.flush();
            java.lang.String currUid = osRes.readLine();
            if (currUid == null) {
                retval = false;
                exitSu = false;
                android.util.Log.d("CPS", "Can't get root access or denied by user");
            } else if (currUid.contains("uid=0")) {
                retval = true;
                exitSu = true;
                android.util.Log.d("CPS", "Root access granted");
            } else {
                retval = false;
                exitSu = true;
                android.util.Log.d("ROOT", "Root access rejected: " + currUid);
            }
            if (!exitSu) {
                return retval;
            }
            os.writeBytes("exit\n");
            os.flush();
            return retval;
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x008c A:{Splitter:B:1:0x0008, ExcHandler: IOException (r2_1 'ex' java.io.IOException)} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x009b A:{Splitter:B:1:0x0008, ExcHandler: SecurityException (r2_2 'ex' java.lang.SecurityException)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:22:0x008c, code skipped:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:23:0x008d, code skipped:
            android.util.Log.w("CPS", "Can't get root access", r2);
     */
    /* JADX WARNING: Missing block: B:27:0x009b, code skipped:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x009c, code skipped:
            android.util.Log.w("CPS", "Can't get root access", r2);
     */
    /* JADX WARNING: Missing block: B:35:0x00b5, code skipped:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:37:?, code skipped:
            android.util.Log.e("CPS", "Error executing root action", r2);
     */
    /* JADX WARNING: Missing block: B:43:?, code skipped:
            return false;
     */
    /* JADX WARNING: Missing block: B:44:?, code skipped:
            return false;
     */
    /* JADX WARNING: Missing block: B:47:?, code skipped:
            return false;
     */
    public final boolean execute() {
        /*
        r13 = this;
        r4 = 0;
        r10 = "CPS";
        r11 = "EXECUTE STARTED";
        android.util.Log.d(r10, r11);
        r0 = r13.getCommandsToExecute();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x006b;
    L_0x000e:
        r10 = r0.size();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        if (r10 <= 0) goto L_0x006b;
    L_0x0014:
        r10 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11 = "su";
        r8 = r10.exec(r11);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r3 = new java.io.DataOutputStream;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10 = r8.getOutputStream();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r3.<init>(r10);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10 = r0.iterator();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
    L_0x002b:
        r11 = r10.hasNext();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        if (r11 != 0) goto L_0x006c;
    L_0x0031:
        r10 = "exit\n";
        r3.writeBytes(r10);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r3.flush();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r7 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11 = r8.getInputStream();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10.<init>(r11);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r7.<init>(r10);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r6 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11 = r8.getErrorStream();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r10.<init>(r11);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r6.<init>(r10);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r5 = 0;
    L_0x0056:
        r5 = r7.readLine();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        if (r5 != 0) goto L_0x0095;
    L_0x005c:
        r5 = r6.readLine();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        if (r5 != 0) goto L_0x00a4;
    L_0x0062:
        r9 = r8.waitFor();	 Catch:{ Exception -> 0x00b5, IOException -> 0x008c, SecurityException -> 0x009b }
        r10 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r10 == r9) goto L_0x00b3;
    L_0x006a:
        r4 = 1;
    L_0x006b:
        return r4;
    L_0x006c:
        r1 = r10.next();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r1 = (java.lang.String) r1;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r12 = java.lang.String.valueOf(r1);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11.<init>(r12);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r12 = "\n";
        r11 = r11.append(r12);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r11 = r11.toString();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r3.writeBytes(r11);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        r3.flush();	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        goto L_0x002b;
    L_0x008c:
        r2 = move-exception;
        r10 = "CPS";
        r11 = "Can't get root access";
        android.util.Log.w(r10, r11, r2);
        goto L_0x006b;
    L_0x0095:
        r10 = "CPS";
        android.util.Log.d(r10, r5);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        goto L_0x0056;
    L_0x009b:
        r2 = move-exception;
        r10 = "CPS";
        r11 = "Can't get root access";
        android.util.Log.w(r10, r11, r2);
        goto L_0x006b;
    L_0x00a4:
        r10 = "CPS";
        android.util.Log.d(r10, r5);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        goto L_0x005c;
    L_0x00aa:
        r2 = move-exception;
        r10 = "CPS";
        r11 = "Error executing internal operation";
        android.util.Log.w(r10, r11, r2);
        goto L_0x006b;
    L_0x00b3:
        r4 = 0;
        goto L_0x006b;
    L_0x00b5:
        r2 = move-exception;
        r10 = "CPS";
        r11 = "Error executing root action";
        android.util.Log.e(r10, r11, r2);	 Catch:{ IOException -> 0x008c, SecurityException -> 0x009b, Exception -> 0x00aa }
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p003fa.p004c.ExecuteAsRootBase.execute():boolean");
    }
}
