package com.wanmi.sbc.setting.searchterms.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.searchterms.SearchAssociationalWordPageRequest;
import com.wanmi.sbc.setting.api.response.searchterms.AssociationLongTailWordLikeResponse;
import com.wanmi.sbc.setting.api.response.searchterms.AssociationLongTailWordResponse;
import com.wanmi.sbc.setting.api.response.searchterms.SearchAssociationalWordResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.AssociationLongTailLikeWordVO;
import com.wanmi.sbc.setting.bean.vo.AssociationLongTailWordVO;
import com.wanmi.sbc.setting.bean.vo.SearchAssociationalWordVO;
import com.wanmi.sbc.setting.searchterms.model.AssociationLongTailWord;
import com.wanmi.sbc.setting.searchterms.model.SearchAssociationalWord;
import com.wanmi.sbc.setting.searchterms.repository.AssociationLongTailWordRepossitory;
import com.wanmi.sbc.setting.searchterms.repository.SearchAssociationalWordRepository;
import com.wanmi.sbc.setting.util.error.SearchTermsErrorCode;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>搜索词Service</p>
 *
 * @author weiwenhao
 * @date 2020-04-16
 */
@Service
public class SearchAssociationalWordService {

    @Autowired
    SearchAssociationalWordRepository searchAssociationalWordRepository;

    @Autowired
    AssociationLongTailWordRepossitory associationLongTailWordRepossitory;

    @Autowired
    private EntityManager entityManager;

