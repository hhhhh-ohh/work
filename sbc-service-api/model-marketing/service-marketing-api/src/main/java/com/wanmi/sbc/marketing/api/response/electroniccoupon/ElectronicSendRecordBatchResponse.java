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

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordAddResponse
 * @description
 * @date 2022/2/11 2:56 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordBatchResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 卡密
     */
    @Schema(description = "卡密")
    private List<ElectronicCardVO>  electronicCardVOList;
}
