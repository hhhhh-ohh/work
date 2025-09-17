package com.wanmi.sbc.job.linkedmall;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelCateQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGetAllCateRequest;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsCateVO;
import com.wanmi.sbc.goods.api.provider.thirdgoodscate.ThirdGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.UpdateAllRequest;
import com.wanmi.sbc.goods.bean.dto.ThirdGoodsCateDTO;
import com.wanmi.sbc.util.CommonUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全量同步linkedmall商品类目
 */
@Component
@Slf4j
public class LinkedMallGoodsCateSyncHandler {
    @Autowired
    private ChannelCateQueryProvider channelCateQueryProvider;

    @Autowired
    private ThirdGoodsCateProvider thirdGoodsCateProvider;

    @Autowired
    private CommonUtil commonUtil;

    @XxlJob(value = "LinkedMallGoodsCateSyncHandler")
    public void execute() throws Exception {
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        if(!flag) {
            return;
        }
        ChannelGetAllCateRequest getAllCateRequest = ChannelGetAllCateRequest.builder().channelType(ThirdPlatformType.LINKED_MALL).build();
        List<ChannelGoodsCateVO> linkedMallGoodsCateVOS = channelCateQueryProvider.getAllCate(getAllCateRequest).getContext().getChannelGoodsCateVOList();
        List<ThirdGoodsCateDTO> thirdGoodsCateDTOS = KsBeanUtil.convert(linkedMallGoodsCateVOS, ThirdGoodsCateDTO.class);
        UpdateAllRequest updateAllRequest = new UpdateAllRequest();
        updateAllRequest.setThirdGoodsCateDTOS(thirdGoodsCateDTOS);
        thirdGoodsCateProvider.updateAll(updateAllRequest);
    }
}
