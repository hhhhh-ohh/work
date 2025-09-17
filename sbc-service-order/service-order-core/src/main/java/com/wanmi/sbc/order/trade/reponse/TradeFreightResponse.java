package com.wanmi.sbc.order.trade.reponse;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.FreightTemplateGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeFreightTemplateVO;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 各店铺运费
 */
@Data
public class TradeFreightResponse extends BasicResponse {
    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 配送费用，可以从TradePriceInfo获取
     */
    private BigDecimal deliveryPrice;

    /**
     * 商家运费
     */
    private BigDecimal supplierFreight;

    /**
     * 供应商运费
     */
    private BigDecimal providerFreight;

    /**
     * 运费模板信息
     */
    private List<TradeFreightTemplateVO> freightInfo;

    /**
    * 运费模板分组信息 用于单品运费 + 非免邮
     **/
    private List<FreightTemplateGroupVO> groupVO;

    public void updateFreightInfo(List<TradeFreightTemplateVO> freightInfo, List<TradeItem> oldTradeItems) {
        if (CollectionUtils.isNotEmpty(freightInfo)) {
            freightInfo.stream()
                    .filter(freight -> freight.getCollectFlag())
                    .collect(Collectors.toList())
                    .forEach(
                            freight -> {
                                if (CollectionUtils.isEmpty(oldTradeItems)
                                        || oldTradeItems.stream()
                                                .noneMatch(
                                                        i ->
                                                                freight.getSkuIdList()
                                                                        .contains(i.getSkuId()))) {
                                    freight.setCollectFlag(Boolean.FALSE);
                                    freight.setFreightDescribe(
                                            String.format("运费%s元，", freight.getFeright()));
                                }
                            });
        }
        this.freightInfo = freightInfo;
    }
}
