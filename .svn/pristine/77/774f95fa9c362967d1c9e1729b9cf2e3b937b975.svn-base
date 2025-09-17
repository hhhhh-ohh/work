package com.wanmi.sbc.elastic.sensitivewords.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.sensitivewords.EsSensitiveWordsQueryRequest;
import com.wanmi.sbc.elastic.api.response.sensitivewords.EsSensitiveWordsPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.sensitivewords.EsSensitiveWordsVO;
import com.wanmi.sbc.elastic.sensitivewords.model.root.EsSensitiveWords;
import com.wanmi.sbc.elastic.sensitivewords.repository.EsSensitiveWordsRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;


/**
 * @author houshuai
 * @date 2020/12/11 16:19
 * @description <p> 敏感词查询 </p>
 */
@Service
public class EsSensitiveWordsQueryService {

    @Autowired
    private EsSensitiveWordsRepository esSensitiveWordsRepository;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 敏感词库列表
     * @param request
     * @return
     */
    public BaseResponse<EsSensitiveWordsPageResponse> page(EsSensitiveWordsQueryRequest request) {

        //查询条件
        Query searchQuery = this.esCriteria(request);
        Page<EsSensitiveWords> sensitiveWordsPage = esBaseService.commonPage(searchQuery, EsSensitiveWords.class, EsConstants.SENSITIVE_WORDS);
        Page<EsSensitiveWordsVO> newPage = sensitiveWordsPage.map(entity -> {
            EsSensitiveWordsVO sensitiveWordsVO = EsSensitiveWordsVO.builder().build();
            BeanUtils.copyProperties(entity, sensitiveWordsVO);
            return sensitiveWordsVO;
        });
        MicroServicePage<EsSensitiveWordsVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        EsSensitiveWordsPageResponse finalRes = new EsSensitiveWordsPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    /**
     * 查询条件
     * @return
     */
    private Query esCriteria(EsSensitiveWordsQueryRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        // 批量查询-敏感词id 主键List
        if (CollectionUtils.isNotEmpty(request.getSensitiveIdList())) {
//            bool.must(QueryBuilders.termsQuery("sensitiveId", request.getSensitiveIdList()));
            bool.must(terms(a -> a.field("sensitiveId").terms(v -> v.value(request.getSensitiveIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 敏感词id 主键
        if (request.getSensitiveId() != null) {
//            bool.must(QueryBuilders.termQuery("sensitiveId", request.getSensitiveId()));
            bool.must(term(a -> a.field("sensitiveId").value(request.getSensitiveId())));
        }

        //敏感词名称查询
        if (StringUtils.isNotEmpty(request.getSensitiveWords())) {
//            bool.must(QueryBuilders.termQuery("sensitiveWords", request.getSensitiveWords()));
            bool.must(term(a -> a.field("sensitiveWords").value(request.getSensitiveWords())));
        }

        // 模糊查询 - 敏感词内容
        if (StringUtils.isNotEmpty(request.getLikeSensitiveWords())) {
//            bool.must(QueryBuilders.wildcardQuery("sensitiveWords", "*" + request.getLikeSensitiveWords() + "*"));
            bool.must(wildcard(a -> a.field("sensitiveWords").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeSensitiveWords().trim()))));
        }

        // 是否删除
        if (request.getDelFlag() != null) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag().toValue()));
            bool.must(term(a -> a.field("delFlag").value(request.getDelFlag().toValue())));
        }

//        FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
//        FieldSortBuilder sensitiveId = SortBuilders.fieldSort("_id").order(SortOrder.DESC);
//        return new NativeSearchQueryBuilder()
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .withSort(createTime)
//                .withSort(sensitiveId)
//                .build();
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(SortOptions.of(g -> g.field(a -> a.field("createTime").order(SortOrder.Desc))),
                        SortOptions.of(g -> g.field(a -> a.field("sensitiveId").order(SortOrder.Desc))))
                .build();
    }
}
