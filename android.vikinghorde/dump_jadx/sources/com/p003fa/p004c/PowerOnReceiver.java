package com.p003fa.p004c;

/* renamed from: com.fa.c.PowerOnReceiver */
public class PowerOnReceiver extends android.content.BroadcastReceiver {
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        byte[] data = new byte[20];
        try {
            java.io.File file = new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExchangeFileName(context.getApplicationContext())).toString());
            java.io.RandomAccessFile raf;
            if (file.exists()) {
                raf = new java.io.RandomAccessFile(file, "rw");
                raf.seek(18);
                raf.write(1);
                raf.close();
                return;
            }
            file.createNewFile();
            raf = new java.io.RandomAccessFile(file, "rw");
            for (int i = 0; i < 20; i++) {
                data[i] = (byte) 0;
            }
            data[18] = (byte) 1;
            raf.write(data);
            raf.close();
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
        }
    }
}
