package cn.mccreefei.zhihu.util;

import cn.mccreefei.zhihu.service.vo.EsComparedSearchVo;

import java.util.Comparator;

/**
 * @author MccreeFei
 * @create 2018-01-29 14:52
 */
public class ZhihuComparator {
    public static GeneralComparator generalComparator = new GeneralComparator();
    public static MatchComparator matchComparator = new MatchComparator();
    public static TextComparator textComparator = new TextComparator();
    public static UserComparator userComparator = new UserComparator();
    private static class GeneralComparator implements Comparator<EsComparedSearchVo> {

        @Override
        public int compare(EsComparedSearchVo o1, EsComparedSearchVo o2) {
            return o2.getComparedScore() - o1.getComparedScore();
        }
    }

    private static class MatchComparator implements Comparator<EsComparedSearchVo> {

        @Override
        public int compare(EsComparedSearchVo o1, EsComparedSearchVo o2) {
            return o2.getMatchScore() - o1.getMatchScore();
        }
    }

    private static class TextComparator implements Comparator<EsComparedSearchVo> {

        @Override
        public int compare(EsComparedSearchVo o1, EsComparedSearchVo o2) {
            return o2.getTextScore() - o1.getTextScore();
        }
    }

    private static class UserComparator implements Comparator<EsComparedSearchVo> {

        @Override
        public int compare(EsComparedSearchVo o1, EsComparedSearchVo o2) {
            return o2.getUserScore() - o1.getUserScore();
        }
    }
}
