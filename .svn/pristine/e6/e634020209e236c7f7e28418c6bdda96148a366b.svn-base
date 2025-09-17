package com.wanmi.sbc.common.validation;

import com.wanmi.sbc.common.enums.ErrorCode;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/***
 * 通用断言类
 * @className Assert
 * @author zhengyang
 * @date 2021/7/6 14:02
 **/
public class Assert {

    protected Assert(){

    }

    /**
     * Fails a test with the given message.
     * @param errorCode the identifying errorCode for the {@link SbcRuntimeException} (<code>null</code>
     * okay)
     * @see SbcRuntimeException
     */
    public static <E extends ErrorCode> void fail(E errorCode) {
        if (Objects.isNull(errorCode)) {
            throw new SbcRuntimeException();
        }
        throw new SbcRuntimeException(errorCode);
    }




    /**
     * Asserts that a condition is true. If it isn't it throws an {@link SbcRuntimeException} with
     * the given errorCode.
     *
     * @param condition condition to be checked
     * @param errorCode the identifying errorCode for the {@link SbcRuntimeException} (<code>null</code>
     *     okay)
     */
    public static <E extends ErrorCode> void assertTrue(boolean condition, E errorCode) {
        if (!condition) {
            fail(errorCode);
        }
    }





    /**
     * Asserts that an object isn't null. If it is an {@link SbcRuntimeException} is thrown with the
     * given errorCode.
     *
     * @param object Object to check or <code>null</code>
     * @param errorCode the identifying errorCode for the {@link SbcRuntimeException} (<code>null</code>
     *     okay)
     */
    public static <E extends ErrorCode>  void assertNotNull(Object object, E errorCode) {
        assertTrue(Objects.nonNull(object), errorCode);
    }

    /**
     * Asserts that an collection isn't empty. If it is an {@link SbcRuntimeException} is thrown with the
     * given errorCode.
     *
     * @param list Object to check or <code>null</code>
     * @param errorCode the identifying errorCode for the {@link SbcRuntimeException} (<code>null</code>
     *     okay)
     */
    public static <E extends ErrorCode> void assertNotEmpty(List list, E errorCode) {
        assertTrue(CollectionUtils.isNotEmpty(list), errorCode);
    }



    /**
     * Asserts that an number is zero. If it is an {@link SbcRuntimeException} is thrown with the
     * given errorCode.
     *
     * @param number number to check is zero
     * @param errorCode the identifying errorCode for the {@link SbcRuntimeException} (<code>null</code>
     *     okay)
     */
    public static <E extends ErrorCode>  void assertIsZero(Number number, E errorCode) {
        assertTrue(Objects.nonNull(number) && number.intValue() == 0, errorCode);
    }
}
