package com.stericson.RootShell.execution;

public class JavaCommand extends com.stericson.RootShell.execution.Command {
    public JavaCommand(int id, android.content.Context context, java.lang.String... command) {
        super(id, command);
        this.context = context;
        this.javaCommand = true;
    }

    public JavaCommand(int id, boolean handlerEnabled, android.content.Context context, java.lang.String... command) {
        super(id, handlerEnabled, command);
        this.context = context;
        this.javaCommand = true;
    }

    public JavaCommand(int id, int timeout, android.content.Context context, java.lang.String... command) {
        super(id, timeout, command);
        this.context = context;
        this.javaCommand = true;
    }

    public void commandOutput(int id, java.lang.String line) {
        super.commandOutput(id, line);
    }

    public void commandTerminated(int id, java.lang.String reason) {
    }

    public void commandCompleted(int id, int exitCode) {
    }
}
