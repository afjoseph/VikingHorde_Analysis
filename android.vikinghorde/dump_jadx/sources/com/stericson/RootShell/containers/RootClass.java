package com.stericson.RootShell.containers;

public class RootClass {
    static java.lang.String PATH_TO_DX = "/Users/Chris/Projects/android-sdk-macosx/build-tools/18.0.1/dx";

    public static class AnnotationsFinder {
        private final java.lang.String AVOIDDIRPATH = ("stericson" + java.io.File.separator + "RootShell" + java.io.File.separator);
        private java.util.List<java.io.File> classFiles;

        /* renamed from: com.stericson.RootShell.containers.RootClass$AnnotationsFinder$2 */
        class C10352 implements java.io.FileFilter {
            C10352() {
            }

            public boolean accept(java.io.File pathname) {
                return pathname.isDirectory();
            }
        }

        public AnnotationsFinder() throws java.io.IOException {
            java.lang.System.out.println("Discovering root class annotations...");
            this.classFiles = new java.util.ArrayList();
            lookup(new java.io.File("src"), this.classFiles);
            java.lang.System.out.println("Done discovering annotations. Building jar file.");
            java.io.File builtPath = getBuiltPath();
            if (builtPath != null) {
                java.lang.String[] cmd;
                java.lang.String rc1 = "com" + java.io.File.separator + "stericson" + java.io.File.separator + "RootShell" + java.io.File.separator + "containers" + java.io.File.separator + "RootClass.class";
                java.lang.String rc2 = "com" + java.io.File.separator + "stericson" + java.io.File.separator + "RootShell" + java.io.File.separator + "containers" + java.io.File.separator + "RootClass$RootArgs.class";
                java.lang.String rc3 = "com" + java.io.File.separator + "stericson" + java.io.File.separator + "RootShell" + java.io.File.separator + "containers" + java.io.File.separator + "RootClass$AnnotationsFinder.class";
                java.lang.String rc4 = "com" + java.io.File.separator + "stericson" + java.io.File.separator + "RootShell" + java.io.File.separator + "containers" + java.io.File.separator + "RootClass$AnnotationsFinder$1.class";
                java.lang.String rc5 = "com" + java.io.File.separator + "stericson" + java.io.File.separator + "RootShell" + java.io.File.separator + "containers" + java.io.File.separator + "RootClass$AnnotationsFinder$2.class";
                boolean onWindows = -1 != java.lang.System.getProperty("os.name").toLowerCase().indexOf("win");
                if (onWindows) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(" " + rc1 + " " + rc2 + " " + rc3 + " " + rc4 + " " + rc5);
                    for (java.io.File file : this.classFiles) {
                        sb.append(" " + file.getPath());
                    }
                    cmd = new java.lang.String[]{"cmd", "/C", "jar cvf anbuild.jar" + sb.toString()};
                } else {
                    java.util.ArrayList<java.lang.String> al = new java.util.ArrayList();
                    al.add("jar");
                    al.add("cf");
                    al.add("anbuild.jar");
                    al.add(rc1);
                    al.add(rc2);
                    al.add(rc3);
                    al.add(rc4);
                    al.add(rc5);
                    for (java.io.File file2 : this.classFiles) {
                        al.add(file2.getPath());
                    }
                    cmd = (java.lang.String[]) al.toArray(new java.lang.String[al.size()]);
                }
                java.lang.ProcessBuilder jarBuilder = new java.lang.ProcessBuilder(cmd);
                jarBuilder.directory(builtPath);
                try {
                    jarBuilder.start().waitFor();
                } catch (java.io.IOException | java.lang.InterruptedException e) {
                }
                java.io.File rawFolder = new java.io.File("res/raw");
                if (!rawFolder.exists()) {
                    rawFolder.mkdirs();
                }
                java.lang.System.out.println("Done building jar file. Creating dex file.");
                try {
                    new java.lang.ProcessBuilder(onWindows ? new java.lang.String[]{"cmd", "/C", "dx --dex --output=res/raw/anbuild.dex " + builtPath + java.io.File.separator + "anbuild.jar"} : new java.lang.String[]{getPathToDx(), "--dex", "--output=res/raw/anbuild.dex", builtPath + java.io.File.separator + "anbuild.jar"}).start().waitFor();
                } catch (java.io.IOException | java.lang.InterruptedException e2) {
                }
            }
            java.lang.System.out.println("All done. ::: anbuild.dex should now be in your project's res/raw/ folder :::");
        }

        protected void lookup(java.io.File path, java.util.List<java.io.File> fileList) {
            java.lang.String desourcedPath = path.toString().replace("src/", "");
            for (java.io.File file : path.listFiles()) {
                if (file.isDirectory()) {
                    if (-1 == file.getAbsolutePath().indexOf(this.AVOIDDIRPATH)) {
                        lookup(file, fileList);
                    }
                } else if (file.getName().endsWith(".java") && hasClassAnnotation(file)) {
                    final java.lang.String fileNamePrefix = file.getName().replace(".java", "");
                    for (java.io.File matchingFile : new java.io.File(getBuiltPath().toString() + java.io.File.separator + desourcedPath).listFiles(new java.io.FilenameFilter() {
                        public boolean accept(java.io.File dir, java.lang.String filename) {
                            return filename.startsWith(fileNamePrefix);
                        }
                    })) {
                        fileList.add(new java.io.File(desourcedPath + java.io.File.separator + matchingFile.getName()));
                    }
                }
            }
        }

