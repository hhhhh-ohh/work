package com.wanmi.sbc.marketing.api.response.distributionrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>DistributionRecord新增结果</p>
 * @author baijz
 * @date 2019-02-25 10:28:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRecordAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的DistributionRecord信息
     */
    private List<DistributionRecordVO> distributionRecordVOs;
}
