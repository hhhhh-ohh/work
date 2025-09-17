package com.wanmi.sbc.setting.api.response.storeexpresscompanyrela;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.StoreExpressCompanyRelaVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺快递公司关联表列表结果</p>
 * @author lq
 * @date 2019-11-05 16:12:13
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExpressCompanyRelaListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺快递公司关联表列表结果
     */
    @Schema(description = "店铺快递公司关联表列表结果")
    private List<StoreExpressCompanyRelaVO> storeExpressCompanyRelaVOList;
}
