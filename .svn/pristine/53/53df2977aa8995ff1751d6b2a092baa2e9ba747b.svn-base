package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;

import java.util.List;
import java.util.Objects;

/**
 * @description 数据查询接口。所有的查询都在此接口完成，做简单校验
 * @author edz
 * @date 2022/2/25 11:31
 */
public interface QueryDataInterface {

    /**
     * @description 查询商品信息，不包括库存
     * @author edz
     * @date 2022/3/18 14:10
     */
    GoodsInfoResponse getGoodsInfo(TradeConfirmGoodsRequest goodsRequest);

    /**
     * @description 查询店铺信息
     * @author edz
     * @date 2022/3/18 14:10
     */
    StoreVO getStoreInfo(long storeId, String customerId);

    /**
     * @description 查询会员信息
     * @author edz
     * @date 2022/3/18 14:10
     */
    CustomerVO getCustomerInfo(String customerId);

    /**
     * @description 查询预约活动信息
     * @author edz
     * @date 2022/3/18 14:11
     */
    List<AppointmentSaleVO> getAppointmentSaleRelaInfo(List<TradeItemRequest> tradeItemRequests);

    /**
     * @description 查询预约活动信息
     * @author edz
     * @date 2022/3/18 14:11
     */
    List<BookingSaleVO> getBookingSaleVOList(List<String> goodsInfoIds);

    /**
     * @description 查询会员等级信息
     * @author edz
     * @date 2022/3/18 14:11
     */
    List<StoreCustomerRelaVO> listByCondition(String customerId, Long storeId);

    /**
     * @description 查询系统分销配置
     * @author edz
     * @date 2022/3/18 14:12
     */
    DistributionSettingGetResponse querySettingCache();

    /**
     * @description 查询店铺分销配置
     * @author edz
     * @date 2022/3/18 14:12
     */
    DistributionStoreSettingGetByStoreIdResponse queryStoreSettingCache(String storeId);

    /**
     * @description 查询积分配置
     * @author edz
     * @date 2022/3/18 14:12
     */
    SystemPointsConfigQueryResponse querySystemPointsConfig();

    /**
     * @description 根据商品查询对应的营销信息
     * @author edz
     * @date: 2022/3/18 14:13
     * @param paramsDataVO
     * @return void
     */
    void getGoodsMarketing(ParamsDataVO paramsDataVO);

    /**
     * @description 获取默认收货地址
     * @author  edz
     * @date: 2022/4/2 11:20
     * @param paramsDataVO
     * @return com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse
     **/
    CustomerDeliveryAddressResponse getDefaultAddress(ParamsDataVO paramsDataVO);
}
