package com.wanmi.sbc.common.util;


import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import org.springframework.util.Base64Utils;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 *
 * @ClassName DeflaterUtil
 * @Description 解压缩工具类
 * @date 2022-01-10 下午4:06:18
 * @author xufeng
 */
@Slf4j
public class DeflaterUtil {

	private static final int BUFFER_SIZE = 256;

	/**
	 * 压缩
	 * @param unzip
	 * @return
	 */
	public static String zipString(String unzip) {
		// 0 ~ 9 压缩等级 低到高
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
		try (
			 ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BUFFER_SIZE)
		) {
			deflater.setInput(unzip.getBytes());
			deflater.finish();
			final byte[] bytes = new byte[BUFFER_SIZE];
			while (!deflater.finished()) {
				int length = deflater.deflate(bytes);
				outputStream.write(bytes, 0, length);
			}
			return Base64Utils.encodeToString(outputStream.toByteArray());
//			return new BASE64Encoder().encodeBuffer(outputStream.toByteArray());
		} catch (Exception ex) {
			log.error("压缩异常,param={},ex={}", unzip, ex);
			return Constants.FAIL;
		} finally {
			deflater.end();
		}
	}
	/**
	 * 解压缩
	 * @param zip
	 * @return
	 */
	public static String unzipString(String zip) {
		final byte[] bytes = new byte[BUFFER_SIZE];
		Inflater inflater = new Inflater();
		try (
			 ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BUFFER_SIZE)
		) {

			byte[] decode = Base64Utils.decodeFromString(zip);
			inflater.setInput(decode);
			while (!inflater.finished()) {
				int length = inflater.inflate(bytes);
				outputStream.write(bytes, 0, length);
			}
			return outputStream.toString();
		} catch (Exception ex) {
			log.error("解压缩异常,param={},ex={}", zip, ex);
			return "";
		} finally {
			inflater.end();
		}
	}

    public static void main(String[] args) {
		String raw = "test";
		String zip = zipString(raw);
		String unzip = unzipString(zip);
		System.out.println("zip = " + zip);
		System.out.println("unzip = " + unzip);
        System.out.println("raw.equals(unzip) = " + raw.equals(unzip));
	}
}
