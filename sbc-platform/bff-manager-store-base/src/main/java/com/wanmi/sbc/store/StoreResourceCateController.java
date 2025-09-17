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

/**
 * 店铺素材分类服务
 * 完全参考平台素材分类服务
 * Created by yinxianzhi on 18/10/18.
 */
@Tag(name = "StoreResourceCateController", description = "店铺素材分类服务 API")
@RestController
@Validated
public class StoreResourceCateController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private StoreResourceCateSaveProvider storeResourceCateSaveProvider;

    @Autowired
    private StoreResourceCateQueryProvider storeResourceCateQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;


    /**
     * 查询店铺素材分类
     */
    @Operation(summary = "查询店铺素材分类")
    @RequestMapping(value = {"/store/resourceCates"}, method = RequestMethod.GET)
    public ResponseEntity<List> list() {
        SystemResourceCateListRequest queryRequest = SystemResourceCateListRequest.builder()
                .storeId(commonUtil.getStoreId()).build();
        BaseResponse<SystemResourceCateListResponse> response = storeResourceCateQueryProvider.list
                (queryRequest);
        return ResponseEntity.ok(response.getContext().getSystemResourceCateVOList());
    }


    /**
     * 新增店铺素材分类
     */
    @Operation(summary = "新增店铺素材分类")
    @RequestMapping(value = "/store/resourceCate", method = RequestMethod.POST)
    public BaseResponse add(@RequestBody SystemResourceCateAddRequest addReq) {
        if (addReq == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        addReq.setStoreId(commonUtil.getStoreId());
        addReq.setCompanyInfoId(commonUtil.getCompanyInfoId());

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
     * 编辑店铺素材分类
     */
    @Operation(summary = "编辑店铺素材分类")
    @RequestMapping(value = "/store/resourceCate", method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody SystemResourceCateModifyRequest modifyReq) {
        if (modifyReq == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询对应分类名称
        SystemResourceCateVO cateInfoByCateId = this.findCateInfoByCateId(modifyReq.getCateId());

        modifyReq.setStoreId(commonUtil.getStoreId());
        modifyReq.setCompanyInfoId(commonUtil.getCompanyInfoId());

        BaseResponse<SystemResourceCateModifyResponse> response = storeResourceCateSaveProvider.modify(modifyReq);

        //记录操作日志
        operateLogMQUtil.convertAndSend("设置", "编辑分类",
                "分类名称：" + cateInfoByCateId.getCateName() + " 改成 " + modifyReq.getCateName());
        return response;
    }



    /**
     * 检测店铺素材分类是否有子类
     */
    @Operation(summary = "检测店铺素材分类是否有子类")
    @RequestMapping(value = "/store/resourceCate/child", method = RequestMethod.POST)
    public BaseResponse checkChild(@RequestBody SystemResourceCateCheckChildRequest request) {
        if (request == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setStoreId(commonUtil.getStoreId());
        BaseResponse<Integer> response =storeResourceCateQueryProvider.checkChild(request);
        return response;
    }




    /**
     * 检测店铺素材分类是否已关联素材
     */
    @Operation(summary = "检测店铺素材分类是否已关联素材")
    @RequestMapping(value = "/store/resourceCate/resource", method = RequestMethod.POST)
    public BaseResponse checkResource(@RequestBody SystemResourceCateCheckResourceRequest request) {
        if (request == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setStoreId(commonUtil.getStoreId());
        BaseResponse<Integer> response =storeResourceCateQueryProvider.checkResource(request);
        return BaseResponse.success(response);
    }


    /**
     * 删除店铺素材分类
     */
    @Operation(summary = "删除店铺素材分类")
    @Parameter(name = "cateId", description = "分类Id", required = true)
    @RequestMapping(value = "/store/resourceCate/{cateId}", method = RequestMethod.DELETE)
    public BaseResponse delete(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询对应分类名称
        SystemResourceCateVO cateInfoByCateId = this.findCateInfoByCateId(cateId);

        SystemResourceCateDelByIdRequest delByIdReq = new SystemResourceCateDelByIdRequest();
        delByIdReq.setCateId(cateId);
        delByIdReq.setStoreId(commonUtil.getStoreId());
        BaseResponse response = storeResourceCateSaveProvider.delete(delByIdReq);

        //记录操作人日志
        operateLogMQUtil.convertAndSend("设置", "删除分类",
                "删除分类：" + cateInfoByCateId.getCateName());
        return response;
    }



    /**
     * 根据cateId查询CateInfo
     *
     * @param cateId
     * @return
     */
    private SystemResourceCateVO findCateInfoByCateId(Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        SystemResourceCateByIdRequest queryRequest = new SystemResourceCateByIdRequest();
        queryRequest.setCateId(cateId);
        BaseResponse<SystemResourceCateByIdResponse> response = storeResourceCateQueryProvider.getById(queryRequest);
        SystemResourceCateVO storeResourceCateVO = response.getContext().getSystemResourceCateVO();
        if (Objects.isNull(storeResourceCateVO)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        commonUtil.checkStoreId(storeResourceCateVO.getStoreId());
        return storeResourceCateVO;
    }
}
