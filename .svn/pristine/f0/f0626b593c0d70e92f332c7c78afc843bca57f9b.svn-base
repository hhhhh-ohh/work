package com.wanmi.sbc.goods.api.response.contract;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 签约信息保存响应结构
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractSaveResponse extends BasicResponse {

    private static final long serialVersionUID = -1270540719960900900L;

    /**
     * 删除后的品牌id
     */
    @Schema(description = "删除后的品牌id")
    private List<Long> brandIds;
}
