package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 根据动态条件查询退单分页列表请求结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 退单分页列表
     */
    @Schema(description = "退单分页列表")
    private MicroServicePage<ReturnOrderVO> returnOrderPage;
}
