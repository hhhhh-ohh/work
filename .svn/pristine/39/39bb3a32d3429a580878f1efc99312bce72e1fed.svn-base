package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 活动优惠券
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class EsActivityCouponInitRequest extends EsInitRequest {

    private static final long serialVersionUID = -5115285012717445946L;
    /**
     * 优惠券活动编号批量ID
     */
    @Schema(description = "优惠券活动编号批量ID")
    private List<String> idList;


    /**
     * 如果有范围进行初始化索引，无需删索引
     *
     * @return true:clear index false:no
     */
    public boolean isClearEsIndex() {
        if (CollectionUtils.isNotEmpty(idList)
                || super.getPageNum() != null) {
            return false;
        }
        return true;
    }

}
