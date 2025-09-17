package com.wanmi.sbc.system;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaSaveProvider;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyDelByIdRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyListBySellTypeRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelQueryRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaDelByExpressCompanyIdRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyByIdResponse;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyAddResponse;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyAddRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyListResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyRelWithNameListResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelWithNameVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanySaveProvider;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * S2B 平台端-物流公司管理
 * Created by bail on 2017/11/21.
 */
@Tag(name = "BossExpressCompanyController", description = "S2B 平台端-物流公司管理API")
@RestController
@Validated
@RequestMapping("/boss/expressCompany")
public class BossExpressCompanyController {

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private ExpressCompanySaveProvider expressCompanySaveProvider;

    @Autowired
    private StoreExpressCompanyRelaSaveProvider storeExpressCompanyRelaSaveProvider;

    @Autowired private ExpressCompanyThirdRelQueryProvider expressCompanyThirdRelQueryProvider;

    /**
     * S2B平台端 查询所有有效的物流公司列表
     *
     * @author bail
     */
    @Operation(summary = "S2B平台端 查询所有有效的物流公司列表")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List> allExpressCompanyList() {
        ExpressCompanyListResponse response = expressCompanyQueryProvider.list().getContext();
        return BaseResponse.success(response.getExpressCompanyVOList());
    }

    /**
     * S2B平台端 新增物流公司
     *
     */
    @Operation(summary = "S2B平台端 新增物流公司")
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse addExpressCompany(@Valid @RequestBody ExpressCompanyAddRequest addRequest) {
        addRequest.setDelFlag(DeleteFlag.NO);
        ExpressCompanyAddResponse response = expressCompanySaveProvider.add(addRequest).getContext();
        operateLogMQUtil.convertAndSend("设置", "新增物流公司", "新增物流公司：" + response.getExpressCompanyVO());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * S2B平台端 删除物流公司
     *
     * @param expressCompanyId
     */
    @Operation(summary = "S2B平台端 删除物流公司")
    @Parameter(name = "expressCompanyId", description = "物流公司id", required = true)
    @RequestMapping(value = "/{expressCompanyId}", method = RequestMethod.DELETE)
    public BaseResponse delExpressCompany(@PathVariable Long expressCompanyId) {
         if(StringUtils.isBlank(expressCompanyId.toString())){
             throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
         }

         ExpressCompanyByIdResponse expressCompanyByIdResponse =  expressCompanyQueryProvider.getById(
                 ExpressCompanyByIdRequest.builder()
                .expressCompanyId(expressCompanyId)
                .build()).getContext();
         if(Objects.isNull(expressCompanyByIdResponse.getExpressCompanyVO())){
            return BaseResponse.error("该物流公司不存在，检查是否已删除");
         }
         expressCompanySaveProvider.deleteById(ExpressCompanyDelByIdRequest
                .builder().expressCompanyId(expressCompanyId).build());

         //删除商家已绑定该物流信息的关系
        StoreExpressCompanyRelaDelByExpressCompanyIdRequest deleteRopRequest = new StoreExpressCompanyRelaDelByExpressCompanyIdRequest();
        deleteRopRequest.setExpressCompanyId(expressCompanyId);
        storeExpressCompanyRelaSaveProvider.deleteByExpressCompanyId(deleteRopRequest);

         //操作日志记录
         operateLogMQUtil.convertAndSend("设置", "删除物流公司", "删除物流公司：" + expressCompanyId);
         return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "查询快递公司映射")
    @PostMapping("/express-rel-list")
    public BaseResponse<ExpressCompanyRelWithNameListResponse> expressRelList(@RequestBody @Valid ExpressCompanyListBySellTypeRequest request) {
        // 查询平台物流快递公司
        List<ExpressCompanyVO> companyList = expressCompanyQueryProvider.list().getContext().getExpressCompanyVOList();
        if (CollectionUtils.isNotEmpty(companyList)) {
            // 转换为出参类型
            List<ExpressCompanyThirdRelWithNameVO> relWithNameList = KsBeanUtil.convert(companyList, ExpressCompanyThirdRelWithNameVO.class);
            // 聚合成平台物流ID列表
            List<Long> companyIds = companyList.stream().map(ExpressCompanyVO::getExpressCompanyId).collect(Collectors.toList());
            // 查询平台ID关联的微信快递公司
            List<ExpressCompanyThirdRelVO> thirdRelList = expressCompanyThirdRelQueryProvider.list(ExpressCompanyThirdRelQueryRequest.builder()
                    .sellPlatformType(request.getSellPlatformType())
                    .expressCompanyIdList(companyIds)
                    .delFlag(DeleteFlag.NO)
                    .build()).getContext().getThirdRelVOList();
            // 转换为平台物流名称Map
            Map<Long, Long> thirdExpressCompanyIdMap = thirdRelList.stream().collect(
                    Collectors.toMap(ExpressCompanyThirdRelVO::getExpressCompanyId, ExpressCompanyThirdRelVO::getThirdExpressCompanyId));
            relWithNameList.forEach(item -> {
                // 填充第三方平台物流ID
                item.setThirdExpressCompanyId(thirdExpressCompanyIdMap.getOrDefault(item.getExpressCompanyId(), null));
                item.setSellPlatformType(request.getSellPlatformType());
            });
            return BaseResponse.success(new ExpressCompanyRelWithNameListResponse(relWithNameList));
        }
        return BaseResponse.success(new ExpressCompanyRelWithNameListResponse(Collections.emptyList()));
    }
}
