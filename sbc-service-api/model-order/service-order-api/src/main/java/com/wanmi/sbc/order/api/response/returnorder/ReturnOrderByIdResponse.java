package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 根据id查询退单响应结构
 * Created by jinwei on 6/5/2017.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class ReturnOrderByIdResponse extends ReturnOrderVO {

    private static final long serialVersionUID = 1L;
}
