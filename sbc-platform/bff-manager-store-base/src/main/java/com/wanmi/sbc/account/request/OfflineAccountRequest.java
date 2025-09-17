package com.wanmi.sbc.account.request;

import com.wanmi.sbc.account.bean.dto.CompanyAccountSaveDTO;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunkun on 2017/11/3.
 */
@Schema
@Data
public class OfflineAccountRequest extends BaseRequest {

    @Schema(description = "删除的Ids")
    private List<Long> deleteIds = new ArrayList<>();

    @Schema(description = "线下收款账户")
    private List<CompanyAccountSaveDTO> offlineAccounts = new ArrayList<>();
}
