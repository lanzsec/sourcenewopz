package com.didi.global.fintech.cashier.fastpay.contract;

import com.didi.global.fintech.cashier.fastpay.consts.FastPayStatus;
import kotlin.Metadata;

@Metadata(mo175977d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, mo175978d2 = {"Lcom/didi/global/fintech/cashier/fastpay/contract/IFastPayCallback;", "", "onClose", "", "refreshed", "", "curStatus", "Lcom/didi/global/fintech/cashier/fastpay/consts/FastPayStatus;", "cashier_fastpay_release"}, mo175979k = 1, mo175980mv = {1, 5, 1}, mo175982xi = 48)
/* compiled from: IFastPayCallback.kt */
public interface IFastPayCallback {
    void onClose(boolean z, FastPayStatus fastPayStatus);
}