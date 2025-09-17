package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xufan
 * @Date: 2020/2/26 16:42
 * @Description:
 *
 */
@Data
public class GetSkuByPageResponse implements Serializable {

    private static final long serialVersionUID = 8323035989218061592L;
    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * skuId集合
     */
    private List<Long> skuIds;
}
