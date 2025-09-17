package com.wanmi.ares.report.customer.model.request;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName CustomerLevelDistributeRequest
 * @Description 客户统计--客户概况按等级统计request类
 * @Author lvzhenwei
 * @Date 2019/9/20 13:55
 **/
@Data
public class CustomerLevelDistributeRequest extends BaseRequest {
    private LocalDate targetDate;
}
