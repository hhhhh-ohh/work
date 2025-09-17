package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据动态条件查询响应类</p>
 *
 * @author daiyitian
 * @dateTime 2018-11-15
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateListByConditionResponse extends BasicResponse {

    private static final long serialVersionUID = 4617598066144890136L;

    /**
     * 签约分类列表
     */
    @Schema(description = "签约分类列表")
    private List<ContractCateVO> contractCateList;
}
