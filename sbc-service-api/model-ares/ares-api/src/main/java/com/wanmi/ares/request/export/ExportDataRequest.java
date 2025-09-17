package com.wanmi.ares.request.export;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StatType;
import com.wanmi.ares.enums.StatisticsDataType;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/** @author edz */
@Data
public class ExportDataRequest extends BaseRequest {

    /** 主键 */
     @Schema(hidden=true)
    private long id;
    /** 用户标识 */
     @Schema(hidden=true)
    private String userId;
    /** 商家标识 */
    @Schema(description = "商家标识")
    private Long companyInfoId;

    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

    /** 店铺id */
    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "商家类型0品牌商城，1商家，2直营店")
    private StoreType storeType;
    /** 开始日期 */
    @Schema(description = "开始日期")
    private String beginDate;
    /** 截止日期 */
    @Schema(description = "截止日期")
    private String endDate;
    /**
     * 导出报表类别
     *
     * @see com.wanmi.ares.enums.ReportType
     */
    @Schema(description = "导出报表类别, 14拼团, 15秒杀, 16预约, 17优惠券, 18全款预售, 19定金预售,20优惠券效果,21优惠券活动效果,22优惠券店铺效果")
    private com.wanmi.ares.enums.ReportType typeCd;
    /**
     * 导出状态(等待生成导出文件,导出文件生成中,导出文件生成完毕)
     *
     * @see com.wanmi.ares.enums.ExportStatus
     */
    private com.wanmi.ares.enums.ExportStatus exportStatus;
    /** 发起导出请求时间 */
     @Schema(hidden=true)
    private String createTime;
    /** 文件成功生成时间/错误时间 */
     @Schema(hidden=true)
    private String finishTime;
    /** 导出文件下载全路径 */
     @Schema(hidden=true)
    private String filePath;
    /** 第几页 */
    @Schema(description = "第几页")
    private int pageNum;
    /** 每页多少条 */
    @Schema(description = "每页多少条")
    private int pageSize;
    /** 从第几条开始查询 */
    @Schema(description = "从第几条开始查询")
    private int startNum;

    @Schema(description = "统计时间类型，1-近30天，2-近90天")
    private StatType statType;

    /** 营销ID */
    @Schema(description = "营销ID")
    private List<String> marketingIds;

    /** 排序字段: */
    @Schema(description = "0:支付ROI, 1:营销支付金额, 2:营销优惠金额, 3:营销支付件数, 4:营销支付订单数, 5:连带率,"
                            + "  6:新用户, 7:老用户, 8:营销支付人数, 9:客单价, 10:发起分享人数, 11:分享访客人数, 12:分享参团数, 13:拼团订单数, 14:拼团人数, 15:成团订单数"
                            + "  16:成团人数, 17:拼团-成团转化率, 18:预约人数")
    private String sortName;

    /** 排序类型 asc desc */
    @Schema(description = "排序类型 asc desc")
    private String sortOrder = "desc";
    /** */
    @Schema(description = "请求参数，留作公用")
    private String param;

    /** 拼团营销ID */
    @Schema(description = "拼团营销ID")
    private List<String> grouponMarketingIds;

    /** 1：昨天，2：最近七天；3：最近30天；4：按月统计 */
    @Schema(description = "1：昨天，2：最近七天；3：最近30天；4：按月统计", required = true)
    private StatisticsDataType statisticsDataType;

    @Schema(description = "自然月份 格式：2021-01")
    private String month;

    /** 平台类型 */
    @Schema(description = "平台类型")
    private Platform platform;

    /** 管理员id */
    @Schema(description = "管理员id")
    private String adminId;

    /** 订单附加数据 */
    @Schema(description = "订单附加数据")
    private String disabled;

    /** 是否购买第三方渠道 */
    @Schema(description = "是否购买第三方渠道")
    private Boolean buyAnyThirdChannelOrNot;

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;

    @Schema(description = "是否付费会员增长报表")
    private Boolean payMemberNewType;

    private Operator operator;
}
