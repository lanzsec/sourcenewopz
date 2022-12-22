package kotlin.random;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo175977d1 = {"\u0000\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\b\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\n"}, mo175978d2 = {"Lkotlin/random/FallbackThreadLocalRandom;", "Lkotlin/random/AbstractPlatformRandom;", "()V", "impl", "Ljava/util/Random;", "getImpl", "()Ljava/util/Random;", "implStorage", "kotlin/random/FallbackThreadLocalRandom$implStorage$1", "Lkotlin/random/FallbackThreadLocalRandom$implStorage$1;", "kotlin-stdlib"}, mo175979k = 1, mo175980mv = {1, 5, 1})
/* compiled from: PlatformRandom.kt */
public final class FallbackThreadLocalRandom extends AbstractPlatformRandom {

    /* renamed from: a */
    private final FallbackThreadLocalRandom$implStorage$1 f59955a = new FallbackThreadLocalRandom$implStorage$1();

    public Random getImpl() {
        Object obj = this.f59955a.get();
        Intrinsics.checkNotNullExpressionValue(obj, "implStorage.get()");
        return (Random) obj;
    }
}