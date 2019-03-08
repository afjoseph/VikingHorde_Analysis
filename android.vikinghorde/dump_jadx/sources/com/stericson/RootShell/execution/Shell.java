package com.stericson.RootShell.execution;

public class Shell {
    private static com.stericson.RootShell.execution.Shell customShell = null;
    public static com.stericson.RootShell.execution.Shell.ShellContext defaultContext = com.stericson.RootShell.execution.Shell.ShellContext.NORMAL;
    private static com.stericson.RootShell.execution.Shell rootShell = null;
    private static com.stericson.RootShell.execution.Shell shell = null;
    private static java.lang.String[] suVersion = new java.lang.String[]{null, null};
    private static final java.lang.String token = "F*D^W@#FGF";
    private boolean close = false;
    private final java.util.List<com.stericson.RootShell.execution.Command> commands = new java.util.ArrayList();
    private java.lang.String error = "";
    private final java.io.BufferedReader errorStream;
    private java.lang.Runnable input = new com.stericson.RootShell.execution.Shell.C10371();
    private final java.io.BufferedReader inputStream;
    private boolean isCleaning = false;
    public boolean isClosed = false;
    public boolean isExecuting = false;
    public boolean isReading = false;
    private java.lang.Boolean isSELinuxEnforcing = null;
    private int maxCommands = 5000;
    private java.lang.Runnable output = new com.stericson.RootShell.execution.Shell.C10393();
    private final java.io.OutputStreamWriter outputStream;
    private final java.lang.Process proc;
    private int read = 0;
    private com.stericson.RootShell.execution.Shell.ShellContext shellContext = com.stericson.RootShell.execution.Shell.ShellContext.NORMAL;
    private int shellTimeout = 25000;
    private com.stericson.RootShell.execution.Shell.ShellType shellType = null;
    private int totalExecuted = 0;
    private int totalRead = 0;
    private int write = 0;

    /* renamed from: com.stericson.RootShell.execution.Shell$1 */
    class C10371 implements java.lang.Runnable {
        C10371() {
        }

