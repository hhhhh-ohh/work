package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.order.bean.dto.PurchaseSaveDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class PurchaseBatchSaveRequest extends PurchaseSaveDTO {

    private static final long serialVersionUID = 1L;

}