    /**
     * 新增搜索词
     *
     * @param searchAssociationalWord
     * @return
     */
    public SearchAssociationalWordResponse add(SearchAssociationalWord searchAssociationalWord) {
        // 搜索词去重
        Integer repeatName =
                searchAssociationalWordRepository.countBySearchTermsAndDelFlag(searchAssociationalWord.getSearchTerms(),
                DeleteFlag.NO);
        if (Objects.nonNull(repeatName) && repeatName > 0) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070012);
        }

        searchAssociationalWord.setCreateTime(LocalDateTime.now());
        searchAssociationalWord.setDelFlag(DeleteFlag.NO);
        SearchAssociationalWordVO searchAssociationalWordVO = new SearchAssociationalWordVO();
        KsBeanUtil.copyPropertiesThird(searchAssociationalWordRepository.save(searchAssociationalWord),
                searchAssociationalWordVO);
        return new SearchAssociationalWordResponse(searchAssociationalWordVO);
    }

    /**
     * 修改搜索词
     *
     * @param searchAssociationalWord
     * @return
     */
    public SearchAssociationalWordResponse modify(SearchAssociationalWord searchAssociationalWord) {
        SearchAssociationalWord searchWord = searchAssociationalWordRepository.findById(searchAssociationalWord.getId())
                        .orElse(null);
        if(searchWord == null || DeleteFlag.YES.equals(searchWord.getDelFlag())){
            throw new SbcRuntimeException(getDeleteIndex(String.valueOf(searchAssociationalWord.getId()))
                    , SettingErrorCodeEnum.K070106);
        }
        // 搜索词去重
        Integer repeatName = searchAssociationalWordRepository.findByNameExceptSelf(searchAssociationalWord.getId(),
                searchAssociationalWord.getSearchTerms());
        if (Objects.nonNull(repeatName) && repeatName > 0) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070012);
        }

        if (searchAssociationalWord.getUpdatePerson() != null) {
            searchWord.setUpdatePerson(searchAssociationalWord.getUpdatePerson());
        }
        if (searchAssociationalWord.getSearchTerms() != null) {
            searchWord.setSearchTerms(searchAssociationalWord.getSearchTerms());
        }
        searchWord.setUpdateTime(LocalDateTime.now());
        searchWord.setDelFlag(DeleteFlag.NO);
        SearchAssociationalWordVO searchAssociationalWordVO = new SearchAssociationalWordVO();
        KsBeanUtil.copyPropertiesThird(searchAssociationalWordRepository.save(searchWord),
                searchAssociationalWordVO);
        return new SearchAssociationalWordResponse(searchAssociationalWordVO);
    }

    /**
     * 搜索词分页
     *
     * @param request
     * @return
     */
    public MicroServicePage<SearchAssociationalWordVO> searchPage(SearchAssociationalWordPageRequest request) {
        if(Objects.nonNull(request.getSort())){
            request.setSortColumn("createTime");
            request.setSortRole(SortType.DESC.toValue());
        }
        Specification<SearchAssociationalWord> specification = (root, query, build) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(build.equal(root.get("delFlag"),0));
            if(CollectionUtils.isNotEmpty(request.getIdList())){
                predicates.add(root.get("id").in(request.getIdList()));
            }
            return predicates.size() == 0 ? null : build.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<SearchAssociationalWord> page = searchAssociationalWordRepository.findAll(specification, request.getPageRequest());
        return new MicroServicePage<>(page.stream().map(source -> {
            SearchAssociationalWordVO response = new SearchAssociationalWordVO();
            BeanUtils.copyProperties(source, response);
            return response;
        }).collect(Collectors.toList()), request.getPageable(), page.getTotalElements());
    }



    /**
     * 新增联想词
     *
     * @param associationLongTailWord
     * @return
     */
    public AssociationLongTailWordResponse addAssociationalWord(AssociationLongTailWord associationLongTailWord) {
        associationLongTailWord.setCreateTime(LocalDateTime.now());
        associationLongTailWord.setDelFlag(DeleteFlag.NO);
        AssociationLongTailWordVO associationLongTailWordVO = new AssociationLongTailWordVO();
        KsBeanUtil.copyPropertiesThird(associationLongTailWordRepossitory.save(associationLongTailWord),
                associationLongTailWordVO);
        return new AssociationLongTailWordResponse(associationLongTailWordVO);
    }

    /**
     * 修改联想词
     *
     * @param associationLongTailWord
     * @return
     */
    public AssociationLongTailWordResponse modifyAssociationalWord(AssociationLongTailWord associationLongTailWord) {
        AssociationLongTailWord associationWord =
                associationLongTailWordRepossitory.findById(associationLongTailWord.getAssociationLongTailWordId()).orElseThrow(() -> new SbcRuntimeException(SettingErrorCodeEnum.K070106));
        if (associationLongTailWord.getAssociationalWord() != null) {
            associationWord.setAssociationalWord(associationLongTailWord.getAssociationalWord());
        }
        associationWord.setLongTailWord(associationLongTailWord.getLongTailWord());
        if (associationLongTailWord.getUpdatePerson() != null) {
            associationWord.setUpdatePerson(associationLongTailWord.getUpdatePerson());
        }
        associationWord.setUpdateTime(LocalDateTime.now());
        associationWord.setDelFlag(DeleteFlag.NO);
        AssociationLongTailWordVO associationLongTailWordVO = new AssociationLongTailWordVO();
        KsBeanUtil.copyPropertiesThird(associationLongTailWordRepossitory.save(associationWord),
                associationLongTailWordVO);
        return new AssociationLongTailWordResponse(associationLongTailWordVO);
    }


    /**
     * 联想词排序
     *
     * @param request
     * @return
     */
    @Transactional
    public BaseResponse sortAssociationalWord(List<AssociationLongTailWord> request) {
        for (AssociationLongTailWord associationLongTailWord : request) {
            associationLongTailWordRepossitory.sortAssociationalWord(associationLongTailWord.getSortNumber(),
                    associationLongTailWord.getAssociationLongTailWordId());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除搜索词
     *
     * @param searchAssociationalWordId
     * @return
     */
    @Transactional
    public int deleteSearchAssociationalWord(Long searchAssociationalWordId) {
        return searchAssociationalWordRepository.deleteSearchAssociationalWord(searchAssociationalWordId);
    }

    /**
     * 删除联想词
     *
     * @param associationLongTailWordId
     * @return
     */
    @Transactional
    public int deleteAssociationLongTailWord(Long associationLongTailWordId) {
        return associationLongTailWordRepossitory.deleteAssociationLongTailWord(associationLongTailWordId);
    }

    /**
     * 根据搜索词id查询该搜索词下的联想词list
     * @param searchAssociationalWordId
     * @return
     */
    public List<AssociationLongTailWord> getAssociationWordList (Long searchAssociationalWordId) {
        return associationLongTailWordRepossitory.findBySearchAssociationalWordIdAndDelFlag(searchAssociationalWordId,
                DeleteFlag.NO);
    }

    /**
     * 除自己外，联想词是否重复
     * @param searchAssociationalWordId   搜索词ID
     * @param associationalWord 联想词名称
     * @param associationLongTailWordId 联想词id
     * @return
     */
    public Integer findByNameExceptSelf(Long associationLongTailWordId, Long searchAssociationalWordId,
                                        String associationalWord) {
        return associationLongTailWordRepossitory.findByNameExceptSelf(associationLongTailWordId, searchAssociationalWordId,
                associationalWord);
    }

    /**
     * 根据联想词id查询联想词
     * @param associationLongTailWordId
     * @return
     */
    public AssociationLongTailWord getAssociationLongTailWordById(Long associationLongTailWordId){
        return associationLongTailWordRepossitory.findById(associationLongTailWordId).orElse(null);
    }

    /**
     * 模糊搜索联想词
     *
     * @param associationalWord
     * @return
     */
    public AssociationLongTailWordLikeResponse likeAssociationalWord(String associationalWord) {
        List<AssociationLongTailLikeWordVO> associationLongTailLikeWordVOLists = new ArrayList<>();
        if(StringUtils.isBlank(associationalWord)){
            return new AssociationLongTailWordLikeResponse(associationLongTailLikeWordVOLists);
        }
        List<SearchAssociationalWord> listSerach =
                searchAssociationalWordRepository.queryLikeSearchTermsAndDelFlag(associationalWord,
                        DeleteFlag.NO);
        if (CollectionUtils.isNotEmpty(listSerach)) {
            for (SearchAssociationalWord searchAssociationalWord : listSerach) {
                List<AssociationLongTailWord> associationLongTailWordList =
                        associationLongTailWordRepossitory.findBySearchAssociationalWordIdAndDelFlag(searchAssociationalWord.getId(), DeleteFlag.NO);
                for (AssociationLongTailWord associationLongTailWord : associationLongTailWordList) {
                    AssociationLongTailLikeWordVO associationLongTailLikeWordVO = new AssociationLongTailLikeWordVO();
                    if (StringUtil.isNotBlank(associationLongTailWord.getLongTailWord())) {
                        associationLongTailLikeWordVO.setLongTailWord(associationLongTailWord.getLongTailWord().split(";"));
                    }
                    KsBeanUtil.copyPropertiesThird(associationLongTailWord, associationLongTailLikeWordVO);
                    associationLongTailLikeWordVOLists.add(associationLongTailLikeWordVO);
                }
            }
        }
        associationLongTailLikeWordVOLists.forEach(wordVO -> {
            if (Objects.isNull(wordVO.getSortNumber())){
                wordVO.setSortNumber(1L);
            }
        });
        return new AssociationLongTailWordLikeResponse(associationLongTailLikeWordVOLists.stream().sorted(Comparator.comparing(AssociationLongTailLikeWordVO::getSortNumber)).collect(Collectors.toList()));
    }

    /**
     * 拼凑删除es-提供给findOne去调
     * @param id 编号
     * @return "{index}:{id}"
     */
    private Object getDeleteIndex(String id){
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.SEARCH_ASSOCIATIONAL_WORD, id);
    }
}
