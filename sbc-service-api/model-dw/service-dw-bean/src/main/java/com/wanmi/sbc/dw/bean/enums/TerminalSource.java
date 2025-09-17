package com.wanmi.sbc.dw.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.HashSet;
import java.util.Set;

@Schema
public enum TerminalSource {

    @ApiEnumProperty("H5")
    H5(1, "H5"),

    @ApiEnumProperty("PC")
    PC(2, "PC"),

    @ApiEnumProperty("APP")
    APP(3, "APP"),

    @ApiEnumProperty("小程序")
    MINI_PROGRAM(4, "小程序");


    private int typeId;
    private String typeName;

    TerminalSource(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TerminalSource getEnum(int typeId) {
       /* for (TerminalSource terminalSource : TerminalSource.values()) {
            if (typeId == terminalSource.getValue()) {
                return terminalSource;
            }
        }
        return null;*/
        switch (typeId) {
            case 1:
                return H5;
            case 2:
                return PC;
            case 3:
                return APP;
            case 4:
                return MINI_PROGRAM;
            default:
                return null;
        }
    }

}