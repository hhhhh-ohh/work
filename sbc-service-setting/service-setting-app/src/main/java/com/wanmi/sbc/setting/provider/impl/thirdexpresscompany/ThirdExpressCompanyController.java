package com.wanmi.sbc.setting.provider.impl.thirdexpresscompany;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.thirdexpresscompany.ThirdExpressCompanyProvider;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyAddRequest;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyBatchAddRequest;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;
import com.wanmi.sbc.setting.thirdexpresscompany.service.ThirdExpressCompanyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 第三方平台物流公司保存服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@RestController
@Validated
public class ThirdExpressCompanyController implements ThirdExpressCompanyProvider {

    @Autowired private ThirdExpressCompanyService thirdExpressCompanyService;

    @Override
    public BaseResponse batchAdd(@Valid @RequestBody ThirdExpressCompanyBatchAddRequest request) {
        List<ThirdExpressCompanyAddRequest> thirdExpressCompanyList = request.getThirdExpressCompanyList();
        if (CollectionUtils.isNotEmpty(thirdExpressCompanyList)) {
            List<ThirdExpressCompany> thirdExpressCompanies = new ArrayList<>();
            thirdExpressCompanyList.forEach(item -> {
                // 构造实体对象
                ThirdExpressCompany thirdExpressCompany = ThirdExpressCompany.builder()
                        .expressName(item.getExpressName())
                        .expressCode(item.getExpressCode())
                        .sellPlatformType(item.getSellPlatformType())
                        .delFlag(DeleteFlag.NO)
                        .createTime(LocalDateTime.now()).build();
                thirdExpressCompanies.add(thirdExpressCompany);
            });
            // 批量保存
            thirdExpressCompanyService.addBatch(thirdExpressCompanies);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
