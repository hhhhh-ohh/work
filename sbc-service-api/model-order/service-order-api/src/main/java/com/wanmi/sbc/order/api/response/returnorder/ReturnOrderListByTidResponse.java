package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据订单id查询所有退单响应结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderListByTidResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 退单列表
     */
    @Schema(description = "退单列表")
    private List<ReturnOrderVO> returnOrderList;

}
