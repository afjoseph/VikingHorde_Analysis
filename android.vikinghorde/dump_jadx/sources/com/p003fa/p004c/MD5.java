package com.p003fa.p004c;

/* renamed from: com.fa.c.MD5 */
public class MD5 {
    private static final java.lang.String TAG = "MD5";

    public static boolean checkMD5(java.lang.String md5, java.io.File updateFile) {
        if (android.text.TextUtils.isEmpty(md5) || updateFile == null) {
            android.util.Log.e("MD5", "MD5 string empty or updateFile null");
            return false;
        }
        java.lang.String calculatedDigest = com.p003fa.p004c.MD5.calculateMD5(updateFile);
        if (calculatedDigest == null) {
            android.util.Log.e("MD5", "calculatedDigest null");
            return false;
        }
        android.util.Log.v("MD5", "Calculated digest: " + calculatedDigest);
        android.util.Log.v("MD5", "Provided digest: " + md5);
        return calculatedDigest.equalsIgnoreCase(md5);
    }

    public static java.lang.String calculateMD5(java.io.File updateFile) {
        java.lang.String output = null;
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            try {
                java.io.InputStream is = new java.io.FileInputStream(updateFile);
                byte[] buffer = new byte[8192];
                while (true) {
                    try {
                        int read = is.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        digest.update(buffer, 0, read);
                    } catch (java.io.IOException e) {
                        throw new java.lang.RuntimeException("Unable to process file for MD5", e);
                    } catch (Throwable th) {
                        try {
                            is.close();
                        } catch (java.io.IOException e2) {
                            android.util.Log.e("MD5", "Exception on closing MD5 input stream", e2);
                        }
                    }
                }
                output = java.lang.String.format("%32s", new java.lang.Object[]{new java.math.BigInteger(1, digest.digest()).toString(16)}).replace(' ', '0');
                try {
                    is.close();
                } catch (java.io.IOException e22) {
                    android.util.Log.e("MD5", "Exception on closing MD5 input stream", e22);
                }
            } catch (java.io.FileNotFoundException e3) {
                android.util.Log.e("MD5", "Exception while getting FileInputStream", e3);
            }
        } catch (java.security.NoSuchAlgorithmException e4) {
            android.util.Log.e("MD5", "Exception while getting digest", e4);
        }
        return output;
    }
}
