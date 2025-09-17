package com.wanmi.sbc.goods.api.response.thirdgoodscate;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

import java.util.Set;

/**
 * @author EDZ
 * @className CheckCateIdResponse
 * @description 检查类目是否存在，返回查询出的类目
 * @date 2021/5/13 10:28
 **/
@Data
public class CheckCateIdResponse extends BasicResponse {
    /**
     * 查询出的三方类目Id集合，存在集合里说明已同步过
     */
    private Set<Long> thirdCateIdSet;

    /**
     * 关联的平台类目（如果已经关联过，返回关联过的平台三级类目）
     */
    private Long cateId;
}
