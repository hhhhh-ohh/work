package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.ElectronicImportRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>卡密导入记录表新增结果</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportRecordAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的卡密导入记录表信息
     */
    @Schema(description = "已新增的卡密导入记录表信息")
    private ElectronicImportRecordVO electronicImportRecordVO;
}
