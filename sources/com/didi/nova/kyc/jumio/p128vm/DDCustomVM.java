package com.didi.nova.kyc.jumio.p128vm;

import android.app.Application;
import com.didi.payment.commonsdk.p129ui.WBaseViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo175977d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, mo175978d2 = {"Lcom/didi/nova/kyc/jumio/vm/DDCustomVM;", "Lcom/didi/payment/commonsdk/ui/WBaseViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "loadData", "", "kyc-jumios_globalRelease"}, mo175979k = 1, mo175980mv = {1, 5, 1}, mo175982xi = 48)
/* renamed from: com.didi.nova.kyc.jumio.vm.DDCustomVM */
/* compiled from: DDCustomVM.kt */
public final class DDCustomVM extends WBaseViewModel {
    public void loadData() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DDCustomVM(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
    }
}