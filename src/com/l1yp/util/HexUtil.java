package com.l1yp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class HexUtil {
    private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final byte[] emptybytes = new byte[0];

    public static String bin2hex(byte[] src){
        if(src == null || src.length == 0) return "";
        char[] chs = new char[src.length * 2];
        int i = 0;
        for (int j = 0; j < src.length; j++){
            chs[i++] = digits[(src[j] >> 4) & 15];
            chs[i++] = digits[src[j] & 15];
        }
//        for (byte b : src) {
//            chs[i++] = digits[(b>>4) & 0xF];
//            chs[i++] = digits[b & 0xF];
//        }

        return new String(chs);
    }

    public static String bin2hex(byte[] src, int pos, int len){
        if(src == null || src.length == 0) return "";
        char[] chs = new char[len * 2];
        int i = 0;
        for (int j = 0; j < len; j++){
            chs[i++] = digits[(src[pos + j] >> 4) & 15];
            chs[i++] = digits[src[pos + j] & 15];
        }
        return new String(chs);
    }

    public static String bin2hex(ByteBuffer buffer){
        if(buffer == null || buffer.remaining() == 0) return "";
        return bin2hex(buffer.array(), buffer.position(), buffer.remaining());
    }

    public static byte[] hex2bin(String src){
        if (src == null || src.length() == 0) return new byte[0];
        char[] chs = src.toCharArray();
        byte[] bys = new byte[chs.length / 2];
        int j = 0;
        for(int i = 0; i < chs.length; i += 2){
            bys[j++] = (byte) (char2Byte(chs[i]) << 4 | char2Byte(chs[i + 1]));

        }
        return bys;
    }

    public static String byte2HexStr(byte b) {
        char[] buf = new char[2];
        buf[1] = digits[b & 15];
        buf[0] = digits[((byte) (b >>> 4)) & 15];
        return new String(buf);
    }

    public static String bytes2HexStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        char[] buf = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf[(i * 2) + 1] = digits[b & 15];
            buf[(i * 2) + 0] = digits[((byte) (b >>> 4)) & 15];
        }
        return new String(buf);
    }

    public static byte hexStr2Byte(String str) {
        if (str == null || str.length() != 1) {
            return (byte) 0;
        }
        return char2Byte(str.charAt(0));
    }

    public static byte char2Byte(char ch) {
        if (ch >= '0' && ch <= '9') {
            return (byte) (ch - 48);
        }
        if (ch >= 'a' && ch <= 'f') {
            return (byte) ((ch - 97) + 10);
        }
        if (ch < 'A' || ch > 'F') {
            return (byte) 0;
        }
        return (byte) ((ch - 65) + 10);
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str == null || str.equals("")) {
            return emptybytes;
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((char2Byte(str.charAt(i * 2)) * 16) + char2Byte(str.charAt((i * 2) + 1)));
        }
        return bytes;
    }

    public static byte[] compress(byte[] src) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        DeflaterOutputStream gZIPOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(src);
        gZIPOutputStream.finish();
        gZIPOutputStream.close();
        byte[] dst = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return dst;
    }

    public static byte[] uncompress(byte[] src) {
        if (src == null || src.length == 0){
            return new byte[0];
        }
        return uncompress(src, 0, src.length);
    }


    public static byte[] uncompress(byte[] src, int pos, int len) {
        if (src == null || src.length == 0){
            return new byte[0];
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(src, pos, len);
        InflaterInputStream ifis = new InflaterInputStream(bis);
        byte[] dst = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (true) {
            try {
                int read = ifis.read(dst, 0, dst.length);
                if (read != -1) {
                    bos.write(dst, 0, read);
                } else {
                    dst = bos.toByteArray();
                    bos.flush();
                    bos.close();
                    ifis.close();
                    bis.close();
                    return dst;
                }
            }catch (IOException e){
                return new byte[0];
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(bytes2HexStr("Hello WebSocket World?".getBytes("gbk")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
