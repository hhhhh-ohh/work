package com.wanmi.sbc.order.purchase;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.response.purchase.PurchaseGetGoodsMarketingResponse;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * 商品-营销映射Service接口
 * @className GoodsMarketingServiceInterface
 * @author zhengyang
 * @date 2021/11/11 15:11
 **/
public interface GoodsMarketingServiceInterface {

    /***
     * 获取商品营销信息
     * @param goodsInfos        商品SKU集合
     * @param customer          登录用户
     * @param storeId           门店Id，O2O模式使用
     * @param pluginType        插件类型，用于路由
     * @return
     */
    PurchaseGetGoodsMarketingResponse getGoodsMarketing(List<GoodsInfoVO> goodsInfos,
                                                        CustomerVO customer, Long storeId, PluginType pluginType);

    /**
     * 修改商品使用的营销
     *
     * @param goodsInfoId   商品SKUId
     * @param marketingId   市场营销ID
     * @param customer      登录用户
     * @param storeId       门店ID，O2O模式用，SBC直接传Null
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    void modifyGoodsMarketing(String goodsInfoId, Long marketingId, CustomerVO customer, Long storeId);
}
