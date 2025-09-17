package com.wanmi.sbc.elastic.searchterms.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.searchterms.EsSearchAssociationalWordPageRequest;
import com.wanmi.sbc.elastic.api.response.searchterms.EsSearchAssociationalWordPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.searchterms.EsAssociationLongTailWordVO;
import com.wanmi.sbc.elastic.bean.vo.searchterms.EsSearchAssociationalWordVO;
import com.wanmi.sbc.elastic.searchterms.model.root.EsSearchAssociationalWord;
import com.wanmi.sbc.setting.api.provider.searchterms.SearchAssociationalWordQueryProvider;
import com.wanmi.sbc.setting.api.request.searchterms.AssociationLongTailWordByIdsRequest;
import com.wanmi.sbc.setting.api.response.searchterms.AssociationLongTailWordByIdsResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;


/**
 * @author houshuai
 * @date 2020/12/17 14:36
 * @description <p> 搜索词查询 </p>
 */
@Service
public class EsSearchAssociationalWordQueryService {

    @Autowired
    private SearchAssociationalWordQueryProvider searchAssociationalWordQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 分页查询搜索词
     * @param request
     * @return
     */
    public BaseResponse<EsSearchAssociationalWordPageResponse> page(EsSearchAssociationalWordPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
//        bool.must(QueryBuilders.termQuery("delFlag", DeleteFlag.NO.toValue()));
//        FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        BoolQuery.Builder bool = QueryBuilders.bool();
        bool.must(term(g -> g.field("delFlag").value(DeleteFlag.NO.toValue())));
        SortOptions createTime = SortOptions.of(a-> a.field(b -> b.field("createTime").order(SortOrder.Desc)));


        NativeQuery searchQuery = NativeQuery.builder()
                .withSort(createTime)
                .withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .build();
        Page<EsSearchAssociationalWord> associationalWordPage = esBaseService.commonPage(searchQuery,
                EsSearchAssociationalWord.class, EsConstants.SEARCH_ASSOCIATIONAL_WORD);
        Page<EsSearchAssociationalWordVO> newPage = this.copyPage(associationalWordPage);
        MicroServicePage<EsSearchAssociationalWordVO> microServicePage = new MicroServicePage<>(newPage, request.getPageable());
        return BaseResponse.success(new EsSearchAssociationalWordPageResponse(microServicePage));
    }

    /**
     *   转VO
     * @param associationalWordPage
     * @return
     */
    private Page<EsSearchAssociationalWordVO> copyPage(Page<EsSearchAssociationalWord> associationalWordPage) {

        List<Long> longList = associationalWordPage.getContent().stream()
                .map(EsSearchAssociationalWord::getId)
                .collect(Collectors.toList());

        Map<Long, List<EsAssociationLongTailWordVO>> groupingMap = this.groupingMap(longList);
        return associationalWordPage.map(source -> {
            EsSearchAssociationalWordVO target = EsSearchAssociationalWordVO.builder().build();
            BeanUtils.copyProperties(source, target);
            if (MapUtils.isNotEmpty(groupingMap)) {
                List<EsAssociationLongTailWordVO> vos = groupingMap.get(source.getId());
                if (CollectionUtils.isNotEmpty(vos)) {
                    target.setAssociationLongTailWordList(vos.stream().sorted(Comparator.comparing(EsAssociationLongTailWordVO::getSortNumber)).collect(Collectors.toList()));
                }
            }
            return target;
        });
    }


    /**
     * 根据SearchAssociationalWordId分组
     * @param longList Id
     * @return Map
     */
    private Map<Long, List<EsAssociationLongTailWordVO>> groupingMap(List<Long> longList) {
        AssociationLongTailWordByIdsRequest request = AssociationLongTailWordByIdsRequest.builder().idList(longList).build();
        AssociationLongTailWordByIdsResponse idsResponse = searchAssociationalWordQueryProvider.listByIds(request).getContext();

        return Optional.ofNullable(idsResponse.getLongTailWordVOList())
                .orElseGet(Lists::newArrayList).stream()
                .map(source -> {
                    EsAssociationLongTailWordVO target = EsAssociationLongTailWordVO.builder().build();
                    BeanUtils.copyProperties(source, target);
                    return target;
                }).collect(Collectors.groupingBy(EsAssociationLongTailWordVO::getSearchAssociationalWordId));
    }

}
