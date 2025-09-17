package com.wanmi.sbc.empower.api.response.channel.linkedmall;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkedMallOrderListQueryResponse implements Serializable {

    @Schema(description = "linkedmall订单详情")
    private List<LmOrderListItem> lmOrderList;

    @Schema(description = "当前页码")
    private Integer pageNum;

    @Schema(description = "当前页条数")
    private Integer pageSize;

    @Data
    public static class LmOrderListItem {

        private String createDate;

        private Long lmOrderId;

        private Long orderAmount;

        private Integer orderStatus;

        private String extJson;

        private String shopName;

        private Integer logisticsStatus;

        private Integer enableStatus;

        private Long tbOrderId;

        private List<FundStructureModelsItem> fundStructureModels;

        private List<SubOrderListItem> subOrderList;

        private PostFee postFee1;
    }

    @Data
    public static class FundStructureModelsItem {

        private Long fundAmount;

        private Long fundAmountMoney;

        private String fundType;
    }

    @Data
    public static class SubOrderListItem {

        private Long itemId;

        private String itemPic;

        private String itemTitle;

        private Long number;

        private Long skuId;

        private String skuName;

        private Long lmOrderId;

        private Integer orderStatus;

        private Integer enableStatus;

        private Long tbOrderId;

        private String lmItemId;

        private List<ItemPriceListItem> itemPriceList;

    }

    @Data
    public static class ItemPriceListItem {

        private Long fundAmount;

        private Long fundAmountMoney;

        private String fundType;
    }

    @Data
    public static class PostFee {

        private Long fundAmount;

        private Long fundAmountMoney;

        private String fundType;
    }
}
