package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.EsStoreInfoVo;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsStoreInfoResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分页查询店铺信息
     */
    @Schema(description = "分页查询店铺信息")
    private List<EsStoreInfoVo> lists;
}
