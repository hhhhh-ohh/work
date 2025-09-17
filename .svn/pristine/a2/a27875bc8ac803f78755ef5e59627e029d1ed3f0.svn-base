package com.wanmi.sbc.order.optimization.trade1.snapshot;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.BuyCycleVO;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.api.response.marketingsuits.MarketingSuitsValidResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import lombok.Data;

import java.util.List;

/**
 * @author edz
 * @className QueryDataVO
 * @description 查询接口返回对象(注意注意注意⚠️：所有数据库查询出的源数据都不允许修改操作)
 * @date 2022/2/25 14:19
 **/
@Data
public class ParamsDataVO extends BasicResponse {

    // 前端请求参数
    private TradeBuyRequest request;

    // 会员信息
    private CustomerVO customerVO;

    // 商品信息(数据查询的源数据，只允许get操作，不允许修改操作。目的是后续迭代可以放心使用，避免重复查询)
    private GoodsInfoResponse goodsInfoResponseSourceData;

    // 商品信息(如需修改查询出的源数据，请深拷贝至此属性操作)
    private GoodsInfoResponse goodsInfoResponse;

    // 店铺信息
    private StoreVO storeVO;

    // 预约活动信息
    private List<AppointmentSaleVO> appointmentSaleVOS;

    // 预售活动信息
    private List<BookingSaleVO> bookingSaleVOS;

    // 会员等级信息
    private List<StoreCustomerRelaVO> storeCustomerRelaVOS;

    // 平台分销设置
    private DistributionSettingGetResponse distributionSettingGetResponse;

    // 店铺分销设置
    private DistributionStoreSettingGetByStoreIdResponse distributionStoreSettingGetByStoreIdResponse;

    // 积分设置
    private SystemPointsConfigQueryResponse systemPointsConfigQueryResponse;

    // 订单快照trade对象
    private List<TradeItemDTO> tradeItemDTOS;

    // 订单标记
    private OrderTag orderTag;

    // 订单快照营销信息
    private List<TradeMarketingDTO> tradeMarketingList;

    private String terminalToken;

    // 营销插件对象
    private GoodsTradePluginResponse goodsTradePluginResponse;

    // 组合购
    private MarketingSuitsValidResponse marketingResponse;

    public ParamsDataVO(TradeBuyRequest request) {
        this.request = request;
    }

    //砍价活动信息
    public BargainVO bargainVO;

    //周期购信息
    public BuyCycleVO buyCycleVO;
}
