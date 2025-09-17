package com.wanmi.sbc.groupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.groupon.EsGrouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityInitRequest;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityModifyCateIdRequest;
import com.wanmi.sbc.marketing.api.provider.grouponcate.GrouponCateSaveProvider;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateAddRequest;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateModifyRequest;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateSortRequest;
import com.wanmi.sbc.marketing.api.response.grouponcate.GrouponCateDelResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

/**
 * S2B的拼团分类服务
 */
@RestController
@Validated
@RequestMapping("/groupon/cate")
@Tag(name =  "S2B平台端-拼团分类服务", description =  "GrouponCateController")
public class GrouponCateController {

    @Autowired
    private GrouponCateSaveProvider grouponCateSaveProvider;

    @Autowired
    private EsGrouponActivityProvider esGrouponActivityProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    /**
     * 新增拼团分类
     *
     * @param request
     * @return
     */
    @Operation(summary = "新增拼团分类")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponse addGrouponCate(@RequestBody @Valid GrouponCateAddRequest request) {
        request.setCreatePerson(commonUtil.getOperatorId());
        grouponCateSaveProvider.add(request);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "新增拼团分类",
                "分类名称:" + request.getGrouponCateName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改拼团分类
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改拼团分类")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public BaseResponse modifyGrouponCate(@RequestBody @Valid GrouponCateModifyRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        grouponCateSaveProvider.modify(request);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "修改拼团分类",
                "分类ID：" + request.getGrouponCateId() + "，分类名称:" + request.getGrouponCateName());

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除拼团分类
     *
     * @param request
     * @return
     */
    @Operation(summary = "删除拼团分类")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResponse delGrouponCate(@RequestBody GrouponCateDelByIdRequest request) {
        request.setDelPerson(commonUtil.getOperatorId());
        GrouponCateDelResponse response = grouponCateSaveProvider.deleteById(request).getContext();

        //更新es数据
        if (CollectionUtils.isNotEmpty(response.getGrouponActivityIds())){
            esGrouponActivityProvider.init(EsGrouponActivityInitRequest.builder()
                    .idList(response.getGrouponActivityIds()).build());
        }

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "删除拼团分类",
                "分类ID:" + request.getGrouponCateId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 拼团分类拖拽排序
     *
     * @param request
     * @return
     */
    @Operation(summary = "拼团分类拖拽排序")
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public BaseResponse dragSort(@RequestBody GrouponCateSortRequest request) {
        grouponCateSaveProvider.dragSort(request);

        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "拼团分类拖拽排序",
                "分类：" + request.getGrouponCateSortVOList());
        return BaseResponse.SUCCESSFUL();
    }
}
