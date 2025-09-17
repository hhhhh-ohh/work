package com.wanmi.sbc.goods.api.response.storecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.StoreCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/11/1 11:27
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateListByStoreCateIdAndIsHaveSelfResponse extends BasicResponse {
    private static final long serialVersionUID = 4394588687801012667L;

    @Schema(description = "店铺分类列表")
    private List<StoreCateVO> storeCateVOList;
}
