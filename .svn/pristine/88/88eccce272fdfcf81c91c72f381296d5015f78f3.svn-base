package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.wanmi.sbc.common.util.ValidateUtil.*;

/**
 * @Author: songhanlin
 * @Date: Created In 下午3:51 2017/11/6
 * @Description: 驳回/通过 审核
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreAuditRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 驳回状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "驳回状态")
    private CheckState auditState;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    @CanEmpty
    private String auditReason;

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    private String contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    private String contractEndDate;

    /**
     * 结算日
     */
    @Schema(description = "结算日")
    private List<Long> days = new ArrayList<>();

    /**
     * 商家类型
     */
    @Schema(description = "商家类型")
    private DefaultFlag companyType;

    /**
     * 结算日期字符串
     */
    @Schema(description = "结算日期字符串")
    private String accountDay;


    @Override
    public void checkParam() {
        //店铺Id不能为空
        Validate.notNull(storeId, NULL_EX_MESSAGE, "storeId");
        // 驳回/通过状态 不能为空
        Validate.notNull(auditState, NULL_EX_MESSAGE, "auditState");

        if (auditState == CheckState.WAIT_CHECK) {
            // 如果是等待审核, 则操作有误
            Validate.validState(false);
        } else if (auditState == CheckState.NOT_PASS) {
            // 如果是驳回状态,驳回原因不能为空
            Validate.notBlank(auditReason, BLANK_EX_MESSAGE, "auditReason");
            if (!ValidateUtil.isBetweenLen(auditReason, Constants.ONE, Constants.NUM_100)) {
                // 原因长度为1-100以内
                Validate.validState(false);
            }
        } else {
            // 起始日期不能为空
            Validate.notBlank(contractStartDate, BLANK_EX_MESSAGE, "contractStartDate");
            // 结束日期不能为空
            Validate.notBlank(contractEndDate, BLANK_EX_MESSAGE, "contractEndDate");
            // 签约日期时间不能为空
            Validate.notEmpty(days, EMPTY_ARRAY_EX_MESSAGE, "days");
            // 商家类型不能为空
            Validate.notNull(companyType, NULL_EX_MESSAGE, "companyType");

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime.parse(contractEndDate, formatter);
                LocalDateTime.parse(contractStartDate, formatter);
            } catch (Exception e) {
                Validate.validState(false);
            }
        }
    }
}
