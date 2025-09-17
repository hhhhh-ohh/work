package com.wanmi.sbc.elastic.systemresource.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.osd.OsdClient;
import com.wanmi.osd.bean.OsdClientParam;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourcePageRequest;
import com.wanmi.sbc.elastic.api.response.systemresource.EsSystemRessourcePageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.elastic.systemresource.model.root.EsSystemResource;
import com.wanmi.sbc.elastic.systemresource.repository.EsSystemResourceRepository;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.systemconfig.SystemConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.SystemConfigVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2020/12/14 10:31
 * @description <p> 资源素材查询 </p>
 */
@Service
public class EsSystemResourceQueryService {

    @Autowired
    private EsSystemResourceRepository esSystemResourceRepository;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 分页查询资源素材
     * @param request
     * @return
     */
    public BaseResponse<EsSystemRessourcePageResponse> page(EsSystemResourcePageRequest request) {
        request.setDelFlag(DeleteFlag.NO);
        // storeId 没传，默认查系统资源
        if (null == request.getStoreId()){
            request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }
        // 查询可用云服务
        SystemConfigVO availableYun = this.getAvailableYun();

        OsdClientParam osdClientParam = OsdClientParam.builder()
                .configType(availableYun.getConfigType())
                .context(availableYun.getContext())
                .build();

        NativeQuery searchQuery = this.esCriteria(request);
        Page<EsSystemResource> systemResourcePage = esBaseService.commonPage(searchQuery, EsSystemResource.class, EsConstants.SYSTEM_RESOURCE);
        Page<EsSystemResourceVO> newPage = systemResourcePage.map(entity -> {
                    //获取url
                    EsSystemResourceVO esSystemResourceVO = EsSystemResourceVO.builder().build();
                    String resourceUrl = OsdClient.instance().getResourceUrl(osdClientParam, entity.getArtworkUrl());
                    entity.setArtworkUrl(resourceUrl);
                    BeanUtils.copyProperties(entity, esSystemResourceVO);
                    return esSystemResourceVO;
                }
        );
        MicroServicePage<EsSystemResourceVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        EsSystemRessourcePageResponse finalRes = new EsSystemRessourcePageResponse(microPage);
        return BaseResponse.success(finalRes);

    }

