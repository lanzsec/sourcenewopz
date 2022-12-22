package com.google.android.gms.common.api.internal;

import com.didi.sdk.apm.SystemUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
final class zaj implements GoogleApiClient.OnConnectionFailedListener {
    public final int zaa;
    public final GoogleApiClient zab;
    public final GoogleApiClient.OnConnectionFailedListener zac;
    final /* synthetic */ zak zad;

    public zaj(zak zak, int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zad = zak;
        this.zaa = i;
        this.zab = googleApiClient;
        this.zac = onConnectionFailedListener;
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        SystemUtils.log(3, "AutoManageHelper", "beginFailureResolution for ".concat(String.valueOf(String.valueOf(connectionResult))), (Throwable) null, "com.google.android.gms.common.api.internal.zaj", 1);
        this.zad.zah(connectionResult, this.zaa);
    }
}