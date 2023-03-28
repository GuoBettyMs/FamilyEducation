package com.example.familyeducation.Bean;

public class UserInfo {
    public int par_id;
    public String par_phone;
    public String par_password;
    public int par_icon;

    public int tea_id;
    public String tea_phone;
    public String tea_sex;
    public String tea_name;
    public String tea_sub;
    public String tea_grade;
//    public boolean tea_isChecked;

    public int reserve_id;
    public String reserve_parphone;
    public String reserve_teaphone;
    public String reserve_teaname;
    public String reserve_teasub;
    public String reserve_teagrade;

    public int order_id;
    public String par_order_phone;
    public String par_order_bookname;
    public String par_order_bookprice;
    public String par_order_bookcount;
    public String par_order_bookstate;

    public int reward_id;
    public String reward_phone;
    public String reward_price;
    public String reward_date;

    public int money_id;
    public String money_phone;
    public String money_yue;
    public String money_jifen;

    public String toString(String table){
        String s = "";
        switch (table){
            case "reserve":
                s = "reserve: id: "+reserve_id+",reserve_parphone: "+reserve_parphone
                        +",reserve_teasub: "+reserve_teasub+",reserve_teagrade: "+reserve_teagrade
                        +",reserve_teaname: "+reserve_teaname+",reserve_teaphone: "+reserve_teaphone
                        +"\n";
                break;
            case "par_money":
                s = "par_money: id: "+money_id+",phone: "+money_phone
                        +",money_yue: "+money_yue+",money_jifen: "+money_jifen
                        +"\n";
                break;
            case "par_reward":
                s = "par_reward: id: "+reward_id+",phone: "+reward_phone
                        +",reward_price: "+reward_price+",reward_date: "+reward_date
                        +"\n";
                break;
            case "par_order":
                s = "par_order: id: "+order_id+",phone: "+par_order_phone
                        +",par_order_bookname: "+par_order_bookname+",par_order_bookprice: "+par_order_bookprice
                        +",par_order_bookcount: "+par_order_bookcount+",par_order_bookstate: "+par_order_bookstate
                        +"\n";
                break;
            case "parent":
                s = "parent: id: "+par_id+",phone: "+par_phone+",password: "+par_password
                        +",par_order_bookname: "+par_order_bookname
                        +"\n";
                break;
            case "teacher":
                s = "teacher: id: "+tea_id+",name: "+tea_name+",sub: "+tea_sub
                        +",grade: "+tea_grade+",phone: "+tea_phone +",sex: "+tea_sex+"\n";
                break;
            default:
                break;
        }
        return s;
    }

}
