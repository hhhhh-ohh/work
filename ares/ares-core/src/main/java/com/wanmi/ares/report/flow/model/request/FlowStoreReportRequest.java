package com.wanmi.ares.report.flow.model.request;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName FlowStoreReportRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/9/11 10:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowStoreReportRequest extends BaseRequest {

    private static final long serialVersionUID = 5632750172070479175L;
    /**
     * 开始时候
     * 20170925
     */
    private LocalDate beginDate;

    /**
     * 结束时间
     * 20170926
     */
    private LocalDate endDate;

}
