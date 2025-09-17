package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordSendAgainResponse
 * @description
 * @date 2022/2/14 10:43 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordSendAgainResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 卡密数据
     */
    @Schema(description = "卡密数据")
    private Map<String, List<ElectronicCardVO>> cardMap;
}
