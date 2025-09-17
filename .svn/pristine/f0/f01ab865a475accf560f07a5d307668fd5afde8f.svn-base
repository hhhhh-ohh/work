package com.wanmi.ares.report.wechatvideo.videocompanymonth.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanyMonth;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanyMonthView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频号订单公司维度月统计数据Mapper
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoCompanyMonthMapper {

    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoCompanyMonthView> getList(VideoQueryPageRequest request);

    void deleteByDate(@Param("year") Integer year,@Param("month")  Integer month);

    void batchAdd(List<WechatVideoCompanyMonth> wechatVideoCompanyThirties);
}
