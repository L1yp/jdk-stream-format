package com.l1yp.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 字符集工具类
 *
 * @Author Lyp
 * @Date   2019/12/5
 * @Email  l1yp@qq.com
 */
public class CharacterUtil {

    public static void main(String[] args) throws Exception{
        // byte[] bytes = new byte[]{-84, -19, 0, 5, 116, 0, 21, 49, 53, 51, 49, 54, 51, 50, 49, 54, 53, 53, 50, 48, 57, 51, 88, 76, 70, 102, 82, 69};
        // byte[] bytes = HexUtil.hex2bin("ACED000573720039636F6D2E6F6E6577617665696E632E74766965772E6D616E6167656D656E742E656E746974792E4465766963654F7065726174696F6E4C6F67C3DCA2BF8A8FB5360200064A00026964490006737461747573490004747970654C00036465737400124C6A6176612F6C616E672F537472696E673B4C0005737462496471007E00014C000474696D657400104C6A6176612F7574696C2F446174653B787000000000496BBA6B000000080000000574004666616C73657C6E6F207374625B30303430303146463030323032323930303030313030323237653062353062385D20756470206865617274206265617420726563656976656474002030303430303146463030323032323930303030313030323237653062353062387372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000172E460E5B678");
        byte[] bytes = new byte[]{-84, -19, 0, 5, 115, 114, 0, 57, 99, 111, 109, 46, 111, 110, 101, 119, 97, 118, 101, 105, 110, 99, 46, 116, 118, 105, 101, 119, 46, 109, 97, 110, 97, 103, 101, 109, 101, 110, 116, 46, 101, 110, 116, 105, 116, 121, 46, 68, 101, 118, 105, 99, 101, 79, 112, 101, 114, 97, 116, 105, 111, 110, 76, 111, 103, -61, -36, -94, -65, -118, -113, -75, 54, 2, 0, 6, 74, 0, 2, 105, 100, 73, 0, 6, 115, 116, 97, 116, 117, 115, 73, 0, 4, 116, 121, 112, 101, 76, 0, 3, 100, 101, 115, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 0, 5, 115, 116, 98, 73, 100, 113, 0, 126, 0, 1, 76, 0, 4, 116, 105, 109, 101, 116, 0, 16, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 68, 97, 116, 101, 59, 120, 112, 0, 0, 0, 0, 73, 107, -70, 81, 0, 0, 0, 0, 0, 0, 0, 2, 112, 116, 0, 32, 48, 48, 51, 54, 48, 51, 70, 70, 48, 48, 48, 50, 50, 56, 48, 48, 49, 55, 51, 56, 70, 52, 52, 67, 55, 48, 56, 66, 51, 50, 51, 66, 115, 114, 0, 14, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 68, 97, 116, 101, 104, 106, -127, 1, 75, 89, 116, 25, 3, 0, 0, 120, 112, 119, 8, 0, 0, 1, 114, -38, -45, -36, -97, 120};
        String encStr = encode(bytes);
        String redisCliRaw = "\\xac\\xed\\x00\\x05sr\\x009com.onewaveinc.tview.management.entity.DeviceOperationLog\\xc3\\xdc\\xa2\\xbf\\x8a\\x8f\\xb56\\x02\\x00\\x06J\\x00\\x02idI\\x00\\x06statusI\\x00\\x04typeL\\x00\\x03dest\\x00\\x12Ljava/lang/String;L\\x00\\x05stbIdq\\x00~\\x00\\x01L\\x00\\x04timet\\x00\\x10Ljava/util/Date;xp\\x00\\x00\\x00\\x00Ik\\xbaQ\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x02pt\\x00 003603FF000228001738F44C708B323Bsr\\x00\\x0ejava.util.Datehj\\x81\\x01KYt\\x19\\x03\\x00\\x00xpw\\b\\x00\\x00\\x01r\\xda\\xd3\\xdc\\x9fx";
        System.out.println(encStr);
        System.out.println(redisCliRaw);
        System.out.println(encStr.equals(redisCliRaw));
        System.out.println(Arrays.toString(decode(encStr)));
        System.out.println(Arrays.equals(bytes, decode(encStr)));
        System.exit(0);
    }

    private static final byte[] PREFIX = "\\x".getBytes();

    // Hex 字符 0123456789ABCDEF
    private static final byte[] digits = new byte[] {
            48, 49, 50, 51, 52,//
            53, 54, 55, 56, 57,//
            97, 98, 99, 100, 101,//
            102
    };

    public static String encode(byte[] src){
        return encode(src, 0, src.length);
    }

