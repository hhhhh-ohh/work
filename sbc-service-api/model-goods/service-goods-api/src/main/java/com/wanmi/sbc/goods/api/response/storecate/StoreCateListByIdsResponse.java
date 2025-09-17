package com.wanmi.sbc.goods.api.response.storecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据分类id批量查询店铺分类列表响应结构
 * Author: daiyitian
 * Time: 2018/11/19.10:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateListByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -8005011386779419201L;

    /**
     * 店铺分类列表
     */
    @Schema(description = "店铺分类列表")
    private List<StoreCateVO> storeCateVOList;
}
