package com.wanmi.sbc.dw.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: com.wanmi.sbc.dw.api.request.HotRecommendRequest
 * @Description: 热门推荐请求头
 * @Author: 何军红
 * @Time: 2020/12/1 16:43
 * @Version: 1.0
 */

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotRecommendRequest extends BaseRequest {

    /**
     * 用户id
     */
    private String customerId;

    /**
     * 每页展示多少
     */
    int pageSize;
    /**
     *
     */

    /**
     * 从第第几条开始展示(后台取存储每个用户的row_number)
     */
    private int pageIndex;
}