        protected boolean hasClassAnnotation(java.io.File file) {
            com.stericson.RootShell.containers.RootClass.READ_STATE readState = com.stericson.RootShell.containers.RootClass.READ_STATE.STARTING;
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(" class ([A-Za-z0-9_]+)");
            try {
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
                while (true) {
                    java.lang.String line = reader.readLine();
                    if (line != null) {
                        switch (readState) {
                            case STARTING:
                                if (-1 >= line.indexOf("@RootClass.Candidate")) {
                                    break;
                                }
                                readState = com.stericson.RootShell.containers.RootClass.READ_STATE.FOUND_ANNOTATION;
                                break;
                            case FOUND_ANNOTATION:
                                java.util.regex.Matcher m = p.matcher(line);
                                if (!m.find()) {
                                    java.lang.System.err.println("Error: unmatched annotation in " + file.getAbsolutePath());
                                    readState = com.stericson.RootShell.containers.RootClass.READ_STATE.STARTING;
                                    break;
                                }
                                java.lang.System.out.println(" Found annotated class: " + m.group(0));
                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
            } catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (java.io.IOException e2) {
                e2.printStackTrace();
                return false;
            }
        }

        protected java.lang.String getPathToDx() throws java.io.IOException {
            java.lang.String androidHome = java.lang.System.getenv("ANDROID_HOME");
            if (androidHome == null) {
                throw new java.io.IOException("Error: you need to set $ANDROID_HOME globally");
            }
            java.lang.String dxPath = null;
            int recentSdkVersion = 0;
            for (java.io.File file : new java.io.File(androidHome + java.io.File.separator + "build-tools").listFiles()) {
                java.lang.String fileName;
                if (file.getName().contains("-")) {
                    java.lang.String[] splitFileName = file.getName().split("-");
                    if (splitFileName[1].contains("W")) {
                        fileName = java.lang.String.valueOf(splitFileName[1].toCharArray()[0]);
                    } else {
                        fileName = splitFileName[1];
                    }
                } else {
                    fileName = file.getName();
                }
                java.lang.String[] sdkVersionBits = fileName.split("[.]");
                int sdkVersion = java.lang.Integer.parseInt(sdkVersionBits[0]) * 10000;
                if (sdkVersionBits.length > 1) {
                    sdkVersion += java.lang.Integer.parseInt(sdkVersionBits[1]) * 100;
                    if (sdkVersionBits.length > 2) {
                        sdkVersion += java.lang.Integer.parseInt(sdkVersionBits[2]);
                    }
                }
                if (sdkVersion > recentSdkVersion) {
                    java.lang.String tentativePath = file.getAbsolutePath() + java.io.File.separator + "dx";
                    if (new java.io.File(tentativePath).exists()) {
                        recentSdkVersion = sdkVersion;
                        dxPath = tentativePath;
                    }
                }
            }
            if (dxPath != null) {
                return dxPath;
            }
            throw new java.io.IOException("Error: unable to find dx binary in $ANDROID_HOME");
        }

        protected java.io.File getBuiltPath() {
            java.io.File foundPath = null;
            java.io.File ideaPath = new java.io.File("out" + java.io.File.separator + "production");
            if (ideaPath.isDirectory()) {
                java.io.File[] children = ideaPath.listFiles(new com.stericson.RootShell.containers.RootClass.AnnotationsFinder.C10352());
                if (children.length > 0) {
                    foundPath = new java.io.File(ideaPath.getAbsolutePath() + java.io.File.separator + children[0].getName());
                }
            }
            if (foundPath != null) {
                return foundPath;
            }
            java.io.File eclipsePath = new java.io.File("bin" + java.io.File.separator + "classes");
            if (eclipsePath.isDirectory()) {
                return eclipsePath;
            }
            return foundPath;
        }
    }

    public @interface Candidate {
    }

    enum READ_STATE {
        STARTING,
        FOUND_ANNOTATION
    }

    public class RootArgs {
        public java.lang.String[] args;
    }

    public RootClass(java.lang.String[] args) throws java.lang.ClassNotFoundException, java.lang.NoSuchMethodException, java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException {
        java.lang.String className = args[0];
        com.stericson.RootShell.containers.RootClass.RootArgs actualArgs = new com.stericson.RootShell.containers.RootClass.RootArgs();
        actualArgs.args = new java.lang.String[(args.length - 1)];
        java.lang.System.arraycopy(args, 1, actualArgs.args, 0, args.length - 1);
        java.lang.Class.forName(className).getConstructor(new java.lang.Class[]{com.stericson.RootShell.containers.RootClass.RootArgs.class}).newInstance(new java.lang.Object[]{actualArgs});
    }

    static void displayError(java.lang.Exception e) {
        java.lang.System.out.println("##ERR##" + e.getMessage() + "##");
        e.printStackTrace();
    }

    public static void main(java.lang.String[] args) {
        try {
            if (args.length == 0) {
                com.stericson.RootShell.containers.RootClass.AnnotationsFinder annotationsFinder = new com.stericson.RootShell.containers.RootClass.AnnotationsFinder();
            } else {
                com.stericson.RootShell.containers.RootClass rootClass = new com.stericson.RootShell.containers.RootClass(args);
            }
        } catch (java.lang.Exception e) {
            displayError(e);
        }
    }
}
