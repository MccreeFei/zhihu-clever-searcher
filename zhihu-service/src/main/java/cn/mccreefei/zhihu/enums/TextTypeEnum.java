package cn.mccreefei.zhihu.enums;

/**
 * @author MccreeFei
 * @create 2018-01-23 9:07
 */
public enum TextTypeEnum {
    ANSWER(1, "回答"),
    ARTICLE(2, "文章");

    private int code;
    private String desc;

    TextTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TextTypeEnum getByCode(int code){
        for (TextTypeEnum type : values()){
            if (type.code == code){
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
