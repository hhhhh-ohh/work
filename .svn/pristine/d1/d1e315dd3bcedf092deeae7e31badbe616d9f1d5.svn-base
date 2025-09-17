package com.wanmi.sbc.elastic.communityleader.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderAddRequest;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderInitRequest;
import com.wanmi.sbc.elastic.api.response.communityleader.EsCommunityLeaderAddResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.communityleader.repository.EsCommunityLeaderRepository;
import com.wanmi.sbc.elastic.communityleader.root.EsCommunityLeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: wc
 * @date: 2020/12/8 17:06
 * @description: 社区团长列表
 */
@Slf4j
@Service
public class EsCommunityLeaderService {

    @Autowired
    private EsCommunityLeaderRepository esCommunityLeaderRepository;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esCommunityLeader.json")
    private Resource mapping;

    /**
     * 新增社区团长
     * @param request
     * @return
     */
    public BaseResponse<EsCommunityLeaderAddResponse> add(EsCommunityLeaderAddRequest request) {
        List<CommunityLeaderVO> communityLeaderVOS = request.getList();
        if (CollectionUtils.isEmpty(communityLeaderVOS)) {
            return BaseResponse.success(new EsCommunityLeaderAddResponse(Collections.emptyList()));
        }

        List<EsCommunityLeader> communityLeaderList = this.copyBeanList(communityLeaderVOS);

        Iterable<EsCommunityLeader> esCommunityLeaders = saveAll(communityLeaderList);

        List<EsCommunityLeader> communityLeaders = Lists.newArrayList(esCommunityLeaders);

        List<CommunityLeaderVO> esCommunityLeaderVOList = this.copyField(communityLeaders);

        EsCommunityLeaderAddResponse addResponse = new EsCommunityLeaderAddResponse(esCommunityLeaderVOList);

        return BaseResponse.success(addResponse);
    }

    /**
     * CommunityLeaderVO  转 EsCommunityLeader
     *
     * @param communityLeaderVOList
     * @return
     */
    private List<EsCommunityLeader> copyBeanList(List<CommunityLeaderVO> communityLeaderVOList) {
        return communityLeaderVOList.stream().map(entity -> {
            EsCommunityLeader communityLeader = EsCommunityLeader.builder().build();
            BeanUtils.copyProperties(entity, communityLeader);
            return communityLeader;
        }).collect(Collectors.toList());
    }

    /**
     * EsCommunityLeader转CommunityLeaderVO
     *
     * @param communityLeaders
     * @return
     */
    private List<CommunityLeaderVO> copyField(List<EsCommunityLeader> communityLeaders) {
        return communityLeaders.stream().map(entity -> {
            CommunityLeaderVO communityLeaderVO = new CommunityLeaderVO();
            BeanUtils.copyProperties(entity, communityLeaderVO);
            return communityLeaderVO;
        }).collect(Collectors.toList());
    }


    /**
     * 初始化社区团长
     * @param request
     */
    public void init(EsCommunityLeaderInitRequest request) {
        boolean flg = true;
        int pageNum = request.getPageNum();
        Integer pageSize = request.getPageSize();

        CommunityLeaderPageRequest queryRequest = KsBeanUtil.convert(request, CommunityLeaderPageRequest.class);
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    queryRequest.setLeaderIdList(request.getIdList());
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    flg = false;
                }
                queryRequest.putSort("createTime", SortType.DESC.toValue());
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<CommunityLeaderVO> communityLeaderVOS = communityLeaderQueryProvider.page(queryRequest).getContext()
                        .getCommunityLeaderPage().getContent();
                if (CollectionUtils.isEmpty(communityLeaderVOS)) {
                    flg = false;
                    log.info("==========ES初始化社区团长列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsCommunityLeader> newInfos = KsBeanUtil.convert(communityLeaderVOS, EsCommunityLeader.class);
                    saveAll(newInfos);
                    log.info("==========ES初始化社区团长列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化社区团长列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040008, new Object[]{pageNum});
        }

    }

    private Iterable<EsCommunityLeader> saveAll(List<EsCommunityLeader> newInfos) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.COMMUNITY_LEADER, mapping);
        return esCommunityLeaderRepository.saveAll(newInfos);
    }

    /**
     * 修改社区团长的会员名称
     * @param customerId
     * @param customerName
     */
    public void modify(String customerId, String customerName) {
        EsCommunityLeader esCommunityLeader = esCommunityLeaderRepository.findByCustomerId(customerId);
        if (Objects.nonNull(esCommunityLeader)) {
            esCommunityLeader.setLeaderName(customerName);
            saveAll(Collections.singletonList(esCommunityLeader));
        }
    }
}
