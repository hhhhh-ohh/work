package com.wanmi.sbc.account.api.request.offline;

import com.wanmi.sbc.account.api.request.AccountBaseRequest;
import com.wanmi.sbc.account.bean.dto.OfflineAccountSaveDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 更新账务信息请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflinesRenewalRequest extends AccountBaseRequest {

    private static final long serialVersionUID = -1375570899905759751L;
    /**
     * 线下商户信息批量数据
     */
    @Schema(description = "线下商户信息批量数据")
    @NotEmpty
    private List<OfflineAccountSaveDTO> saveDTOList;

    /**
     * 需删除的线下账户id
     */
    @Schema(description = "需删除的线下账户id")
    @NotEmpty
    private List<Long> ids;

    /**
     * 公司信息Id
     */
    @Schema(description = "公司信息Id")
    @NotNull
    private Long companyInfoId;
}
