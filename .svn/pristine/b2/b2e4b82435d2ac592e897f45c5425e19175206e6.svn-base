package com.wanmi.ares.report.paymember.model.root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className PayMemberGrowReport
 * @description
 * @date 2022/5/26 9:52 AM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMemberGrowReport implements Serializable {
    private static final long serialVersionUID = -5628930449545945923L;

    private String id;

    /**
     * 日期
     */
    private String baseDate;

    /**
     * 付费会员总数
     */
    private Long allCount;

    /**
     * 新增付费会员数
     */
    private Long dayGrowthCount;

    /**
     * 续费会员数
     */
    private Long dayRenewalCount;

    /**
     * 到期未续费会员数
     */
    private Long dayOvertimeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;
}
