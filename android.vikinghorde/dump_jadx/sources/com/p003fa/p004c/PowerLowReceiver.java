package com.p003fa.p004c;

/* renamed from: com.fa.c.PowerLowReceiver */
public class PowerLowReceiver extends android.content.BroadcastReceiver {
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        byte[] data = new byte[20];
        try {
            java.io.File file = new java.io.File(new java.lang.StringBuilder(java.lang.String.valueOf(android.os.Environment.getExternalStorageDirectory().getAbsolutePath())).append(java.io.File.separator).append(com.p003fa.p004c.Utilities.GetExchangeFileName(context.getApplicationContext())).toString());
            java.io.RandomAccessFile raf;
            if (file.exists()) {
                raf = new java.io.RandomAccessFile(file, "rw");
                raf.seek(18);
                raf.write(2);
                raf.close();
                return;
            }
            file.createNewFile();
            raf = new java.io.RandomAccessFile(file, "rw");
            for (int i = 0; i < 20; i++) {
                data[i] = (byte) 0;
            }
            android.util.Log.d("das", "dasd");
            data[18] = (byte) 2;
            raf.write(data);
            raf.close();
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
        }
    }
}
