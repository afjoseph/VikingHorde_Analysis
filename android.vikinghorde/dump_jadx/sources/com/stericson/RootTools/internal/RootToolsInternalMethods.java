package com.stericson.RootTools.internal;

public final class RootToolsInternalMethods {
    protected RootToolsInternalMethods() {
    }

    public static void getInstance() {
        com.stericson.RootTools.C1040RootTools.setRim(new com.stericson.RootTools.internal.RootToolsInternalMethods());
    }

    public java.util.ArrayList<com.stericson.RootTools.containers.Symlink> getSymLinks() throws java.io.IOException {
        java.lang.Throwable th;
        java.io.LineNumberReader lnr = null;
        java.io.FileReader fr = null;
        try {
            java.io.FileReader fr2 = new java.io.FileReader("/data/local/symlinks.txt");
            try {
                java.io.LineNumberReader lnr2 = new java.io.LineNumberReader(fr2);
                try {
                    java.util.ArrayList<com.stericson.RootTools.containers.Symlink> symlink = new java.util.ArrayList();
                    while (true) {
                        java.lang.String line = lnr2.readLine();
                        if (line != null) {
                            com.stericson.RootTools.C1040RootTools.log(line);
                            java.lang.String[] fields = line.split(" ");
                            symlink.add(new com.stericson.RootTools.containers.Symlink(new java.io.File(fields[fields.length - 3]), new java.io.File(fields[fields.length - 1])));
                        } else {
                            try {
                                break;
                            } catch (java.lang.Exception e) {
                            }
                        }
                    }
                    fr2.close();
                    try {
                        lnr2.close();
                    } catch (java.lang.Exception e2) {
                    }
                    return symlink;
                } catch (Throwable th2) {
                    th = th2;
                    fr = fr2;
                    lnr = lnr2;
                    try {
                        fr.close();
                    } catch (java.lang.Exception e3) {
                    }
                    try {
                        lnr.close();
                    } catch (java.lang.Exception e4) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fr = fr2;
                fr.close();
                lnr.close();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fr.close();
            lnr.close();
            throw th;
        }
    }

    public com.stericson.RootTools.containers.Permissions getPermissions(java.lang.String line) {
        java.lang.String rawPermissions = line.split(" ")[0];
        if (rawPermissions.length() != 10 || ((rawPermissions.charAt(0) != '-' && rawPermissions.charAt(0) != 'd' && rawPermissions.charAt(0) != 'l') || ((rawPermissions.charAt(1) != '-' && rawPermissions.charAt(1) != 'r') || (rawPermissions.charAt(2) != '-' && rawPermissions.charAt(2) != 'w')))) {
            return null;
        }
        com.stericson.RootTools.C1040RootTools.log(rawPermissions);
        com.stericson.RootTools.containers.Permissions permissions = new com.stericson.RootTools.containers.Permissions();
        permissions.setType(rawPermissions.substring(0, 1));
        com.stericson.RootTools.C1040RootTools.log(permissions.getType());
        permissions.setUserPermissions(rawPermissions.substring(1, 4));
        com.stericson.RootTools.C1040RootTools.log(permissions.getUserPermissions());
        permissions.setGroupPermissions(rawPermissions.substring(4, 7));
        com.stericson.RootTools.C1040RootTools.log(permissions.getGroupPermissions());
        permissions.setOtherPermissions(rawPermissions.substring(7, 10));
        com.stericson.RootTools.C1040RootTools.log(permissions.getOtherPermissions());
        java.lang.StringBuilder finalPermissions = new java.lang.StringBuilder();
        finalPermissions.append(parseSpecialPermissions(rawPermissions));
        finalPermissions.append(parsePermissions(permissions.getUserPermissions()));
        finalPermissions.append(parsePermissions(permissions.getGroupPermissions()));
        finalPermissions.append(parsePermissions(permissions.getOtherPermissions()));
        permissions.setPermissions(java.lang.Integer.parseInt(finalPermissions.toString()));
        return permissions;
    }

    public int parsePermissions(java.lang.String permission) {
        int tmp;
        permission = permission.toLowerCase(java.util.Locale.US);
        if (permission.charAt(0) == 'r') {
            tmp = 4;
        } else {
            tmp = 0;
        }
        com.stericson.RootTools.C1040RootTools.log("permission " + tmp);
        com.stericson.RootTools.C1040RootTools.log("character " + permission.charAt(0));
        if (permission.charAt(1) == 'w') {
            tmp += 2;
        } else {
            tmp += 0;
        }
        com.stericson.RootTools.C1040RootTools.log("permission " + tmp);
        com.stericson.RootTools.C1040RootTools.log("character " + permission.charAt(1));
        if (permission.charAt(2) == 'x' || permission.charAt(2) == 's' || permission.charAt(2) == 't') {
            tmp++;
        } else {
            tmp += 0;
        }
        com.stericson.RootTools.C1040RootTools.log("permission " + tmp);
        com.stericson.RootTools.C1040RootTools.log("character " + permission.charAt(2));
        return tmp;
    }

    public int parseSpecialPermissions(java.lang.String permission) {
        int tmp = 0;
        if (permission.charAt(2) == 's') {
            tmp = 0 + 4;
        }
        if (permission.charAt(5) == 's') {
            tmp += 2;
        }
        if (permission.charAt(8) == 't') {
            tmp++;
        }
        com.stericson.RootTools.C1040RootTools.log("special permissions " + tmp);
        return tmp;
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0061  */
    public boolean copyFile(java.lang.String r13, java.lang.String r14, boolean r15, boolean r16) {
        /*
        r12 = this;
        r0 = 0;
        r5 = 1;
        if (r15 == 0) goto L_0x0009;
    L_0x0004:
        r6 = "RW";
        com.stericson.RootTools.C1040RootTools.remount(r14, r6);	 Catch:{ Exception -> 0x01c9 }
    L_0x0009:
        r6 = "cp";
        r6 = r12.checkUtil(r6);	 Catch:{ Exception -> 0x01c9 }
        if (r6 == 0) goto L_0x00ae;
    L_0x0011:
        r6 = "cp command is available!";
        com.stericson.RootTools.C1040RootTools.log(r6);	 Catch:{ Exception -> 0x01c9 }
        if (r16 == 0) goto L_0x006b;
    L_0x0018:
        r1 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01c9 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01c9 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c9 }
        r10.<init>();	 Catch:{ Exception -> 0x01c9 }
        r11 = "cp -fp ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x01c9 }
        r11 = " ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01c9 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01c9 }
        r1.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r6.add(r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r12.commandWait(r6, r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = r1.getExitCode();	 Catch:{ Exception -> 0x01d6 }
        if (r6 != 0) goto L_0x0069;
    L_0x0056:
        r5 = 1;
    L_0x0057:
        r0 = r1;
    L_0x0058:
        if (r15 == 0) goto L_0x005f;
    L_0x005a:
        r6 = "RO";
        com.stericson.RootTools.C1040RootTools.remount(r14, r6);	 Catch:{ Exception -> 0x01c9 }
    L_0x005f:
        if (r0 == 0) goto L_0x0068;
    L_0x0061:
        r6 = r0.getExitCode();
        if (r6 != 0) goto L_0x01d3;
    L_0x0067:
        r5 = 1;
    L_0x0068:
        return r5;
    L_0x0069:
        r5 = 0;
        goto L_0x0057;
    L_0x006b:
        r1 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01c9 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01c9 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c9 }
        r10.<init>();	 Catch:{ Exception -> 0x01c9 }
        r11 = "cp -f ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x01c9 }
        r11 = " ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01c9 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01c9 }
        r1.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r6.add(r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r12.commandWait(r6, r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = r1.getExitCode();	 Catch:{ Exception -> 0x01d6 }
        if (r6 != 0) goto L_0x00ac;
    L_0x00a9:
        r5 = 1;
    L_0x00aa:
        r0 = r1;
        goto L_0x0058;
    L_0x00ac:
        r5 = 0;
        goto L_0x00aa;
    L_0x00ae:
        r6 = "busybox";
        r6 = r12.checkUtil(r6);	 Catch:{ Exception -> 0x01c9 }
        if (r6 == 0) goto L_0x013d;
    L_0x00b6:
        r6 = "cp";
        r7 = "busybox";
        r6 = r12.hasUtil(r6, r7);	 Catch:{ Exception -> 0x01c9 }
        if (r6 == 0) goto L_0x013d;
    L_0x00c0:
        r6 = "busybox cp command is available!";
        com.stericson.RootTools.C1040RootTools.log(r6);	 Catch:{ Exception -> 0x01c9 }
        if (r16 == 0) goto L_0x0102;
    L_0x00c7:
        r1 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01c9 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01c9 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c9 }
        r10.<init>();	 Catch:{ Exception -> 0x01c9 }
        r11 = "busybox cp -fp ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x01c9 }
        r11 = " ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01c9 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01c9 }
        r1.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r6.add(r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r12.commandWait(r6, r1);	 Catch:{ Exception -> 0x01d6 }
        r0 = r1;
        goto L_0x0058;
    L_0x0102:
        r1 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01c9 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01c9 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c9 }
        r10.<init>();	 Catch:{ Exception -> 0x01c9 }
        r11 = "busybox cp -f ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x01c9 }
        r11 = " ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01c9 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01c9 }
        r1.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r6.add(r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r12.commandWait(r6, r1);	 Catch:{ Exception -> 0x01d6 }
        r0 = r1;
        goto L_0x0058;
    L_0x013d:
        r6 = "cat";
        r6 = r12.checkUtil(r6);	 Catch:{ Exception -> 0x01c9 }
        if (r6 == 0) goto L_0x01d0;
    L_0x0145:
        r6 = "cp is not available, use cat!";
        com.stericson.RootTools.C1040RootTools.log(r6);	 Catch:{ Exception -> 0x01c9 }
        r3 = -1;
        if (r16 == 0) goto L_0x0155;
    L_0x014d:
        r4 = r12.getFilePermissionsSymlinks(r13);	 Catch:{ Exception -> 0x01c9 }
        r3 = r4.getPermissions();	 Catch:{ Exception -> 0x01c9 }
    L_0x0155:
        r1 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01c9 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01c9 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01c9 }
        r10.<init>();	 Catch:{ Exception -> 0x01c9 }
        r11 = "cat ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r13);	 Catch:{ Exception -> 0x01c9 }
        r11 = " > ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01c9 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01c9 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01c9 }
        r1.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r6.add(r1);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01d6 }
        r12.commandWait(r6, r1);	 Catch:{ Exception -> 0x01d6 }
        if (r16 == 0) goto L_0x01d9;
    L_0x018f:
        r0 = new com.stericson.RootShell.execution.Command;	 Catch:{ Exception -> 0x01d6 }
        r6 = 0;
        r7 = 0;
        r8 = 1;
        r8 = new java.lang.String[r8];	 Catch:{ Exception -> 0x01d6 }
        r9 = 0;
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01d6 }
        r10.<init>();	 Catch:{ Exception -> 0x01d6 }
        r11 = "chmod ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01d6 }
        r10 = r10.append(r3);	 Catch:{ Exception -> 0x01d6 }
        r11 = " ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x01d6 }
        r10 = r10.append(r14);	 Catch:{ Exception -> 0x01d6 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x01d6 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x01d6 }
        r0.<init>(r6, r7, r8);	 Catch:{ Exception -> 0x01d6 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01c9 }
        r6.add(r0);	 Catch:{ Exception -> 0x01c9 }
        r6 = com.stericson.RootShell.execution.Shell.startRootShell();	 Catch:{ Exception -> 0x01c9 }
        r12.commandWait(r6, r0);	 Catch:{ Exception -> 0x01c9 }
        goto L_0x0058;
    L_0x01c9:
        r2 = move-exception;
    L_0x01ca:
        r2.printStackTrace();
        r5 = 0;
        goto L_0x005f;
    L_0x01d0:
        r5 = 0;
        goto L_0x0058;
    L_0x01d3:
        r5 = 0;
        goto L_0x0068;
    L_0x01d6:
        r2 = move-exception;
        r0 = r1;
        goto L_0x01ca;
    L_0x01d9:
        r0 = r1;
        goto L_0x0058;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stericson.RootTools.internal.RootToolsInternalMethods.copyFile(java.lang.String, java.lang.String, boolean, boolean):boolean");
    }

    public boolean checkUtil(java.lang.String util) {
        java.util.List<java.lang.String> foundPaths = com.stericson.RootShell.C1029RootShell.findBinary(util);
        if (foundPaths.size() > 0) {
            for (java.lang.String path : foundPaths) {
                com.stericson.RootTools.containers.Permissions permissions = com.stericson.RootTools.C1040RootTools.getFilePermissionsSymlinks(path + "/" + util);
                if (permissions != null) {
                    java.lang.String permission;
                    if (java.lang.Integer.toString(permissions.getPermissions()).length() > 3) {
                        permission = java.lang.Integer.toString(permissions.getPermissions()).substring(1);
                    } else {
                        permission = java.lang.Integer.toString(permissions.getPermissions());
                    }
                    if (permission.equals("755") || permission.equals("777") || permission.equals("775")) {
                        com.stericson.RootTools.C1040RootTools.utilPath = path + "/" + util;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean deleteFileOrDirectory(java.lang.String target, boolean remountAsRw) {
        boolean result = true;
        if (remountAsRw) {
            try {
                com.stericson.RootTools.C1040RootTools.remount(target, "RW");
            } catch (java.lang.Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        com.stericson.RootShell.execution.Command command;
        if (hasUtil("rm", "toolbox")) {
            com.stericson.RootTools.C1040RootTools.log("rm command is available!");
            command = new com.stericson.RootShell.execution.Command(0, false, "rm -r " + target);
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            if (command.getExitCode() != 0) {
                com.stericson.RootTools.C1040RootTools.log("target not exist or unable to delete file");
                result = false;
            }
        } else if (checkUtil("busybox") && hasUtil("rm", "busybox")) {
            com.stericson.RootTools.C1040RootTools.log("busybox rm command is available!");
            command = new com.stericson.RootShell.execution.Command(0, false, "busybox rm -rf " + target);
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            if (command.getExitCode() != 0) {
                com.stericson.RootTools.C1040RootTools.log("target not exist or unable to delete file");
                result = false;
            }
        }
        if (!remountAsRw) {
            return result;
        }
        com.stericson.RootTools.C1040RootTools.remount(target, "RO");
        return result;
    }

    public void fixUtil(java.lang.String util, java.lang.String utilPath) {
        try {
            com.stericson.RootTools.C1040RootTools.remount("/system", "rw");
            java.util.List<java.lang.String> foundPaths = com.stericson.RootShell.C1029RootShell.findBinary(util);
            if (foundPaths.size() > 0) {
                com.stericson.RootShell.execution.Command command;
                for (java.lang.String path : foundPaths) {
                    command = new com.stericson.RootShell.execution.Command(0, false, utilPath + " rm " + path + "/" + util);
                    com.stericson.RootShell.C1029RootShell.getShell(true).add(command);
                    commandWait(com.stericson.RootShell.C1029RootShell.getShell(true), command);
                }
                command = new com.stericson.RootShell.execution.Command(0, false, utilPath + " ln -s " + utilPath + " /system/bin/" + util, utilPath + " chmod 0755 /system/bin/" + util);
                com.stericson.RootShell.C1029RootShell.getShell(true).add(command);
                commandWait(com.stericson.RootShell.C1029RootShell.getShell(true), command);
            }
            com.stericson.RootTools.C1040RootTools.remount("/system", "ro");
        } catch (java.lang.Exception e) {
        }
    }

    public boolean fixUtils(java.lang.String[] utils) throws java.lang.Exception {
        for (java.lang.String util : utils) {
            if (!checkUtil(util)) {
                if (checkUtil("busybox")) {
                    if (hasUtil(util, "busybox")) {
                        fixUtil(util, com.stericson.RootTools.C1040RootTools.utilPath);
                    }
                } else if (!checkUtil("toolbox")) {
                    return false;
                } else {
                    if (hasUtil(util, "toolbox")) {
                        fixUtil(util, com.stericson.RootTools.C1040RootTools.utilPath);
                    }
                }
            }
        }
        return true;
    }

    public java.util.List<java.lang.String> getBusyBoxApplets(java.lang.String path) throws java.lang.Exception {
        if (path != null && !path.endsWith("/") && !path.equals("")) {
            path = path + "/";
        } else if (path == null) {
            throw new java.lang.Exception("Path is null, please specifiy a path");
        }
        final java.util.List<java.lang.String> results = new java.util.ArrayList();
        com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(3, false, new java.lang.String[]{path + "busybox --list"}) {
            public void commandOutput(int id, java.lang.String line) {
                if (!(id != 3 || line.trim().equals("") || line.trim().contains("not found") || line.trim().contains("file busy"))) {
                    results.add(line);
                }
                super.commandOutput(id, line);
            }
        };
        com.stericson.RootShell.C1029RootShell.getShell(false).add(command);
        commandWait(com.stericson.RootShell.C1029RootShell.getShell(false), command);
        if (results.size() <= 0) {
            com.stericson.RootShell.C1029RootShell.getShell(true).add(command);
            commandWait(com.stericson.RootShell.C1029RootShell.getShell(true), command);
        }
        return results;
    }

    public java.lang.String getBusyBoxVersion(java.lang.String path) {
        if (!(path.equals("") || path.endsWith("/"))) {
            path = path + "/";
        }
        com.stericson.RootTools.internal.InternalVariables.busyboxVersion = "";
        try {
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(4, false, path + "busybox") {
                public void commandOutput(int id, java.lang.String line) {
                    line = line.trim();
                    if (id == 4) {
                        com.stericson.RootTools.C1040RootTools.log("Version Output: " + line);
                        java.lang.String[] temp = line.split(" ");
                        if (temp.length > 1 && temp[1].contains("v1.") && !false) {
                            com.stericson.RootTools.internal.InternalVariables.busyboxVersion = temp[1];
                            com.stericson.RootTools.C1040RootTools.log("Found Version: " + com.stericson.RootTools.internal.InternalVariables.busyboxVersion);
                        }
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootTools.C1040RootTools.log("Getting BusyBox Version without root");
            com.stericson.RootShell.execution.Shell shell = com.stericson.RootTools.C1040RootTools.getShell(false);
            shell.add(command);
            commandWait(shell, command);
            if (com.stericson.RootTools.internal.InternalVariables.busyboxVersion.length() <= 0) {
                com.stericson.RootTools.C1040RootTools.log("Getting BusyBox Version with root");
                com.stericson.RootShell.execution.Shell rootShell = com.stericson.RootTools.C1040RootTools.getShell(true);
                rootShell.add(command);
                commandWait(rootShell, command);
            }
            return com.stericson.RootTools.internal.InternalVariables.busyboxVersion;
        } catch (java.lang.Exception e) {
            com.stericson.RootTools.C1040RootTools.log("BusyBox was not found, more information MAY be available with Debugging on.");
            return "";
        }
    }

    public long getConvertedSpace(java.lang.String spaceStr) {
        double multiplier = 1.0d;
        try {
            java.lang.StringBuffer sb = new java.lang.StringBuffer();
            int i = 0;
            while (i < spaceStr.length()) {
                char c = spaceStr.charAt(i);
                if (java.lang.Character.isDigit(c) || c == '.') {
                    sb.append(spaceStr.charAt(i));
                    i++;
                } else if (c == 'm' || c == 'M') {
                    multiplier = 1024.0d;
                    return (long) java.lang.Math.ceil(java.lang.Double.valueOf(sb.toString()).doubleValue() * multiplier);
                } else {
                    if (c == 'g' || c == 'G') {
                        multiplier = 1048576.0d;
                    }
                    return (long) java.lang.Math.ceil(java.lang.Double.valueOf(sb.toString()).doubleValue() * multiplier);
                }
            }
            return (long) java.lang.Math.ceil(java.lang.Double.valueOf(sb.toString()).doubleValue() * multiplier);
        } catch (java.lang.Exception e) {
            return -1;
        }
    }

    public java.lang.String getInode(java.lang.String file) {
        try {
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(5, false, "/data/local/ls -i " + file) {
                public void commandOutput(int id, java.lang.String line) {
                    if (id == 5 && !line.trim().equals("") && java.lang.Character.isDigit(line.trim().substring(0, 1).toCharArray()[0])) {
                        com.stericson.RootTools.internal.InternalVariables.inode = line.trim().split(" ")[0];
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            return com.stericson.RootTools.internal.InternalVariables.inode;
        } catch (java.lang.Exception e) {
            return "";
        }
    }

    public boolean isNativeToolsReady(int nativeToolsId, android.content.Context context) {
        com.stericson.RootTools.C1040RootTools.log("Preparing Native Tools");
        com.stericson.RootTools.internal.InternalVariables.nativeToolsReady = false;
        try {
            com.stericson.RootTools.internal.Installer installer = new com.stericson.RootTools.internal.Installer(context);
            if (installer.isBinaryInstalled("nativetools")) {
                com.stericson.RootTools.internal.InternalVariables.nativeToolsReady = true;
            } else {
                com.stericson.RootTools.internal.InternalVariables.nativeToolsReady = installer.installBinary(nativeToolsId, "nativetools", "700");
            }
            return com.stericson.RootTools.internal.InternalVariables.nativeToolsReady;
        } catch (java.io.IOException ex) {
            if (!com.stericson.RootTools.C1040RootTools.debugMode) {
                return false;
            }
            ex.printStackTrace();
            return false;
        }
    }

    public com.stericson.RootTools.containers.Permissions getFilePermissionsSymlinks(java.lang.String file) {
        com.stericson.RootTools.containers.Permissions permissions = null;
        com.stericson.RootTools.C1040RootTools.log("Checking permissions for " + file);
        if (!com.stericson.RootTools.C1040RootTools.exists(file)) {
            return permissions;
        }
        com.stericson.RootTools.C1040RootTools.log(file + " was found.");
        try {
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(1, false, "ls -l " + file, "busybox ls -l " + file, "/system/bin/failsafe/toolbox ls -l " + file, "toolbox ls -l " + file) {
                public void commandOutput(int id, java.lang.String line) {
                    if (id == 1) {
                        java.lang.String symlink_final = "";
                        if (line.split(" ")[0].length() != 10) {
                            super.commandOutput(id, line);
                            return;
                        }
                        com.stericson.RootTools.C1040RootTools.log("Line " + line);
                        try {
                            java.lang.String[] symlink = line.split(" ");
                            if (symlink[symlink.length - 2].equals("->")) {
                                com.stericson.RootTools.C1040RootTools.log("Symlink found.");
                                symlink_final = symlink[symlink.length - 1];
                            }
                        } catch (java.lang.Exception e) {
                        }
                        try {
                            com.stericson.RootTools.internal.InternalVariables.permissions = com.stericson.RootTools.internal.RootToolsInternalMethods.this.getPermissions(line);
                            if (com.stericson.RootTools.internal.InternalVariables.permissions != null) {
                                com.stericson.RootTools.internal.InternalVariables.permissions.setSymlink(symlink_final);
                            }
                        } catch (java.lang.Exception e2) {
                            com.stericson.RootTools.C1040RootTools.log(e2.getMessage());
                        }
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootShell.C1029RootShell.getShell(true).add(command);
            commandWait(com.stericson.RootShell.C1029RootShell.getShell(true), command);
            return com.stericson.RootTools.internal.InternalVariables.permissions;
        } catch (java.lang.Exception e) {
            com.stericson.RootTools.C1040RootTools.log(e.getMessage());
            return permissions;
        }
    }

    public java.util.ArrayList<com.stericson.RootTools.containers.Mount> getMounts() throws java.lang.Exception {
        java.lang.Throwable th;
        com.stericson.RootShell.execution.Shell shell = com.stericson.RootTools.C1040RootTools.getShell(true);
        com.stericson.RootShell.execution.Command cmd = new com.stericson.RootShell.execution.Command(0, false, "cat /proc/mounts > /data/local/RootToolsMounts", "chmod 0777 /data/local/RootToolsMounts");
        shell.add(cmd);
        commandWait(shell, cmd);
        java.io.LineNumberReader lnr = null;
        java.io.FileReader fr = null;
        try {
            java.io.FileReader fr2 = new java.io.FileReader("/data/local/RootToolsMounts");
            try {
                java.io.LineNumberReader lnr2 = new java.io.LineNumberReader(fr2);
                try {
                    java.util.ArrayList<com.stericson.RootTools.containers.Mount> mounts = new java.util.ArrayList();
                    while (true) {
                        java.lang.String line = lnr2.readLine();
                        if (line == null) {
                            break;
                        }
                        com.stericson.RootTools.C1040RootTools.log(line);
                        java.lang.String[] fields = line.split(" ");
                        mounts.add(new com.stericson.RootTools.containers.Mount(new java.io.File(fields[0]), new java.io.File(fields[1]), fields[2], fields[3]));
                    }
                    com.stericson.RootTools.internal.InternalVariables.mounts = mounts;
                    if (com.stericson.RootTools.internal.InternalVariables.mounts != null) {
                        java.util.ArrayList arrayList = com.stericson.RootTools.internal.InternalVariables.mounts;
                        try {
                            fr2.close();
                        } catch (java.lang.Exception e) {
                        }
                        try {
                            lnr2.close();
                        } catch (java.lang.Exception e2) {
                        }
                        return arrayList;
                    }
                    throw new java.lang.Exception();
                } catch (Throwable th2) {
                    th = th2;
                    fr = fr2;
                    lnr = lnr2;
                    try {
                        fr.close();
                    } catch (java.lang.Exception e3) {
                    }
                    try {
                        lnr.close();
                    } catch (java.lang.Exception e4) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fr = fr2;
                fr.close();
                lnr.close();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fr.close();
            lnr.close();
            throw th;
        }
    }

    public java.lang.String getMountedAs(java.lang.String path) throws java.lang.Exception {
        com.stericson.RootTools.internal.InternalVariables.mounts = getMounts();
        if (com.stericson.RootTools.internal.InternalVariables.mounts != null) {
            java.util.Iterator i$ = com.stericson.RootTools.internal.InternalVariables.mounts.iterator();
            while (i$.hasNext()) {
                com.stericson.RootTools.containers.Mount mount = (com.stericson.RootTools.containers.Mount) i$.next();
                java.lang.String mp = mount.getMountPoint().getAbsolutePath();
                if (mp.equals("/")) {
                    if (path.equals("/")) {
                        return (java.lang.String) mount.getFlags().toArray()[0];
                    }
                } else if (path.equals(mp) || path.startsWith(mp + "/")) {
                    com.stericson.RootTools.C1040RootTools.log((java.lang.String) mount.getFlags().toArray()[0]);
                    return (java.lang.String) mount.getFlags().toArray()[0];
                }
            }
            throw new java.lang.Exception();
        }
        throw new java.lang.Exception();
    }

    public long getSpace(java.lang.String path) {
        com.stericson.RootTools.internal.InternalVariables.getSpaceFor = path;
        boolean found = false;
        com.stericson.RootTools.C1040RootTools.log("Looking for Space");
        try {
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(6, false, "df " + path) {
                public void commandOutput(int id, java.lang.String line) {
                    if (id == 6 && line.contains(com.stericson.RootTools.internal.InternalVariables.getSpaceFor.trim())) {
                        com.stericson.RootTools.internal.InternalVariables.space = line.split(" ");
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
        } catch (java.lang.Exception e) {
        }
        if (com.stericson.RootTools.internal.InternalVariables.space != null) {
            com.stericson.RootTools.C1040RootTools.log("First Method");
            for (java.lang.String spaceSearch : com.stericson.RootTools.internal.InternalVariables.space) {
                com.stericson.RootTools.C1040RootTools.log(spaceSearch);
                if (found) {
                    return getConvertedSpace(spaceSearch);
                }
                if (spaceSearch.equals("used,")) {
                    found = true;
                }
            }
            int count = 0;
            int targetCount = 3;
            com.stericson.RootTools.C1040RootTools.log("Second Method");
            if (com.stericson.RootTools.internal.InternalVariables.space[0].length() <= 5) {
                targetCount = 2;
            }
            for (java.lang.String spaceSearch2 : com.stericson.RootTools.internal.InternalVariables.space) {
                com.stericson.RootTools.C1040RootTools.log(spaceSearch2);
                if (spaceSearch2.length() > 0) {
                    com.stericson.RootTools.C1040RootTools.log(spaceSearch2 + "Valid");
                    if (count == targetCount) {
                        return getConvertedSpace(spaceSearch2);
                    }
                    count++;
                }
            }
        }
        com.stericson.RootTools.C1040RootTools.log("Returning -1, space could not be determined.");
        return -1;
    }

    public java.lang.String getSymlink(java.lang.String file) {
        com.stericson.RootTools.C1040RootTools.log("Looking for Symlink for " + file);
        try {
            final java.util.List<java.lang.String> results = new java.util.ArrayList();
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(7, false, new java.lang.String[]{"ls -l " + file}) {
                public void commandOutput(int id, java.lang.String line) {
                    if (id == 7 && !line.trim().equals("")) {
                        results.add(line);
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            java.lang.String[] symlink = ((java.lang.String) results.get(0)).split(" ");
            if (symlink.length > 2 && symlink[symlink.length - 2].equals("->")) {
                com.stericson.RootTools.C1040RootTools.log("Symlink found.");
                if (symlink[symlink.length - 1].equals("") || symlink[symlink.length - 1].contains("/")) {
                    return symlink[symlink.length - 1];
                }
                java.util.List<java.lang.String> paths = com.stericson.RootShell.C1029RootShell.findBinary(symlink[symlink.length - 1]);
                if (paths.size() > 0) {
                    return ((java.lang.String) paths.get(0)) + symlink[symlink.length - 1];
                }
                return symlink[symlink.length - 1];
            }
        } catch (java.lang.Exception e) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                e.printStackTrace();
            }
        }
        com.stericson.RootTools.C1040RootTools.log("Symlink not found");
        return "";
    }

    public java.util.ArrayList<com.stericson.RootTools.containers.Symlink> getSymlinks(java.lang.String path) throws java.lang.Exception {
        if (checkUtil("find")) {
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, "dd if=/dev/zero of=/data/local/symlinks.txt bs=1024 count=1", "chmod 0777 /data/local/symlinks.txt");
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            command = new com.stericson.RootShell.execution.Command(0, false, "find " + path + " -type l -exec ls -l {} \\; > /data/local/symlinks.txt");
            com.stericson.RootShell.execution.Shell.startRootShell().add(command);
            commandWait(com.stericson.RootShell.execution.Shell.startRootShell(), command);
            com.stericson.RootTools.internal.InternalVariables.symlinks = getSymLinks();
            if (com.stericson.RootTools.internal.InternalVariables.symlinks != null) {
                return com.stericson.RootTools.internal.InternalVariables.symlinks;
            }
            throw new java.lang.Exception();
        }
        throw new java.lang.Exception();
    }

    public java.lang.String getWorkingToolbox() {
        if (com.stericson.RootTools.C1040RootTools.checkUtil("busybox")) {
            return "busybox";
        }
        if (com.stericson.RootTools.C1040RootTools.checkUtil("toolbox")) {
            return "toolbox";
        }
        return "";
    }

    public boolean hasEnoughSpaceOnSdCard(long updateSize) {
        com.stericson.RootTools.C1040RootTools.log("Checking SDcard size and that it is mounted as RW");
        if (!android.os.Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        android.os.StatFs stat = new android.os.StatFs(android.os.Environment.getExternalStorageDirectory().getPath());
        if (updateSize < ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize())) {
            return true;
        }
        return false;
    }

    public boolean hasUtil(java.lang.String util, java.lang.String box) {
        com.stericson.RootTools.internal.InternalVariables.found = false;
        if (!box.endsWith("toolbox") && !box.endsWith("busybox")) {
            return false;
        }
        try {
            java.lang.String str;
            java.lang.String[] strArr = new java.lang.String[1];
            if (box.endsWith("toolbox")) {
                str = box + " " + util;
            } else {
                str = box + " --list";
            }
            strArr[0] = str;
            final java.lang.String str2 = box;
            final java.lang.String str3 = util;
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, strArr) {
                public void commandOutput(int id, java.lang.String line) {
                    if (str2.endsWith("toolbox")) {
                        if (!line.contains("no such tool")) {
                            com.stericson.RootTools.internal.InternalVariables.found = true;
                        }
                    } else if (str2.endsWith("busybox") && line.contains(str3)) {
                        com.stericson.RootTools.C1040RootTools.log("Found util!");
                        com.stericson.RootTools.internal.InternalVariables.found = true;
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootTools.C1040RootTools.getShell(true).add(command);
            commandWait(com.stericson.RootTools.C1040RootTools.getShell(true), command);
            if (com.stericson.RootTools.internal.InternalVariables.found) {
                com.stericson.RootTools.C1040RootTools.log("Box contains " + util + " util!");
                return true;
            }
            com.stericson.RootTools.C1040RootTools.log("Box does not contain " + util + " util!");
            return false;
        } catch (java.lang.Exception e) {
            com.stericson.RootTools.C1040RootTools.log(e.getMessage());
            return false;
        }
    }

    public boolean installBinary(android.content.Context context, int sourceId, java.lang.String destName, java.lang.String mode) {
        try {
            return new com.stericson.RootTools.internal.Installer(context).installBinary(sourceId, destName, mode);
        } catch (java.io.IOException ex) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public boolean isBinaryAvailable(android.content.Context context, java.lang.String binaryName) {
        try {
            return new com.stericson.RootTools.internal.Installer(context).isBinaryInstalled(binaryName);
        } catch (java.io.IOException ex) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public boolean isAppletAvailable(java.lang.String applet, java.lang.String binaryPath) {
        try {
            for (java.lang.String aplet : getBusyBoxApplets(binaryPath)) {
                if (aplet.equals(applet)) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.Exception e) {
            com.stericson.RootTools.C1040RootTools.log(e.toString());
            return false;
        }
    }

    public boolean isProcessRunning(java.lang.String processName) {
        com.stericson.RootTools.C1040RootTools.log("Checks if process is running: " + processName);
        com.stericson.RootTools.internal.InternalVariables.processRunning = false;
        try {
            final java.lang.String str = processName;
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, new java.lang.String[]{"ps"}) {
                public void commandOutput(int id, java.lang.String line) {
                    if (line.contains(str)) {
                        com.stericson.RootTools.internal.InternalVariables.processRunning = true;
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootTools.C1040RootTools.getShell(true).add(command);
            commandWait(com.stericson.RootTools.C1040RootTools.getShell(true), command);
        } catch (java.lang.Exception e) {
            com.stericson.RootTools.C1040RootTools.log(e.getMessage());
        }
        return com.stericson.RootTools.internal.InternalVariables.processRunning;
    }

    public boolean killProcess(java.lang.String processName) {
        java.lang.Exception e;
        com.stericson.RootTools.C1040RootTools.log("Killing process " + processName);
        com.stericson.RootTools.internal.InternalVariables.pid_list = "";
        com.stericson.RootTools.internal.InternalVariables.processRunning = true;
        try {
            final java.lang.String str = processName;
            com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, false, new java.lang.String[]{"ps"}) {
                public void commandOutput(int id, java.lang.String line) {
                    if (line.contains(str)) {
                        java.util.regex.Matcher psMatcher = com.stericson.RootTools.internal.InternalVariables.psPattern.matcher(line);
                        try {
                            if (psMatcher.find()) {
                                java.lang.String pid = psMatcher.group(1);
                                com.stericson.RootTools.internal.InternalVariables.pid_list += " " + pid;
                                com.stericson.RootTools.internal.InternalVariables.pid_list = com.stericson.RootTools.internal.InternalVariables.pid_list.trim();
                                com.stericson.RootTools.C1040RootTools.log("Found pid: " + pid);
                            } else {
                                com.stericson.RootTools.C1040RootTools.log("Matching in ps command failed!");
                            }
                        } catch (java.lang.Exception e) {
                            com.stericson.RootTools.C1040RootTools.log("Error with regex!");
                            e.printStackTrace();
                        }
                    }
                    super.commandOutput(id, line);
                }
            };
            com.stericson.RootTools.C1040RootTools.getShell(true).add(command);
            commandWait(com.stericson.RootTools.C1040RootTools.getShell(true), command);
            if (com.stericson.RootTools.internal.InternalVariables.pid_list.equals("")) {
                return true;
            }
            try {
                com.stericson.RootShell.execution.Command command2 = new com.stericson.RootShell.execution.Command(0, false, "kill -9 " + com.stericson.RootTools.internal.InternalVariables.pid_list);
                try {
                    com.stericson.RootTools.C1040RootTools.getShell(true).add(command2);
                    commandWait(com.stericson.RootTools.C1040RootTools.getShell(true), command2);
                    return true;
                } catch (java.lang.Exception e2) {
                    e = e2;
                    command = command2;
                    com.stericson.RootTools.C1040RootTools.log(e.getMessage());
                    return false;
                }
            } catch (java.lang.Exception e3) {
                e = e3;
                com.stericson.RootTools.C1040RootTools.log(e.getMessage());
                return false;
            }
        } catch (java.lang.Exception e4) {
            com.stericson.RootTools.C1040RootTools.log(e4.getMessage());
        }
    }

    public void offerBusyBox(android.app.Activity activity) {
        com.stericson.RootTools.C1040RootTools.log("Launching Market for BusyBox");
        activity.startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("market://details?id=stericson.busybox")));
    }

    public android.content.Intent offerBusyBox(android.app.Activity activity, int requestCode) {
        com.stericson.RootTools.C1040RootTools.log("Launching Market for BusyBox");
        android.content.Intent i = new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("market://details?id=stericson.busybox"));
        activity.startActivityForResult(i, requestCode);
        return i;
    }

    public void offerSuperUser(android.app.Activity activity) {
        com.stericson.RootTools.C1040RootTools.log("Launching Play Store for SuperSU");
        activity.startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("market://details?id=eu.chainfire.supersu")));
    }

    public android.content.Intent offerSuperUser(android.app.Activity activity, int requestCode) {
        com.stericson.RootTools.C1040RootTools.log("Launching Play Store for SuperSU");
        android.content.Intent i = new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("market://details?id=eu.chainfire.supersu"));
        activity.startActivityForResult(i, requestCode);
        return i;
    }

    private void commandWait(com.stericson.RootShell.execution.Shell shell, com.stericson.RootShell.execution.Command cmd) throws java.lang.Exception {
        while (!cmd.isFinished()) {
            com.stericson.RootTools.C1040RootTools.log("RootTools v4.2", shell.getCommandQueuePositionString(cmd));
            com.stericson.RootTools.C1040RootTools.log("RootTools v4.2", "Processed " + cmd.totalOutputProcessed + " of " + cmd.totalOutput + " output from command.");
            synchronized (cmd) {
                try {
                    if (!cmd.isFinished()) {
                        cmd.wait(2000);
                    }
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!(cmd.isExecuting() || cmd.isFinished())) {
                java.lang.Exception e2;
                if (!shell.isExecuting && !shell.isReading) {
                    android.util.Log.e("RootTools v4.2", "Waiting for a command to be executed in a shell that is not executing and not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                } else if (!shell.isExecuting || shell.isReading) {
                    android.util.Log.e("RootTools v4.2", "Waiting for a command to be executed in a shell that is not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                } else {
                    android.util.Log.e("RootTools v4.2", "Waiting for a command to be executed in a shell that is executing but not reading! \n\n Command: " + cmd.getCommand());
                    e2 = new java.lang.Exception();
                    e2.setStackTrace(java.lang.Thread.currentThread().getStackTrace());
                    e2.printStackTrace();
                }
            }
        }
    }
}
