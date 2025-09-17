package com.wanmi.ares.report.paymember.model.root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className PayMemberAreaDistributeReport
 * @description
 * @date 2022/5/25 10:47 AM
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayMemberAreaDistributeReport implements Serializable {
    private static final long serialVersionUID = -4991278083592118268L;

    private Long id;

    /**
     * 城市id
     */
    private Long cityId;

    /**
     * 会员数
     */
    private Long num;

    /**
     * 报表生成时间
     */
    private LocalDateTime createTime;

    /**
     * 统计的目标数据日期
     */
    private LocalDate targetDate;


}
