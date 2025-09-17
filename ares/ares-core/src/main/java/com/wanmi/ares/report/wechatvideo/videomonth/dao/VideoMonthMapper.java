package com.wanmi.ares.report.wechatvideo.videomonth.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoMonth;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoMonthView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频号维度自然月统计Mapper
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoMonthMapper {

    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoMonthView> getList(VideoQueryPageRequest request);

    void deleteByDate(@Param("year") Integer year, @Param("month")  Integer month);

    void batchAdd(List<WechatVideoMonth> wechatVideoMonths);
}
