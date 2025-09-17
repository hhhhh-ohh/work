package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询单品运费模板列表响应
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsListByStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = 7549454392679408248L;

    /**
     * 单品运费模板列表
     */
    @Schema(description = "单品运费模板列表")
    private List<FreightTemplateGoodsVO> freightTemplateGoodsVOList;

}
