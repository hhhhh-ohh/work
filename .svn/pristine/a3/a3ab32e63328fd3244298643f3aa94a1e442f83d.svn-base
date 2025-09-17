package com.wanmi.ares.report.wechatvideo.videothird.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoThirty;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoThirtyView;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 视频号维度每30天统计Mapper
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoThirtyMapper {
    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoThirtyView> getList(VideoQueryPageRequest request);

    void deleteByDate(LocalDate date);

    void batchAdd(List<WechatVideoThirty> videoTradeDayList);
}
