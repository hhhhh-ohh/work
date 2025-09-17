package com.wanmi.sbc.dw.api.request;


import com.wanmi.sbc.dw.bean.enums.AnalysisValueEnum;
import com.wanmi.sbc.dw.bean.enums.ReportType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.wanmi.sbc.dw.bean.recommend.analysis.RecommendEffectAnalysisRequest @Description:
 * 推荐效果分析请求对象 @Author: 何军红 @Time: 2021/4/6
 */
@Schema
@Data
public class RecommendEffectAnalysisRequest extends DwBaseQueryRequest {

    /**
     * 终端类型
     */
    @Schema(description = "终端类型，1:PC，2:H5，3:APP，4:小程序")
    private Integer terminalType;

    /**
     * 坑位
     */
    @Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方")
    private Integer location;

    /**
     * 推荐策略
     */
    @Schema(description = "推荐策略  0：热门推荐；1：商品相关性推荐；2：基于用户兴趣推荐")
    private Integer recommendType;

    /**
     * 查询开始日期
     */
    @Schema(description = "查询开始日期")
    private String startDate;

    /**
     * 查询日期
     */
    @Schema(description = "查询结束日期")
    private String endDate;

    /**
     * day 天，week 周， month 月
     */
    @Schema(description = "结果展示时间纬度，day：按天，week：按周，month：按月")
    private String timeDimension;

    /**
     * 报表类型
     */
    @Schema(description = "报表类型，0:天报表 1:商品报表 ,2:类目报表,3:品牌")
    private ReportType reportsType;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段:0:曝光人数,1:曝光商品数,2:曝光量,3:点击量,4:点击人数,5:点击率,6:平均点击次数,"
                            + "7:加购次数,8:加购人,9:下单笔数,10:下单人数,11：付款笔数,12：付款人数,13：付款金额,14:下单付款转化率,15:曝光-付款展示率")
    private AnalysisValueEnum orderField;

    /**
     * 搜索的内容
     */
    @Schema(description = "搜索框的传入数据")
    private String searcher;

    /**
     * 搜索的类目级别
     */
    @Schema(description = "1 ：一级,2：二级,3:三级")
    private Integer cateGrade;

    /**
     * 搜索时前端传进来的类目转为集合
     */
    @Schema(description = "cateId集合")
    private List<Long> cateIds;
}
