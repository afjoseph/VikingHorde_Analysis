package com.stericson.RootShell.execution;

public class Command {
    java.lang.String[] command;
    protected android.content.Context context;
    boolean executing;
    com.stericson.RootShell.execution.Command.ExecutionMonitor executionMonitor;
    int exitCode;
    boolean finished;
    boolean handlerEnabled;
    /* renamed from: id */
    int f879id;
    protected boolean javaCommand;
    android.os.Handler mHandler;
    boolean terminated;
    int timeout;
    public int totalOutput;
    public int totalOutputProcessed;

    private class CommandHandler extends android.os.Handler {
        public static final java.lang.String ACTION = "action";
        public static final int COMMAND_COMPLETED = 2;
        public static final int COMMAND_OUTPUT = 1;
        public static final int COMMAND_TERMINATED = 3;
        public static final java.lang.String TEXT = "text";

        private CommandHandler() {
        }

        public final void handleMessage(android.os.Message msg) {
            int action = msg.getData().getInt("action");
            java.lang.String text = msg.getData().getString("text");
            switch (action) {
                case 1:
                    com.stericson.RootShell.execution.Command.this.commandOutput(com.stericson.RootShell.execution.Command.this.f879id, text);
                    return;
                case 2:
                    com.stericson.RootShell.execution.Command.this.commandCompleted(com.stericson.RootShell.execution.Command.this.f879id, com.stericson.RootShell.execution.Command.this.exitCode);
                    return;
                case 3:
                    com.stericson.RootShell.execution.Command.this.commandTerminated(com.stericson.RootShell.execution.Command.this.f879id, text);
                    return;
                default:
                    return;
            }
        }
    }

    private class ExecutionMonitor extends java.lang.Thread {
        private ExecutionMonitor() {
        }

        public void run() {
            if (com.stericson.RootShell.execution.Command.this.timeout > 0) {
                while (!com.stericson.RootShell.execution.Command.this.finished) {
                    synchronized (com.stericson.RootShell.execution.Command.this) {
                        try {
                            com.stericson.RootShell.execution.Command.this.wait((long) com.stericson.RootShell.execution.Command.this.timeout);
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                    if (!com.stericson.RootShell.execution.Command.this.finished) {
                        com.stericson.RootShell.C1029RootShell.log("Timeout Exception has occurred.");
                        com.stericson.RootShell.execution.Command.this.terminate("Timeout Exception");
                    }
                }
            }
        }
    }

    public Command(int id, java.lang.String... command) {
        this.javaCommand = false;
        this.context = null;
        this.totalOutput = 0;
        this.totalOutputProcessed = 0;
        this.executionMonitor = null;
        this.mHandler = null;
        this.executing = false;
        this.command = new java.lang.String[0];
        this.finished = false;
        this.terminated = false;
        this.handlerEnabled = true;
        this.exitCode = -1;
        this.f879id = 0;
        this.timeout = com.stericson.RootShell.C1029RootShell.defaultCommandTimeout;
        this.command = command;
        this.f879id = id;
        createHandler(com.stericson.RootShell.C1029RootShell.handlerEnabled);
    }

    public Command(int id, boolean handlerEnabled, java.lang.String... command) {
        this.javaCommand = false;
        this.context = null;
        this.totalOutput = 0;
        this.totalOutputProcessed = 0;
        this.executionMonitor = null;
        this.mHandler = null;
        this.executing = false;
        this.command = new java.lang.String[0];
        this.finished = false;
        this.terminated = false;
        this.handlerEnabled = true;
        this.exitCode = -1;
        this.f879id = 0;
        this.timeout = com.stericson.RootShell.C1029RootShell.defaultCommandTimeout;
        this.command = command;
        this.f879id = id;
        createHandler(handlerEnabled);
    }

    public Command(int id, int timeout, java.lang.String... command) {
        this.javaCommand = false;
        this.context = null;
        this.totalOutput = 0;
        this.totalOutputProcessed = 0;
        this.executionMonitor = null;
        this.mHandler = null;
        this.executing = false;
        this.command = new java.lang.String[0];
        this.finished = false;
        this.terminated = false;
        this.handlerEnabled = true;
        this.exitCode = -1;
        this.f879id = 0;
        this.timeout = com.stericson.RootShell.C1029RootShell.defaultCommandTimeout;
        this.command = command;
        this.f879id = id;
        this.timeout = timeout;
        createHandler(com.stericson.RootShell.C1029RootShell.handlerEnabled);
    }

    public void commandOutput(int id, java.lang.String line) {
        com.stericson.RootShell.C1029RootShell.log("Command", "ID: " + id + ", " + line);
        this.totalOutputProcessed++;
    }

    public void commandTerminated(int id, java.lang.String reason) {
    }

    public void commandCompleted(int id, int exitcode) {
    }

    protected final void commandFinished() {
        if (!this.terminated) {
            synchronized (this) {
                if (this.mHandler == null || !this.handlerEnabled) {
                    commandCompleted(this.f879id, this.exitCode);
                } else {
                    android.os.Message msg = this.mHandler.obtainMessage();
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putInt("action", 2);
                    msg.setData(bundle);
                    this.mHandler.sendMessage(msg);
                }
                com.stericson.RootShell.C1029RootShell.log("Command " + this.f879id + " finished.");
                finishCommand();
            }
        }
    }

    private void createHandler(boolean handlerEnabled) {
        this.handlerEnabled = handlerEnabled;
        if (android.os.Looper.myLooper() == null || !handlerEnabled) {
            com.stericson.RootShell.C1029RootShell.log("CommandHandler not created");
            return;
        }
        com.stericson.RootShell.C1029RootShell.log("CommandHandler created");
        this.mHandler = new com.stericson.RootShell.execution.Command.CommandHandler();
    }

    public final void finish() {
        com.stericson.RootShell.C1029RootShell.log("Command finished at users request!");
        commandFinished();
    }

    protected final void finishCommand() {
        this.executing = false;
        this.finished = true;
        notifyAll();
    }

    public final java.lang.String getCommand() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.command.length; i++) {
            if (i > 0) {
                sb.append(10);
            }
            sb.append(this.command[i]);
        }
        return sb.toString();
    }

