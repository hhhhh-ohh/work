package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.empower.bean.vo.ChannelRefundReasonVO;

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
public class ChannelRefundReasonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退货原因列表
     */
    @Schema(description = "退货原因列表")
    private List<ChannelRefundReasonVO> reasonList;
}
