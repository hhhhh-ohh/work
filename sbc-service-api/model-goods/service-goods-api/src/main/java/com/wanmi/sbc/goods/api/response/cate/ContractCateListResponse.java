package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>签约分类相应类</p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateListResponse extends BasicResponse {

    private static final long serialVersionUID = -1868203387486188933L;

    @Schema(description = "签约分类")
    private List<ContractCateVO> contractCateList;
}
