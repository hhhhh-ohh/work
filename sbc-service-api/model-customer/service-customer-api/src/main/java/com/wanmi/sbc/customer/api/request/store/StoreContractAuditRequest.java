package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.wanmi.sbc.common.util.ValidateUtil.*;

/**
 * @Author: wangchao
 * @Date: Created In 下午3:51 2017/11/6
 * @Description: 二次签约驳回/通过 审核
 */
@Schema
@Data
public class StoreContractAuditRequest extends BaseRequest {

    private static final long serialVersionUID = 8772143731327189398L;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 二次签约审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState contractAuditState;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String contractAuditReason;
}
