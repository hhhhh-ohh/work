package com.wanmi.ares.report.wechatvideo.videoseven.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoSeven;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoSevenView;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 视频号维度最近7天统计Mapper
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoSevenMapper {

    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoSevenView> getList(VideoQueryPageRequest request);

    void deleteByDate(LocalDate date);


    void batchAdd(List<WechatVideoSeven> videoTradeDayList);
}
