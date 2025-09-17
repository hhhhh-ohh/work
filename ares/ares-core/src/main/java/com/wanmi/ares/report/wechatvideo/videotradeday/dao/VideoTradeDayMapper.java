package com.wanmi.ares.report.wechatvideo.videotradeday.dao;

import com.wanmi.ares.report.base.dao.MyBatisBaseDao;
import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoTradeDay;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.request.wechatvideo.VideoOverviewQueryRequest;
import com.wanmi.ares.view.wechatvideo.VideoTradeDayView;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 视频号维度每天统计Mapper
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoTradeDayMapper extends MyBatisBaseDao<WechatVideoTradeDay, Long> {
    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoTradeDayView> getList(VideoQueryPageRequest request);

    /**
     * 统计数量
     * @param request
     * @return
     */
    long total(VideoQueryPageRequest request);

    void deleteByDate(LocalDate yesterday);

    void batchAdd(List<WechatVideoTradeDay> wechatVideoTradeDay);

    /**
     * 汇总销售概况
     * @param request
     * @return
     */
    VideoTradeDayView summary(VideoQueryPageRequest request);
}
