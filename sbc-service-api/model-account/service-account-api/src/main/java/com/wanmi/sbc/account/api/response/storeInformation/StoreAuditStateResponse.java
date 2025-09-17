package com.wanmi.sbc.account.api.response.storeInformation;

import com.wanmi.sbc.common.base.BasicResponse;

import com.wanmi.sbc.account.bean.enums.CheckState;
import com.wanmi.sbc.account.bean.vo.CompanyAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Schema
@Data
public class StoreAuditStateResponse extends BasicResponse {

    private static final long serialVersionUID = 3428053292925492893L;
    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState auditState;


    /**
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    @Schema(description = "是否确认打款(-1:全部,0:否,1:是)")
    private Integer remitAffirm;
}
