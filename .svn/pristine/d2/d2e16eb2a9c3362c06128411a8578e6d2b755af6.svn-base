package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.vop.address.VopAddressProvider;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * VOP地址增量映射
 * 只对VOP未映射成功的数据再次尝试映射
 *
 * @author wur
 * @date: 2021/5/12 16:49
 */
@Component
@Slf4j
public class ThirdVopAddressMappingJobHandler {

    @Autowired
    private VopAddressProvider vopAddressProvider;

    @Autowired
    private CommonUtil commonUtil;

    @XxlJob(value = "thirdVopAddressMappingJobHandler")
    public void execute() throws Exception {
        init();
    }

    /**
     * VOP地址增量映射
     *
     * @return 无
     * @author wur
     * @date: 2021/5/12 16:51
     */
    public void init() {
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_VOP);
        if (flag) {
            XxlJobHelper.log("VOP地址初始化自动映射执行 " + LocalDateTime.now());
            vopAddressProvider.mapping();
        }
    }
}
