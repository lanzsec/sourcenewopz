package com.datadog.android.core.internal.persistence.file;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo175977d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, mo175978d2 = {"<anonymous>", "", "Ljava/io/File;"}, mo175979k = 3, mo175980mv = {1, 5, 1}, mo175982xi = 48)
/* compiled from: FileExt.kt */
final class FileExtKt$renameToSafe$1 extends Lambda implements Function1<File, Boolean> {
    final /* synthetic */ File $dest;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FileExtKt$renameToSafe$1(File file) {
        super(1);
        this.$dest = file;
    }

    public /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((File) obj));
    }

    public final boolean invoke(File file) {
        Intrinsics.checkNotNullParameter(file, "$this$safeCall");
        return file.renameTo(this.$dest);
    }
}