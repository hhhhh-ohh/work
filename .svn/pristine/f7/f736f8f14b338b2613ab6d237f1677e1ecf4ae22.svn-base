package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.account.bean.dto.CompanyAccountSaveDTO;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 更新商家财务信息参数
 * Created by daiyitian on 2018/10/15.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountBatchRenewalRequest extends BaseRequest {

    private static final long serialVersionUID = 6352906899297549701L;
    /**
     * 更新商家财务信息
     */
    @Schema(description = "更新商家财务信息")
    private List<CompanyAccountSaveDTO> companyAccountSaveDTOList;

    /**
     * 需要删除的线下账户编号
     */
    @Schema(description = "需要删除的线下账户编号")
    private List<Long> accountIds;

    /**
     * 公司信息id
     */
    @Schema(description = "公司信息id")
    @NotNull
    private Long companyInfoId;

    /**
     * 商家类型,用于门店新增账户
     */
    @Schema(description = "商家类型，用于门店新增账户")
    private StoreType storeType;
}
