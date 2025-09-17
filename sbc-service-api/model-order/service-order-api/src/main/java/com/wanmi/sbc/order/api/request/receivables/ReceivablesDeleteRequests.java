package com.wanmi.sbc.order.api.request.receivables;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema
public class ReceivablesDeleteRequests  implements Serializable {

    @Schema(description = "content")
    List<String> content;
}
