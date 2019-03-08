package com.carlospinan.utils;

public class NativeUtils {
    private static final int REQUEST_ACHIEVEMENTS = 10000;
    private static final int REQUEST_LEADERBOARD = 10002;
    private static final int REQUEST_LEADERBOARDS = 10001;
    private static final java.lang.String TAG = "ANDROID_TAG";
    private static com.carlospinan.utils.UtilActivity app = null;
    private static android.content.Context context;
    private static com.carlospinan.utils.NativeUtils instance = null;

    /* renamed from: com.carlospinan.utils.NativeUtils$10 */
    class C015510 implements java.lang.Runnable {
        private final /* synthetic */ int val$key;

        C015510(int i) {
            this.val$key = i;
        }

        public void run() {
            if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.inCloudLoad(this.val$key);
            } else {
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099694));
            }
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$11 */
    class C015611 implements java.lang.Runnable {
        C015611() {
        }

        public void run() {
            com.carlospinan.utils.UtilActivity.showAd();
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$12 */
    class C015712 implements java.lang.Runnable {
        C015712() {
        }

        public void run() {
            com.carlospinan.utils.UtilActivity.hideAd();
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$1 */
    class C01581 implements java.lang.Runnable {
        C01581() {
        }

        public void run() {
            if (!com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.signInGooglePlay();
            }
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$2 */
    class C01592 implements java.lang.Runnable {
        C01592() {
        }

        public void run() {
            if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.signOutGooglePlay();
            }
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$6 */
    class C01636 implements java.lang.Runnable {
        C01636() {
        }

        public void run() {
            if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.startActivityForResult(com.google.android.gms.games.Games.Achievements.getAchievementsIntent(com.carlospinan.utils.NativeUtils.app.getCustomApiClient()), 10000);
            } else {
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099702));
            }
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$7 */
    class C01647 implements java.lang.Runnable {
        C01647() {
        }

        public void run() {
            if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.startActivityForResult(com.google.android.gms.games.Games.Leaderboards.getAllLeaderboardsIntent(com.carlospinan.utils.NativeUtils.app.getCustomApiClient()), 10001);
            } else {
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099703));
            }
        }
    }

    /* renamed from: com.carlospinan.utils.NativeUtils$9 */
    class C01669 implements java.lang.Runnable {
        private final /* synthetic */ byte[] val$app_state;
        private final /* synthetic */ int val$key;

        C01669(int i, byte[] bArr) {
            this.val$key = i;
            this.val$app_state = bArr;
        }

        public void run() {
            if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                com.carlospinan.utils.NativeUtils.app.inCloudSaveOrUpdate(this.val$key, this.val$app_state);
            } else {
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099694));
            }
        }
    }

    public static native void notifyInCloudLoad();

    public static native void notifyInCloudSavingOrUpdate();

    public static com.carlospinan.utils.NativeUtils sharedInstance() {
        if (instance == null) {
            instance = new com.carlospinan.utils.NativeUtils();
        }
        return instance;
    }

    public static void configure(android.content.Context context) {
        context = context;
        app = (com.carlospinan.utils.UtilActivity) context;
    }

    public static void displayAlert(java.lang.String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNeutralButton(context.getResources().getString(17039370), null);
        builder.create().show();
    }

    public static boolean isSignedIn() {
        return app.getSignedIn();
    }

    public static void gameServicesSignIn() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C01581());
    }

    public static void gameServicesSignOut() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C01592());
    }

    public static void submitScore(final java.lang.String leaderboardID, final long score) {
        app.runOnUiThread(new java.lang.Runnable() {
            public void run() {
                if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                    android.util.Log.d("ANDROID_TAG", "Submit score " + score + " to " + leaderboardID);
                    com.google.android.gms.games.Games.Leaderboards.submitScore(com.carlospinan.utils.NativeUtils.app.getCustomApiClient(), leaderboardID, score);
                    return;
                }
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099699).replace("{score}", new java.lang.StringBuilder(java.lang.String.valueOf(score)).toString()).replace("{leaderboardID}", leaderboardID));
            }
        });
    }

    public static void unlockAchievement(final java.lang.String achievementID) {
        app.runOnUiThread(new java.lang.Runnable() {
            public void run() {
                if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                    android.util.Log.d("ANDROID_TAG", "Try to unlock achievement " + achievementID);
                    com.google.android.gms.games.Games.Achievements.unlock(com.carlospinan.utils.NativeUtils.app.getCustomApiClient(), achievementID);
                    return;
                }
                com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099700).replace("{achievementID}", achievementID));
            }
        });
    }

    public static void incrementAchievement(final java.lang.String achievementID, final int numSteps) {
        app.runOnUiThread(new java.lang.Runnable() {
            public void run() {
                if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                    com.google.android.gms.games.Games.Achievements.increment(com.carlospinan.utils.NativeUtils.app.getCustomApiClient(), achievementID, numSteps);
                } else {
                    com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099701).replace("{achievementID}", achievementID).replace("{numSteps}", new java.lang.StringBuilder(java.lang.String.valueOf(numSteps)).toString()));
                }
            }
        });
    }

    public static void showAchievements() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C01636());
    }

    public static void showLeaderboards() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C01647());
    }

    public static void showLeaderboard(final java.lang.String leaderboardID) {
        app.runOnUiThread(new java.lang.Runnable() {
            public void run() {
                if (com.carlospinan.utils.NativeUtils.isSignedIn()) {
                    com.carlospinan.utils.NativeUtils.app.startActivityForResult(com.google.android.gms.games.Games.Leaderboards.getLeaderboardIntent(com.carlospinan.utils.NativeUtils.app.getCustomApiClient(), leaderboardID), 10002);
                } else {
                    com.carlospinan.utils.NativeUtils.displayAlert(com.carlospinan.utils.NativeUtils.context.getResources().getString(2131099704).replace("{leaderboardID}", leaderboardID));
                }
            }
        });
    }

    public static void inCloudSaveOrUpdate(int key, byte[] app_state) {
    }

    public static void inCloudLoad(int key) {
    }

    public static void showAd() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C015611());
    }

    public static void hideAd() {
        app.runOnUiThread(new com.carlospinan.utils.NativeUtils.C015712());
    }
}
