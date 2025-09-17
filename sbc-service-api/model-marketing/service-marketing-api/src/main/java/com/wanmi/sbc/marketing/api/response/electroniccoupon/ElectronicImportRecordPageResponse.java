package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.ElectronicImportRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>卡密导入记录表分页结果</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportRecordPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 卡密导入记录表分页结果
     */
    @Schema(description = "卡密导入记录表分页结果")
    private MicroServicePage<ElectronicImportRecordVO> electronicImportRecordVOPage;
}
