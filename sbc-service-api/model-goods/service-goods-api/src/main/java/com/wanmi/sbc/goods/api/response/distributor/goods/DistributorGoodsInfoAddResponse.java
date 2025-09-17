package com.wanmi.sbc.goods.api.response.distributor.goods;

import com.wanmi.sbc.goods.bean.vo.DistributorGoodsInfoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分销员商品-新增对象
 *
 * @author Geek Wang
 * @version 1.0
 * @createDate 2019/2/28 14:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DistributorGoodsInfoAddResponse extends DistributorGoodsInfoVO implements Serializable {
    private static final long serialVersionUID = 6381426325890070875L;
}
