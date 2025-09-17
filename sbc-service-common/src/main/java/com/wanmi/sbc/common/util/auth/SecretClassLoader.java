package com.wanmi.sbc.common.util.auth;


import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SecretClassLoader extends ClassLoader{

	 public SecretClassLoader(){

	    }

	    public SecretClassLoader(ClassLoader parent){
	        super(parent);
	    }

	    @Override
	    protected Class<?> findClass(String name) throws ClassNotFoundException{
			try {
				if(!name.equals(new String(Type.getType(), StandardCharsets.UTF_8.name()))){
				   return Class.forName(name);
			   }
			} catch (UnsupportedEncodingException e) {
				log.error("查找类异常", e);
			}
			try{
	            byte[] bytes = getClassBytesFromEncode();
	            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
	            return c;
	        }
	        catch (Exception e){
				log.error("查找类异常", e);
	        }

	        return super.findClass(name);
	    }

	    private byte[] getClassBytesFromEncode() throws Exception{
			InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream("auth_wanmi_110.pin");
			byte[] data = AuthHelp.getByte(fileStream);
			fileStream.close();
			byte[] processData = AuthHelp.getProcessByte(data);
			Class<?> c = this.defineClass(new String(Type.sName,StandardCharsets.UTF_8.name()), processData, 0, processData.length);
			int size = processData.length+AuthHelp.size;
			byte[] secretData = new byte[data.length-size];
			System.arraycopy(data,size,secretData,0,secretData.length);
			AbstractSecret abstractSecret = (AbstractSecret) c.newInstance();
			return abstractSecret.action(secretData);

	    }



}
