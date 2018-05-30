package cn.mccreefei.zhihu.service.vo;

import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-01-22 9:46
 */
@Data
public class TextVo {
    private Integer articleId;
    private Integer questionId;
    private Integer answerId;
    private String content;
    private Integer textType;
}
