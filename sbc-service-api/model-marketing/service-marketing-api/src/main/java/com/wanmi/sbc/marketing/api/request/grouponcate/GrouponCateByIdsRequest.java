package com.wanmi.sbc.marketing.api.request.grouponcate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>单个查询拼团活动信息表请求参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCateByIdsRequest extends BaseRequest {
    private static final long serialVersionUID = -2951165509501759303L;

    /**
     * 拼团分类Id
     */
    @Schema(description = "拼团分类Id")
    private List<String> grouponCateIds;
}