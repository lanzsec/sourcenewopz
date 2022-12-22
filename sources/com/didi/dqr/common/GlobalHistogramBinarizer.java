package com.didi.dqr.common;

import com.didi.dqr.Binarizer;
import com.didi.dqr.LuminanceSource;
import com.didi.dqr.NotFoundException;
import com.didi.dqrutil.analysis.AnalysisManager;
import com.didi.dqrutil.analysis.EventId;

public class GlobalHistogramBinarizer extends Binarizer {

    /* renamed from: a */
    private static final int f18539a = 5;

    /* renamed from: b */
    private static final int f18540b = 3;

    /* renamed from: c */
    private static final int f18541c = 32;

    /* renamed from: d */
    private static final byte[] f18542d = new byte[0];

    /* renamed from: e */
    private byte[] f18543e = f18542d;

    /* renamed from: f */
    private final int[] f18544f = new int[32];

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
        AnalysisManager.report(EventId.CREATE_GLOBAL_BINARIZER);
    }

    public BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException {
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        if (bitArray == null || bitArray.getSize() < width) {
            bitArray = new BitArray(width);
        } else {
            bitArray.clear();
        }
        m13727a(width);
        byte[] row = luminanceSource.getRow(i, this.f18543e);
        int[] iArr = this.f18544f;
        for (int i2 = 0; i2 < width; i2++) {
            int i3 = (row[i2] & 255) >> 3;
            iArr[i3] = iArr[i3] + 1;
        }
        int a = m13726a(iArr);
        if (width < 3) {
            for (int i4 = 0; i4 < width; i4++) {
                if ((row[i4] & 255) < a) {
                    bitArray.set(i4);
                }
            }
        } else {
            int i5 = 1;
            byte b = row[1] & 255;
            byte b2 = row[0] & 255;
            byte b3 = b;
            while (i5 < width - 1) {
                int i6 = i5 + 1;
                byte b4 = row[i6] & 255;
                if ((((b3 * 4) - b2) - b4) / 2 < a) {
                    bitArray.set(i5);
                }
                b2 = b3;
                i5 = i6;
                b3 = b4;
            }
        }
        return bitArray;
    }

    public BitArray getRotatedBlackRow(int i, BitArray bitArray) throws NotFoundException {
        LuminanceSource rotateLuminanceSource = getRotateLuminanceSource();
        int width = rotateLuminanceSource.getWidth();
        if (bitArray == null || bitArray.getSize() < width) {
            bitArray = new BitArray(width);
        } else {
            bitArray.clear();
        }
        m13727a(width);
        byte[] row = rotateLuminanceSource.getRow(i, this.f18543e);
        int[] iArr = this.f18544f;
        for (int i2 = 0; i2 < width; i2++) {
            int i3 = (row[i2] & 255) >> 3;
            iArr[i3] = iArr[i3] + 1;
        }
        int a = m13726a(iArr);
        if (width < 3) {
            for (int i4 = 0; i4 < width; i4++) {
                if ((row[i4] & 255) < a) {
                    bitArray.set(i4);
                }
            }
        } else {
            int i5 = 1;
            byte b = row[1] & 255;
            byte b2 = row[0] & 255;
            byte b3 = b;
            while (i5 < width - 1) {
                int i6 = i5 + 1;
                byte b4 = row[i6] & 255;
                if ((((b3 * 4) - b2) - b4) / 2 < a) {
                    bitArray.set(i5);
                }
                b2 = b3;
                i5 = i6;
                b3 = b4;
            }
        }
        return bitArray;
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        BitMatrix bitMatrix = new BitMatrix(width, height);
        m13727a(width);
        int[] iArr = this.f18544f;
        for (int i = 1; i < 5; i++) {
            byte[] row = luminanceSource.getRow((height * i) / 5, this.f18543e);
            int i2 = (width * 4) / 5;
            for (int i3 = width / 5; i3 < i2; i3++) {
                int i4 = (row[i3] & 255) >> 3;
                iArr[i4] = iArr[i4] + 1;
            }
        }
        int a = m13726a(iArr);
        byte[] matrix = luminanceSource.getMatrix();
        for (int i5 = 0; i5 < height; i5++) {
            int i6 = i5 * width;
            for (int i7 = 0; i7 < width; i7++) {
                if ((matrix[i6 + i7] & 255) < a) {
                    bitMatrix.set(i7, i5);
                }
            }
        }
        bitMatrix.setSource(luminanceSource.getMatrix());
        return bitMatrix;
    }

    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    /* renamed from: a */
    private void m13727a(int i) {
        if (this.f18543e.length < i) {
            this.f18543e = new byte[i];
        }
        for (int i2 = 0; i2 < 32; i2++) {
            this.f18544f[i2] = 0;
        }
    }

    /* renamed from: a */
    private static int m13726a(int[] iArr) throws NotFoundException {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (iArr[i4] > i) {
                i = iArr[i4];
                i3 = i4;
            }
            if (iArr[i4] > i2) {
                i2 = iArr[i4];
            }
        }
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < length; i7++) {
            int i8 = i7 - i3;
            int i9 = iArr[i7] * i8 * i8;
            if (i9 > i6) {
                i5 = i7;
                i6 = i9;
            }
        }
        if (i3 <= i5) {
            int i10 = i3;
            i3 = i5;
            i5 = i10;
        }
        if (i3 - i5 > length / 16) {
            int i11 = i3 - 1;
            int i12 = i11;
            int i13 = -1;
            while (i11 > i5) {
                int i14 = i11 - i5;
                int i15 = i14 * i14 * (i3 - i11) * (i2 - iArr[i11]);
                if (i15 > i13) {
                    i12 = i11;
                    i13 = i15;
                }
                i11--;
            }
            return i12 << 3;
        }
        AnalysisManager.report(EventId.GLOBAL_BINARIZER_FAILED);
        throw NotFoundException.getNotFoundInstance();
    }
}