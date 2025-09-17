package com.wanmi.sbc.order.follow.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.order.api.request.follow.validGroups.*;
import com.wanmi.sbc.order.enums.FollowFlag;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 商品客户收藏请求
 * Created by daiyitian on 2017/5/17.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCustomerFollowRequest extends BaseRequest {

    /**
     * 编号
     */
    private List<Long> followIds;

    /**
     * SKU编号
     */
    @NotBlank(groups = { FollowAdd.class})
    private String goodsInfoId;

    /**
     * SKU编号
     */
    @NotEmpty(groups = {FollowDelete.class, FollowFilter.class})
    private List<String> goodsInfoIds;

    /**
     * 会员编号
     */
    private String customerId;

    /**
     * 购买数量
     */
    private Long goodsNum;

    /**
     * 收藏标识
     */
    @Enumerated
    private FollowFlag followFlag;

    /**
     * 终端来源
     */
    private TerminalSource terminalSource;
}
