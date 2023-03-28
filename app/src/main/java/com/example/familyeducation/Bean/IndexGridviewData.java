package com.example.familyeducation.Bean;

import com.example.familyeducation.R;

import java.util.ArrayList;
import java.util.List;

public class IndexGridviewData {

    public static String[] subArray = {"数学","语文","英语","物理",
            "政治","化学","生物","地理"};
    public static String[] gradeArray = {"一年级","二年级","三年级","四年级",
            "五年级","六年级","初一","初二","初三","高一","高二","高三"};
    private static String[] teachernames =
            new String[]{"热门心理老师","热门英语老师","热门物理老师","热门数学老师",
                    "热门语文老师","热门地理老师"};
    private static int[] classImgs =
            new int[]{R.drawable.formula,R.drawable.chinese,R.drawable.english,R.drawable.magnet,
                    R.drawable.balance_two,R.drawable.cuvette,R.drawable.leaf,R.drawable.world};
    private static int[] teacherImgs =
            new int[]{R.drawable.face_without_mouth,R.drawable.expressionless_face,
                    R.drawable.smiling_face,R.drawable.weary_face,
                    R.drawable.upside_down_face,R.drawable.emotion_unhappy};

    private static String[] meslisttitles = new String[]{"预约老师消息","我要提建议"};
    private static String[] meslisttitles1 = new String[]{"消息论坛"};
    private static String[] meslisttitles2 = new String[]{"优惠信息"};
    private static int[] meslistImgs =
            new int[]{R.drawable.message_success,R.drawable.repair};
    private static int[] meslistImgs1 = new int[]{R.drawable.communication};
    private static int[] meslistImgs2 = new int[]{R.drawable.coupon};

    private static String[] usermoneytitles = new String[]{"我的订单","我的钱包","奖学券"};
    private static String[] userlisttitles = new String[]{"我的老师","我的评价"};
    private static String[] userlisttitles1 = new String[]{"我的回答","我的课程","学习计划与总结"};
    private static String[] userlisttitles2 = new String[]{"设置"};
    private static String[] user_moneylisttitles2 = new String[]{"我的余额","我的积分"};
    private static int[] usermoneyImgs =
            new int[]{R.drawable.order,R.drawable.wallet,R.drawable.coupon};
    private static int[] userlistImgs =
            new int[]{R.drawable.message_success,R.drawable.repair};
    private static int[] userlistImgs1 =
            new int[]{R.drawable.file_question,R.drawable.blackboard,R.drawable.plan};
    private static int[] userlistImgs2 = new int[]{R.drawable.setting};
    private static int[] user_moneylistImgs2 =
            new int[]{R.drawable.financing,R.drawable.scoreboard};

    public static List<IndexGridviewIcon> getuserListDatas(int listnum){
        List<IndexGridviewIcon> list = new ArrayList<IndexGridviewIcon>();
        switch (listnum){
            case -1:
                for (int i = 0; i < usermoneyImgs.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = usermoneyImgs[i];
                    bean.iName = usermoneytitles[i];
                    list.add(bean);
                }
                break;
            case 0:
                for (int i = 0; i < userlistImgs.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = userlistImgs[i];
                    bean.iName = userlisttitles[i];
                    list.add(bean);
                }
                break;
            case 1:
                for (int i = 0; i < userlistImgs1.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = userlistImgs1[i];
                    bean.iName = userlisttitles1[i];
                    list.add(bean);
                }
                break;
            case 2:
                for (int i = 0; i < userlistImgs2.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = userlistImgs2[i];
                    bean.iName = userlisttitles2[i];
                    list.add(bean);
                }
                break;
            case 3:
                for (int i = 0; i < user_moneylistImgs2.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = user_moneylistImgs2[i];
                    bean.iName = user_moneylisttitles2[i];
                    list.add(bean);
                }
                break;
            case 4:
                String[] s = {"奖学券","金额","到期时间"};
                for (int i = 0; i < 3; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iName = s[i];
                    list.add(bean);
                }
                break;
            case 5:
                String[] s1 = {"书名/数量","价格","状态"};
                for (int i = 0; i < 3; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iName = s1[i];
                    list.add(bean);
                }
                break;
            default:
                break;
        }
        return list;
    }

    public static List<IndexGridviewIcon> getmesListDatas(int listnum){
        List<IndexGridviewIcon> list = new ArrayList<IndexGridviewIcon>();
        switch (listnum){
            case 0:
                for (int i = 0; i < meslistImgs.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = meslistImgs[i];
                    bean.iName = meslisttitles[i];
                    list.add(bean);
                }
                break;
            case 1:
                for (int i = 0; i < meslistImgs1.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = meslistImgs1[i];
                    bean.iName = meslisttitles1[i];
                    list.add(bean);
                }
                break;
            case 2:
                for (int i = 0; i < meslistImgs2.length; i++) {
                    IndexGridviewIcon bean = new IndexGridviewIcon();
                    bean.iId = meslistImgs2[i];
                    bean.iName = meslisttitles2[i];
                    list.add(bean);
                }
                break;
            default:
                break;
        }
        return list;
    }

    public static List<IndexGridviewIcon> getclassDatas(){
        List<IndexGridviewIcon> list = new ArrayList<IndexGridviewIcon>();
        for (int i = 0; i < classImgs.length; i++) {
            IndexGridviewIcon bean = new IndexGridviewIcon();
            bean.iId = classImgs[i];
            bean.iName = subArray[i];
            list.add(bean);
        }
        return list;
    }

    public static List<IndexGridviewIcon> getteacherDatas(){
        List<IndexGridviewIcon> list = new ArrayList<IndexGridviewIcon>();
        for (int i = 0; i < teacherImgs.length; i++) {
            IndexGridviewIcon bean = new IndexGridviewIcon();
            bean.iId = teacherImgs[i];
            bean.iName = teachernames[i];
            list.add(bean);
        }
        return list;
    }

}
