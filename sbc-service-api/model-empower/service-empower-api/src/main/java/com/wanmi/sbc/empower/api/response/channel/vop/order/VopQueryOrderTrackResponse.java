package com.wanmi.sbc.empower.api.response.channel.vop.order;

import com.wanmi.sbc.empower.bean.vo.channel.order.VopOrderTrack;
import com.wanmi.sbc.empower.bean.vo.channel.order.VopWaybillCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xufan
 * @Date: 2020/3/7 17:44
 * @Description: 配送信息响应结果
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopQueryOrderTrackResponse implements Serializable {
    private static final long serialVersionUID = 5521001704796586184L;

    /**
     * 运单信息
     */
    private List<VopWaybillCode> waybillCode;

    /**
     * 配送信息
     */
    private List<VopOrderTrack> orderTrack;
}
