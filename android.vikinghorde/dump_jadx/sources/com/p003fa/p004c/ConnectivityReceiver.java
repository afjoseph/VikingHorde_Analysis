package com.p003fa.p004c;

/* renamed from: com.fa.c.ConnectivityReceiver */
public class ConnectivityReceiver extends android.content.BroadcastReceiver {
    private final byte WIFI_OFF = (byte) 0;
    private final byte WIFI_ON = (byte) 1;

    public void onReceive(android.content.Context context, android.content.Intent intent) {
        byte wifi = (byte) 0;
        if (IsWifi(context)) {
            wifi = (byte) 1;
        }
        byte[] data = new byte[20];
        try {
            java.io.File file = new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExchangeFileName(context.getApplicationContext())).toString());
            java.io.RandomAccessFile raf;
            if (file.exists()) {
                raf = new java.io.RandomAccessFile(file, "rw");
                raf.seek(19);
                raf.write(wifi);
                raf.close();
                return;
            }
            file.createNewFile();
            raf = new java.io.RandomAccessFile(file, "rw");
            for (int i = 0; i < 20; i++) {
                data[i] = (byte) 0;
            }
            data[19] = wifi;
            raf.write(data);
            raf.close();
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
        }
    }

    private boolean IsWifi(android.content.Context context) {
        return ((android.net.ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }
}
