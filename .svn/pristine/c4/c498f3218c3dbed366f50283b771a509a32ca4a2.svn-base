package com.wanmi.ares.export.model.entity;

import com.google.common.base.Joiner;
import com.wanmi.ares.enums.*;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.ares.utils.MD5Util;
import com.wanmi.ares.view.export.ExportDataView;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Author: bail
 * Time: 2017/11/3.17:12
 */
@Component
@Data
public class ExportDataEntity {

    private static final Pattern PATTERN = Pattern.compile(",");

    /**
     * 主键
     */
    private long id;

    /**
     * 用户标识
     */
    private String userId;

    /**
     * 商家标识
     */
    private Long companyInfoId;

    /**
     * 开始日期
     */
    private String beginDate;

    /**
     * 截止日期
     */
    private String endDate;

    /**
     * 导出报表类别
     */
    private Integer typeCd;

    /**
     * 导出状态(等待生成导出文件,导出文件生成中,导出文件生成完毕)
     */
    private Integer exportStatus;

    /**
     * 发起导出请求时间
     */
    private String createTime;

    /**
     * 文件成功生成时间/错误时间
     */
    private String finishTime;

    /**
     * 导出文件下载全路径
     */
    private String filePath;

    /**
     * 第几页
     */
    private int pageNum;

    /**
     * 每页多少条
     */
    private int pageSize;

    /**
     * 从第几条开始查询
     */
    private int startNum;

    /**
     * 营销ID
     */
    private List<String> marketingIds;

    /**
     * 拼团营销ID
     */
    private List<String> grouponMarketingIds;

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
     * 店铺id
     */
    private Long storeId;

    /**
     * 统计时间类型，1-近30天，2-近90天
     */
    private StatType statType;

    private String param;

    // 参数MD5
    private String paramsMD5;

    /**
     * 1：昨天，2：最近七天；3：最近30天；4：按月统计
     */
    private StatisticsDataType statisticsDataType;

    /**
     * 自然月份 格式：2021-01
     */
    private String month;

    private ReportType reportType;

    /**
     * 平台类型
     */
    private Platform platform;

    /**
     * 管理员id
     */
    private String adminId;

    /**
     * 订单附加数据
     */
    private String disabled;

    /**
     * 是否购买第三方渠道
     */
    private Boolean buyAnyThirdChannelOrNot;

    /**
     * 0全部，1商家，2门店
     */
    private StoreSelectType storeSelectType;

    /**
     * 是否付费会员增长报表
     */
    private Boolean payMemberNewType;

    private Operator operator;

    /**
     * 从传入对象 转换成 数据库交互的实体
     * @param request
     */
    public ExportDataEntity convertEntityFromRequest(ExportDataRequest request){
        this.id = request.getId();
        this.userId = request.getUserId();
        this.companyInfoId = request.getCompanyInfoId();
        this.beginDate = request.getBeginDate();
        this.endDate = request.getEndDate();
        if(request.getTypeCd() != null){
            this.typeCd = request.getTypeCd().toValue();
        }
        if(request.getExportStatus() != null){
            this.exportStatus = request.getExportStatus().getValue();
        }
        this.createTime = request.getCreateTime();
        this.finishTime = request.getFinishTime();
        this.filePath = request.getFilePath();
        this.pageNum = request.getPageNum();
        this.pageSize = request.getPageSize();
        this.startNum = request.getStartNum();
        this.storeId = request.getStoreId();
        this.marketingIds = request.getMarketingIds();
        this.sortName = request.getSortName();
        this.sortOrder = request.getSortOrder();
        this.statType = request.getStatType();
        this.param = request.getParam();
        this.grouponMarketingIds = request.getGrouponMarketingIds();
        this.statisticsDataType = request.getStatisticsDataType();
        this.month = request.getMonth();
        this.adminId = request.getAdminId();
        this.platform = request.getPlatform();
        this.disabled = request.getDisabled();
        this.buyAnyThirdChannelOrNot = request.getBuyAnyThirdChannelOrNot();
        this.storeSelectType = request.getStoreSelectType();
        this.payMemberNewType = request.getPayMemberNewType();
        this.operator = request.getOperator();
        return this;
    }

    /**
     * 从数据库交互的实体 转换成 返回前端的对象
     */
    public ExportDataView convertViewFromEntity(String prefix){
        ExportDataView view = new ExportDataView();
        view.setId(this.id);
        view.setUserId(this.userId);
        view.setCompanyInfoId(this.companyInfoId);
        view.setBeginDate(this.beginDate);
        view.setEndDate(this.endDate);
        view.setTypeCd(ReportType.findByValue(this.typeCd));
        view.setExportStatus(ExportStatus.findByValue(this.exportStatus));
        view.setCreateTime(this.createTime);
        view.setFinishTime(this.finishTime);
        view.setMonth(this.month);
        if(this.filePath!=null){
            //拼接完整的下载路径
            view.setFilePath(PATTERN.splitAsStream(this.filePath).map(str -> prefix+str).collect(Collectors.joining(",")));
        }
        return view;
    }

    public String getMd5HexParams(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.beginDate);
        sb.append(',');
        sb.append(this.endDate);
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
        if (this.payMemberNewType != null) {
            sb.append(this.payMemberNewType).append(',');
        }

        if (StringUtils.isNotBlank(param)){
            sb.append(param).append(',');
        }
        String result = sb.toString();
        if(StringUtils.isNotBlank(result)){
            return MD5Util.md5Hex(result);
        }
        return null;
    }

}
