package com.wanmi.sbc.elastic.base.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.utils.EsDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

/** ES公共服务 */
@Slf4j
@Service
public class EsBaseService {

    @Autowired private ElasticsearchOperations elasticsearchOperations;

    @Autowired private ElasticsearchTemplate elasticsearchTemplate;

    @WmResource("mapping/esCommonSetting.json")
    private Resource setting;

    /**
     * @description 公共查询es分页方法
     * @author songhanlin
     * @date: 2021/11/23 下午2:18
     * @return
     */
    public <T> Page<T> commonPage(Query query, Class<T> clazz, String index) {
        SearchHits<T> searchHits = commonSearchHits(query, clazz, index);
        return EsDataUtil.convertToPage(searchHits, query.getPageable());
    }

    public <T> SearchHits<T> commonSearchHits(Query query, Class<T> clazz, String index) {
        SearchHits<T> searchHits =
                elasticsearchTemplate.search(query, clazz, IndexCoordinates.of(index));
        return searchHits;
    }

    public <T> Page<T> commonSearchPage(SearchHits<T> searchHits, Pageable pageable) {
        return EsDataUtil.convertToPage(searchHits, pageable);
    }

    /**
     * @description 删除索引, 重新设置mapping
     * @author  songhanlin
     * @date: 2021/11/23 下午2:23
     * @return
     **/
    public <T> void existsOrCreate(String index, Class<T> clazz, boolean checkExists) {
        //手动删除索引时，重新设置mapping
        IndexOperations indexOperations = this.indexOps(index);
        if(checkExists) {
            if(!indexOperations.exists()) {
                createIndex(indexOperations, clazz);
            }
        }else {
            createIndex(indexOperations, clazz);
        }
    }

    public <T> void existsOrCreate(String index, Class<T> clazz) {
        existsOrCreate(index, clazz, true);
    }

    /**
     * @return
     * @description 删除索引, 重新设置mapping
     * @author songhanlin
     * @date: 2021/11/23 下午2:23
     **/
    public <T> void existsOrCreate(String index, Resource mappingResource, boolean checkExists) {
        //手动删除索引时，重新设置mapping
        IndexOperations indexOperations = this.indexOps(index);
        if (checkExists) {
            if (!indexOperations.exists()) {
                createIndex(indexOperations, mappingResource);
            }
        } else {
            createIndex(indexOperations, mappingResource);
        }
    }

    public <T> void existsOrCreate(String index, Resource mappingResource) {
        existsOrCreate(index, mappingResource, true);
    }

    /**
     * @return
     * @description 删除索引, 重新设置mapping
     * @author songhanlin
     * @date: 2021/11/23 下午2:23
     **/
    public <T> void createIndex(IndexOperations indexOperations, Resource mappingResource) {
        String settingJson = this.readFileFromUrl(setting);
        Document settingDocument = Document.parse(settingJson);
        indexOperations.create(settingDocument);
        //手动删除索引时，重新设置mapping
        String json = this.readFileFromUrl(mappingResource);
        Document document = Document.parse(json);
        indexOperations.putMapping(document);
    }

    private String readFileFromUrl(Resource mappingResource) {
        String json;
        try (InputStream is = mappingResource.getInputStream()) {
            json = StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        return json;
    }

    /**
     * @description 删除索引, 重新设置mapping
     * @author  songhanlin
     * @date: 2021/11/23 下午2:23
     * @return
     **/
    public <T> void createIndex(IndexOperations indexOperations, Class<T> clazz) {
        //手动删除索引时，重新设置mapping
        indexOperations.create();
        Document document = indexOperations.createMapping(clazz);
        indexOperations.putMapping(document);
    }

    public boolean exists(String index) {
        IndexOperations indexOperations = this.indexOps(index);
        return indexOperations.exists();
    }

    public IndexOperations indexOps(String index) {
        return elasticsearchOperations.indexOps(IndexCoordinates.of(index));
    }

    public void refresh(String index) {
        IndexOperations indexOperations = indexOps(index);
        indexOperations.refresh();
    }

    public void deleteIndex(String index) {
        IndexOperations indexOperations = indexOps(index);
        indexOperations.delete();
    }

    public void deleteIndexById(String id, String index) {
        elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
        elasticsearchTemplate.delete(id, IndexCoordinates.of(index));
    }
}
