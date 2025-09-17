package com.wanmi.sbc.store.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.api.response.store.StoreBaseInfoResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.List;

@Schema
@Data
public class StoreCustSystemResponse extends BasicResponse {
    /**
     * 会员在该店铺的会员等级信息
     */
    @Schema(description = "会员在该店铺的会员等级信息")
    private CustomerLevelVO level;

    /**
     * 店铺的会员体系
     */
    @Schema(description = "店铺的会员体系")
    private List<CustomerLevelVO> levelList;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreBaseInfoResponse store;

    public StoreCustSystemResponse(StoreVO store, CustomerLevelVO level, List<CustomerLevelVO> levelList) {
        this.level = level;
        this.levelList = levelList;
        this.store = new StoreBaseInfoResponse().convertFromEntity(store);
    }

}
