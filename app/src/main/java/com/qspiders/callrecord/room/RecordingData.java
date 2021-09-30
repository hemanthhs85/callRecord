package com.qspiders.callrecord.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordingData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("call_type")
    @Expose
    private Integer callType;
    @SerializedName("main_status")
    @Expose
    private Integer mainStatus;
    @SerializedName("sub_status")
    @Expose
    private Integer subStatus;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rating_url")
    @Expose
    private String ratingUrl;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public Integer getMainStatus() {
        return mainStatus;
    }

    public void setMainStatus(Integer mainStatus) {
        this.mainStatus = mainStatus;
    }

    public Integer getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatingUrl() {
        return ratingUrl;
    }

    public void setRatingUrl(String ratingUrl) {
        this.ratingUrl = ratingUrl;
    }

}