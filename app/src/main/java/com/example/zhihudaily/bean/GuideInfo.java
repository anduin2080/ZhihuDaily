package com.example.zhihudaily.bean;

public class GuideInfo {

    public GuideInfo(String guideName, String guidePic) {
        this.guideName = guideName;
        this.guidePic = guidePic;
    }

    private String guideName;
    private String guidePic;

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getGuidePic() {
        return guidePic;
    }

    public void setGuidePic(String guidePic) {
        this.guidePic = guidePic;
    }

    @Override
    public String toString() {
        return "GuideInfo{" +
                "guideName='" + guideName + '\'' +
                ", guidePic=" + guidePic +
                '}';
    }
}
