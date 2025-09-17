package com.wanmi.sbc.customer.api.response.follow;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerFollowVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 店铺收藏响应
 * Created by bail on 2017/11/29.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCustomerFollowPageResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 店铺收藏分页数据
     */
    @Schema(description = "店铺收藏分页数据")
    private MicroServicePage<StoreCustomerFollowVO> customerFollowVOPage;
}
