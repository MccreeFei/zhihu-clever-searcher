package cn.mccreefei.zhihu.dao.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2018-01-19 16:36
 */
@Data
public class UserDo {
    private Integer id;
    private String characterUrl;
    private String headUrl;
    private String userName;
    private String simpleDescription;
    private Integer thanks;
    private Integer agrees;
    private Integer collects;
    private Integer followers;
    private Integer followees;
    private Date createTime;
    private Date modifyTime;

    public UserDo(){}

    @Builder
    public UserDo(Integer id, String characterUrl, String headUrl, String userName, String simpleDescription,
                  Integer thanks, Integer agrees, Integer collects, Integer followers,
                  Integer followees, Date createTime, Date modifyTime) {
        this.id = id;
        this.characterUrl = characterUrl;
        this.headUrl = headUrl;
        this.userName = userName;
        this.simpleDescription = simpleDescription;
        this.thanks = thanks;
        this.agrees = agrees;
        this.collects = collects;
        this.followers = followers;
        this.followees = followees;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }
}
