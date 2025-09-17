package com.wanmi.sbc.marketing.api.request.discount;

import com.wanmi.sbc.marketing.bean.dto.MarketingFullReductionSaveDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-21
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingFullReductionAddRequest extends MarketingFullReductionSaveDTO {

    private static final long serialVersionUID = -3761825305290948629L;

}
