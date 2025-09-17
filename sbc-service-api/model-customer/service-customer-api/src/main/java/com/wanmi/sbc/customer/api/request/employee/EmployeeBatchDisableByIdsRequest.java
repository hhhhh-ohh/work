package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

import static com.wanmi.sbc.common.util.ValidateUtil.*;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeBatchDisableByIdsRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 员工Id
     */
    @Schema(description = "员工Id")
    private List<String> employeeIds;

    /**
     * 账号状态 0:启用 1:禁用
     */
    @Schema(description = "账号状态")
    private AccountState accountState;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String accountDisableReason;

    @Override
    public void checkParam() {

        if (CollectionUtils.isEmpty(employeeIds)) {
            Validate.notEmpty(employeeIds, EMPTY_COLLECTION_EX_MESSAGE, "employeeIds");
        }

        //账号状态状态不能为空
        if (Objects.isNull(accountState)) {
            Validate.notNull(accountState, NULL_EX_MESSAGE, "accountState");
        }
        //如果是关店状态
        else if (accountState.toValue() == AccountState.DISABLE.toValue()) {
            //关店原因不能为空
            if (StringUtils.isBlank(accountDisableReason)) {
                Validate.notBlank(accountDisableReason, BLANK_EX_MESSAGE, "accountDisableReason");
            }
            //原因长度为1-100以内
            else if (!ValidateUtil.isBetweenLen(accountDisableReason, Constants.ONE, Constants.NUM_100)) {
                Validate.validState(false);
            }
        }
    }
}
