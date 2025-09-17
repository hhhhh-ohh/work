package com.wanmi.sbc.crm.bean.enums;

public enum WeekDayInfoEnum {
    Mon(0, "周一"),
    Tus(1, "周二"),
    Wen(2, "周三"),
    Thu(3, "周四"),
    Fri(4, "周五"),
    Sat(5, "周六"),
    Sun(6, "周日");

    private int typeId;
    private String typeName;

    WeekDayInfoEnum(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static WeekDayInfoEnum getEnum(int typeId) {
        /*for (WeekDayEnum weekDayEnum : WeekDayEnum.values()) {
            if (typeId == weekDayEnum.getTypeId()) {
                return weekDayEnum;
            }
        }
        return null;*/

        switch (typeId){
            case 0:
                return Mon;
            case 1:
                return Tus;
            case 2:
                return Wen;
            case 3:
                return Thu;
            case 4:
                return Fri;
            case 5:
                return Sat;
            case 6:
                return Sun;
            default:
                return null;
        }
    }

    public static WeekDayInfoEnum getEnum(String typename) {
        switch (typename){
            case "周一":
                return Mon;
            case "周二":
                return Tus;
            case "周三":
                return Wen;
            case "周四":
                return Thu;
            case "周五":
                return Fri;
            case "周六":
                return Sat;
            case "周日":
                return Sun;
            default:
                return null;
        }
    }
}