    public static String encode(byte[] src, int off, int len){
        Encoder encoder = EncodeFactory.getEncoder(src, UTF_8);
        return encoder.encode(src, off, len);
    }

    public static String encode(byte[] src, Charset charset){
        return encode(src, 0, src.length, charset);
    }

    public static String encode(byte[] src, int off, int len, Charset charset){
        Encoder encoder = EncodeFactory.getEncoder(src, off, len, charset);
        return encoder.encode(src, off, len);
    }

    public static boolean isPrintable(byte[] src){
        return isPrintable(src, 0, src.length, UTF_8);
    }

    public static boolean isPrintable(byte[] src, Charset charset){
        return isPrintable(src, 0, src.length, charset);
    }

    public static boolean isPrintable(byte[] src, int off, int len, Charset charset){
        Encoder encoder = EncodeFactory.getEncoder(src, off, len, charset);
        return encoder.isPrintable(src, off, len);
    }

    /**
     * 反转移\xxx 字节
     * @param src 转义后的字符串
     * @return 源字节数组
     */
    public static byte[] decode(String src){
        byte[] bytes = src.getBytes(UTF_8);
        ByteBuffer bf = ByteBuffer.allocate(bytes.length); // 分配一个缓冲区 字符串的字节数组长度 永远大于 反转义后的字节数组

        int i = 0;
        while (i < bytes.length){
            if (bytes[i] == 92){ // '\'
                if (i + 1 < bytes.length && bytes[i + 1] == 120){ // 'x'
                    if (i + 2 < bytes.length && CharUtil.isHexChar(bytes[i + 2])){
                        if (i + 3 < bytes.length && CharUtil.isHexChar(bytes[i + 3])){
                            // 例： \xAC 转为 (byte) -84
                            bf.put(CharUtil.mergeToByte(bytes[i + 2], bytes[i + 3]));
                            i += 4;
                            continue;
                        }
                    }
                }
            }
            bf.put(bytes[i++]);
        }
        byte[] dst = new byte[bf.position()];
        bf.flip();
        bf.get(dst);
        return dst;
    }

    private static class EncodeFactory {
        public static Encoder getEncoder(byte[] src, Charset charset){
            if (charset == null){
                if (src != null && src.length > 2 && src[0] == -84 && src[1] == -19){
                    return new JDKSerializableEncoder();
                }
            }else if (charset.equals(UTF_8)){
                return new UTF8Encoder();
            }else if (charset.equals(StandardCharsets.US_ASCII)){
                return new AsciiEncoder();
            }
            return new AsciiEncoder();
        }

        public static Encoder getEncoder(byte[] src, int off, int len, Charset charset){
            if (charset == null){
                if (src != null && len > 2 && src[off] == -84 && src[off + 1] == -19){
                    return new JDKSerializableEncoder();
                }
            }else if (charset.equals(UTF_8)){
                return new UTF8Encoder();
            }else if (charset.equals(StandardCharsets.US_ASCII)){
                return new AsciiEncoder();
            }
            return new AsciiEncoder();
        }

    }

    private interface Encoder{
        String encode(byte[] src);
        String encode(byte[] src, int off, int len);

        boolean isPrintable(byte[] src);
        boolean isPrintable(byte[] src, int off, int len);
    }

    private static class JDKSerializableEncoder implements Encoder {

        @Override
        public String encode(byte[] src) {
            return encode(src, 0, src.length);
        }

        @Override
        public String encode(byte[] src, int off, int len) {
            ByteBuffer bf = ByteBuffer.allocate(src.length * 4);
            byte b;

            for (int i = off; i < off + len; i++) {
                b = src[i];
                bf.put(PREFIX);
                bf.put(digits[(b >>> 4) & 0xF]);
                bf.put(digits[(b >>> 0) & 0xF]);
            }
            byte[] bytes = new byte[bf.position()];
            bf.flip();
            bf.get(bytes);
            return new String(bytes);
        }

        @Override
        public boolean isPrintable(byte[] src) {
            return false;
        }

        @Override
        public boolean isPrintable(byte[] src, int off, int len) {
            return false;
        }
    }

    private static class AsciiEncoder implements Encoder {

        @Override
        public String encode(byte[] src) {
            return encode(src, 0, src.length);
        }

        @Override
        public String encode(byte[] src, int off, int len) {
            ByteBuffer bf = ByteBuffer.allocate(src.length * 4);
            byte b;

            for (int i = off; i < off + len; i++) {
                b = src[i];
                if (b > 0 && CharUtil.isPrintable(b)) {
                    bf.put(b);
                }else {
                    bf.put(PREFIX);
                    bf.put(digits[(b >>> 4) & 0xF]);
                    bf.put(digits[(b >>> 0) & 0xF]);
                }
            }
            byte[] bytes = new byte[bf.position()];
            bf.flip();
            bf.get(bytes);
            return new String(bytes);
        }

