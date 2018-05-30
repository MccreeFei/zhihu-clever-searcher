package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-09 9:41
 */
@Data
public class ZhihuUser {
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

    public static class UserBuilder {
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

        public UserBuilder setCharacterUrl(String characterUrl) {
            this.characterUrl = characterUrl;
            return this;
        }

        public UserBuilder setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setSimpleDescription(String simpleDescription) {
            this.simpleDescription = simpleDescription;
            return this;
        }

        public UserBuilder setThanks(Integer thanks) {
            this.thanks = thanks;
            return this;
        }

        public UserBuilder setAgrees(Integer agrees) {
            this.agrees = agrees;
            return this;
        }

        public UserBuilder setCollects(Integer collects) {
            this.collects = collects;
            return this;
        }

        public UserBuilder setFollowers(Integer followers) {
            this.followers = followers;
            return this;
        }

        public UserBuilder setFollowees(Integer followees) {
            this.followees = followees;
            return this;
        }

        public UserBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public UserBuilder setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public ZhihuUser build() {
            ZhihuUser user = new ZhihuUser();
            user.setCharacterUrl(characterUrl);
            user.setHeadUrl(headUrl);
            user.setUserName(userName);
            user.setSimpleDescription(simpleDescription);
            user.setAgrees(agrees);
            user.setThanks(thanks);
            user.setCollects(collects);
            user.setFollowers(followers);
            user.setFollowees(followees);
            user.setCreateTime(createTime);
            user.setModifyTime(modifyTime);
            return user;
        }
    }

}
