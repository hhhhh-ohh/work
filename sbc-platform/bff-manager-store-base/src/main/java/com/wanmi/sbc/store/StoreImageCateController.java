package com.wanmi.sbc.store;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.storeresourcecate.StoreResourceCateQueryProvider;
import com.wanmi.sbc.setting.api.provider.storeresourcecate.StoreResourceCateSaveProvider;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateAddRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckChildRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckResourceRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateDelByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateListRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateModifyRequest;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateAddResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateByIdResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateListResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateModifyResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceCateVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 店铺图片分类服务
 * 完全参考平台图片分类服务
 * Created by bail on 17/11/17.
 */
@Tag(name = "StoreImageCateController", description = "店铺图片分类服务 API")
@RestController
@Validated
public class StoreImageCateController {

    @Autowired
    private CommonUtil commonUtil;


    @Autowired
    private StoreResourceCateSaveProvider storeResourceCateSaveProvider;

    @Autowired
    private StoreResourceCateQueryProvider storeResourceCateQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 查询店铺图片分类
     * @return
     */
    @Operation(summary = "查询店铺图片分类")
    @RequestMapping(value = {"/store/imageCates"}, method = RequestMethod.GET)
    public ResponseEntity<List> list() {
        SystemResourceCateListRequest queryRequest = SystemResourceCateListRequest.builder()
                .storeId(commonUtil.getStoreId()).build();
        BaseResponse<SystemResourceCateListResponse> response = storeResourceCateQueryProvider.list
                (queryRequest);
        return ResponseEntity.ok(response.getContext().getSystemResourceCateVOList());
    }

    /**
     * 新增店铺图片分类
     */
    @Operation(summary = "新增店铺图片分类")
    @RequestMapping(value = "/store/imageCate", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody SystemResourceCateAddRequest addReq) {
        if (addReq == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        addReq.setCompanyInfoId(commonUtil.getCompanyInfoId());
        addReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<SystemResourceCateAddResponse> response = storeResourceCateSaveProvider.add(addReq);
        //记录操作日志
        if (Objects.nonNull(addReq.getCateParentId())) {
            operateLogMQUtil.convertAndSend("设置", "新增子分类", "新增子分类：" + addReq.getCateName());
        } else {
            operateLogMQUtil.convertAndSend("设置", "新增一级分类", "新增一级分类：" + addReq.getCateName());
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 编辑店铺图片分类
     */
    @Operation(summary = "编辑店铺图片分类")
    @RequestMapping(value = "/store/imageCate", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody SystemResourceCateModifyRequest modifyReq) {
        if (modifyReq == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询对应分类名称
        Optional<SystemResourceCateVO> cateInfoOptional = this.findCateInfoByCateId(modifyReq.getCateId());

        modifyReq.setStoreId(commonUtil.getStoreId());
        modifyReq.setCompanyInfoId(commonUtil.getCompanyInfoId());

        BaseResponse<SystemResourceCateModifyResponse> response = storeResourceCateSaveProvider.modify(modifyReq);

        //记录操作日志
        cateInfoOptional.ifPresent(map -> operateLogMQUtil.convertAndSend("设置", "编辑分类",
                "分类名称：" + map.getCateName() + " 改成 " + modifyReq.getCateName()));
        return response;
    }

    /**
     * 检测店铺图片分类是否有子类
     */
    @Operation(summary = "检测店铺图片分类是否有子类")
    @RequestMapping(value = "/store/imageCate/child", method = RequestMethod.POST)
    public BaseResponse checkChild(@RequestBody SystemResourceCateCheckChildRequest request) {
        if (request == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setStoreId(commonUtil.getStoreId());
        BaseResponse<Integer> response =storeResourceCateQueryProvider.checkChild(request);
        return response;
    }


    /**
     * 检测店铺图片分类是否已关联图片
     */
    @Operation(summary = "检测店铺图片分类是否已关联图片")
    @RequestMapping(value = "/store/imageCate/image", method = RequestMethod.POST)
    public BaseResponse checkResource(@RequestBody SystemResourceCateCheckResourceRequest request) {
        if (request == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setStoreId(commonUtil.getStoreId());
        BaseResponse<Integer> response =storeResourceCateQueryProvider.checkResource(request);
        return BaseResponse.success(response);
    }

    /**
     * 删除店铺图片分类
     */
    @Operation(summary = "删除店铺图片分类")
    @Parameter(name = "cateId", description = "分类Id", required = true)
    @RequestMapping(value = "/store/imageCate/{cateId}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询对应分类名称
        Optional<SystemResourceCateVO> cateInfoOptional = this.findCateInfoByCateId(cateId);

        SystemResourceCateDelByIdRequest delByIdReq = new SystemResourceCateDelByIdRequest();
        delByIdReq.setCateId(cateId);
        delByIdReq.setStoreId(commonUtil.getStoreId());
        BaseResponse response = storeResourceCateSaveProvider.delete(delByIdReq);

        //记录操作人日志
        cateInfoOptional.ifPresent(map -> operateLogMQUtil.convertAndSend("设置", "删除分类",
                "删除分类：" + map.getCateName()));
        return response;
    }

    /**
     * 获取所有的Cate
     *
     * @return
     */
    private Optional<SystemResourceCateVO> findCateInfoByCateId(Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        SystemResourceCateByIdRequest queryRequest = new SystemResourceCateByIdRequest();
        queryRequest.setCateId(cateId);
        BaseResponse<SystemResourceCateByIdResponse> response = storeResourceCateQueryProvider.getById(queryRequest);
        SystemResourceCateVO storeResourceCateVO = response.getContext().getSystemResourceCateVO();
        if (Objects.isNull(storeResourceCateVO)) {
            return Optional.empty();
        }
        return Optional.of(storeResourceCateVO);
    }
}
