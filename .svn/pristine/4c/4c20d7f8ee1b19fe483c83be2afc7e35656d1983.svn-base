package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.ContractBrandAuditVO;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 二次签约品牌列表响应结构
 * Created by wangchao on 2018/11/02.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractBrandAuditListResponse extends BasicResponse {

    private static final long serialVersionUID = 5158544900755693052L;

    /**
     * 签约品牌列表
     */
    @Schema(description = "签约品牌列表")
    private List<ContractBrandAuditVO> contractBrandVOList;

}
