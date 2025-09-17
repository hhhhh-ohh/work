package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateStoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询店铺运费模板分页响应
 * Created by daiyitian on 2018/11/1.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStorePageResponse extends BasicResponse {

    private static final long serialVersionUID = 517384122561163847L;

    /**
     * 店铺运费模板分页列表
     */
    @Schema(description = "店铺运费模板分页列表")
    private MicroServicePage<FreightTemplateStoreVO> freightTemplateStorePage;

}
