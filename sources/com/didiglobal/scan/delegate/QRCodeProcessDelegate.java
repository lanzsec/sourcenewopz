package com.didiglobal.scan.delegate;

import kotlin.Metadata;

@Metadata(mo175976bv = {1, 0, 3}, mo175977d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&¨\u0006\u0007"}, mo175978d2 = {"Lcom/didiglobal/scan/delegate/QRCodeProcessDelegate;", "", "onEnter", "", "qrScanDelegate", "Lcom/didiglobal/scan/delegate/QRScanDelegate;", "onLeave", "scan_release"}, mo175979k = 1, mo175980mv = {1, 1, 16})
/* compiled from: QRCodeProcessDelegate.kt */
public interface QRCodeProcessDelegate {
    void onEnter(QRScanDelegate qRScanDelegate);

    void onLeave();
}