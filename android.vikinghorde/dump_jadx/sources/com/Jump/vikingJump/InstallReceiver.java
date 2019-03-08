package com.Jump.vikingJump;

/** ANAL: InstallReceiver broadcast receiver
 *
 * - Get the "referrer" extra from the intent
 * - put it in SharedPreferences
 * - Send a RRR_AAA_FFF broadcast
 * - Attached the "referrer" to a new intent and use it to launch AppActivity
 */
public class InstallReceiver extends android.content.BroadcastReceiver {
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        java.lang.String referrer = intent.getStringExtra("referrer");
        android.content.SharedPreferences.Editor editor = com.p003fa.p004c.Utilities.GetPreferncesEditor(context);
        editor.putString("SHARED_PREF_KEY_REF", referrer);
        editor.commit();
        android.content.Intent intent2open = new android.content.Intent(context, org.cocos2dx.cpp.AppActivity.class);
        intent2open.addFlags(268435456);
        intent2open.addFlags(536870912);
        intent2open.putExtra("referrer", referrer);
        android.content.Intent i = new android.content.Intent();
        i.setAction("RRR_AAA_FFF");
        i.putExtra("r", referrer);
        context.sendBroadcast(i);
        context.startActivity(intent2open);
        android.util.Log.d("TRACKING", "Ref: " + referrer);
    }
}
