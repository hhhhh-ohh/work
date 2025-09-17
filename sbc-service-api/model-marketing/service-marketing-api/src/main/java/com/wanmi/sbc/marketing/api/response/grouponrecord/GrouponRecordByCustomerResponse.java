package com.wanmi.sbc.marketing.api.response.grouponrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.GrouponRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>拼团活动参团信息表新增结果</p>
 * @author groupon
 * @date 2019-05-17 16:17:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponRecordByCustomerResponse extends BasicResponse {


    private static final long serialVersionUID = -1304851847616075913L;
    /**
     * 已新增的拼团活动参团信息表信息
     */
    private GrouponRecordVO grouponRecordVO;
}
