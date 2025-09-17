package com.wanmi.sbc.setting.api.response.expresscompany;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>物流公司新增结果</p>
 * @author lq
 * @date 2019-11-05 16:10:00
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的物流公司信息
     */
    @Schema(description = "已新增的物流公司信息")
    private ExpressCompanyVO expressCompanyVO;
}
