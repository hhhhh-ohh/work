package com.wanmi.sbc.returnorder.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *
 * Created by jinwei on 6/5/2017.
 */
@Schema
@Data
public class ReturnRequest extends BaseRequest {

    @Schema(description = "退单Id")
    private List<String> rids;

    @Schema(description = "地址Id")
    private String addressId;
}
