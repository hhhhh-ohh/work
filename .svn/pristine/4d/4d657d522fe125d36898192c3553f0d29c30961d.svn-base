package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 签约品牌列表响应结构
 * Created by daiyitian on 2018/11/02.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractBrandListVerifyByStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = 7639389691733596107L;

    /**
     * 签约品牌列表
     */
    @Schema(description = "签约品牌列表")
    private List<ContractBrandVO> contractBrandVOList;

}
