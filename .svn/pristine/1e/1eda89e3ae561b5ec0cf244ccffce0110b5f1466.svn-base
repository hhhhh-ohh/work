package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>电子卡密表修改结果</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的电子卡密表信息
     */
    @Schema(description = "已修改的电子卡密表信息")
    private ElectronicCardVO electronicCardVO;
}
