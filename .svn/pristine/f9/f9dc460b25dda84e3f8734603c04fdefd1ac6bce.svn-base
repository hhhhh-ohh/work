package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.empower.api.provider.sellplatform.cate.PlatformCateProvider;
import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.api.response.sellplatform.cate.PlatformCateResponse;
import com.wanmi.sbc.goods.api.provider.thirdgoodscate.ThirdGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.UpdateAllRequest;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;
import com.wanmi.sbc.util.CommonUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步微信商品类目
 */
@Component
public class WechatCateSyncHandler {
    @Autowired
    private PlatformCateProvider platformCateProvider;

    @Autowired
    private ThirdGoodsCateProvider thirdGoodsCateProvider;

    @Autowired
    private CommonUtil commonUtil;


    @XxlJob(value = "WechatCateSyncHandler")
    public void execute() throws Exception {
        if (!commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            return;
        }
        ThirdBaseRequest request = new ThirdBaseRequest();
        request.setSellPlatformType(SellPlatformType.WECHAT_VIDEO);
        List<PlatformCateResponse> cateResponseList = platformCateProvider.queryCate(request).getContext();
        if (CollectionUtils.isNotEmpty(cateResponseList)) {
            //三级类目
            ArrayList<ThirdGoodsCateDTO> thirdList = new ArrayList<>();
            //二级类目
            ArrayList<ThirdGoodsCateDTO> secondList = new ArrayList<>();
            //一级类目
            ArrayList<ThirdGoodsCateDTO> oneList = new ArrayList<>();
            for (PlatformCateResponse wxChannelsCateResponse : cateResponseList) {
                ThirdGoodsCateDTO third = new ThirdGoodsCateDTO();
                third.setCateId(Long.valueOf(wxChannelsCateResponse.getThird_cat_id()));
                third.setCateGrade(3);
                third.setCateName(wxChannelsCateResponse.getThird_cat_name());
                third.setCateParentId(Long.valueOf(wxChannelsCateResponse.getSecond_cat_id()));
                third.setCatePath("0|" + wxChannelsCateResponse.getFirst_cat_id() + "|" + wxChannelsCateResponse.getSecond_cat_id());
                third.setQualification(wxChannelsCateResponse.getQualification());
                third.setQualificationType(wxChannelsCateResponse.getQualification_type());
                third.setThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO);
                third.setProductQualificationType(Integer.valueOf(wxChannelsCateResponse.getProduct_qualification_type()));
                third.setProductQualification(wxChannelsCateResponse.getProduct_qualification());
                thirdList.add(third);
                ThirdGoodsCateDTO second = new ThirdGoodsCateDTO();
                second.setCateId(Long.valueOf(wxChannelsCateResponse.getSecond_cat_id()));
                second.setCateGrade(2);
                second.setCateName(wxChannelsCateResponse.getSecond_cat_name());
                second.setCateParentId(Long.valueOf(wxChannelsCateResponse.getFirst_cat_id()));
                second.setCatePath("0|" + wxChannelsCateResponse.getFirst_cat_id());
                second.setThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO);
                secondList.add(second);
                ThirdGoodsCateDTO one = new ThirdGoodsCateDTO();
                one.setCateId(Long.valueOf(wxChannelsCateResponse.getFirst_cat_id()));
                one.setCateGrade(1);
                one.setCateName(wxChannelsCateResponse.getFirst_cat_name());
                one.setCateParentId(0L);
                one.setCatePath("0");
                one.setThirdPlatformType(ThirdPlatformType.WECHAT_VIDEO);
                oneList.add(one);
            }
            thirdList.addAll(secondList.stream().distinct().collect(Collectors.toList()));
            thirdList.addAll(oneList.stream().distinct().collect(Collectors.toList()));
            thirdGoodsCateProvider.updateAll(new UpdateAllRequest(thirdList));
        }
    }
}
