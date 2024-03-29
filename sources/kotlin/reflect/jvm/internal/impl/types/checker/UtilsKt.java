package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;

/* compiled from: utils.kt */
public final class UtilsKt {
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.impl.types.KotlinType findCorrespondingSupertype(kotlin.reflect.jvm.internal.impl.types.KotlinType r9, kotlin.reflect.jvm.internal.impl.types.KotlinType r10, kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks r11) {
        /*
            java.lang.String r0 = "subtype"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r0 = "supertype"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.lang.String r0 = "typeCheckingProcedureCallbacks"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            java.util.ArrayDeque r0 = new java.util.ArrayDeque
            r0.<init>()
            kotlin.reflect.jvm.internal.impl.types.checker.a r1 = new kotlin.reflect.jvm.internal.impl.types.checker.a
            r2 = 0
            r1.<init>(r9, r2)
            r0.add(r1)
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r9 = r10.getConstructor()
        L_0x0021:
            boolean r10 = r0.isEmpty()
            if (r10 != 0) goto L_0x0129
            java.lang.Object r10 = r0.poll()
            kotlin.reflect.jvm.internal.impl.types.checker.a r10 = (kotlin.reflect.jvm.internal.impl.types.checker.C21763a) r10
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = r10.mo180135a()
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r3 = r1.getConstructor()
            boolean r4 = r11.assertEqualTypeConstructors(r3, r9)
            if (r4 == 0) goto L_0x0107
            boolean r0 = r1.isMarkedNullable()
            kotlin.reflect.jvm.internal.impl.types.checker.a r10 = r10.mo180136b()
        L_0x0043:
            if (r10 == 0) goto L_0x00c5
            kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = r10.mo180135a()
            java.util.List r4 = r3.getArguments()
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            boolean r5 = r4 instanceof java.util.Collection
            r6 = 1
            r7 = 0
            if (r5 == 0) goto L_0x0060
            r5 = r4
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x0060
        L_0x005e:
            r4 = 0
            goto L_0x007e
        L_0x0060:
            java.util.Iterator r4 = r4.iterator()
        L_0x0064:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x005e
            java.lang.Object r5 = r4.next()
            kotlin.reflect.jvm.internal.impl.types.TypeProjection r5 = (kotlin.reflect.jvm.internal.impl.types.TypeProjection) r5
            kotlin.reflect.jvm.internal.impl.types.Variance r5 = r5.getProjectionKind()
            kotlin.reflect.jvm.internal.impl.types.Variance r8 = kotlin.reflect.jvm.internal.impl.types.Variance.INVARIANT
            if (r5 == r8) goto L_0x007a
            r5 = 1
            goto L_0x007b
        L_0x007a:
            r5 = 0
        L_0x007b:
            if (r5 == 0) goto L_0x0064
            r4 = 1
        L_0x007e:
            if (r4 == 0) goto L_0x009e
            kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution$Companion r4 = kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution.Companion
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitution r4 = r4.create(r3)
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitution r4 = kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(r4, r7, r6, r2)
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r4 = r4.buildSubstitutor()
            kotlin.reflect.jvm.internal.impl.types.Variance r5 = kotlin.reflect.jvm.internal.impl.types.Variance.INVARIANT
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = r4.safeSubstitute(r1, r5)
            java.lang.String r4 = "TypeConstructorSubstitut…uted, Variance.INVARIANT)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r4)
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = m45080a((kotlin.reflect.jvm.internal.impl.types.KotlinType) r1)
            goto L_0x00b3
        L_0x009e:
            kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution$Companion r4 = kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution.Companion
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitution r4 = r4.create(r3)
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r4 = r4.buildSubstitutor()
            kotlin.reflect.jvm.internal.impl.types.Variance r5 = kotlin.reflect.jvm.internal.impl.types.Variance.INVARIANT
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = r4.safeSubstitute(r1, r5)
            java.lang.String r4 = "{\n                    Ty…ARIANT)\n                }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r4)
        L_0x00b3:
            if (r0 != 0) goto L_0x00be
            boolean r0 = r3.isMarkedNullable()
            if (r0 == 0) goto L_0x00bc
            goto L_0x00be
        L_0x00bc:
            r0 = 0
            goto L_0x00bf
        L_0x00be:
            r0 = 1
        L_0x00bf:
            kotlin.reflect.jvm.internal.impl.types.checker.a r10 = r10.mo180136b()
            goto L_0x0043
        L_0x00c5:
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r10 = r1.getConstructor()
            boolean r2 = r11.assertEqualTypeConstructors(r10, r9)
            if (r2 == 0) goto L_0x00d4
            kotlin.reflect.jvm.internal.impl.types.KotlinType r9 = kotlin.reflect.jvm.internal.impl.types.TypeUtils.makeNullableAsSpecified(r1, r0)
            return r9
        L_0x00d4:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Type constructors should be equals!\nsubstitutedSuperType: "
            r1.append(r2)
            java.lang.String r2 = m45078a((kotlin.reflect.jvm.internal.impl.types.TypeConstructor) r10)
            r1.append(r2)
            java.lang.String r2 = ", \n\nsupertype: "
            r1.append(r2)
            java.lang.String r2 = m45078a((kotlin.reflect.jvm.internal.impl.types.TypeConstructor) r9)
            r1.append(r2)
            java.lang.String r2 = " \n"
            r1.append(r2)
            boolean r9 = r11.assertEqualTypeConstructors(r10, r9)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        L_0x0107:
            java.util.Collection r1 = r3.getSupertypes()
            java.util.Iterator r1 = r1.iterator()
        L_0x010f:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0021
            java.lang.Object r3 = r1.next()
            kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r3
            kotlin.reflect.jvm.internal.impl.types.checker.a r4 = new kotlin.reflect.jvm.internal.impl.types.checker.a
            java.lang.String r5 = "immediateSupertype"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
            r4.<init>(r3, r10)
            r0.add(r4)
            goto L_0x010f
        L_0x0129:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.checker.UtilsKt.findCorrespondingSupertype(kotlin.reflect.jvm.internal.impl.types.KotlinType, kotlin.reflect.jvm.internal.impl.types.KotlinType, kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks):kotlin.reflect.jvm.internal.impl.types.KotlinType");
    }

    /* renamed from: a */
    private static final KotlinType m45080a(KotlinType kotlinType) {
        return CapturedTypeApproximationKt.approximateCapturedTypes(kotlinType).getUpper();
    }

    /* renamed from: a */
    private static final String m45078a(TypeConstructor typeConstructor) {
        StringBuilder sb = new StringBuilder();
        m45079a(Intrinsics.stringPlus("type: ", typeConstructor), sb);
        m45079a(Intrinsics.stringPlus("hashCode: ", Integer.valueOf(typeConstructor.hashCode())), sb);
        m45079a(Intrinsics.stringPlus("javaClass: ", typeConstructor.getClass().getCanonicalName()), sb);
        for (DeclarationDescriptor declarationDescriptor = typeConstructor.getDeclarationDescriptor(); declarationDescriptor != null; declarationDescriptor = declarationDescriptor.getContainingDeclaration()) {
            m45079a(Intrinsics.stringPlus("fqName: ", DescriptorRenderer.FQ_NAMES_IN_TYPES.render(declarationDescriptor)), sb);
            m45079a(Intrinsics.stringPlus("javaClass: ", declarationDescriptor.getClass().getCanonicalName()), sb);
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* renamed from: a */
    private static final StringBuilder m45079a(String str, StringBuilder sb) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        sb.append(str);
        Intrinsics.checkNotNullExpressionValue(sb, "append(value)");
        sb.append(10);
        Intrinsics.checkNotNullExpressionValue(sb, "append('\\n')");
        return sb;
    }
}
