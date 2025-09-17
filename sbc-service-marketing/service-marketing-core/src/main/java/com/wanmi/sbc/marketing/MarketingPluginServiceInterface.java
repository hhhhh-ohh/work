package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.response.info.GoodsInfoListByGoodsInfoResponse;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.request.MarketingPluginRequest;

import java.util.List;
import java.util.Map;

/***
 * 营销插件接口
 * @className MarketingPluginServiceInterface
 * @author zhengyang
 * @date 2021/10/27 11:24
 **/
public interface MarketingPluginServiceInterface {
    /**
     * 商品列表处理
     *
     * @param goodsInfos 商品数据
     * @param request    参数
     * @throws SbcRuntimeException
     */
    GoodsInfoListByGoodsInfoResponse goodsListFilter(List<GoodsInfoVO> goodsInfos, MarketingPluginRequest request) throws SbcRuntimeException;

    /**
     * 获取营销
     * @param goodsInfoList     商品集合
     * @param levelMap          等级Map
     * @param pluginType        插件类型
     * @param storeId           门店ID
     * @return
     */
    Map<String, List<MarketingResponse>> getMarketing(List<GoodsInfoVO> goodsInfoList, Map<Long, CommonLevelVO> levelMap,
                                                             PluginType pluginType, Long storeId, String customerId);
}
