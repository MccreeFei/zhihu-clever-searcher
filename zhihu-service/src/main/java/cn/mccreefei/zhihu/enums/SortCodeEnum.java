package cn.mccreefei.zhihu.enums;

/**
 * @author MccreeFei
 * @create 2018-01-29 14:31
 */
public enum SortCodeEnum {
    GENERAL_SORT(0, "综合排序"),
    MATCH_SORT(1, "文本匹配排序"),
    TEXT_SORT(2, "文本影响度排序"),
    USER_SORT(3, "作者影响度排序");
    private Integer code;
    private String desc;

    SortCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SortCodeEnum getByCode(Integer code){
        for (SortCodeEnum _enum : values()){
            if (_enum.getCode().equals(code)){
                return _enum;
            }
        }
        return null;
    }
}
