package com.wanmi.sbc.common.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ServletOutputStreamCopier
 * Created by aqlu on 15/10/13.
 */
public class ServletOutputStreamCopier extends ServletOutputStream {
    private OutputStream outputStream;

    private ByteArrayOutputStream copy;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
}