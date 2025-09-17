package com.wanmi.sbc.goods.tcc;

import io.seata.rm.tcc.api.BusinessActionContextParameter;

/**
 * @author zhanggaolei
 * @className TccParam
 * @description
 * @date 2022/6/28 15:32
 **/
public class TccParam {

    /**
     * The Num.
     */
    protected int num;

    /**
     * The Email.
     */
    @BusinessActionContextParameter(paramName = "email")
    protected String email;

    /**
     * Instantiates a new Tcc param.
     *
     * @param num   the num
     * @param email the email
     */
    public TccParam(int num, String email) {
        this.num = num;
        this.email = email;
    }

    /**
     * Gets num.
     *
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets num.
     *
     * @param num the num
     */
    public void setNum(int num) {
        this.num = num;
    }
}
