package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap<>();

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.receiver);
            sb.append(" filter=");
            sb.append(this.filter);
            if (this.dead) {
                sb.append(" DEAD");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent2, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent2;
            this.receivers = arrayList;
        }
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    super.handleMessage(message);
                } else {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, broadcastReceiver);
            ArrayList arrayList = this.mReceivers.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put(broadcastReceiver, arrayList);
            }
            arrayList.add(receiverRecord);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList remove = this.mReceivers.remove(broadcastReceiver);
            if (remove != null) {
                for (int size = remove.size() - 1; size >= 0; size--) {
                    ReceiverRecord receiverRecord = (ReceiverRecord) remove.get(size);
                    receiverRecord.dead = true;
                    for (int i = 0; i < receiverRecord.filter.countActions(); i++) {
                        String action = receiverRecord.filter.getAction(i);
                        ArrayList arrayList = this.mActions.get(action);
                        if (arrayList != null) {
                            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                                ReceiverRecord receiverRecord2 = (ReceiverRecord) arrayList.get(size2);
                                if (receiverRecord2.receiver == broadcastReceiver) {
                                    receiverRecord2.dead = true;
                                    arrayList.remove(size2);
                                }
                            }
                            if (arrayList.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x019f, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01a1, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r24) {
        /*
            r23 = this;
            r1 = r23
            r0 = r24
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r2 = r1.mReceivers
            monitor-enter(r2)
            java.lang.String r10 = r24.getAction()     // Catch:{ all -> 0x01a3 }
            android.content.Context r3 = r1.mAppContext     // Catch:{ all -> 0x01a3 }
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ all -> 0x01a3 }
            java.lang.String r11 = r0.resolveTypeIfNeeded(r3)     // Catch:{ all -> 0x01a3 }
            android.net.Uri r12 = r24.getData()     // Catch:{ all -> 0x01a3 }
            java.lang.String r13 = r24.getScheme()     // Catch:{ all -> 0x01a3 }
            java.util.Set r14 = r24.getCategories()     // Catch:{ all -> 0x01a3 }
            int r3 = r24.getFlags()     // Catch:{ all -> 0x01a3 }
            r3 = r3 & 8
            r9 = 1
            if (r3 == 0) goto L_0x002d
            r16 = 1
            goto L_0x002f
        L_0x002d:
            r16 = 0
        L_0x002f:
            if (r16 == 0) goto L_0x005d
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
            r3.<init>()     // Catch:{ all -> 0x01a3 }
            java.lang.String r5 = "Resolving type "
            r3.append(r5)     // Catch:{ all -> 0x01a3 }
            r3.append(r11)     // Catch:{ all -> 0x01a3 }
            java.lang.String r5 = " scheme "
            r3.append(r5)     // Catch:{ all -> 0x01a3 }
            r3.append(r13)     // Catch:{ all -> 0x01a3 }
            java.lang.String r5 = " of intent "
            r3.append(r5)     // Catch:{ all -> 0x01a3 }
            r3.append(r0)     // Catch:{ all -> 0x01a3 }
            java.lang.String r5 = r3.toString()     // Catch:{ all -> 0x01a3 }
            r3 = 2
            r6 = 0
            java.lang.String r7 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r8 = 223(0xdf, float:3.12E-43)
            com.didi.sdk.apm.SystemUtils.log(r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x01a3 }
        L_0x005d:
            java.util.HashMap<java.lang.String, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r3 = r1.mActions     // Catch:{ all -> 0x01a3 }
            java.lang.String r4 = r24.getAction()     // Catch:{ all -> 0x01a3 }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x01a3 }
            r8 = r3
            java.util.ArrayList r8 = (java.util.ArrayList) r8     // Catch:{ all -> 0x01a3 }
            if (r8 == 0) goto L_0x01a0
            if (r16 == 0) goto L_0x008c
            java.lang.String r18 = "LocalBroadcastManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
            r3.<init>()     // Catch:{ all -> 0x01a3 }
            java.lang.String r4 = "Action list: "
            r3.append(r4)     // Catch:{ all -> 0x01a3 }
            r3.append(r8)     // Catch:{ all -> 0x01a3 }
            java.lang.String r19 = r3.toString()     // Catch:{ all -> 0x01a3 }
            r17 = 2
            r20 = 0
            java.lang.String r21 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r22 = 229(0xe5, float:3.21E-43)
            com.didi.sdk.apm.SystemUtils.log(r17, r18, r19, r20, r21, r22)     // Catch:{ all -> 0x01a3 }
        L_0x008c:
            r3 = 0
            r7 = r3
            r6 = 0
        L_0x008f:
            int r3 = r8.size()     // Catch:{ all -> 0x01a3 }
            if (r6 >= r3) goto L_0x0170
            java.lang.Object r3 = r8.get(r6)     // Catch:{ all -> 0x01a3 }
            r5 = r3
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r5 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r5     // Catch:{ all -> 0x01a3 }
            if (r16 == 0) goto L_0x00be
            java.lang.String r18 = "LocalBroadcastManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
            r3.<init>()     // Catch:{ all -> 0x01a3 }
            java.lang.String r4 = "Matching against filter "
            r3.append(r4)     // Catch:{ all -> 0x01a3 }
            android.content.IntentFilter r4 = r5.filter     // Catch:{ all -> 0x01a3 }
            r3.append(r4)     // Catch:{ all -> 0x01a3 }
            java.lang.String r19 = r3.toString()     // Catch:{ all -> 0x01a3 }
            r17 = 2
            r20 = 0
            java.lang.String r21 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r22 = 234(0xea, float:3.28E-43)
            com.didi.sdk.apm.SystemUtils.log(r17, r18, r19, r20, r21, r22)     // Catch:{ all -> 0x01a3 }
        L_0x00be:
            boolean r3 = r5.broadcasting     // Catch:{ all -> 0x01a3 }
            if (r3 == 0) goto L_0x00df
            if (r16 == 0) goto L_0x00d3
            java.lang.String r18 = "LocalBroadcastManager"
            java.lang.String r19 = "  Filter's target already added"
            r17 = 2
            r20 = 0
            java.lang.String r21 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r22 = 238(0xee, float:3.34E-43)
            com.didi.sdk.apm.SystemUtils.log(r17, r18, r19, r20, r21, r22)     // Catch:{ all -> 0x01a3 }
        L_0x00d3:
            r19 = r6
            r21 = r8
            r20 = r10
            r22 = r11
            r11 = 1
            r10 = r7
            goto L_0x0164
        L_0x00df:
            android.content.IntentFilter r3 = r5.filter     // Catch:{ all -> 0x01a3 }
            java.lang.String r17 = "LocalBroadcastManager"
            r4 = r10
            r15 = r5
            r5 = r11
            r19 = r6
            r6 = r13
            r20 = r10
            r10 = r7
            r7 = r12
            r21 = r8
            r8 = r14
            r22 = r11
            r11 = 1
            r9 = r17
            int r3 = r3.match(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x01a3 }
            if (r3 < 0) goto L_0x012c
            if (r16 == 0) goto L_0x011d
            java.lang.String r5 = "LocalBroadcastManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
            r4.<init>()     // Catch:{ all -> 0x01a3 }
            java.lang.String r6 = "  Filter matched!  match=0x"
            r4.append(r6)     // Catch:{ all -> 0x01a3 }
            java.lang.String r3 = java.lang.Integer.toHexString(r3)     // Catch:{ all -> 0x01a3 }
            r4.append(r3)     // Catch:{ all -> 0x01a3 }
            java.lang.String r6 = r4.toString()     // Catch:{ all -> 0x01a3 }
            r4 = 2
            r7 = 0
            java.lang.String r8 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r9 = 246(0xf6, float:3.45E-43)
            com.didi.sdk.apm.SystemUtils.log(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x01a3 }
        L_0x011d:
            if (r10 != 0) goto L_0x0125
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x01a3 }
            r7.<init>()     // Catch:{ all -> 0x01a3 }
            goto L_0x0126
        L_0x0125:
            r7 = r10
        L_0x0126:
            r7.add(r15)     // Catch:{ all -> 0x01a3 }
            r15.broadcasting = r11     // Catch:{ all -> 0x01a3 }
            goto L_0x0165
        L_0x012c:
            if (r16 == 0) goto L_0x0164
            r4 = -4
            if (r3 == r4) goto L_0x0146
            r4 = -3
            if (r3 == r4) goto L_0x0143
            r4 = -2
            if (r3 == r4) goto L_0x0140
            r4 = -1
            if (r3 == r4) goto L_0x013d
            java.lang.String r3 = "unknown reason"
            goto L_0x0148
        L_0x013d:
            java.lang.String r3 = "type"
            goto L_0x0148
        L_0x0140:
            java.lang.String r3 = "data"
            goto L_0x0148
        L_0x0143:
            java.lang.String r3 = "action"
            goto L_0x0148
        L_0x0146:
            java.lang.String r3 = "category"
        L_0x0148:
            java.lang.String r5 = "LocalBroadcastManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
            r4.<init>()     // Catch:{ all -> 0x01a3 }
            java.lang.String r6 = "  Filter did not match: "
            r4.append(r6)     // Catch:{ all -> 0x01a3 }
            r4.append(r3)     // Catch:{ all -> 0x01a3 }
            java.lang.String r6 = r4.toString()     // Catch:{ all -> 0x01a3 }
            r4 = 2
            r7 = 0
            java.lang.String r8 = "androidx.localbroadcastmanager.content.LocalBroadcastManager"
            r9 = 263(0x107, float:3.69E-43)
            com.didi.sdk.apm.SystemUtils.log(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x01a3 }
        L_0x0164:
            r7 = r10
        L_0x0165:
            int r6 = r19 + 1
            r10 = r20
            r8 = r21
            r11 = r22
            r9 = 1
            goto L_0x008f
        L_0x0170:
            r10 = r7
            r11 = 1
            if (r10 == 0) goto L_0x01a0
            r3 = 0
        L_0x0175:
            int r4 = r10.size()     // Catch:{ all -> 0x01a3 }
            if (r3 >= r4) goto L_0x0187
            java.lang.Object r4 = r10.get(r3)     // Catch:{ all -> 0x01a3 }
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r4 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r4     // Catch:{ all -> 0x01a3 }
            r5 = 0
            r4.broadcasting = r5     // Catch:{ all -> 0x01a3 }
            int r3 = r3 + 1
            goto L_0x0175
        L_0x0187:
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r3 = r1.mPendingBroadcasts     // Catch:{ all -> 0x01a3 }
            androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord r4 = new androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x01a3 }
            r4.<init>(r0, r10)     // Catch:{ all -> 0x01a3 }
            r3.add(r4)     // Catch:{ all -> 0x01a3 }
            android.os.Handler r0 = r1.mHandler     // Catch:{ all -> 0x01a3 }
            boolean r0 = r0.hasMessages(r11)     // Catch:{ all -> 0x01a3 }
            if (r0 != 0) goto L_0x019e
            android.os.Handler r0 = r1.mHandler     // Catch:{ all -> 0x01a3 }
            r0.sendEmptyMessage(r11)     // Catch:{ all -> 0x01a3 }
        L_0x019e:
            monitor-exit(r2)     // Catch:{ all -> 0x01a3 }
            return r11
        L_0x01a0:
            monitor-exit(r2)     // Catch:{ all -> 0x01a3 }
            r0 = 0
            return r0
        L_0x01a3:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01a3 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.localbroadcastmanager.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (r3 >= r1) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        r4 = r2[r3];
        r5 = r4.receivers.size();
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
        if (r6 >= r5) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        r7 = r4.receivers.get(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        if (r7.dead != false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        r7.receiver.onReceive(r10.mAppContext, r4.intent);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r10 = this;
        L_0x0000:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r0 = r10.mReceivers
            monitor-enter(r0)
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r1 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0044 }
            int r1 = r1.size()     // Catch:{ all -> 0x0044 }
            if (r1 > 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            return
        L_0x000d:
            androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord[] r2 = new androidx.localbroadcastmanager.content.LocalBroadcastManager.BroadcastRecord[r1]     // Catch:{ all -> 0x0044 }
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r3 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0044 }
            r3.toArray(r2)     // Catch:{ all -> 0x0044 }
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r3 = r10.mPendingBroadcasts     // Catch:{ all -> 0x0044 }
            r3.clear()     // Catch:{ all -> 0x0044 }
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            r0 = 0
            r3 = 0
        L_0x001c:
            if (r3 >= r1) goto L_0x0000
            r4 = r2[r3]
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord> r5 = r4.receivers
            int r5 = r5.size()
            r6 = 0
        L_0x0027:
            if (r6 >= r5) goto L_0x0041
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord> r7 = r4.receivers
            java.lang.Object r7 = r7.get(r6)
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r7 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r7
            boolean r8 = r7.dead
            if (r8 != 0) goto L_0x003e
            android.content.BroadcastReceiver r7 = r7.receiver
            android.content.Context r8 = r10.mAppContext
            android.content.Intent r9 = r4.intent
            r7.onReceive(r8, r9)
        L_0x003e:
            int r6 = r6 + 1
            goto L_0x0027
        L_0x0041:
            int r3 = r3 + 1
            goto L_0x001c
        L_0x0044:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0044 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.localbroadcastmanager.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}