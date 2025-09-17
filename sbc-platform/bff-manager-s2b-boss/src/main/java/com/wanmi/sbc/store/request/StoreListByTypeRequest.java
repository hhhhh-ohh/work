package com.wanmi.sbc.store.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *  根据店铺类型查询店铺信息
 *  如果 两个请求参数都为空 则只查询非供应商的商家
 * @description
 * @author  wur
 * @date: 2022/1/14 10:27
 **/
@Schema
@Data
public class StoreListByTypeRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;


    /**
     * 商家类型 0供应商 1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型")
    private StoreType storeType;

    @Schema(description = "商家类型集合")
    private List<Integer> storeTypeList;
}
