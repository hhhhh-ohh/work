package com.wanmi.ares.report.wechatvideo.videocompanythirty.dao;

import com.wanmi.ares.report.wechatvideo.model.entity.WechatVideoCompanyThirty;
import com.wanmi.ares.request.wechatvideo.VideoQueryPageRequest;
import com.wanmi.ares.view.wechatvideo.VideoCompanyThirtyView;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 视频号订单公司维度30天统计
 * 
 * @author zhaiqiankun
 * @date 2022-04-08 09:17:38
 */
@Mapper
public interface VideoCompanyThirtyMapper {

    /**
     * 列表查询
     * @param request
     * @return
     */
    List<VideoCompanyThirtyView> getList(VideoQueryPageRequest request);

    void deleteByDate(LocalDate date);

    void batchAdd(List<WechatVideoCompanyThirty> wechatVideoCompanyThirties);
}
