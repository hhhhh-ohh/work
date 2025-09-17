package com.wanmi.sbc.recommendcate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.recommendcate.RecommendCateProvider;
import com.wanmi.sbc.setting.api.provider.recommendcate.RecommendCateQueryProvider;
import com.wanmi.sbc.setting.api.request.recommendcate.*;
import com.wanmi.sbc.setting.api.response.recommendcate.*;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 笔记分类表管理API
 */
@Tag(name =  "笔记分类表管理API", description =  "RecommendCateController")
@RestController
@Validated
@RequestMapping(value = "/recommendcate")
public class RecommendCateController {

    @Autowired
    private RecommendCateQueryProvider recommendCateQueryProvider;

    @Autowired
    private RecommendCateProvider recommendCateProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 分页查询笔记分类
     * @param pageReq
     * @return
     */
    @Operation(summary = "分页查询笔记分类表")
    @PostMapping("/page")
    public BaseResponse<RecommendCatePageResponse> getPage(@RequestBody @Valid RecommendCatePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("cateId", "desc");
        return recommendCateQueryProvider.page(pageReq);
    }

    /**
     * 列表查询笔记分类
     * @param listReq
     * @return
     */
    @Operation(summary = "列表查询笔记分类表")
    @PostMapping("/list")
    public BaseResponse<RecommendCateListResponse> getList(@RequestBody @Valid RecommendCateListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return recommendCateQueryProvider.list(listReq);
    }

    /**
     * 根据id查询笔记分类表
     * @param cateId
     * @return
     */
    @Operation(summary = "根据id查询笔记分类表")
    @GetMapping("/detail/{cateId}")
    public BaseResponse<RecommendCateByIdResponse> getById(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendCateByIdRequest idReq = new RecommendCateByIdRequest();
        idReq.setCateId(cateId);
        return recommendCateQueryProvider.getById(idReq);
    }

    /**
     * 新增笔记分类
     * @param addReq
     * @return
     */
    @Operation(summary = "新增笔记分类表")
    @PostMapping("/add")
    public BaseResponse<RecommendCateAddResponse> add(@RequestBody @Valid RecommendCateAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());

        //记录操作日志
        operateLogMQUtil.convertAndSend("应用", "新增笔记分类",
                "分类：" + addReq.toString());


        return recommendCateProvider.add(addReq);
    }

    /**
     * 修改笔记分类
     * @param modifyReq
     * @return
     */
    @Operation(summary = "修改笔记分类表")
    @PutMapping("/modify")
    public BaseResponse<RecommendCateModifyResponse> modify(@RequestBody @Valid RecommendCateModifyRequest modifyReq) {
        if (modifyReq.getCateId() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());

        //记录操作日志
        operateLogMQUtil.convertAndSend("应用", "笔记分类根据id修改分类",
                "分类：" + modifyReq.toString());

        return recommendCateProvider.modify(modifyReq);
    }

    /**
     * 根据id删除笔记分类
     * @param cateId
     * @return
     */
    @Operation(summary = "根据id删除笔记分类表")
    @DeleteMapping("/delete/{cateId}")
    public BaseResponse deleteById(@PathVariable Long cateId) {
        if (cateId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        RecommendCateDelByIdRequest delByIdReq = new RecommendCateDelByIdRequest();
        delByIdReq.setCateId(cateId);

        //记录操作日志
        operateLogMQUtil.convertAndSend("应用", "笔记分类根据id删除",
                "分类：" + cateId);

        return recommendCateProvider.deleteById(delByIdReq);
    }

    /**
     * 根据idList批量删除笔记分类
     * @param delByIdListReq
     * @return
     */
    @Operation(summary = "根据idList批量删除笔记分类表")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateDelByIdListRequest delByIdListReq) {

        //记录操作日志
        operateLogMQUtil.convertAndSend("应用", "笔记分类批量删除",
                "分类：" + delByIdListReq.getCateIdList());

        return recommendCateProvider.deleteByIdList(delByIdListReq);
    }

    /**
     * 内容分类拖拽排序
     *
     * @param request
     * @return
     */
    @Operation(summary = "内容分类拖拽排序")
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public BaseResponse dragSort(@RequestBody RecommendCateSortRequest request) {
        recommendCateProvider.dragSort(request);

        //记录操作日志
        operateLogMQUtil.convertAndSend("应用", "笔记分类拖拽排序",
                "分类：" + request.getRecommendCateSortVOS());
        return BaseResponse.SUCCESSFUL();
    }


}
