package com.wanmi.sbc.elastic.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;

/** @Author: songhanlin @Date: Created In 下午2:03 2021/11/23 @Description: TODO */
public class EsDataUtil {

    public static <T> Page<T> convertToPage(SearchHits<T> searchHits, Pageable pageable) {
        SearchPage<T> searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);
        return searchPage.map(SearchHit::getContent);
    }
}