        @Override
        public boolean isPrintable(byte[] src) {
            return isPrintable(src, 0, src.length);
        }

        @Override
        public boolean isPrintable(byte[] src, int off, int len) {
            for (int i = off; i < off + len; i++){
                if (!CharUtil.isPrintable(src[i])) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class UTF8Encoder implements Encoder {

        @Override
        public String encode(byte[] src) {
            return encode(src, 0, src.length);
        }

        @Override
        public String encode(byte[] src, int off, int len) {
            /* 最坏情况下，每一个字节都是不可打印，每个字节都要转换成‘\xxx’ 也就是从1变成4字节 */
            ByteBuffer bf = ByteBuffer.allocate(src.length * 4);
            byte b;
            int i = off;
            int limit = off + len;
            while (i < limit){
                b = src[i];
                if ((b & 128) == 0){
                    // 0xxx xxxx为单字节字符 ASCII
                    if (CharUtil.isPrintable(b)){
                        bf.put(b);
                    }else {
                        bf.put(PREFIX);
                        bf.put(digits[(b >>> 4) & 0xF]);
                        bf.put(digits[(b >>> 0) & 0xF]);
                    }
                    i++;
                }else if ((b & 192) == 192 && (b & 32) == 0){
                    // 110x xxxx 为双字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        bf.put(src[i + 0]);
                        bf.put(src[i + 1]);
                        i += 2;
                    }else {
                        // 字节序列不符合UTF8编码 当即截断 当不符合编码的字节用\xxx表示 以下同
                        bf.put(PREFIX); // \x
                        bf.put(digits[(b >>> 4) & 0xF]);
                        bf.put(digits[(b >>> 0) & 0xF]);
                        i ++;
                    }
                }else if ((b & 224) == 224 && (b & 16) == 0){
                    // 1110 xxxx 为3字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        if (i + 2 < limit && CharUtil.isUTF8AfterByte(src[i + 2])){
                            bf.put(src[i + 0]);
                            bf.put(src[i + 1]);
                            bf.put(src[i + 2]);
                            i += 3;
                        }else {
                            bf.put(PREFIX);
                            bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                            bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                            bf.put(PREFIX);
                            bf.put(digits[(src[i + 1] >>> 4) & 0xF]);
                            bf.put(digits[(src[i + 1] >>> 0) & 0xF]);
                            i += 2;
                        }
                    }else {
                        bf.put(PREFIX);
                        bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                        bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                        i++;
                    }
                }else if ((b & 240) == 240 && (b & 8) == 0){
                    // 1111 0xxx 为4字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        if (i + 2 < limit && CharUtil.isUTF8AfterByte(src[i + 2])){
                            if (i + 3 < limit && CharUtil.isUTF8AfterByte(src[i + 3])){
                                bf.put(src[i + 0]);
                                bf.put(src[i + 1]);
                                bf.put(src[i + 2]);
                                bf.put(src[i + 3]);
                                i += 4;
                            }else {
                                bf.put(PREFIX);
                                bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                                bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                                bf.put(PREFIX);
                                bf.put(digits[(src[i + 1] >>> 4) & 0xF]);
                                bf.put(digits[(src[i + 1] >>> 0) & 0xF]);
                                bf.put(PREFIX);
                                bf.put(digits[(src[i + 2] >>> 4) & 0xF]);
                                bf.put(digits[(src[i + 2] >>> 0) & 0xF]);
                                i += 3;
                            }

                        }else {
                            bf.put(PREFIX);
                            bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                            bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                            bf.put(PREFIX);
                            bf.put(digits[(src[i + 1] >>> 4) & 0xF]);
                            bf.put(digits[(src[i + 1] >>> 0) & 0xF]);
                            i += 2;
                        }
                    }else {
                        bf.put(PREFIX);
                        bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                        bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                        i++;
                    }
                }else {
                    bf.put(PREFIX);
                    bf.put(digits[(src[i + 0] >>> 4) & 0xF]);
                    bf.put(digits[(src[i + 0] >>> 0) & 0xF]);
                    i++;
                }
            }
            byte[] bytes = new byte[bf.position()];
            bf.flip();
            bf.get(bytes);
            return new String(bytes);
        }

        @Override
        public boolean isPrintable(byte[] src) {
            return isPrintable(src, 0, src.length);
        }

        @Override
        public boolean isPrintable(byte[] src, int off, int len) {
            byte b;
            int i = off;
            int limit = off + len;
            while (i < limit){
                b = src[i];
                if ((b & 128) == 0){
                    // 0xxx xxxx为单字节字符 ASCII
                    if (!CharUtil.isPrintable(b)){
                        return false;
                    }
                    i++;
                }else if ((b & 192) == 192 && (b & 32) == 0){
                    // 110x xxxx 为双字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        i += 2;
                    }else {
                        // 字节序列不符合UTF8编码 当即截断 当不符合编码的字节用\xxx表示 以下同
                        return false;
                    }
                }else if ((b & 224) == 224 && (b & 16) == 0){
                    // 1110 xxxx 为3字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        if (i + 2 < limit && CharUtil.isUTF8AfterByte(src[i + 2])){
                            i += 3;
                        }else {
                            return false;
                        }
                    }else {
                        return false;
                    }
                }else if ((b & 240) == 240 && (b & 8) == 0){
                    // 1111 0xxx 为4字节字符
                    if (i + 1 < limit && CharUtil.isUTF8AfterByte(src[i + 1])){
                        if (i + 2 < limit && CharUtil.isUTF8AfterByte(src[i + 2])){
                            if (i + 3 < limit && CharUtil.isUTF8AfterByte(src[i + 3])){
                                i += 4;
                            }else {
                                return false;
                            }

                        }else {
                            return false;
                        }
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
            }
            return true;
        }
    }

    private static class CharUtil {

        /**
         * 目标字节是否可打印的ASCII字符
         * @param b 目标字节
         * @return
         */
        public static boolean isPrintable(byte b){
            return !unprintable.contains(b);
        }

        /**
         * 是否UTF8的跟随后字节 二进制10xx xxxx 匹配二进制是否以10开头
         * @param b 目标字节
         * @return
         */
        public static boolean isUTF8AfterByte(byte b){
            return (b & 128) == 128 && (b & 64) == 0;
        }

        /**
         * 该字节是否是Hex字符 也就是 [0-9A-Fa-f]正则
         * @param b 目标字节
         * @return
         */
        public static boolean isHexChar(byte b){
            return (b > 47 && b < 58) || (b > 64 && b < 71) || (b > 96 && b < 103);
        }

        /**
         * 将两个Hex字符还原为字节，例：'A'(65),'C'(67) => (byte)-84,其中A为高4位,C为低4位
         * @param hi 高4位
         * @param lo 低4位
         * @return 返回合并后的字节
         */
        public static byte mergeToByte(byte hi, byte lo){
            byte result = 0;
            //
            if (hi >= 48 && hi <= 57){
                result = (byte) ((hi - 48) << 4);
            }else if (hi >= 65 && hi <= 70){
                result = (byte)(((hi - 65) + 10) << 4);
            }else if (hi >= 97 && hi <= 102){
                result = (byte)(((hi - 97) + 10) << 4);
            }else {
                result = 0;
            }
            if (lo >= 48 && lo <= 57){
                result |= (byte) (lo - 48);
            }else if (lo >= 65 && lo <= 70){
                result |= (byte)((lo - 65) + 10);
            }else if (lo >= 97 && lo <= 102){
                result |= (byte)((lo - 97) + 10);
            }else {
                result |= 0;
            }
            return result;

        }

        /* 不可打印的ASCII字符集合 */
        private static HashSet<Byte> unprintable = new HashSet<Byte>(64);

        static {
            unprintable.add((byte) 0);
            unprintable.add((byte) 1);
            unprintable.add((byte) 2);
            unprintable.add((byte) 3);
            unprintable.add((byte) 4);
            unprintable.add((byte) 5);
            unprintable.add((byte) 6);
            unprintable.add((byte) 7);
            unprintable.add((byte) 8);
            unprintable.add((byte) 11);
            unprintable.add((byte) 12);
            unprintable.add((byte) 14);
            unprintable.add((byte) 15);
            unprintable.add((byte) 16);
            unprintable.add((byte) 17);
            unprintable.add((byte) 18);
            unprintable.add((byte) 19);
            unprintable.add((byte) 20);
            unprintable.add((byte) 21);
            unprintable.add((byte) 22);
            unprintable.add((byte) 23);
            unprintable.add((byte) 24);
            unprintable.add((byte) 25);
            unprintable.add((byte) 26);
            unprintable.add((byte) 27);
            unprintable.add((byte) 28);
            unprintable.add((byte) 29);
            unprintable.add((byte) 30);
            unprintable.add((byte) 31);
        }
    }

}