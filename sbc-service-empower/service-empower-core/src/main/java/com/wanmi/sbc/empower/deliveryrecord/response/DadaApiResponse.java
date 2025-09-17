package com.wanmi.sbc.empower.deliveryrecord.response;

import lombok.Data;

/**
 * DATE: 18/9/3
 *
 * @author: wan
 */
@Data
public class DadaApiResponse<T> {

    private int code;

    private String msg;

    private T result;
}
