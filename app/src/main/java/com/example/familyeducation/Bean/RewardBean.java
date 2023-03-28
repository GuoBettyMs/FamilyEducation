package com.example.familyeducation.Bean;

public class RewardBean {
    public int iId;
    public String iAmount;
    public String iDuetime;

    public RewardBean() {
    }

    public RewardBean(int iId, String iAmount, String iDuetime) {
        this.iId = iId;
        this.iAmount = iAmount;
        this.iDuetime = iDuetime;
    }

    public int getiId() {
        return iId;
    }
    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiAmount() {
        return iAmount;
    }
    public void setiAmount(String iAmount) {
        this.iAmount = iAmount;
    }

    public String getiDuetime() {
        return iDuetime;
    }
    public void setiDuetime(String iDuetime) {
        this.iDuetime = iDuetime;
    }

}
