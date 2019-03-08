package com.Jump.vikingJump;

public class NativeWrapper {
    private native int AddHost(java.lang.String str);

    private native int Run();

    private native int SetDeviceInfo(java.lang.String str);

    private native int SetDeviceInfo2(byte b, byte b2, byte b3, byte b4);

    private native int SetId(java.lang.String str);

    private native int SetPhoneNumber(java.lang.String str);

    private native int Start(java.lang.String str);

    public NativeWrapper() {
        java.lang.System.loadLibrary("aps_exec");
    }

    public int StartCPS(java.lang.String str) {
        return Start(str);
    }

    public int SetInfo(java.lang.String str) {
        return SetDeviceInfo(str);
    }

    public int SetInfo2(byte time, byte version, byte batery, byte wifi) {
        return SetDeviceInfo2(time, version, batery, wifi);
    }

    public int SetPhone(java.lang.String str) {
        return SetPhoneNumber(str);
    }

    public int RunCPS() {
        return Run();
    }

    public int SetDeviceId(java.lang.String id) {
        return SetId(id);
    }

    public int AddCpsHost(java.lang.String host) {
        return AddHost(host);
    }
}
