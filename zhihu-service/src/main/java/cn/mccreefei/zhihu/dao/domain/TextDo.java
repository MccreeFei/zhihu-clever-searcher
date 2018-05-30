package cn.mccreefei.zhihu.dao.domain;

import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-01-22 9:34
 */
@Data
public class TextDo {
    private Integer articleId;
    private Integer questionId;
    private Integer answerId;
    private String content;
    private Integer textType;
}
