package com.wanmi.sbc.marketing.api.request.gift;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.api.request.market.MarketingModifyRequest;
import com.wanmi.sbc.marketing.bean.dto.FullGiftDetailDTO;
import com.wanmi.sbc.marketing.bean.dto.FullGiftLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:16
 */
@Schema
@Data
public class FullGiftReduceStockRequest{


    @Schema(description = "营销活动id")
    @NotNull
    private Long marketingId;

    @Schema(description = "营销满赠商品列表")
    @NotNull
    private List<FullGiftDetailDTO> fullGiftDetailList;



}
