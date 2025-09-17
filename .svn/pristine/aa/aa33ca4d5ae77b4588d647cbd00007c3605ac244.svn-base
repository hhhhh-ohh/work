package com.wanmi.sbc.empower.api.response.sellplatform.order;

import com.wanmi.sbc.empower.bean.vo.sellplatform.order.PlatformCompanyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ThirdPlatformCompanyResponse
 * @description 生成订单返回
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformCompanyResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 物流公司集合
     */
    @Schema(description = "物流公司集合")
    private List<PlatformCompanyVO> company_list;

}