    /**
     * 查询可用的云配置
     *
     * @return
     */
    private SystemConfigVO getAvailableYun() {
        SystemConfigQueryRequest queryRequest = SystemConfigQueryRequest.builder()
                .configKey(ConfigKey.RESOURCESERVER.toString())
                .status(EnableStatus.ENABLE.toValue())
                .delFlag(DeleteFlag.NO)
                .build();
        BaseResponse<SystemConfigResponse> response = systemConfigQueryProvider.list(queryRequest);
        List<SystemConfigVO> systemConfigList = response.getContext().getSystemConfigVOList();
        if (CollectionUtils.isEmpty(systemConfigList)) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070040);
        }
        return systemConfigList.get(0);
    }

    /**
     * 查询条件
     * @return
     */
    private NativeQuery esCriteria(EsSystemResourcePageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        // 批量查询-素材资源IDList
        if (CollectionUtils.isNotEmpty(request.getResourceIdList())) {
//            bool.must(QueryBuilders.termsQuery("distributionId", request.getResourceIdList()));
            bool.must(terms(g -> g.field("distributionId").terms(x -> x.value(request.getResourceIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 素材资源ID
        if (request.getResourceId() != null) {
//            bool.must(QueryBuilders.termQuery("resourceId", request.getResourceId()));
            bool.must(term(g -> g.field("resourceId").value(request.getResourceId())));
        }

        // 资源类型(0:图片,1:视频)
        if (request.getResourceType() != null) {
//            bool.must(QueryBuilders.termQuery("resourceType", request.getResourceType().toValue()));
            bool.must(term(g -> g.field("resourceType").value(request.getResourceType().toValue())));
        }

        // 素材分类ID
        if (request.getCateId() != null) {
//            bool.must(QueryBuilders.termQuery("cateId", request.getCateId()));
            bool.must(term(g -> g.field("cateId").value(request.getCateId())));
        }

        // 店铺标识
        if (request.getStoreId() != null) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        // 商家标识
        if (request.getCompanyInfoId() != null) {
//            bool.must(QueryBuilders.termQuery("companyInfoId", request.getCompanyInfoId()));
            bool.must(term(g -> g.field("companyInfoId").value(request.getCompanyInfoId())));
        }

        // 模糊查询 - 素材KEY
        if (StringUtils.isNotEmpty(request.getResourceKey())) {
//            bool.must(QueryBuilders.wildcardQuery("resourceKey", "*" + request.getResourceKey() + "*"));
            bool.must(wildcard(g -> g.field("resourceKey").value("*" + request.getResourceKey() + "*")));
        }

        // 模糊查询 - 素材名称
        if (StringUtils.isNotEmpty(request.getResourceName())) {
//            bool.must(QueryBuilders.wildcardQuery("resourceName", "*" + request.getResourceName() + "*"));
            bool.must(wildcard(g -> g.field("resourceName").value("*" + request.getResourceName() + "*")));
        }

        // 模糊查询 - 素材地址
        if (StringUtils.isNotEmpty(request.getArtworkUrl())) {
//            bool.must(QueryBuilders.wildcardQuery("artworkUrl", "*" + request.getArtworkUrl() + "*"));
            bool.must(wildcard(g -> g.field("artworkUrl").value("*" + request.getArtworkUrl() + "*")));
        }

        // 大于或等于 搜索条件:创建时间开始
        if (request.getCreateTimeBegin() != null) {
//            bool.must(QueryBuilders.rangeQuery("createTime").gte(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("createTime").gte(JsonData.of((DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4))))));
        }
        // 小于或等于 搜索条件:创建时间截止
        if (request.getCreateTimeEnd() != null) {
//            bool.must(QueryBuilders.rangeQuery("createTime").lte(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("createTime").lte(JsonData.of((DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4))))));
        }

        // 大于或等于 搜索条件:更新时间开始
        if (request.getUpdateTimeBegin() != null) {
//            bool.must(QueryBuilders.rangeQuery("updateTime").gte(DateUtil.format(request.getUpdateTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("updateTime").gte(JsonData.of((DateUtil.format(request.getUpdateTimeBegin(), DateUtil.FMT_TIME_4))))));
        }

        // 小于或等于 搜索条件:更新时间截止
        if (request.getUpdateTimeEnd() != null) {
//            bool.must(QueryBuilders.rangeQuery("updateTime").lte(DateUtil.format(request.getUpdateTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("updateTime").lte(JsonData.of((DateUtil.format(request.getUpdateTimeEnd(), DateUtil.FMT_TIME_4))))));
        }

        // 删除标识,0:未删除1:已删除
        if (request.getDelFlag() != null) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag().toValue()));
            bool.must(term(g -> g.field("delFlag").value(request.getDelFlag().toValue())));
        }

        // 模糊查询 - oss服务器类型，对应system_config的config_type
        if (StringUtils.isNotEmpty(request.getServerType())) {
//            bool.must(QueryBuilders.wildcardQuery("serverType", "*" + request.getServerType() + "*"));
            bool.must(wildcard(g -> g.field("serverType").value("*" + request.getServerType() + "*")));
        }


        //批量分类编号
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(request.getCateIds())) {
//            bool.must(QueryBuilders.termsQuery("cateId", request.getCateIds()));
            bool.must(terms(g -> g.field("cateId").terms(x -> x.value(request.getCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

//        FieldSortBuilder sort = SortBuilders.fieldSort("sort").order(SortOrder.DESC);
//        FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
//        FieldSortBuilder resourceId = SortBuilders.fieldSort("_id").order(SortOrder.ASC);
//        return new NativeSearchQueryBuilder()
//                .withSort(sort)
//                .withSort(createTime)
//                .withSort(resourceId)
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .build();
        SortOptions sort = SortOptions.of(a -> a.field(b -> b.field("sort").order(SortOrder.Desc)));
        SortOptions resourceName = SortOptions.of(a -> a.field(b -> b.field("resourceName").order(SortOrder.Desc)));
//        SortOptions createTime = SortOptions.of(a -> a.field(b -> b.field("createTime").order(SortOrder.Desc)));
        SortOptions resourceId = SortOptions.of(a -> a.field(b -> b.field("resourceId").order(SortOrder.Desc)));
        return new NativeQueryBuilder()
                .withSort(sort,resourceName, resourceId)
                .withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .build();
    }
}
