package com.wanmi.sbc.order.api.request.quickorder;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.order.bean.dto.QuickOrderGoodsQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @className QuickOrderGoodsListRequest
 * @description TODO
 * @author edz
 * @date 2023/6/2 09:28
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class QuickOrderGoodsListRequest extends BaseQueryRequest {

    @Schema(description = "订货号list", required = true)
    @Valid
    @NotEmpty
    private List<QuickOrderGoodsQueryDTO> orderGoodsNoList;

    @Schema(description = "0批量搜索，1单货号搜索", required = true)
    @NotNull
    private Integer searchType;

    /**
     * 会员编号
     */
    @Schema(description = "会员编号")
    private String customerId;

    @Schema(description = "区的区域码")
    private Long areaId;

    /**
     * 本地地址区域
     */
    @Schema(description = "本地地址区域")
    private PlatformAddress address;

    /**
     * 登录用户
     */
    @Schema(description = "登录用户")
    private CustomerVO customer;

    @Schema(description = "终端类型")
    private TerminalSource terminalSource;

    @Schema(description = "门店ID")
    private Long storeId;

    /**
     * 邀请人
     */
    @Schema(description = "邀请人")
    private String inviteeId;

}
