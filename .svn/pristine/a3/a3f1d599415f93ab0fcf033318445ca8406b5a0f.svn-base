package com.wanmi.sbc.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhanggaolei
 * @className CustomClassLoader
 * @description TODO
 * @date 2021/4/21 15:14
 **/
@Slf4j
public class CustomClassLoader extends ClassLoader {

    private byte[] bytes;

    public CustomClassLoader(){

    }
    public CustomClassLoader(ClassLoader parent){
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
    //    log.info("className:",name);
        return Class.forName(name);
    }

    public Class<?>  defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
        Class<?> clazz = defineClass(name, b, off, len);

        return clazz;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
