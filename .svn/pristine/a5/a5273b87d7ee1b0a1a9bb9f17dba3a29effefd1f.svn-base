package com.wanmi.sbc.order.api.response.returnorder;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.enums.ReturnWay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询所有退货方式响应结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnWayListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 退货方式列表
     */
    @Schema(description = "退货方式列表")
    private List<ReturnWay> returnWayList;
}
