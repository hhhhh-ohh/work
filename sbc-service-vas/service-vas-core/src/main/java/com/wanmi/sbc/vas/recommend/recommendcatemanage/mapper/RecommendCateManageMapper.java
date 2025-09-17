package com.wanmi.sbc.vas.recommend.recommendcatemanage.mapper;

import com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage.RecommendCateManageInfoListRequest;
import com.wanmi.sbc.vas.bean.vo.recommend.RecommendCateManageInfoVO;

import java.util.List;

public interface RecommendCateManageMapper {

    List<RecommendCateManageInfoVO> getRecommendCateInfoList(RecommendCateManageInfoListRequest request);
}
