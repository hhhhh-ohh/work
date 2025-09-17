package com.wanmi.ares.report.wechatvideo.videocompanyseven.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanySeven;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanySevenView;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 视频号订单公司维度7天统计
 * 
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoCompanySevenMapper {

    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoCompanySevenView> getList(VideoQueryPageRequest request);

    void batchAdd(List<WechatVideoCompanySeven> videoTradeDayList);

    void deleteByDate(LocalDate date);
}
