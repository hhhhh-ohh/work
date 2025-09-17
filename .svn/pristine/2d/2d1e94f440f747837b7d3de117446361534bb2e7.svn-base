package com.wanmi.sbc.mq.report.entity;

import com.wanmi.ares.enums.ExportStatus;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.Data;

/**
 * @description 导出请求数据
 * @author  xuyunpeng
 * @date 2021/5/28 5:51 下午
 */
@Data
public class ExportData {
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

    private BoolFlag companyType;

    /**
     * 店铺id
     */
    private Long storeId;

    private StoreType storeType;

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
    private ReportType typeCd;

    /**
     * 导出状态(等待生成导出文件,导出文件生成中,导出文件生成完毕)
     */
    private ExportStatus exportStatus;

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
     * 页面请求参数
     */
    private String param;

    /**
     * 平台类型
     */
    private Platform platform;

    /**
     * 管理员id
     */
    private String adminId;

    /**
     * 是否导出子订单
     */
    private String disabled;

    /**
     * 是否购买第三方渠道
     */
    private Boolean buyAnyThirdChannelOrNot;

    private Operator operator;
}
