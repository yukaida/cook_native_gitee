package com.ebanswers.kitchendiary.network.bean;

public class CreateTimeInfo {

    /**
     * day : 29
     * eon : null
     * eonAndYear : 2018
     * fractionalSecond : 0.386
     * hour : 10
     * millisecond : 386
     * minute : 47
     * month : 10
     * second : 35
     * timezone : 480
     * valid : true
     * xMLSchemaType : {"$ref":"$.object.birthday.xMLSchemaType"}
     * year : 2018
     */

    private int day;
    private Object eon;
    private int eonAndYear;
    private double fractionalSecond;
    private int hour;
    private int millisecond;
    private int minute;
    private int month;
    private int second;
    private int timezone;
    private boolean valid;
    private XMLSchemaTypeBeanX xMLSchemaType;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Object getEon() {
        return eon;
    }

    public void setEon(Object eon) {
        this.eon = eon;
    }

    public int getEonAndYear() {
        return eonAndYear;
    }

    public void setEonAndYear(int eonAndYear) {
        this.eonAndYear = eonAndYear;
    }

    public double getFractionalSecond() {
        return fractionalSecond;
    }

    public void setFractionalSecond(double fractionalSecond) {
        this.fractionalSecond = fractionalSecond;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public XMLSchemaTypeBeanX getXMLSchemaType() {
        return xMLSchemaType;
    }

    public void setXMLSchemaType(XMLSchemaTypeBeanX xMLSchemaType) {
        this.xMLSchemaType = xMLSchemaType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static class XMLSchemaTypeBeanX {
        /**
         * $ref : $.object.birthday.xMLSchemaType
         */

        private String $ref;

        public String get$ref() {
            return $ref;
        }

        public void set$ref(String $ref) {
            this.$ref = $ref;
        }
    }
}
