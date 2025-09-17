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
public class LinkedMallLogisticsQueryResponse implements Serializable {

    @Schema(description = "linkedmall订单物流详情")
    private List<DataItem> dataItems;

    @Data
    public static class DataItem {

        private String mailNo;

        private String dataProvider;

        private String dataProviderTitle;

        private String logisticsCompanyName;

        private String logisticsCompanyCode;

        private List<LogisticsDetailListItem> logisticsDetailList;

        private List<GoodsItem> goods;
    }

    @Data
    public static class LogisticsDetailListItem {

        private String standerdDesc;

        private String ocurrTimeStr;
    }

    @Data
    public static class GoodsItem {

        private String goodName;

        private Integer quantity;

        private Long itemId;
    }
}


