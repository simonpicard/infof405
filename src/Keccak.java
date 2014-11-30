/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2014 Osman KOCAK <kocakosm@gmail.com>                   *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/
import java.util.Arrays;

/**
 * The Keccak digest algorithm. Instances of this class are not thread safe.
 * Note: this implementation is focused on readability instead of performance,
 * so it should not perform as well as other implementations.
 *
 * @author Osman KOCAK
 */
final class Keccak
{
    private static final long[] RC = new long[] {
        0x0000000000000001L, 0x0000000000008082L, 0x800000000000808aL,
        0x8000000080008000L, 0x000000000000808bL, 0x0000000080000001L,
        0x8000000080008081L, 0x8000000000008009L, 0x000000000000008aL,
        0x0000000000000088L, 0x0000000080008009L, 0x000000008000000aL,
        0x000000008000808bL, 0x800000000000008bL, 0x8000000000008089L,
        0x8000000000008003L, 0x8000000000008002L, 0x8000000000000080L,
        0x000000000000800aL, 0x800000008000000aL, 0x8000000080008081L,
        0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L
    };
    private static final int[] R = new int[] {
        0, 1, 62, 28, 27, 36, 44, 6, 55, 20, 3, 10, 43,
        25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14
    };

    private final long[] A;
    private final int blockLen;
    private final byte[] buffer;
	private final int length;
    private int bufferLen;

    /**
     * Creates a new ready to use {@code Keccak}.
     *
     * @param length the digest length (in bytes, 64 for a 512-bit hash).
     */
    Keccak(int length)
    {
        this.A = new long[25];
        this.blockLen = 200 - 2 * length;
        this.buffer = new byte[blockLen];
		this.length = length;
        this.bufferLen = 0;
    }

    public void reset()
    {
        for (int i = 0; i < 25; i++) {
            A[i] = 0L;
        }
        bufferLen = 0;
    }

    public void update(byte input)
    {
        buffer[bufferLen] = input;
        if (++bufferLen == blockLen) {
            processBuffer();
        }
    }

    public void update(byte[] input, int off, int len)
    {
        while (len > 0) {
            int cpLen = Math.min(blockLen - bufferLen, len);
            System.arraycopy(input, off, buffer, bufferLen, cpLen);
            bufferLen += cpLen;
            off += cpLen;
            len -= cpLen;
            if (bufferLen == blockLen) {
                processBuffer();
            }
        }
    }

    public byte[] digest()
    {
        addPadding();
        processBuffer();
        byte[] tmp = new byte[length * 8];
        for (int i = 0; i < length; i += 8) {
            LittleEndian.encode(A[i >>> 3], tmp, i);
        }
        reset();
        return Arrays.copyOf(tmp, length);
    }

    private void addPadding()
    {
        if (bufferLen + 1 == buffer.length) {
            buffer[bufferLen] = (byte) 0x81;
        } else {
            buffer[bufferLen] = (byte) 0x01;
            for (int i = bufferLen + 1; i < buffer.length - 1; i++) {
                buffer[i] = 0;
            }
            buffer[buffer.length - 1] = (byte) 0x80;
        }
    }

    private void processBuffer()
    {
        for (int i = 0; i < buffer.length; i += 8) {
            A[i >>> 3] ^= LittleEndian.decodeLong(buffer, i);
        }
        keccakf();
        bufferLen = 0;
    }

    private void keccakf()
    {
        long[] B = new long[25];
        long[] C = new long[5];
        long[] D = new long[5];
        for (int n = 0; n < 24; n++) {
            for (int x = 0; x < 5; x++) {
                C[x] = A[index(x, 0)] ^ A[index(x, 1)] ^ A[index(x, 2)] ^ A[index(x, 3)] ^ A[index(x, 4)];
            }
            for (int x = 0; x < 5; x++) {
                D[x] = C[index(x - 1)] ^ Bits.rotateLeft(C[index(x + 1)], 1);
                for (int y = 0; y < 5; y++) {
                    A[index(x, y)] ^= D[x];
                }
            }
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    int i = index(x, y);
                    B[index(y, x * 2 + 3 * y)] = Bits.rotateLeft(A[i], R[i]);
                }
            }
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    int i = index(x, y);
                    A[i] = B[i] ^ (~B[index(x + 1, y)] & B[index(x + 2, y)]);
                }
            }
            A[0] ^= RC[n];
        }
    }

    private int index(int x)
    {
        return x < 0 ? index(x + 5) : x % 5;
    }

    private int index(int x, int y)
    {
        return index(x) + 5 * index(y);
    }
}

