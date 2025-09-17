package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.expresscompany.ExpressCompanyQueryProvider;
import com.wanmi.sbc.setting.api.provider.expresscompanythirdrel.ExpressCompanyThirdRelQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaSaveProvider;
import com.wanmi.sbc.setting.api.request.StoreExpressRequest;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyByIdRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyListBySellTypeRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaAddRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaByIdRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaDelByIdRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaListRequest;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyByIdResponse;
import com.wanmi.sbc.setting.api.response.expresscompany.ExpressCompanyListResponse;
import com.wanmi.sbc.setting.api.response.expresscompanythirdrel.ExpressCompanyListBySellTypeResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaListResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressRelaRopResponse;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.setting.bean.vo.StoreExpressCompanyRelaVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 店铺-物流公司配置Controller
 * Created by bail on 2017/11/20.
 */
@Tag(name = "StoreExpressCompanyController", description = "店铺-物流公司配置 API")
@RestController
@Validated
@RequestMapping("/store/expressCompany")
public class StoreExpressCompanyController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExpressCompanyQueryProvider expressCompanyQueryProvider;

    @Autowired
    private StoreExpressCompanyRelaQueryProvider storeExpressCompanyRelaQueryProvider;

    @Autowired
    private StoreExpressCompanyRelaSaveProvider storeExpressCompanyRelaSaveProvider;

    @Autowired private ExpressCompanyThirdRelQueryProvider expressCompanyThirdRelQueryProvider;


    /**
     * 查询所有有效的物流公司列表
     * @author bail
     */
    @Operation(summary = "查询所有有效的物流公司列表")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public BaseResponse<List> allExpressCompanyList() {
        ExpressCompanyListResponse response = expressCompanyQueryProvider.list().getContext();
        //如果是个空对象集合
        if (response.getExpressCompanyVOList().get(0).getExpressCompanyId() == null) {
            return BaseResponse.success(new ArrayList());
        }
        return BaseResponse.success(response.getExpressCompanyVOList());
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/4/28 11:09
     * @return
     **/
    @Operation(summary = "根据代销类型平台类型查询 可用物流公司")
    @RequestMapping(value = "/allBySell", method = RequestMethod.POST)
    public BaseResponse<List> allExpressCompanyListBySell(@RequestBody @Valid ExpressCompanyListBySellTypeRequest request) {
        ExpressCompanyListBySellTypeResponse response = expressCompanyThirdRelQueryProvider.getExpressCompanyBySellType(request).getContext();
        //如果是个空对象集合
        if (response.getExpressCompanyVOList().get(0).getExpressCompanyId() == null) {
            return BaseResponse.success(new ArrayList());
        }
        return BaseResponse.success(response.getExpressCompanyVOList());
    }

    /**
     * 查询店铺正在使用的物流公司
     * @return
     */
    @Operation(summary = "查询店铺正在使用的物流公司")
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List> findCheckedExpressCompanies() {
        StoreExpressCompanyRelaListRequest queryRopRequest = new StoreExpressCompanyRelaListRequest();
        queryRopRequest.setStoreId(commonUtil.getStoreId());
        StoreExpressCompanyRelaListResponse response = storeExpressCompanyRelaQueryProvider.list(queryRopRequest).getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(null);
        }
        return BaseResponse.success(response.getStoreExpressCompanyRelaVOList());
    }

    /**
     * 查询店铺正在使用的物流公司
     * @return
     */
    @Operation(summary = "查询店铺正在使用的物流公司")
    @RequestMapping(value = "/bySell",method = RequestMethod.POST)
    public BaseResponse<List> findCheckedExpressCompaniesBySell(@RequestBody @Valid ExpressCompanyListBySellTypeRequest request) {
        StoreExpressCompanyRelaListRequest queryRopRequest = new StoreExpressCompanyRelaListRequest();
        queryRopRequest.setStoreId(commonUtil.getStoreId());
        StoreExpressCompanyRelaListResponse response = storeExpressCompanyRelaQueryProvider.list(queryRopRequest).getContext();
        if (Objects.isNull(response)) {
            return BaseResponse.success(null);
        }
        List<Long> expressCompanyIdList = response.getStoreExpressCompanyRelaVOList().stream().map(StoreExpressCompanyRelaVO::getExpressCompanyId).collect(Collectors.toList());
        request.setExpressCompanyId(expressCompanyIdList);
        ExpressCompanyListBySellTypeResponse bySellResponse = expressCompanyThirdRelQueryProvider.getExpressCompanyBySellType(request).getContext();
        if (Objects.isNull(bySellResponse) || CollectionUtils.isEmpty(bySellResponse.getExpressCompanyVOList())) {
            return BaseResponse.success(null);
        }
        List<Long> finalExpressCompanyIdList = bySellResponse.getExpressCompanyVOList().stream().map(ExpressCompanyVO :: getExpressCompanyId).collect(Collectors.toList());
        List<StoreExpressCompanyRelaVO> storeExpressCompanyRelaVOList = response.getStoreExpressCompanyRelaVOList().stream().filter(vo-> finalExpressCompanyIdList.contains(vo.getExpressCompanyId())).collect(Collectors.toList());
        return BaseResponse.success(storeExpressCompanyRelaVOList);
    }

    /**
     * 为店铺设置需要使用的物流公司们
     * @param queryRequest
     */
    @Operation(summary = "为店铺设置需要使用的物流公司")
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<StoreExpressRelaRopResponse> saveStoreExpressCompany(@RequestBody StoreExpressRequest queryRequest) {
        StoreExpressCompanyRelaAddRequest request = new StoreExpressCompanyRelaAddRequest();
        if (StringUtils.isBlank(queryRequest.getExpressCompanyId().toString())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setStoreId(commonUtil.getStoreId());
        request.setCompanyInfoId(Integer.valueOf(commonUtil.getCompanyInfoId().toString()));
        request.setExpressCompanyId(queryRequest.getExpressCompanyId());
        return BaseResponse.success(storeExpressCompanyRelaSaveProvider.add(request).getContext());
    }

    /**
     * 删除正在使用的某个物流公司
     * @param id
     */
    @Operation(summary = "删除正在使用的某个物流公司")
    @Parameters({
            @Parameter(name = "id", description = "店铺物流Id", required = true),
            @Parameter(name = "expressCompanyId", description = "物流公司Id", required = true)
    })
    @RequestMapping(value = "/{id}/{expressCompanyId}", method = RequestMethod.DELETE)
    public BaseResponse deleteStoreExpressCompany(@PathVariable("id") Long id,
                                                  @PathVariable("expressCompanyId") Long expressCompanyId) {

        if (StringUtils.isBlank(id.toString()) || StringUtils.isBlank(expressCompanyId.toString())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        StoreExpressCompanyRelaByIdRequest request = new StoreExpressCompanyRelaByIdRequest();
        request.setId(id);
        //如果查询不到，抛出异常
        if (Objects.isNull(storeExpressCompanyRelaQueryProvider.getById(request).getContext())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //做删除
        StoreExpressCompanyRelaDelByIdRequest deleteRopRequest = new StoreExpressCompanyRelaDelByIdRequest();
        deleteRopRequest.setId(id);
        storeExpressCompanyRelaSaveProvider.deleteById(deleteRopRequest);

        //输出操作日志
        ExpressCompanyByIdRequest expressCompanyByIdRequest = new ExpressCompanyByIdRequest();
        expressCompanyByIdRequest.setExpressCompanyId(expressCompanyId);
        ExpressCompanyByIdResponse response = expressCompanyQueryProvider.getById(expressCompanyByIdRequest).getContext();

        operateLogMQUtil.convertAndSend("设置", "取消物流公司", "取消物流公司：" + response.getExpressCompanyVO().getExpressName());
        return BaseResponse.SUCCESSFUL();
    }
}
