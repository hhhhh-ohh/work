package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceProvider;
import com.wanmi.sbc.elastic.api.provider.systemresource.EsSystemResourceQueryProvider;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourcePageRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.api.response.systemresource.EsSystemRessourcePageResponse;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceSaveProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceModifyRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceMoveRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceEditResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceModifyResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 素材服务
 * Created by yinxianzhi on 2018/10/13.
 */
@Tag(name = "ResourceController", description = "素材服务 Api")
@RestController
@Validated
@RequestMapping("/system")
public class ResourceController {

    @Autowired
    private SystemResourceSaveProvider systemResourceSaveProvider;

    @Autowired
    private EsSystemResourceProvider esSystemResourceProvider;

    @Autowired
    private EsSystemResourceQueryProvider esSystemResourceQueryProvider;

    /**
     * 分页素材
     *
     * @param pageRequest 素材参数
     * @return
     */
    @Operation(summary = "分页素材")
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EsSystemResourceVO>> page(@RequestBody @Valid EsSystemResourcePageRequest pageRequest) {
        BaseResponse<EsSystemRessourcePageResponse> response = esSystemResourceQueryProvider.page(pageRequest);
        return BaseResponse.success(response.getContext().getSystemResourceVOPage());

    }

    /**
     * 编辑素材
     */
    @GlobalTransactional
    @Operation(summary = "编辑素材")
    @RequestMapping(value = "/resource", method = RequestMethod.PUT)
    public BaseResponse<SystemResourceModifyResponse> modify(@RequestBody @Valid SystemResourceModifyRequest
                                                                     modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());
        BaseResponse<SystemResourceModifyResponse> modify = systemResourceSaveProvider.modify(modifyReq);
        SystemResourceVO systemResourceVO = modify.getContext().getSystemResourceVO();
        if (Objects.nonNull(systemResourceVO)) {
            this.addEsSystemResource(Collections.singletonList(systemResourceVO));
        }
        return modify;

    }

    /**
     * 删除素材
     */
    @GlobalTransactional
    @Operation(summary = "删除素材")
    @RequestMapping(value = "/resource", method = RequestMethod.DELETE)
    public BaseResponse delete(@RequestBody @Valid SystemResourceDelByIdListRequest delByIdListReq) {
        SystemResourceEditResponse response = systemResourceSaveProvider.deleteByIdList(delByIdListReq).getContext();
        List<SystemResourceVO> systemResourceVOList = response.getSystemResourceVOList();
        //图片库同步es
        return this.addEsSystemResource(systemResourceVOList);
    }

    /**
     * 批量修改素材的分类
     */
    @GlobalTransactional
    @Operation(summary = "批量修改素材的分类")
    @RequestMapping(value = "/resource/resourceCate", method = RequestMethod.PUT)
    public BaseResponse updateCate(@RequestBody @Valid SystemResourceMoveRequest
                                           moveRequest) {

        if (moveRequest.getCateId() == null || CollectionUtils.isEmpty(moveRequest.getResourceIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        SystemResourceEditResponse editResponse = systemResourceSaveProvider.move(moveRequest).getContext();
        List<SystemResourceVO> systemResourceVOList = editResponse.getSystemResourceVOList();
        //同步es
        return this.addEsSystemResource(systemResourceVOList);
    }


    /**
     * 图片库同步es
     *
     * @param systemResourceVOList
     * @return
     */
    private BaseResponse addEsSystemResource(List<SystemResourceVO> systemResourceVOList) {
        if (CollectionUtils.isNotEmpty(systemResourceVOList)) {
            List<EsSystemResourceVO> esSystemResourceVOList = KsBeanUtil.convert(systemResourceVOList,
                    EsSystemResourceVO.class);
            EsSystemResourceSaveRequest saveRequest = EsSystemResourceSaveRequest.builder()
                    .systemResourceVOList(esSystemResourceVOList)
                    .build();
            esSystemResourceProvider.add(saveRequest);
        }
        return BaseResponse.SUCCESSFUL();
    }
}
