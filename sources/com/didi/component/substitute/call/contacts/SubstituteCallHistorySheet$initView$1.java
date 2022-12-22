package com.didi.component.substitute.call.contacts;

import android.view.View;
import com.didi.autotracker.AutoTrackHelper;
import kotlin.Metadata;

@Metadata(mo175977d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n"}, mo175978d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType"}, mo175979k = 3, mo175980mv = {1, 5, 1}, mo175982xi = 48)
/* compiled from: SubstituteCallHistorySheet.kt */
final class SubstituteCallHistorySheet$initView$1 implements View.OnClickListener {
    final /* synthetic */ SubstituteCallHistorySheet this$0;

    SubstituteCallHistorySheet$initView$1(SubstituteCallHistorySheet substituteCallHistorySheet) {
        this.this$0 = substituteCallHistorySheet;
    }

    public final void onClick(View view) {
        AutoTrackHelper.trackViewOnClick(view);
        this.this$0.dismiss();
    }
}