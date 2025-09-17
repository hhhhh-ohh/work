package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeRequest implements Serializable {

    private List<Long> cateIds;

    private ThirdPlatformType thirdPlatformType;
}