    public final boolean isExecuting() {
        return this.executing;
    }

    public final boolean isHandlerEnabled() {
        return this.handlerEnabled;
    }

    public final boolean isFinished() {
        return this.finished;
    }

    public final int getExitCode() {
        return this.exitCode;
    }

    protected final void setExitCode(int code) {
        synchronized (this) {
            this.exitCode = code;
        }
    }

    protected final void startExecution() {
        this.executionMonitor = new com.stericson.RootShell.execution.Command.ExecutionMonitor();
        this.executionMonitor.setPriority(1);
        this.executionMonitor.start();
        this.executing = true;
    }

    public final void terminate() {
        com.stericson.RootShell.C1029RootShell.log("Terminating command at users request!");
        terminated("Terminated at users request!");
    }

    protected final void terminate(java.lang.String reason) {
        try {
            com.stericson.RootShell.execution.Shell.closeAll();
            com.stericson.RootShell.C1029RootShell.log("Terminating all shells.");
            terminated(reason);
        } catch (java.io.IOException e) {
        }
    }

    protected final void terminated(java.lang.String reason) {
        synchronized (this) {
            if (this.mHandler == null || !this.handlerEnabled) {
                commandTerminated(this.f879id, reason);
            } else {
                android.os.Message msg = this.mHandler.obtainMessage();
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt("action", 3);
                bundle.putString("text", reason);
                msg.setData(bundle);
                this.mHandler.sendMessage(msg);
            }
            com.stericson.RootShell.C1029RootShell.log("Command " + this.f879id + " did not finish because it was terminated. Termination reason: " + reason);
            setExitCode(-1);
            this.terminated = true;
            finishCommand();
        }
    }

    protected final void output(int id, java.lang.String line) {
        this.totalOutput++;
        if (this.mHandler == null || !this.handlerEnabled) {
            commandOutput(id, line);
            return;
        }
        android.os.Message msg = this.mHandler.obtainMessage();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("action", 1);
        bundle.putString("text", line);
        msg.setData(bundle);
        this.mHandler.sendMessage(msg);
    }

    public final void resetCommand() {
        this.finished = false;
        this.totalOutput = 0;
        this.totalOutputProcessed = 0;
        this.executing = false;
        this.terminated = false;
        this.exitCode = -1;
    }
}
