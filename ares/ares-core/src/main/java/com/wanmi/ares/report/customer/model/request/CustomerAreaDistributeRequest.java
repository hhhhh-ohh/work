package com.wanmi.ares.report.customer.model.request;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName CustomerAreaDistributeRequest
 * @Description 客户统计--客户概况按区域统计request类
 * @Author lvzhenwei
 * @Date 2019/9/20 13:56
 **/
@Data
public class CustomerAreaDistributeRequest extends BaseRequest {
    private LocalDate targetDate;
}
