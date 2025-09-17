package com.wanmi.sbc.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.NoDeleteStoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.freight.*;
import com.wanmi.sbc.goods.api.request.freight.*;
import com.wanmi.sbc.goods.api.response.freight.FreightTemplateGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.freight.FreightTemplateGoodsExpressByIdResponse;
import com.wanmi.sbc.goods.api.response.freight.FreightTemplateStoreByIdResponse;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsVO;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateStoreVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * 运费模板控制器
 * Created by sunkun on 2018/5/2.
 */
@Tag(name = "FreightTemplateController", description = "运费模板服务API")
@RestController
@Validated
@RequestMapping("/freightTemplate")
public class FreightTemplateController {

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private FreightTemplateGoodsProvider freightTemplateGoodsProvider;

    @Autowired
    private FreightTemplateStoreProvider freightTemplateStoreProvider;

    @Autowired
    private FreightTemplateStoreQueryProvider freightTemplateStoreQueryProvider;

    @Autowired
    private FreightTemplateGoodsExpressQueryProvider freightTemplateGoodsExpressQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 查询店铺下所有单品运费模板
     *
     * @return
     */
    @Operation(summary = "查询店铺下所有单品运费模板")
    @RequestMapping(value = "/freightTemplateGoods", method = RequestMethod.GET)
    public BaseResponse<List<FreightTemplateGoodsVO>> getFreightTemplateGoodsAll() {
        Long storeId = commonUtil.getStoreId();
        if (storeId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<FreightTemplateGoodsVO> voList = freightTemplateGoodsQueryProvider.listByStoreId(
                FreightTemplateGoodsListByStoreIdRequest.builder().storeId(storeId).build()
        ).getContext().getFreightTemplateGoodsVOList();

        return BaseResponse.success(voList);
    }

    /**
     * 查询单品运费模板
     *
     * @param freightTempId
     * @return
     */
    @Operation(summary = "查询单品运费模板")
    @Parameter(name = "freightTempId", description = "运费模板Id",
            required = true)
    @RequestMapping(value = "/freightTemplateGoods/{freightTempId}", method = RequestMethod.GET)
    public BaseResponse<FreightTemplateGoodsVO> getFreightTemplateGoodsById(@PathVariable Long freightTempId) {
        FreightTemplateGoodsVO freightTemplateGoods = freightTemplateGoodsQueryProvider.getById(
                FreightTemplateGoodsByIdRequest.builder().freightTempId(freightTempId).build()
        ).getContext();
        // -1表示为虚拟商品，使用虚拟商品运费模板
        if(!Objects.equals(-1L,freightTempId)) {
            NoDeleteStoreByIdResponse storeByIdResponse = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                    .storeId(freightTemplateGoods.getStoreId()).build()).getContext();
            StoreVO storeVO = storeByIdResponse.getStoreVO();
            if ((!Objects.equals(freightTemplateGoods.getStoreId(), commonUtil.getStoreId())&& !(StoreType.PROVIDER == storeVO.getStoreType()))
                    ||(!Objects.equals(freightTemplateGoods.getStoreId(), commonUtil.getStoreId())
                    && commonUtil.getStoreType() == storeVO.getStoreType()
                    && StoreType.PROVIDER == commonUtil.getStoreType())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return BaseResponse.success(freightTemplateGoods);
    }

    /**
     * 更新单品运费模板
     *
     * @return
     */
    @Operation(summary = "更新单品运费模板")
    @RequestMapping(value = "/freightTemplateGoods", method = RequestMethod.POST)
    public BaseResponse renewalFreightTemplateGoods(@Valid @RequestBody FreightTemplateGoodsSaveRequest freightTemplateGoodsSaveRequest) {
        if (commonUtil.getStoreId() == null || commonUtil.getCompanyInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        freightTemplateGoodsSaveRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        freightTemplateGoodsSaveRequest.setStoreId(commonUtil.getStoreId());
        if (isNull(freightTemplateGoodsSaveRequest.getFreightTempId())) {
            operateLogMQUtil.convertAndSend("设置", "新增单品运费模板",
                    "新增单品运费模板：" + freightTemplateGoodsSaveRequest.getFreightTempName());
        } else {
            operateLogMQUtil.convertAndSend("设置", "编辑单品运费模板",
                    "编辑单品运费模板：" + freightTemplateGoodsSaveRequest.getFreightTempName());
        }
        return freightTemplateGoodsProvider.save(freightTemplateGoodsSaveRequest);

    }

    /**
     * 更新店铺运费模板
     *
     * @param request
     * @return
     */
    @Operation(summary = "更新店铺运费模板")
    @RequestMapping(value = "/freightTemplateStore", method = RequestMethod.POST)
    public BaseResponse renewalFreightTemplateStore(@Valid @RequestBody FreightTemplateStoreSaveRequest request) {
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setStoreId(commonUtil.getStoreId());

        if (isNull(request.getFreightTempId())) {
            operateLogMQUtil.convertAndSend("设置", "新增店铺运费模板",
                    "新增店铺运费模板：" + request.getFreightTempName());
        } else {
            operateLogMQUtil.convertAndSend("设置", "编辑店铺运费模板",
                    "编辑店铺运费模板：" + request.getFreightTempName());
        }

        return freightTemplateStoreProvider.save(request);
    }

    /**
     * 根据id获取店铺运费模板
     *
     * @param freightTempId
     * @return
     */
    @Operation(summary = "根据id获取店铺运费模板")
    @Parameter(name = "freightTempId", description = "运费模板Id",
            required = true)
    @RequestMapping(value = "/freightTemplateStore/{freightTempId}", method = RequestMethod.GET)
    public BaseResponse<FreightTemplateStoreVO> getFreightTemplateStore(@PathVariable Long freightTempId) {
        FreightTemplateStoreVO freightTemplateStore = freightTemplateStoreQueryProvider.getById(
                FreightTemplateStoreByIdRequest.builder().freightTempId(freightTempId).build()
        ).getContext();
        Long storeId = commonUtil.getStoreId();
        if (!Objects.equals(freightTemplateStore.getStoreId(), storeId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        freightTemplateStore.setSelectedAreas(freightTemplateStoreQueryProvider.queryAreaIdsByIdAndStoreId(
                FreightTemplateStoreAreaIdByIdAndStoreIdRequest.builder()
                        .freightTempId(freightTempId).storeId(storeId).build()
        ).getContext().getAreaIds());
        return BaseResponse.success(freightTemplateStore);
    }


    /**
     * 查询店铺运费模板已选区域
     *
     * @return
     */
    @Operation(summary = "查询店铺运费模板已选区域")
    @RequestMapping(value = "/freightTemplateStore/selected/area", method = RequestMethod.GET)
    public BaseResponse<List<Long>> getFreigthTemplateStoreSelectedArea() {
        if (commonUtil.getStoreId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return BaseResponse.success(freightTemplateStoreQueryProvider.queryAreaIdsByIdAndStoreId(
                FreightTemplateStoreAreaIdByIdAndStoreIdRequest.builder()
                        .freightTempId(0L).storeId(commonUtil.getStoreId()).build()
        ).getContext().getAreaIds());
    }

    /**
     * 查询店铺下所有店铺运费模板
     *
     * @return
     */
    @Operation(summary = "查询店铺下所有店铺运费模板")
    @RequestMapping(value = "/freightTemplateStore/list", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<FreightTemplateStoreVO>> getFreightTemplateStoreAll(@RequestBody FreightTemplateStorePageRequest request) {
        Long storeId = commonUtil.getStoreId();
        if (storeId == null || commonUtil.getCompanyInfoId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setStoreId(storeId);

        MicroServicePage<FreightTemplateStoreVO> page =
                freightTemplateStoreQueryProvider.page(request).getContext().getFreightTemplateStorePage();
        if (request.getPageNum() > 0 && CollectionUtils.isEmpty(page.getContent())) {
            request.setPageNum(0);
            page = freightTemplateStoreQueryProvider.page(request).getContext().getFreightTemplateStorePage();
        }
        return BaseResponse.success(page);
    }

    /**
     * 根据主键删除单品运费模板
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据主键删除单品运费模板")
    @Parameter(name = "id", description = "运费模板Id", required = true)
    @RequestMapping(value = "/freightTemplateGoods/{id}", method = RequestMethod.DELETE)
    public BaseResponse delFreightTemplateGoods(@PathVariable Long id) {
        String name = getFreightTemplateGoodsName(id);

        operateLogMQUtil.convertAndSend("设置", "删除单品运费模板",
                "删除单品运费模板：" + name);
        FreightTemplateGoodsDeleteByIdAndStoreIdRequest request = new FreightTemplateGoodsDeleteByIdAndStoreIdRequest();
        request.setFreightTempId(id);
        request.setStoreId(commonUtil.getStoreId());
        // 如果是门店，改变路由规则
        if (commonUtil.getOperator().getPlatform() == Platform.STOREFRONT){
            request.setPluginType(PluginType.O2O);
        }
        return freightTemplateGoodsProvider.deleteByIdAndStoreId(request);
    }

    /**
     * 复制单品运费模板
     *
     * @param id
     * @return
     */
    @Operation(summary = "复制单品运费模板")
    @Parameter(name = "id", description = "运费模板Id", required = true)
    @RequestMapping(value = "/freightTemplateGoods/{id}", method = RequestMethod.PUT)
    public BaseResponse copyFreightTemplateGoods(@PathVariable Long id) {

        operateLogMQUtil.convertAndSend("设置", "复制单品运费模板",
                "复制单品运费模板：" + getFreightTemplateGoodsName(id));
        return freightTemplateGoodsProvider.copyByIdAndStoreId(
                FreightTemplateGoodsCopyByIdAndStoreIdRequest.builder()
                        .freightTempId(id)
                        .storeId(commonUtil.getStoreId()).build());
    }

    /**
     * 根据主键删除店铺运费模板
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据主键删除店铺运费模板")
    @Parameter(name = "id", description = "运费模板Id", required = true)
    @RequestMapping(value = "/freightTemplateStore/{id}", method = RequestMethod.DELETE)
    public BaseResponse delFreightTemplateStore(@PathVariable Long id) {
        FreightTemplateStoreByIdResponse store =
                freightTemplateStoreQueryProvider.getById(FreightTemplateStoreByIdRequest.builder().freightTempId(id).build()).getContext();
        operateLogMQUtil.convertAndSend("设置", "删除店铺运费模板",
                "删除店铺运费模板：" + (Objects.nonNull(store) ? store.getFreightTempName() : ""));

        BaseResponse response = freightTemplateStoreProvider.deleteByIdAndStoreId(
                FreightTemplateStoreDeleteByIdAndStoreIdRequest.builder()
                        .freightTempId(id).storeId(commonUtil.getStoreId()).build());
        return response;
    }

    @Operation(summary = "根据主键查询单品运费模板")
    @Parameter(name = "freightTempId", description = "运费模板Id", required = true)
    @RequestMapping(value = "/freightTemplateExpress/{freightTempId}", method = RequestMethod.GET)
    public BaseResponse<FreightTemplateGoodsExpressByIdResponse> findByFreightTempIdAndDelFlag(@PathVariable Long freightTempId) {
        BaseResponse<FreightTemplateGoodsExpressByIdResponse> baseResponse =
                freightTemplateGoodsExpressQueryProvider.getById(new FreightTemplateGoodsExpressByIdRequest(freightTempId));
        return BaseResponse.success(baseResponse.getContext());
    }

    /**
     * 公共方法获取运费模板名称
     *
     * @param id
     * @return
     */
    private String getFreightTemplateGoodsName(Long id) {
        FreightTemplateGoodsByIdResponse goodsByIdResponse =
                freightTemplateGoodsQueryProvider.getById(FreightTemplateGoodsByIdRequest.builder().freightTempId(id).build()).getContext();
        return Objects.nonNull(goodsByIdResponse) ? goodsByIdResponse.getFreightTempName() : " ";
    }
}
