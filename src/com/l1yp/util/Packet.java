package com.l1yp.util;

public class Packet {

    private byte[] buf;
    private int writerPos = 0;
    private int readerPos = 0;
    private int limit = 0;

    public Packet(){
        this(1024);
    }

    public Packet(byte[] desc, int offset, int len){
        buf = desc;
        readerPos = offset;
        writerPos = offset;
        limit = offset + len;
    }

    public Packet(int capacity){
        if(capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        buf = new byte[capacity];
        readerPos = 0;
        writerPos = 0;
        limit = capacity;
    }

    public Packet(byte[] desc){
        this(desc, 0, desc.length);
    }



    public Packet(byte[] desc, boolean isCopy){
        if (isCopy){
            if(desc.length <= 0) throw new IllegalArgumentException("desc.length must be > 0");
            buf = new byte[desc.length];
            System.arraycopy(desc, 0, buf, 0, desc.length);
        }else{
            buf = desc;
        }
        readerPos = 0;
        writerPos = 0;
        limit = desc.length;
    }

    public void empty(){
        writerPos = 0;
    }

    public void setByte(int val){
        checkWriteBound(1);
        buf[writerPos] = (byte)val;
        writerPos++;
    }

    public void setShort(int val){
        checkWriteBound(2);
        buf[writerPos + 1] = (byte) (val >> 0);
        buf[writerPos + 0] = (byte) (val >> 8);
        writerPos += 2;
    }

    public void setToken(byte[] bytes){
        if (bytes == null) return;
        checkWriteBound(2 + bytes.length);
        setShort(bytes.length);
        setBytes(bytes);
    }

    public void setInt(long val){
        checkWriteBound(4);
        buf[writerPos + 3] = (byte) (val >> 0);
        buf[writerPos + 2] = (byte) (val >> 8);
        buf[writerPos + 1] = (byte) (val >> 16);
        buf[writerPos + 0] = (byte) (val >> 24);
        writerPos += 4;
    }

    public void setLong(long val){
        checkWriteBound(8);
        buf[writerPos + 7] = (byte) ((int) (val >> 0));
        buf[writerPos + 6] = (byte) ((int) (val >> 8));
        buf[writerPos + 5] = (byte) ((int) (val >> 16));
        buf[writerPos + 4] = (byte) ((int) (val >> 24));
        buf[writerPos + 3] = (byte) ((int) (val >> 32));
        buf[writerPos + 2] = (byte) ((int) (val >> 40));
        buf[writerPos + 1] = (byte) ((int) (val >> 48));
        buf[writerPos + 0] = (byte) ((int) (val >> 56));
        writerPos += 8;
    }

    public void setBytes(byte[] src){
        if (src == null || src.length == 0) return;
        checkWriteBound(src.length);
        System.arraycopy(src, 0, buf, writerPos, src.length);
        writerPos += src.length;
    }

    private void checkWriteBound(int len){
        if (writerPos + len > buf.length){
            int newLen = (writerPos + len) * 2;
            byte[] buffer = new byte[newLen];
            System.arraycopy(buf, 0, buffer, 0, writerPos);
            buf = buffer;
        }
    }

    private void checkReadBound(int len) {
        if (len + readerPos > limit){
            throw new IllegalCallerException(
                    String.format("current readerPos: %d, limit: %d, require len: %d",
                            readerPos, limit, len));
        }
    }

    public int getWriterPos() {
        return writerPos;
    }

    public void setWriterPos(int writerPos){
        this.writerPos = writerPos;
    }

    public byte[] array(){
        if(writerPos == buf.length) return buf;
        byte[] tmp = new byte[writerPos];
        System.arraycopy(buf, 0, tmp, 0, writerPos);
        return tmp;
    }

    // read

    public byte peek(){
        checkReadBound(1);
        byte b = buf[readerPos];
        return b;
    }

    public byte read(){
        checkReadBound(1);
        byte b = buf[readerPos];
        readerPos++;
        return b;
    }

    public short readShort(){
        checkReadBound(2);
        byte b1 = buf[readerPos];
        byte b2 = buf[readerPos + 1];
        short s = (short) (((b1 << 8) & 65280) + ((b2) & 255));
        readerPos += 2;
        return s;
    }

    public char readChar(){
        checkReadBound(2);
        byte b1 = buf[readerPos];
        byte b2 = buf[readerPos + 1];
        char s = (char) (((b1 << 8) & 65280) + ((b2) & 255));
        readerPos += 2;
        return s;
    }

    private static final int MAX_SHORT_VALUE = 65535;

    public int readUShort(){
        checkReadBound(2);
        int i = (((buf[readerPos] << 8) & 65280) + (buf[readerPos + 1] & 255));
        readerPos += 2;
        return i & MAX_SHORT_VALUE;
    }

    public String readUTF(){
        checkReadBound(2);
        int size = readUShort();
        if (size == 0){
            return "";
        }
        checkReadBound(size);
        String result = new String(buf, readerPos, size);
        readerPos += size;
        return result;
    }

    public String readLongUTF(){
        checkReadBound(4);
        int size = readInt();
        if (size == 0){
            return "";
        }
        checkReadBound(size);
        String result = new String(buf, readerPos, size);
        readerPos += size;
        return result;
    }

    public int readInt(){
        checkReadBound(4);
        int i = ((((buf[readerPos] << 24) & -16777216)
                + ((buf[readerPos + 1] << 16) & 16711680))
                + ((buf[readerPos + 2] << 8) & 65280))
                + ((buf[readerPos + 3] << 0) & 255);

        readerPos += 4;
        return i;
    }

    private static final long MAX_INT_VALUE = 4294967295L;

    public long readUInt(){
        checkReadBound(4);
        int i = ((((buf[readerPos] << 24) & -16777216)
                + ((buf[readerPos + 1] << 16) & 16711680))
                + ((buf[readerPos + 2] << 8) & 65280))
                + ((buf[readerPos + 3] << 0) & 255);
        readerPos += 4;
        return i & MAX_INT_VALUE;
    }

    public long readLong(){
        checkReadBound(8);
        long l = (((((((((((long) buf[readerPos]) << 56) & -72057594037927936L))
                + ((((long) buf[readerPos + 1]) << 48) & 71776119061217280L))
                + ((((long) buf[readerPos + 2]) << 40) & 280375465082880L))
                + ((((long) buf[readerPos + 3]) << 32) & 1095216660480L))
                + ((((long) buf[readerPos + 4]) << 24) & 4278190080L))
                + ((((long) buf[readerPos + 5]) << 16) & 16711680))
                + ((((long) buf[readerPos + 6]) << 8) & 65280))
                + ((((long) buf[readerPos + 7]) << 0) & 255);


        readerPos += 8;
        return l;
    }

    public boolean readBoolean(){
        checkReadBound(1);
        return read() != 0;
    }

    public float readFloat(){
        checkReadBound(4);
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble(){
        checkReadBound(8);
        return Double.longBitsToDouble(readLong());
    }

    public void readBytes(byte[] dst, int off, int size){
        checkReadBound(size);
        System.arraycopy(buf, readerPos, dst, off, size);
        readerPos += size;
    }

    public byte[] readBytes(int size){
        checkReadBound(size);
        byte[] bytes = new byte[size];
        System.arraycopy(buf, readerPos, bytes, 0, size);
        readerPos += size;
        return bytes;
    }

    public void skip(int size){
        checkReadBound(size);
        readerPos += size;
    }

    // read protobuf type

    public long readRawVarint64() {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            int b = read();
            j |= ((long) (b & Byte.MAX_VALUE)) << i;
            if ((b & 128) == 0) {
                return j;
            }
        }
        return 0;
    }

    public int readRawVarint32(){
        int j = 0;
        for (int i = 0; i < 64; i += 7) {
            int b = read();
            j |= (b & Byte.MAX_VALUE) << i;
            if ((b & 128) == 0) {
                return (int) j;
            }
        }
        return 0;
    }

    public int peekVarintSize(){
        int size = 0;
        for (int i = 0; i < 64 && size < remaining(); i += 7) {
            int b = read();
            size++;
            if ((b & 128) == 0) {
                return size;
            }
        }
        return size;
    }


    public int remaining(){
        return limit - readerPos;
    }

}
