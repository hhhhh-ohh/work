package com.wanmi.sbc.xsite;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ImageForms {

    @Schema(description = "魔方建站参数")
    private String advice;

    @Schema(description = "文件")
    private byte[] duration;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "魔方建站参数")
    private Integer height;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "魔方建站参数")
    private String scene;

    @Schema(description = "魔方建站参数")
    private Long size;

    @Schema(description = "魔方建站参数")
    private String url;

    @Schema(description = "魔方建站参数")
    private Integer width;

}
