package com.stericson.RootTools.internal;

public class Remounter {
    public boolean remount(java.lang.String file, java.lang.String mountType) {
        if (file.endsWith("/") && !file.equals("/")) {
            file = file.substring(0, file.lastIndexOf("/"));
        }
        boolean foundMount = false;
        while (!foundMount) {
            try {
                java.util.Iterator i$ = com.stericson.RootTools.C1040RootTools.getMounts().iterator();
                while (i$.hasNext()) {
                    com.stericson.RootTools.containers.Mount mount = (com.stericson.RootTools.containers.Mount) i$.next();
                    com.stericson.RootTools.C1040RootTools.log(mount.getMountPoint().toString());
                    if (file.equals(mount.getMountPoint().toString())) {
                        foundMount = true;
                        break;
                    }
                }
                if (!foundMount) {
                    try {
                        file = new java.io.File(file).getParent();
                    } catch (java.lang.Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            } catch (java.lang.Exception e2) {
                if (com.stericson.RootTools.C1040RootTools.debugMode) {
                    e2.printStackTrace();
                }
                return false;
            }
        }
        com.stericson.RootTools.containers.Mount mountPoint = findMountPointRecursive(file);
        if (mountPoint != null) {
            com.stericson.RootTools.C1040RootTools.log("RootTools v4.2", "Remounting " + mountPoint.getMountPoint().getAbsolutePath() + " as " + mountType.toLowerCase());
            if (!mountPoint.getFlags().contains(mountType.toLowerCase())) {
                try {
                    com.stericson.RootShell.execution.Command command = new com.stericson.RootShell.execution.Command(0, true, "busybox mount -o remount," + mountType.toLowerCase() + " " + mountPoint.getDevice().getAbsolutePath() + " " + mountPoint.getMountPoint().getAbsolutePath(), "toolbox mount -o remount," + mountType.toLowerCase() + " " + mountPoint.getDevice().getAbsolutePath() + " " + mountPoint.getMountPoint().getAbsolutePath(), "mount -o remount," + mountType.toLowerCase() + " " + mountPoint.getDevice().getAbsolutePath() + " " + mountPoint.getMountPoint().getAbsolutePath(), "/system/bin/toolbox mount -o remount," + mountType.toLowerCase() + " " + mountPoint.getDevice().getAbsolutePath() + " " + mountPoint.getMountPoint().getAbsolutePath());
                    com.stericson.RootShell.execution.Shell.startRootShell().add(command);
                    commandWait(command);
                } catch (java.lang.Exception e3) {
                }
                mountPoint = findMountPointRecursive(file);
            }
            if (mountPoint != null) {
                com.stericson.RootTools.C1040RootTools.log("RootTools v4.2", mountPoint.getFlags() + " AND " + mountType.toLowerCase());
                if (mountPoint.getFlags().contains(mountType.toLowerCase())) {
                    com.stericson.RootTools.C1040RootTools.log(mountPoint.getFlags().toString());
                    return true;
                }
                com.stericson.RootTools.C1040RootTools.log(mountPoint.getFlags().toString());
                return false;
            }
            com.stericson.RootTools.C1040RootTools.log("mount is null, file was: " + file + " mountType was: " + mountType);
        } else {
            com.stericson.RootTools.C1040RootTools.log("mount is null, file was: " + file + " mountType was: " + mountType);
        }
        return false;
    }

    private com.stericson.RootTools.containers.Mount findMountPointRecursive(java.lang.String file) {
        try {
            java.util.ArrayList<com.stericson.RootTools.containers.Mount> mounts = com.stericson.RootTools.C1040RootTools.getMounts();
            java.io.File path = new java.io.File(file);
            while (path != null) {
                java.util.Iterator i$ = mounts.iterator();
                while (i$.hasNext()) {
                    com.stericson.RootTools.containers.Mount mount = (com.stericson.RootTools.containers.Mount) i$.next();
                    if (mount.getMountPoint().equals(path)) {
                        return mount;
                    }
                }
            }
            return null;
        } catch (java.io.IOException e) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                e.printStackTrace();
            }
            return null;
        } catch (java.lang.Exception e2) {
            if (com.stericson.RootTools.C1040RootTools.debugMode) {
                e2.printStackTrace();
            }
            return null;
        }
    }

    private void commandWait(com.stericson.RootShell.execution.Command cmd) {
        synchronized (cmd) {
            try {
                if (!cmd.isFinished()) {
                    cmd.wait(2000);
                }
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
