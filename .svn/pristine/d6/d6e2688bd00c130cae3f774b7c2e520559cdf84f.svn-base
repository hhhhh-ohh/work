package com.wanmi.sbc.elastic;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SortType;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RestTemplateLoadBalancedTest {

    @Qualifier("restTemplateLoadBalanced")
    @Autowired
    private RestTemplate restTemplateLoadBalanced;

    @Test
    public void test(){
        BaseQueryRequest queryRequest = new BaseQueryRequest();
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        queryRequest.setPageNum(0);
        queryRequest.setPageSize(200);
        ResponseEntity<BaseResponse> responseEntity = restTemplateLoadBalanced.postForEntity(
                "http://sbc-service-setting/setting/1.5.0/sensitivewords/page"
                , queryRequest, BaseResponse.class);
        System.out.println(JSON.toJSONString(responseEntity));
    }
}
