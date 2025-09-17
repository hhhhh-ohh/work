package com.wanmi.sbc.marketing.api.response.grouponcate;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className GrouponCateDelResponse
 * @description TODO
 * @author 黄昭
 * @date 2021/11/26 11:35
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponCateDelResponse extends BasicResponse {

    /**
     * 拼团活动Id
     */
    private List<String> grouponActivityIds;
}
