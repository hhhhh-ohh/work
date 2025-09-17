package com.wanmi.sbc.marketing.api.request.discount;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullDiscountSaveDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-20
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingFullDiscountModifyRequest extends MarketingFullDiscountSaveDTO {

    private static final long serialVersionUID = 3237307625226519386L;

}