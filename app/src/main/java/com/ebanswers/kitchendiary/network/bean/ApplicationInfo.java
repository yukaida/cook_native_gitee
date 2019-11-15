package com.ebanswers.kitchendiary.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationInfo implements Parcelable{

    public ApplicationInfo() {
    }

    private String applicationName;
    private int imageDrawable;
    private int applicationId;
    private String applicationCode;
    private String isAdded;
    private String isVisiable;

    protected ApplicationInfo(Parcel in) {
        applicationName = in.readString();
        imageDrawable = in.readInt();
        applicationId = in.readInt();
        applicationCode = in.readString();
        isAdded = in.readString();
        isVisiable = in.readString();
    }

    public static final Creator<ApplicationInfo> CREATOR = new Creator<ApplicationInfo>() {
        @Override
        public ApplicationInfo createFromParcel(Parcel in) {
            return new ApplicationInfo(in);
        }

        @Override
        public ApplicationInfo[] newArray(int size) {
            return new ApplicationInfo[size];
        }
    };

    public String getIsVisiable() {
        return isVisiable;
    }

    public void setIsVisiable(String isVisiable) {
        this.isVisiable = isVisiable;
    }

    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(applicationName);
        dest.writeInt(imageDrawable);
        dest.writeInt(applicationId);
        dest.writeString(applicationCode);
        dest.writeString(isAdded);
        dest.writeString(isVisiable);
    }
}
