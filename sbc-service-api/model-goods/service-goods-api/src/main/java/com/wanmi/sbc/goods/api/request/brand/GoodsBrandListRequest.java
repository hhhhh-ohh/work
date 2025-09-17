package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.SortType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 品牌查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandListRequest extends BaseRequest {

    private static final long serialVersionUID = -1561641414890367704L;

    /**
     * 批量品牌编号
     */
    @Schema(description = "批量品牌编号")
    private List<Long> brandIds;

    /**
     * and 精准查询，品牌名称
     */
    @Schema(description = "and 精准查询，品牌名称")
    private String brandName;

    /**
     * and 模糊查询，品牌名称
     */
    @Schema(description = "and 模糊查询，品牌名称")
    private String likeBrandName;

    /**
     * and 精准查询，品牌昵称
     */
    @Schema(description = "and 精准查询，品牌昵称")
    private String nickName;

    /**
     * and 模糊查询，品牌昵称
     */
    @Schema(description = "and 模糊查询，品牌昵称")
    private String likeNickName;

    /**
     * 模糊查询，品牌拼音
     */
    @Schema(description = "模糊查询，品牌拼音")
    private String likePinYin;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 非品牌编号
     */
    @Schema(description = "品牌编号")
    private Long notBrandId;

    /**
     * 关键字查询，可能含空格
     */
    @Schema(description = "品牌编号")
    private String keywords;

    /**
     * 多重排序
     * 内容：key:字段,value:desc或asc
     */
    @Schema(description = "品牌编号")
    private Map<String, String> sortMap = new LinkedHashMap<>();

    /**
     * 填序排序
     *
     * @param column
     * @param sort
     */
    public void putSort(String column, String sort) {
        sortMap.put(column, sort);
    }

    public Sort getSort() {
        //多重排序
        if (MapUtils.isNotEmpty(sortMap)) {
            List<Sort.Order> orders =
                    sortMap.keySet().stream().filter(StringUtils::isNotBlank)
                            .map(column -> new Sort.Order(SortType.ASC.toValue().equalsIgnoreCase(sortMap.get(column)
                            ) ? Sort.Direction.ASC : Sort.Direction.DESC, column))
                            .collect(Collectors.toList());
            return Sort.by(orders);
        }
        return null;
    }
}
