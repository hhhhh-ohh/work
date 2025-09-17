package com.wanmi.ares.report.base.model;

import com.google.common.base.Joiner;
import com.wanmi.ares.enums.StatType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.export.model.entity.ExportDataEntity;
import com.wanmi.ares.utils.MD5Util;
import com.wanmi.sbc.common.base.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>报表导出请求参数</p>
 * Created by of628-wenzhi on 2017-11-01-下午5:27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExportQuery {

    /**
     * 开始日期,yyyy-MM-dd
     */
    private String dateFrom;

    /**
     * 结束日期,yyyy-MM-dd
     */
    private String dateTo;

    /**
     * 商户id
     */
    private String companyId;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 营销ID
     */
    private List<String> marketingIds;

    /**
     * 商品名称
     */
    private String goodsInfoName;

    /**
     * 数据开始index
     */
    private int beginIndex;

    /**
     * 抓取条数
     */
    private int size = 5000;

    /**
     * 统计时间类型，1-近30天，2-近90天
     */
    private StatType statType;

    /**
     * 排序字段: 0:支付ROI, 1:营销支付金额, 2:营销优惠金额, 3:营销支付件数, 4:营销支付订单数, 5:连带率, " +
     *             "6:新用户, 7:老用户, 8:营销支付人数, 9:客单价, 10:发起分享人数, 11:分享访客人数, 12:分享参团数, 13:拼团订单数, 14:拼团人数, 15:成团订单数," +
     *             "16:成团人数, 17:拼团-成团转化率, 18:预约人数
     */
    private String sortName;

    /**
     * 排序类型 asc desc
     */
    private String sortOrder;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 拼团营销ID
     */
    private List<String> grouponMarketingIds;

    /**
     * 1：昨天，2：最近七天；3：最近30天；4：按月统计
     */
    private StatisticsDataType statisticsDataType;

    /**
     * 自然月份 格式：2021-01
     */
    private String month;

    /**
     * 0全部，1商家，2门店
     */
    private StoreSelectType storeSelectType;

    /**
     * 导出报表类别
     */
    private Integer typeCd;

    /**
     * 是否付费会员增长报表
     */
    private Boolean payMemberNewType;

    private Operator operator;

    /**
     * 从传入对象 转换成 生成报表excel的查询实体
     *
     * @param entity
     */
    public ExportQuery convertFromRequest(ExportDataEntity entity) {
        this.dateFrom = entity.getBeginDate();
        this.dateTo = entity.getEndDate();
        this.companyId = Objects.isNull(entity.getCompanyInfoId()) ? "" : entity.getCompanyInfoId().toString();
        this.storeId = entity.getStoreId();
        this.statType = entity.getStatType();
        this.param = entity.getParam();
        this.marketingIds = entity.getMarketingIds();
        this.grouponMarketingIds = entity.getGrouponMarketingIds();
        this.statisticsDataType = entity.getStatisticsDataType();
        this.month = entity.getMonth();
        this.storeSelectType = entity.getStoreSelectType();
        this.typeCd = entity.getTypeCd();
        this.payMemberNewType = entity.getPayMemberNewType();
        this.operator = entity.getOperator();
        return this;
    }

    public String getCompanyId() {
        return StringUtils.isBlank(companyId) ? "0" : companyId;
    }

    public String getMd5HexParams(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.dateFrom);
        sb.append(',');
        sb.append(this.dateTo);
        sb.append(',');
        if (StringUtils.isNoneBlank(this.month)) {
            sb.append(this.month);
        }
        if (CollectionUtils.isNotEmpty(this.marketingIds)) {
            sb.append(Joiner.on(",").join(this.marketingIds));
        }else if(CollectionUtils.isNotEmpty(this.grouponMarketingIds)){
            sb.append(Joiner.on(",").join(this.grouponMarketingIds));
        }
        if(StringUtils.isNotBlank(this.sortName) && StringUtils.isNotBlank(this.sortOrder)){
            sb.append(',');
            sb.append(this.sortName);
            sb.append(',');
            sb.append(this.sortOrder);
        }
        String result = sb.toString();
        if(StringUtils.isNotBlank(result)){
            return MD5Util.md5Hex(result);
        }
        return null;
    }
}
