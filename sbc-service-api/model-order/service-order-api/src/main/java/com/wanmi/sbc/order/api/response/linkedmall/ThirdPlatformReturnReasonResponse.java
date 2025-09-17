package com.wanmi.sbc.order.api.response.linkedmall;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.LinkedMallReasonVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询所有退货原因响应结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformReturnReasonResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 退货原因列表
     */
    @Schema(description = "退货原因列表")
    private List<LinkedMallReasonVO> reasonList;

    /**
     * 退货说明
     */
    @Schema(description = "退货说明")
    private String description;

    /**
     * 退单本身的附件
     */
    @Schema(description = "退单本身的附件")
    private List<String> images;
}
