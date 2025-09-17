package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PurchaseQueryRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    @Schema(description = "商品ids")
    private List<String> goodsInfoIds;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    @Schema(description = "活动id")
    private Long marketingId;

    @Schema(description = "是否o2o")
    @NotNull
    private Boolean isO2O=Boolean.FALSE;

    public Boolean getIsO2O() {
        if(isO2O==null){
            return Boolean.FALSE;
        }
        return isO2O;
    }
}
