package com.ebanswers.kitchendiary.network.bean;

public class BirthdayInfo {

    /**
     * day : 19
     * eon : null
     * eonAndYear : 1991
     * fractionalSecond : null
     * hour : 0
     * millisecond : -2147483648
     * minute : 0
     * month : 3
     * second : 0
     * timezone : 480
     * valid : true
     * xMLSchemaType : {"localPart":"dateTime","namespaceURI":"http://www.w3.org/2001/XMLSchema","prefix":""}
     * year : 1991
     */

    private int day;
    private String eon;
    private int eonAndYear;
    private String fractionalSecond;
    private int hour;
    private int millisecond;
    private int minute;
    private int month;
    private int second;
    private int timezone;
    private boolean valid;
    private XMLSchemaTypeBean xMLSchemaType;
    private int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getEon() {
        return eon;
    }

    public void setEon(String eon) {
        this.eon = eon;
    }

    public int getEonAndYear() {
        return eonAndYear;
    }

    public void setEonAndYear(int eonAndYear) {
        this.eonAndYear = eonAndYear;
    }

    public String getFractionalSecond() {
        return fractionalSecond;
    }

    public void setFractionalSecond(String fractionalSecond) {
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

    public XMLSchemaTypeBean getXMLSchemaType() {
        return xMLSchemaType;
    }

    public void setXMLSchemaType(XMLSchemaTypeBean xMLSchemaType) {
        this.xMLSchemaType = xMLSchemaType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static class XMLSchemaTypeBean {
        /**
         * localPart : dateTime
         * namespaceURI : http://www.w3.org/2001/XMLSchema
         * prefix :
         */

        private String localPart;
        private String namespaceURI;
        private String prefix;

        public String getLocalPart() {
            return localPart;
        }

        public void setLocalPart(String localPart) {
            this.localPart = localPart;
        }

        public String getNamespaceURI() {
            return namespaceURI;
        }

        public void setNamespaceURI(String namespaceURI) {
            this.namespaceURI = namespaceURI;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