        public void run() {
            while (true) {
                try {
                    synchronized (com.stericson.RootShell.execution.Shell.this.commands) {
                        while (!com.stericson.RootShell.execution.Shell.this.close && com.stericson.RootShell.execution.Shell.this.write >= com.stericson.RootShell.execution.Shell.this.commands.size()) {
                            com.stericson.RootShell.execution.Shell.this.isExecuting = false;
                            com.stericson.RootShell.execution.Shell.this.commands.wait();
                        }
                    }
                    if (com.stericson.RootShell.execution.Shell.this.write >= com.stericson.RootShell.execution.Shell.this.maxCommands) {
                        while (com.stericson.RootShell.execution.Shell.this.read != com.stericson.RootShell.execution.Shell.this.write) {
                            com.stericson.RootShell.C1029RootShell.log("Waiting for read and write to catch up before cleanup.");
                        }
                        com.stericson.RootShell.execution.Shell.this.cleanCommands();
                    }
                    if (com.stericson.RootShell.execution.Shell.this.write < com.stericson.RootShell.execution.Shell.this.commands.size()) {
                        com.stericson.RootShell.execution.Shell.this.isExecuting = true;
                        com.stericson.RootShell.execution.Command cmd = (com.stericson.RootShell.execution.Command) com.stericson.RootShell.execution.Shell.this.commands.get(com.stericson.RootShell.execution.Shell.this.write);
                        cmd.startExecution();
                        com.stericson.RootShell.C1029RootShell.log("Executing: " + cmd.getCommand() + " with context: " + com.stericson.RootShell.execution.Shell.this.shellContext);
                        com.stericson.RootShell.execution.Shell.this.outputStream.write(cmd.getCommand());
                        com.stericson.RootShell.execution.Shell.this.outputStream.write("\necho F*D^W@#FGF " + com.stericson.RootShell.execution.Shell.this.totalExecuted + " $?\n");
                        com.stericson.RootShell.execution.Shell.this.outputStream.flush();
                        com.stericson.RootShell.execution.Shell.this.write = com.stericson.RootShell.execution.Shell.this.write + 1;
                        com.stericson.RootShell.execution.Shell.this.totalExecuted = com.stericson.RootShell.execution.Shell.this.totalExecuted + 1;
                    } else if (com.stericson.RootShell.execution.Shell.this.close) {
                        com.stericson.RootShell.execution.Shell.this.isExecuting = false;
                        com.stericson.RootShell.execution.Shell.this.outputStream.write("\nexit 0\n");
                        com.stericson.RootShell.execution.Shell.this.outputStream.flush();
                        com.stericson.RootShell.C1029RootShell.log("Closing shell");
                        com.stericson.RootShell.execution.Shell.this.write = 0;
                        com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Writer) com.stericson.RootShell.execution.Shell.this.outputStream);
                        return;
                    }
                } catch (java.io.IOException e) {
                    try {
                        com.stericson.RootShell.C1029RootShell.log(e.getMessage(), com.stericson.RootShell.C1029RootShell.LogLevel.ERROR, e);
                        return;
                    } finally {
                        com.stericson.RootShell.execution.Shell.this.write = 0;
                        com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Writer) com.stericson.RootShell.execution.Shell.this.outputStream);
                    }
                } catch (java.lang.InterruptedException e2) {
                    com.stericson.RootShell.C1029RootShell.log(e2.getMessage(), com.stericson.RootShell.C1029RootShell.LogLevel.ERROR, e2);
                    com.stericson.RootShell.execution.Shell.this.write = 0;
                    com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Writer) com.stericson.RootShell.execution.Shell.this.outputStream);
                    return;
                }
            }
        }
    }

    /* renamed from: com.stericson.RootShell.execution.Shell$2 */
    class C10382 extends java.lang.Thread {
        C10382() {
        }

        public void run() {
            synchronized (com.stericson.RootShell.execution.Shell.this.commands) {
                com.stericson.RootShell.execution.Shell.this.commands.notifyAll();
            }
        }
    }

    /* renamed from: com.stericson.RootShell.execution.Shell$3 */
    class C10393 implements java.lang.Runnable {
        C10393() {
        }

        public void run() {
            com.stericson.RootShell.execution.Command command = null;
            while (true) {
                try {
                    if (!com.stericson.RootShell.execution.Shell.this.close || com.stericson.RootShell.execution.Shell.this.inputStream.ready() || com.stericson.RootShell.execution.Shell.this.read < com.stericson.RootShell.execution.Shell.this.commands.size()) {
                        com.stericson.RootShell.execution.Shell.this.isReading = false;
                        java.lang.String outputLine = com.stericson.RootShell.execution.Shell.this.inputStream.readLine();
                        com.stericson.RootShell.execution.Shell.this.isReading = true;
                        if (outputLine != null) {
                            if (command == null) {
                                if (com.stericson.RootShell.execution.Shell.this.read < com.stericson.RootShell.execution.Shell.this.commands.size()) {
                                    command = (com.stericson.RootShell.execution.Command) com.stericson.RootShell.execution.Shell.this.commands.get(com.stericson.RootShell.execution.Shell.this.read);
                                } else if (com.stericson.RootShell.execution.Shell.this.close) {
                                    break;
                                }
                            }
                            int pos = outputLine.indexOf("F*D^W@#FGF");
                            if (pos == -1) {
                                command.output(command.f879id, outputLine);
                            } else if (pos > 0) {
                                command.output(command.f879id, outputLine.substring(0, pos));
                            }
                            if (pos >= 0) {
                                java.lang.String[] fields = outputLine.substring(pos).split(" ");
                                if (fields.length >= 2 && fields[1] != null) {
                                    int id = 0;
                                    try {
                                        id = java.lang.Integer.parseInt(fields[1]);
                                    } catch (java.lang.NumberFormatException e) {
                                    }
                                    int exitCode = -1;
                                    try {
                                        exitCode = java.lang.Integer.parseInt(fields[2]);
                                    } catch (java.lang.NumberFormatException e2) {
                                    }
                                    if (id == com.stericson.RootShell.execution.Shell.this.totalRead) {
                                        com.stericson.RootShell.execution.Shell.this.processErrors(command);
                                        int iterations = 0;
                                        while (command.totalOutput > command.totalOutputProcessed) {
                                            if (iterations == 0) {
                                                iterations++;
                                                com.stericson.RootShell.C1029RootShell.log("Waiting for output to be processed. " + command.totalOutputProcessed + " Of " + command.totalOutput);
                                            }
                                            try {
                                                synchronized (this) {
                                                    wait(2000);
                                                }
                                            } catch (java.lang.Exception e3) {
                                                com.stericson.RootShell.C1029RootShell.log(e3.getMessage());
                                            }
                                        }
                                        com.stericson.RootShell.C1029RootShell.log("Read all output");
                                        command.setExitCode(exitCode);
                                        command.commandFinished();
                                        command = null;
                                        com.stericson.RootShell.execution.Shell.this.read = com.stericson.RootShell.execution.Shell.this.read + 1;
                                        com.stericson.RootShell.execution.Shell.this.totalRead = com.stericson.RootShell.execution.Shell.this.totalRead + 1;
                                    }
                                }
                            }
                        }
                    }
                } catch (java.io.IOException e4) {
                    com.stericson.RootShell.C1029RootShell.log(e4.getMessage(), com.stericson.RootShell.C1029RootShell.LogLevel.ERROR, e4);
                } finally {
                    com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Writer) com.stericson.RootShell.execution.Shell.this.outputStream);
                    com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Reader) com.stericson.RootShell.execution.Shell.this.errorStream);
                    com.stericson.RootShell.execution.Shell.this.closeQuietly((java.io.Reader) com.stericson.RootShell.execution.Shell.this.inputStream);
                    com.stericson.RootShell.C1029RootShell.log("Shell destroyed");
                    com.stericson.RootShell.execution.Shell.this.isClosed = true;
                    com.stericson.RootShell.execution.Shell.this.isReading = false;
                }
            }
            try {
                com.stericson.RootShell.execution.Shell.this.proc.waitFor();
                com.stericson.RootShell.execution.Shell.this.proc.destroy();
            } catch (java.lang.Exception e5) {
            }
            while (com.stericson.RootShell.execution.Shell.this.read < com.stericson.RootShell.execution.Shell.this.commands.size()) {
                if (command == null) {
                    command = (com.stericson.RootShell.execution.Command) com.stericson.RootShell.execution.Shell.this.commands.get(com.stericson.RootShell.execution.Shell.this.read);
                }
                if (command.totalOutput < command.totalOutputProcessed) {
                    command.terminated("All output not processed!");
                    command.terminated("Did you forget the super.commandOutput call or are you waiting on the command object?");
                } else {
                    command.terminated("Unexpected Termination.");
                }
                command = null;
                com.stericson.RootShell.execution.Shell.this.read = com.stericson.RootShell.execution.Shell.this.read + 1;
            }
            com.stericson.RootShell.execution.Shell.this.read = 0;
        }
    }

    public enum ShellContext {
        NORMAL("normal"),
        SHELL("u:r:shell:s0"),
        SYSTEM_SERVER("u:r:system_server:s0"),
        SYSTEM_APP("u:r:system_app:s0"),
        PLATFORM_APP("u:r:platform_app:s0"),
        UNTRUSTED_APP("u:r:untrusted_app:s0"),
        RECOVERY("u:r:recovery:s0");
        
        private java.lang.String value;

        private ShellContext(java.lang.String value) {
            this.value = value;
        }

        public java.lang.String getValue() {
            return this.value;
        }
    }

    public enum ShellType {
        NORMAL,
        ROOT,
        CUSTOM
    }

    protected static class Worker extends java.lang.Thread {
        public int exit;
        public com.stericson.RootShell.execution.Shell shell;

        /* synthetic */ Worker(com.stericson.RootShell.execution.Shell x0, com.stericson.RootShell.execution.Shell.C10371 x1) {
            this(x0);
        }

        private Worker(com.stericson.RootShell.execution.Shell shell) {
            this.exit = -911;
            this.shell = shell;
        }

        public void run() {
            try {
                this.shell.outputStream.write("echo Started\n");
                this.shell.outputStream.flush();
                while (true) {
                    java.lang.String line = this.shell.inputStream.readLine();
                    if (line == null) {
                        throw new java.io.EOFException();
                    } else if (!"".equals(line)) {
                        if ("Started".equals(line)) {
                            this.exit = 1;
                            setShellOom();
                            return;
                        }
                        this.shell.error = "unkown error occured.";
                    }
                }
            } catch (java.io.IOException e) {
                this.exit = -42;
                if (e.getMessage() != null) {
                    this.shell.error = e.getMessage();
                } else {
                    this.shell.error = "RootAccess denied?.";
                }
            }
        }

        private void setShellOom() {
            try {
                java.lang.reflect.Field field;
                java.lang.Class<?> processClass = this.shell.proc.getClass();
                try {
                    field = processClass.getDeclaredField("pid");
                } catch (java.lang.NoSuchFieldException e) {
                    field = processClass.getDeclaredField("id");
                }
                field.setAccessible(true);
                this.shell.outputStream.write("(echo -17 > /proc/" + ((java.lang.Integer) field.get(this.shell.proc)).intValue() + "/oom_adj) &> /dev/null\n");
                this.shell.outputStream.write("(echo -17 > /proc/$$/oom_adj) &> /dev/null\n");
                this.shell.outputStream.flush();
            } catch (java.lang.Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:36:0x01c1=Splitter:B:36:0x01c1, B:12:0x00ee=Splitter:B:12:0x00ee} */
    private Shell(java.lang.String r12, com.stericson.RootShell.execution.Shell.ShellType r13, com.stericson.RootShell.execution.Shell.ShellContext r14, int r15) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        /*
        r11 = this;
        r10 = 0;
        r8 = 0;
        r11.<init>();
        r6 = 25000; // 0x61a8 float:3.5032E-41 double:1.23516E-319;
        r11.shellTimeout = r6;
        r11.shellType = r10;
        r6 = com.stericson.RootShell.execution.Shell.ShellContext.NORMAL;
        r11.shellContext = r6;
        r6 = "";
        r11.error = r6;
        r6 = new java.util.ArrayList;
        r6.<init>();
        r11.commands = r6;
        r11.close = r8;
        r11.isSELinuxEnforcing = r10;
        r11.isExecuting = r8;
        r11.isReading = r8;
        r11.isClosed = r8;
        r6 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r11.maxCommands = r6;
        r11.read = r8;
        r11.write = r8;
        r11.totalExecuted = r8;
        r11.totalRead = r8;
        r11.isCleaning = r8;
        r6 = new com.stericson.RootShell.execution.Shell$1;
        r6.<init>();
        r11.input = r6;
        r6 = new com.stericson.RootShell.execution.Shell$3;
        r6.<init>();
        r11.output = r6;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Starting shell: ";
        r6 = r6.append(r7);
        r6 = r6.append(r12);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Context: ";
        r6 = r6.append(r7);
        r7 = r14.getValue();
        r6 = r6.append(r7);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Timeout: ";
        r6 = r6.append(r7);
        r6 = r6.append(r15);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        r11.shellType = r13;
        if (r15 <= 0) goto L_0x0116;
    L_0x008a:
        r11.shellTimeout = r15;
        r11.shellContext = r14;
        r6 = r11.shellContext;
        r7 = com.stericson.RootShell.execution.Shell.ShellContext.NORMAL;
        if (r6 != r7) goto L_0x011a;
    L_0x0094:
        r6 = java.lang.Runtime.getRuntime();
        r6 = r6.exec(r12);
        r11.proc = r6;
    L_0x009e:
        r6 = new java.io.BufferedReader;
        r7 = new java.io.InputStreamReader;
        r8 = r11.proc;
        r8 = r8.getInputStream();
        r9 = "UTF-8";
        r7.<init>(r8, r9);
        r6.<init>(r7);
        r11.inputStream = r6;
        r6 = new java.io.BufferedReader;
        r7 = new java.io.InputStreamReader;
        r8 = r11.proc;
        r8 = r8.getErrorStream();
        r9 = "UTF-8";
        r7.<init>(r8, r9);
        r6.<init>(r7);
        r11.errorStream = r6;
        r6 = new java.io.OutputStreamWriter;
        r7 = r11.proc;
        r7 = r7.getOutputStream();
        r8 = "UTF-8";
        r6.<init>(r7, r8);
        r11.outputStream = r6;
        r5 = new com.stericson.RootShell.execution.Shell$Worker;
        r5.<init>(r11, r10);
        r5.start();
        r6 = r11.shellTimeout;	 Catch:{ InterruptedException -> 0x0105 }
        r6 = (long) r6;	 Catch:{ InterruptedException -> 0x0105 }
        r5.join(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r5.exit;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = -911; // 0xfffffffffffffc71 float:NaN double:NaN;
        if (r6 != r7) goto L_0x01b6;
    L_0x00e9:
        r6 = r11.proc;	 Catch:{ Exception -> 0x01fb }
        r6.destroy();	 Catch:{ Exception -> 0x01fb }
    L_0x00ee:
        r6 = r11.inputStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.errorStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.outputStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = new java.util.concurrent.TimeoutException;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = r11.error;	 Catch:{ InterruptedException -> 0x0105 }
        r6.<init>(r7);	 Catch:{ InterruptedException -> 0x0105 }
        throw r6;	 Catch:{ InterruptedException -> 0x0105 }
    L_0x0105:
        r1 = move-exception;
        r5.interrupt();
        r6 = java.lang.Thread.currentThread();
        r6.interrupt();
        r6 = new java.util.concurrent.TimeoutException;
        r6.<init>();
        throw r6;
    L_0x0116:
        r15 = r11.shellTimeout;
        goto L_0x008a;
    L_0x011a:
        r0 = r11.getSuVersion(r8);
        r6 = 1;
        r2 = r11.getSuVersion(r6);
        r6 = r11.isSELinuxEnforcing();
        if (r6 == 0) goto L_0x016a;
    L_0x0129:
        if (r0 == 0) goto L_0x016a;
    L_0x012b:
        if (r2 == 0) goto L_0x016a;
    L_0x012d:
        r6 = "SUPERSU";
        r6 = r0.endsWith(r6);
        if (r6 == 0) goto L_0x016a;
    L_0x0135:
        r6 = java.lang.Integer.valueOf(r2);
        r6 = r6.intValue();
        r7 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        if (r6 < r7) goto L_0x016a;
    L_0x0141:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r6 = r6.append(r12);
        r7 = " --context ";
        r6 = r6.append(r7);
        r7 = r11.shellContext;
        r7 = r7.getValue();
        r6 = r6.append(r7);
        r12 = r6.toString();
    L_0x015e:
        r6 = java.lang.Runtime.getRuntime();
        r6 = r6.exec(r12);
        r11.proc = r6;
        goto L_0x009e;
    L_0x016a:
        r6 = "Su binary --context switch not supported!";
        com.stericson.RootShell.C1029RootShell.log(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Su binary display version: ";
        r6 = r6.append(r7);
        r6 = r6.append(r0);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Su binary internal version: ";
        r6 = r6.append(r7);
        r6 = r6.append(r2);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "SELinuxEnforcing: ";
        r6 = r6.append(r7);
        r7 = r11.isSELinuxEnforcing();
        r6 = r6.append(r7);
        r6 = r6.toString();
        com.stericson.RootShell.C1029RootShell.log(r6);
        goto L_0x015e;
    L_0x01b6:
        r6 = r5.exit;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = -42;
        if (r6 != r7) goto L_0x01d8;
    L_0x01bc:
        r6 = r11.proc;	 Catch:{ Exception -> 0x01f9 }
        r6.destroy();	 Catch:{ Exception -> 0x01f9 }
    L_0x01c1:
        r6 = r11.inputStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.errorStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.outputStream;	 Catch:{ InterruptedException -> 0x0105 }
        r11.closeQuietly(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = new com.stericson.RootShell.exceptions.RootDeniedException;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = "Root Access Denied";
        r6.<init>(r7);	 Catch:{ InterruptedException -> 0x0105 }
        throw r6;	 Catch:{ InterruptedException -> 0x0105 }
    L_0x01d8:
        r3 = new java.lang.Thread;	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.input;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = "Shell Input";
        r3.<init>(r6, r7);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = 5;
        r3.setPriority(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r3.start();	 Catch:{ InterruptedException -> 0x0105 }
        r4 = new java.lang.Thread;	 Catch:{ InterruptedException -> 0x0105 }
        r6 = r11.output;	 Catch:{ InterruptedException -> 0x0105 }
        r7 = "Shell Output";
        r4.<init>(r6, r7);	 Catch:{ InterruptedException -> 0x0105 }
        r6 = 5;
        r4.setPriority(r6);	 Catch:{ InterruptedException -> 0x0105 }
        r4.start();	 Catch:{ InterruptedException -> 0x0105 }
        return;
    L_0x01f9:
        r6 = move-exception;
        goto L_0x01c1;
    L_0x01fb:
        r6 = move-exception;
        goto L_0x00ee;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stericson.RootShell.execution.Shell.<init>(java.lang.String, com.stericson.RootShell.execution.Shell$ShellType, com.stericson.RootShell.execution.Shell$ShellContext, int):void");
    }

    public com.stericson.RootShell.execution.Command add(com.stericson.RootShell.execution.Command command) throws java.io.IOException {
        if (this.close) {
            throw new java.lang.IllegalStateException("Unable to add commands to a closed shell");
        }
        do {
        } while (this.isCleaning);
        command.resetCommand();
        this.commands.add(command);
        notifyThreads();
        return command;
    }

    public final void useCWD(android.content.Context context) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        add(new com.stericson.RootShell.execution.Command(-1, false, "cd " + context.getApplicationInfo().dataDir));
    }

    private void cleanCommands() {
        this.isCleaning = true;
        int toClean = java.lang.Math.abs(this.maxCommands - (this.maxCommands / 4));
        com.stericson.RootShell.C1029RootShell.log("Cleaning up: " + toClean);
        for (int i = 0; i < toClean; i++) {
            this.commands.remove(0);
        }
        this.read = this.commands.size() - 1;
        this.write = this.commands.size() - 1;
        this.isCleaning = false;
    }

    private void closeQuietly(java.io.Reader input) {
        if (input != null) {
            try {
                input.close();
            } catch (java.lang.Exception e) {
            }
        }
    }

    private void closeQuietly(java.io.Writer output) {
        if (output != null) {
            try {
                output.close();
            } catch (java.lang.Exception e) {
            }
        }
    }

    public void close() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.log("Request to close shell!");
        int count = 0;
        while (this.isExecuting) {
            com.stericson.RootShell.C1029RootShell.log("Waiting on shell to finish executing before closing...");
            count++;
            if (count > 10000) {
                break;
            }
        }
        synchronized (this.commands) {
            this.close = true;
            notifyThreads();
        }
        com.stericson.RootShell.C1029RootShell.log("Shell Closed!");
        if (this == rootShell) {
            rootShell = null;
        } else if (this == shell) {
            shell = null;
        } else if (this == customShell) {
            customShell = null;
        }
    }

    public static void closeCustomShell() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.log("Request to close custom shell!");
        if (customShell != null) {
            customShell.close();
        }
    }

    public static void closeRootShell() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.log("Request to close root shell!");
        if (rootShell != null) {
            rootShell.close();
        }
    }

    public static void closeShell() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.log("Request to close normal shell!");
        if (shell != null) {
            shell.close();
        }
    }

    public static void closeAll() throws java.io.IOException {
        com.stericson.RootShell.C1029RootShell.log("Request to close all shells!");
        closeShell();
        closeRootShell();
        closeCustomShell();
    }

    public int getCommandQueuePosition(com.stericson.RootShell.execution.Command cmd) {
        return this.commands.indexOf(cmd);
    }

    public java.lang.String getCommandQueuePositionString(com.stericson.RootShell.execution.Command cmd) {
        return "Command is in position " + getCommandQueuePosition(cmd) + " currently executing command at position " + this.write + " and the number of commands is " + this.commands.size();
    }

    public static com.stericson.RootShell.execution.Shell getOpenShell() {
        if (customShell != null) {
            return customShell;
        }
        if (rootShell != null) {
            return rootShell;
        }
        return shell;
    }

    private synchronized java.lang.String getSuVersion(boolean internal) {
        java.lang.String str = null;
        synchronized (this) {
            int idx = internal ? 0 : 1;
            if (suVersion[idx] == null) {
                java.lang.String version = null;
                try {
                    java.lang.String line;
                    java.lang.Process process = java.lang.Runtime.getRuntime().exec(internal ? "su -V" : "su -v", null);
                    process.waitFor();
                    java.util.List<java.lang.String> stdout = new java.util.ArrayList();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
                    while (true) {
                        try {
                            line = reader.readLine();
                            if (line != null) {
                                stdout.add(line);
                            }
                        } catch (java.io.IOException e) {
                        }
                        try {
                            break;
                        } catch (java.io.IOException e2) {
                        }
                    }
                    reader.close();
                    process.destroy();
                    java.util.List<java.lang.String> ret = stdout;
                    if (ret != null) {
                        for (java.lang.String line2 : ret) {
                            if (internal) {
                                try {
                                    if (java.lang.Integer.parseInt(line2) > 0) {
                                        version = line2;
                                        break;
                                    }
                                } catch (java.lang.NumberFormatException e3) {
                                }
                            } else if (line2.contains(".")) {
                                version = line2;
                                break;
                            }
                        }
                    }
                    suVersion[idx] = version;
                } catch (java.io.IOException e4) {
                    e4.printStackTrace();
                } catch (java.lang.InterruptedException e5) {
                    e5.printStackTrace();
                }
            }
            str = suVersion[idx];
        }
        return str;
    }

    public static boolean isShellOpen() {
        return shell == null;
    }

    public static boolean isCustomShellOpen() {
        return customShell == null;
    }

    public static boolean isRootShellOpen() {
        return rootShell == null;
    }

    public static boolean isAnyShellOpen() {
        return (shell == null && rootShell == null && customShell == null) ? false : true;
    }

    public synchronized boolean isSELinuxEnforcing() {
        if (this.isSELinuxEnforcing == null) {
            java.lang.Boolean enforcing = null;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                boolean z;
                if (new java.io.File("/sys/fs/selinux/enforce").exists()) {
                    java.io.InputStream is;
                    try {
                        is = new java.io.FileInputStream("/sys/fs/selinux/enforce");
                        if (is.read() == 49) {
                            z = true;
                        } else {
                            z = false;
                        }
                        enforcing = java.lang.Boolean.valueOf(z);
                        is.close();
                    } catch (java.lang.Exception e) {
                    } catch (Throwable th) {
                        is.close();
                    }
                }
                if (enforcing == null) {
                    if (android.os.Build.VERSION.SDK_INT >= 19) {
                        z = true;
                    } else {
                        z = false;
                    }
                    enforcing = java.lang.Boolean.valueOf(z);
                }
            }
            if (enforcing == null) {
                enforcing = java.lang.Boolean.valueOf(false);
            }
            this.isSELinuxEnforcing = enforcing;
        }
        return this.isSELinuxEnforcing.booleanValue();
    }

    protected void notifyThreads() {
        new com.stericson.RootShell.execution.Shell.C10382().start();
    }

    public void processErrors(com.stericson.RootShell.execution.Command command) {
        while (this.errorStream.ready() && command != null) {
            try {
                java.lang.String line = this.errorStream.readLine();
                if (line != null) {
                    command.output(command.f879id, line);
                } else {
                    return;
                }
            } catch (java.lang.Exception e) {
                com.stericson.RootShell.C1029RootShell.log(e.getMessage(), com.stericson.RootShell.C1029RootShell.LogLevel.ERROR, e);
                return;
            }
        }
    }

    public static void runRootCommand(com.stericson.RootShell.execution.Command command) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        startRootShell().add(command);
    }

    public static void runCommand(com.stericson.RootShell.execution.Command command) throws java.io.IOException, java.util.concurrent.TimeoutException {
        startShell().add(command);
    }

    public static com.stericson.RootShell.execution.Shell startRootShell() throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return startRootShell(0, 3);
    }

    public static com.stericson.RootShell.execution.Shell startRootShell(int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return startRootShell(timeout, 3);
    }

    public static com.stericson.RootShell.execution.Shell startRootShell(int timeout, int retry) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return startRootShell(timeout, defaultContext, retry);
    }

    public static com.stericson.RootShell.execution.Shell startRootShell(int timeout, com.stericson.RootShell.execution.Shell.ShellContext shellContext, int retry) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        int retries;
        int retries2 = 0;
        if (rootShell == null) {
            com.stericson.RootShell.C1029RootShell.log("Starting Root Shell!");
            java.lang.String cmd = "su";
            while (rootShell == null) {
                try {
                    com.stericson.RootShell.C1029RootShell.log("Trying to open Root Shell, attempt #" + retries2);
                    rootShell = new com.stericson.RootShell.execution.Shell(cmd, com.stericson.RootShell.execution.Shell.ShellType.ROOT, shellContext, timeout);
                } catch (java.io.IOException e) {
                    retries = retries2 + 1;
                    if (retries2 >= retry) {
                        com.stericson.RootShell.C1029RootShell.log("IOException, could not start shell");
                        throw e;
                    }
                    retries2 = retries;
                } catch (com.stericson.RootShell.exceptions.RootDeniedException e2) {
                    retries = retries2 + 1;
                    if (retries2 >= retry) {
                        com.stericson.RootShell.C1029RootShell.log("RootDeniedException, could not start shell");
                        throw e2;
                    }
                    retries2 = retries;
                } catch (java.util.concurrent.TimeoutException e3) {
                    retries = retries2 + 1;
                    if (retries2 >= retry) {
                        com.stericson.RootShell.C1029RootShell.log("TimeoutException, could not start shell");
                        throw e3;
                    }
                    retries2 = retries;
                }
            }
        } else if (rootShell.shellContext != shellContext) {
            try {
                com.stericson.RootShell.C1029RootShell.log("Context is different than open shell, switching context... " + rootShell.shellContext + " VS " + shellContext);
                rootShell.switchRootShellContext(shellContext);
            } catch (java.io.IOException e4) {
                retries = 0 + 1;
                if (0 >= retry) {
                    com.stericson.RootShell.C1029RootShell.log("IOException, could not switch context!");
                    throw e4;
                }
                retries2 = retries;
            } catch (com.stericson.RootShell.exceptions.RootDeniedException e22) {
                retries = 0 + 1;
                if (0 >= retry) {
                    com.stericson.RootShell.C1029RootShell.log("RootDeniedException, could not switch context!");
                    throw e22;
                }
                retries2 = retries;
            } catch (java.util.concurrent.TimeoutException e32) {
                retries = 0 + 1;
                if (0 >= retry) {
                    com.stericson.RootShell.C1029RootShell.log("TimeoutException, could not switch context!");
                    throw e32;
                }
                retries2 = retries;
            }
        } else {
            com.stericson.RootShell.C1029RootShell.log("Using Existing Root Shell!");
        }
        return rootShell;
    }

    public static com.stericson.RootShell.execution.Shell startCustomShell(java.lang.String shellPath) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        return startCustomShell(shellPath, 0);
    }

    public static com.stericson.RootShell.execution.Shell startCustomShell(java.lang.String shellPath, int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        if (customShell == null) {
            com.stericson.RootShell.C1029RootShell.log("Starting Custom Shell!");
            customShell = new com.stericson.RootShell.execution.Shell(shellPath, com.stericson.RootShell.execution.Shell.ShellType.CUSTOM, com.stericson.RootShell.execution.Shell.ShellContext.NORMAL, timeout);
        } else {
            com.stericson.RootShell.C1029RootShell.log("Using Existing Custom Shell!");
        }
        return customShell;
    }

    public static com.stericson.RootShell.execution.Shell startShell() throws java.io.IOException, java.util.concurrent.TimeoutException {
        return startShell(0);
    }

    public static com.stericson.RootShell.execution.Shell startShell(int timeout) throws java.io.IOException, java.util.concurrent.TimeoutException {
        try {
            if (shell == null) {
                com.stericson.RootShell.C1029RootShell.log("Starting Shell!");
                shell = new com.stericson.RootShell.execution.Shell("/system/bin/sh", com.stericson.RootShell.execution.Shell.ShellType.NORMAL, com.stericson.RootShell.execution.Shell.ShellContext.NORMAL, timeout);
            } else {
                com.stericson.RootShell.C1029RootShell.log("Using Existing Shell!");
            }
            return shell;
        } catch (com.stericson.RootShell.exceptions.RootDeniedException e) {
            throw new java.io.IOException();
        }
    }

    public com.stericson.RootShell.execution.Shell switchRootShellContext(com.stericson.RootShell.execution.Shell.ShellContext shellContext) throws java.io.IOException, java.util.concurrent.TimeoutException, com.stericson.RootShell.exceptions.RootDeniedException {
        if (this.shellType == com.stericson.RootShell.execution.Shell.ShellType.ROOT) {
            try {
                closeRootShell();
            } catch (java.lang.Exception e) {
                com.stericson.RootShell.C1029RootShell.log("Problem closing shell while trying to switch context...");
            }
            return startRootShell(this.shellTimeout, shellContext, 3);
        }
        com.stericson.RootShell.C1029RootShell.log("Can only switch context on a root shell!");
        return this;
    }
}
