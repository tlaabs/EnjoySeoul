package site.devsim.enjoyseoul.DB.POJO;

import java.io.Serializable;

import site.devsim.enjoyseoul.Util.PureImgSrcUtil;

public class EventItem implements Serializable {
    private String id;
    private String genre;
    private String title;
    private String startDate;
    private String endDate;
    private String time;
    private String place;
    private String orgLink;
    private String mainImg;
    private String target;
    private String fee;
    private String inquiry;
    private String etcDesc;
    private String isFree;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }

    public String getMainImg() {
        return PureImgSrcUtil.in(mainImg);
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public String getEtcDesc() {
        return etcDesc;
    }

    public void setEtcDesc(String etcDesc) {
        this.etcDesc = etcDesc;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }
}
