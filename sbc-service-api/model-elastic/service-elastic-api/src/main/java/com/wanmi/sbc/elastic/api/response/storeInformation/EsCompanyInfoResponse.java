package com.wanmi.sbc.elastic.api.response.storeInformation;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @Author yangzhen
 * @Description // 商家结算账户查分页询
 * @Date 14:42 2020/12/9
 * @Param
 * @return
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsCompanyInfoResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsCompanyInfoVO> esCompanyAccountPage = new MicroServicePage<>(new ArrayList<>());

}
