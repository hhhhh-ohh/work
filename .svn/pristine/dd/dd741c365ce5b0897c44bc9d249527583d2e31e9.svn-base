package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaQueryProvider;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyListBySellTypeRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelQueryRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaListRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyListResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyListBySellTypeResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyRelWithNameListResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaListResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelWithNameVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺-物流公司Controller
 * Created by bail on 2017/12/14.
 */
@Tag(name = "StoreExpressCompanyBaseController", description = "店铺-物流公司 API")
@RestController
@Validated
public class StoreExpressCompanyBaseController {


    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private StoreExpressCompanyRelaQueryProvider storeExpressCompanyRelaQueryProvider;

    @Autowired private ExpressCompanyThirdRelQueryProvider expressCompanyThirdRelQueryProvider;

    /**
     * 查询店铺正在使用的物流公司
     * @return
     */
    @Operation(summary = "查询店铺正在使用的物流公司")
    @Parameter(name = "storeId", description = "店铺Id", required = true)
    @RequestMapping(value = "/store/expressCompany/{storeId}", method = RequestMethod.GET)
    public BaseResponse<List> findCheckedExpressCompanies(@PathVariable("storeId") Long storeId) {

        StoreExpressCompanyRelaListRequest queryRopRequest = new StoreExpressCompanyRelaListRequest();
        queryRopRequest.setStoreId(storeId);
        StoreExpressCompanyRelaListResponse response = storeExpressCompanyRelaQueryProvider.list(queryRopRequest).getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(response.getStoreExpressCompanyRelaVOList());
//        StoreQueryRopRequest queryRopRequest = new StoreQueryRopRequest();
//        queryRopRequest.setStoreId( storeId);
//
//        CompositeResponse<List> response = sdkClient.buildClientRequest().post( queryRopRequest,List.class, "storeExpCom.listChecked", "1.0.0");
//        return BaseResponse.success( response.getSuccessResponse());
    }

    /**
     * pc/h5修改退货物流公司为平台配置物流
     * @return
     */
    @Operation(summary = "pc/h5修改退货物流公司为平台配置物流")
    @GetMapping(value = "/boss/expressCompany")
    public BaseResponse<List> allExpressCompanyList() {
        ExpressCompanyListResponse response = expressCompanyQueryProvider.list().getContext();
        //如果是个空对象集合
        if (response.getExpressCompanyVOList().get(0).getExpressCompanyId() == null) {
            return BaseResponse.success(new ArrayList());
        }
        return BaseResponse.success(response.getExpressCompanyVOList());
//        CompositeResponse<List> response = sdkClient.buildClientRequest().post(List.class, "platformExpressCom.list", "1.0.0");
//        return BaseResponse.success( response.getSuccessResponse());
    }

    /**
     * pc/h5修改退货物流公司为平台配置物流
     * @return
     */
    @Operation(summary = "根据代销类型平台类型查询 可用物流公司")
    @PostMapping(value = "/boss/expressCompany/bySell")
    public BaseResponse<List> allExpressCompanyListBySell(@RequestBody @Valid ExpressCompanyListBySellTypeRequest request) {
        ExpressCompanyListBySellTypeResponse response = expressCompanyThirdRelQueryProvider.getExpressCompanyBySellType(request).getContext();
        //如果是个空对象集合
        if (response.getExpressCompanyVOList().get(0).getExpressCompanyId() == null) {
            return BaseResponse.success(new ArrayList());
        }
        return BaseResponse.success(response.getExpressCompanyVOList());
    }


    @Operation(summary = "查询快递公司映射")
    @PostMapping("/boss/express-rel-list")
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
