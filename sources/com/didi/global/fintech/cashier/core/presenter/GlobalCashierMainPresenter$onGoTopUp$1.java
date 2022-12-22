package com.didi.global.fintech.cashier.core.presenter;

import com.didi.global.fintech.cashier.core.contract.IGlobalCashierActionInterceptCallback;
import com.didi.global.fintech.cashier.model.net.request.CashierAction;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo175977d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, mo175978d2 = {"<anonymous>", ""}, mo175979k = 3, mo175980mv = {1, 5, 1}, mo175982xi = 48)
/* compiled from: GlobalCashierMainPresenter.kt */
final class GlobalCashierMainPresenter$onGoTopUp$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ GlobalCashierMainPresenter this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    GlobalCashierMainPresenter$onGoTopUp$1(GlobalCashierMainPresenter globalCashierMainPresenter) {
        super(0);
        this.this$0 = globalCashierMainPresenter;
    }

    public final void invoke() {
        this.this$0.loading(true, 3000);
        IGlobalCashierActionInterceptCallback actionInterceptHandler = this.this$0.getActionInterceptHandler();
        if (actionInterceptHandler != null) {
            actionInterceptHandler.onActionInterceptJumpThirdPage(CashierAction.ACTION_TOP_UP);
        }
    }
}