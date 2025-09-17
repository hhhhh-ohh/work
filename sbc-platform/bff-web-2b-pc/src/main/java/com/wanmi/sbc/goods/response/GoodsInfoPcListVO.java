package com.wanmi.sbc.goods.response;

import lombok.Data;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoPcListVO
 * @description
 * @date 2021/10/13 7:28 下午
 **/
@Data
public class GoodsInfoPcListVO extends GoodsInfoListVO{
    private List<GoodsInfoListVO> skuList;
}
