package com.wanmi.sbc.crm.customgroup;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.crm.api.provider.customgroup.CustomGroupProvider;
import com.wanmi.sbc.crm.api.request.customgroup.CustomGroupListRequest;
import com.wanmi.sbc.crm.api.request.customgroup.CustomGroupRequest;
import com.wanmi.sbc.crm.api.request.customgroup.CustomGroupBatchSortRequest;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-15
 * \* Time: 15:33
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Tag(name =  "自定义人群管理", description =  "CustomGroupController")
@RestController
@Validated
@RequestMapping(value = "/crm/customgroup")
public class CustomGroupController {

    @Autowired
    private CustomGroupProvider customGroupProvider;
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Operation(summary = "新增自定义人群配置")
    @PostMapping("/add")
    @MultiSubmit
    BaseResponse add(@RequestBody @Valid CustomGroupRequest request){
        //redis锁，防止并发情况下，会员分群超出数量
        RLock rLock = redissonClient.getFairLock(request.getGroupType().name());
        rLock.lock();
        try{
            request.setCreateTime(LocalDateTime.now());
            request.setCreatePerson(commonUtil.getOperatorId());
            this.customGroupProvider.add(request).getContext();
        } catch (Exception e){
            throw e;
        } finally {
            rLock.unlock();
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改自定义人群
     * @param request
     * @return
     */
    @Operation(summary = "修改自定义人群配置")
    @PutMapping("/modify")
    @MultiSubmit
    BaseResponse modify(@RequestBody @Valid CustomGroupRequest request){
        request.setUpdateTime(LocalDateTime.now());
        request.setUpdatePerson(commonUtil.getOperatorId());
        return this.customGroupProvider.modify(request);
    }

    /**
     * 根据id删除自定义人群
     * @param id
     * @return
     */
    @Operation(summary = "删除自定义人群配置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomGroupRequest request = CustomGroupRequest.builder().id(id).build();
        return this.customGroupProvider.deleteById(request);
    }

    /**
     * 根据id获取自定人群
     * @param id
     * @return
     */
    @Operation(summary = "根据id获取自定义人群")
    @GetMapping("/{id}")
    public BaseResponse queryById(@PathVariable Long id){
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomGroupRequest request = CustomGroupRequest.builder().id(id).build();
        return this.customGroupProvider.queryById(request);
    }

    @Operation(summary = "查询定义人群列表")
    @PostMapping("/page")
    public BaseResponse list(@RequestBody @Valid CustomGroupListRequest request){
        return this.customGroupProvider.list(request);
    }

    @Operation(summary = "引入系统生命周期分群")
    @PostMapping("/importSystemLifeCycleGroup")
    BaseResponse importSystemLifeCycleGroup(){
        String operatorId = commonUtil.getOperatorId();
        return this.customGroupProvider.importSystemLifeCycleGroup(CustomGroupRequest.builder().createPerson(operatorId).build());
    }

    @Operation(summary = "生命周期分群批量排序")
    @PostMapping("/batchSort")
    BaseResponse batchSort(@RequestBody @Valid CustomGroupBatchSortRequest request){
        String operatorId = commonUtil.getOperatorId();
        request.setOperatorId(operatorId);
        return this.customGroupProvider.batchSort(request);
    }
}
