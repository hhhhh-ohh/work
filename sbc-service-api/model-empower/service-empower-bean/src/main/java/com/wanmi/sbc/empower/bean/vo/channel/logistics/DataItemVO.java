package com.wanmi.sbc.empower.bean.vo.channel.logistics;

import lombok.Data;

import java.util.List;

/**
 * @author edz
 * @className DataItem
 * @description
 * @date 2021/5/29 20:15
 **/
@Data
public class DataItemVO {

    private String mailNo;

    private String dataProvider;

    private String dataProviderTitle;

    private String logisticsCompanyName;

    private String logisticsCompanyCode;

    private List<LogisticsDetailListItemVO> logisticsDetailList;

    private List<GoodsItemVO> goods;
}
