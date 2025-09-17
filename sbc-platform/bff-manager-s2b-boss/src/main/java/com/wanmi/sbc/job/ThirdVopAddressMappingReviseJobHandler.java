package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.vop.address.VopAddressProvider;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description VOP地址映射[修正]
 * @author malianfeng
 * @date 2023/2/22 15:42
 */
@Component
@Slf4j
public class ThirdVopAddressMappingReviseJobHandler {

    @Autowired private VopAddressProvider vopAddressProvider;

    @Autowired private CommonUtil commonUtil;

    @XxlJob(value = "thirdVopAddressMappingReviseJobHandler")
    public void execute(String param) throws Exception {
        init();
    }

    /**
     * @description VOP地址映射[修正]
     * @author malianfeng
     * @date 2023/2/22 15:41
     * @return void
     */
    public void init() {
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP);
        if (flag) {
            XxlJobHelper.log("VOP地址映射[修正]执行 " + LocalDateTime.now());
            vopAddressProvider.mappingRevise();
        }
    }
}
