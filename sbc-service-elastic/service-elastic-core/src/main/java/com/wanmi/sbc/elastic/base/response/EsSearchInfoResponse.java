package com.wanmi.sbc.elastic.base.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @Author yangzhen
 * @Description //Es 商家结算账号分页结果
 * @Date 15:43 2020/12/9
 * @Param
 * @return
 **/
@Data
@Schema
@Slf4j
public class EsSearchInfoResponse<T> {



    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 查询结果
     */
    @Schema(description = "查询结果")
    private List<T> data;

    /**
     * 设置结果总数
     *
     * @param count ES总的搜索结果数
     * @return
     */
    public EsSearchInfoResponse addTotalNum(Long count) {
        this.total = count;
        return this;
    }

    /**
     * 转换 ES 查询结果
     *
     * @param searchHits      查询结果
     * @param pages 结果映射
     * @return
     */
    private EsSearchInfoResponse addQueryResults(SearchHits<T> searchHits, Page<T> pages) {
        if (Objects.nonNull(pages)) {
            data = pages.getContent();
        }

        if (CollectionUtils.isNotEmpty(data)) {
            boolean hasHighLight = false;
            if (searchHits.getSearchHits().size() > 0) {
                if (MapUtils.isNotEmpty(searchHits.getSearchHit(0).getHighlightFields())) {
                    hasHighLight = true;
                }
            }

            if (hasHighLight) {
                IntStream.range(0, data.size()).parallel().forEach(index -> {
                    T obj = data.get(index);
                    SearchHit sh = searchHits.getSearchHit(index);
                    sh.getHighlightFields().forEach((key, value) -> {
                        try {
                            PropertyUtils.setProperty(obj, (String) key, value);
                        } catch (Exception e) {
                            log.error("Set EsGoodsInfo highLight property error = {}, Property key = {}, value = " +
                                    "{}", e, key, value);
                        }
                    });
                });
            }
        }

        return this;
    }

    /**
     * 返回空结果
     *
     * @return
     */
    public static EsSearchInfoResponse empty() {
        EsSearchInfoResponse response = new EsSearchInfoResponse();
        response.setTotal(0L);
        response.setData(Collections.emptyList());
        return response;
    }


    /**
     * 根据ES查询返回结果 构建 EsSearchResponse实例
     *
     * @param searchHits
     * @param pages
     * @return
     */
    public static <T> EsSearchInfoResponse<T> build(SearchHits<T> searchHits, Page<T> pages) {
        return new EsSearchInfoResponse().addQueryResults(searchHits, pages).addTotalNum(pages.getTotalElements());
    }

}