/**
 * Bitwise operations.
 *
 * @author Osman KOCAK
 */
final class Bits
{
    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code int} value left by the
     * specified number of bits. Note that left rotation with a negative
     * distance is equivalent to right rotation. Note also that rotation by
     * any multiple of 32 is a no-op.
     *
     * @param x the number to rotate.
     * @param n the rotation distance.
     *
     * @return the value obtained by rotating the two's complement binary
     *  representation of the specified {@code int} value left by the
     *  specified number of bits.
     */
    public static int rotateLeft(int x, int n)
    {
        return (x << n) | (x >>> (32 - n));
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value left by the
     * specified number of bits. Note that left rotation with a negative
     * distance is equivalent to right rotation. Note also that rotation by
     * any multiple of 64 is a no-op.
     *
     * @param x the number to rotate.
     * @param n the rotation distance.
     *
     * @return the value obtained by rotating the two's complement binary
     *  representation of the specified {@code long} value left by the
     *  specified number of bits.
     */
    public static long rotateLeft(long x, int n)
    {
        return (x << n) | (x >>> (64 - n));
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code int} value right by the
     * specified number of bits. Note that right rotation with a negative
     * distance is equivalent to left rotation. Note also that rotation by
     * any multiple of 64 is a no-op.
     *
     * @param x the number to rotate.
     * @param n the rotation distance.
     *
     * @return the value obtained by rotating the two's complement binary
     *  representation of the specified {@code int} value right by the
     *  specified number of bits.
     */
    public static int rotateRight(int x, int n)
    {
        return (x >>> n) | (x << (32 - n));
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value right by the
     * specified number of bits. Note that right rotation with a negative
     * distance is equivalent to left rotation. Note also that rotation by
     * any multiple of 64 is a no-op.
     *
     * @param x the number to rotate.
     * @param n the rotation distance.
     *
     * @return the value obtained by rotating the two's complement binary
     *  representation of the specified {@code long} value right by the
     *  specified number of bits.
     */
    public static long rotateRight(long x, int n)
    {
        return (x >>> n) | (x << (64 - n));
    }
}

/**
* Little-endian encoding and decoding.
*
* @author Osman KOCAK
*/
final class LittleEndian
{
    /**
    * Encodes the given {@code long} value using little-endian byte
    * ordering convention into the given array, starting at the given
    * offset.
    *
    * @param n the {@code long} value to encode.
    * @param out the output buffer.
    * @param off the output offset.
    *
    * @throws NullPointerException if {@code out} is {@code null}.
    * @throws IndexOutOfBoundsException if {@code off} is negative or if
    * {@code out}'s length is lower than {@code off + 8}.
    */
    public static void encode(long n, byte[] out, int off)
    {
        out[off] = (byte) n;
        out[off + 1] = (byte) (n >>> 8);
        out[off + 2] = (byte) (n >>> 16);
        out[off + 3] = (byte) (n >>> 24);
        out[off + 4] = (byte) (n >>> 32);
        out[off + 5] = (byte) (n >>> 40);
        out[off + 6] = (byte) (n >>> 48);
        out[off + 7] = (byte) (n >>> 56);
    }
    /**
    * Decodes the first 8 bytes starting at {@code off} of the given array
    * into a {@code long} value using little-endian byte ordering
    * convention.
    *
    * @param in the encoded value.
    * @param off the input offset.
    *
    * @return the decoded {@code long} value.
    *
    * @throws NullPointerException if {@code in} is {@code null}.
    * @throws IndexOutOfBoundsException if {@code off} is negative or if
    * {@code in}'s length is lower than {@code off + 8}.
    */
    public static long decodeLong(byte[] in, int off)
    {
        return (long) (in[off] & 0xFF)
        | ((long) (in[off + 1] & 0xFF) << 8)
        | ((long) (in[off + 2] & 0xFF) << 16)
        | ((long) (in[off + 3] & 0xFF) << 24)
        | ((long) (in[off + 4] & 0xFF) << 32)
        | ((long) (in[off + 5] & 0xFF) << 40)
        | ((long) (in[off + 6] & 0xFF) << 48)
        | ((long) (in[off + 7] & 0xFF) << 56);
    }
}