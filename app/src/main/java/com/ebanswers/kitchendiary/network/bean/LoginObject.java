package com.ebanswers.kitchendiary.network.bean;

public class LoginObject {

    /**
     * address : null
     * birthday : {"day":19,"eon":null,"eonAndYear":1991,"fractionalSecond":null,"hour":0,"millisecond":-2147483648,"minute":0,"month":3,"second":0,"timezone":480,"valid":true,"xMLSchemaType":{"localPart":"dateTime","namespaceURI":"http://www.w3.org/2001/XMLSchema","prefix":""},"year":1991}
     * cellphone : 13051666059
     * cellphoneNumber : null
     * compId : 62
     * createTime : {"day":29,"eon":null,"eonAndYear":2018,"fractionalSecond":0.386,"hour":10,"millisecond":386,"minute":47,"month":10,"second":35,"timezone":480,"valid":true,"xMLSchemaType":{"$ref":"$.object.birthday.xMLSchemaType"},"year":2018}
     * deptId : 63
     * deptName : 研发部
     * disabled : 2
     * email : null
     * expired : 2
     * gender : 1
     * groupId : 19
     * id : 590
     * idCard : 230921199103191618
     * idCardNum : null
     * lastLoginTime : null
     * lastLogoutTime : null
     * locked : 2
     * naming : null
     * password : 3a95b65a40e78dce053be1f72eef71b937b649767e514d31de26361a123c820e
     * phone :
     * qq : 0
     * realname : 姚智荣
     * roleName : 权限分配,【中科倚天】项目经理,【中科倚天】部门经理
     * state : 1
     * telephoneNumber : null
     * type : 1
     * updateTime : null
     * userPass : null
     * username : yaozhirong
     */

    private String address;
    private BirthdayInfo birthday;
    private String cellphone;
    private String cellphoneNumber;
    private int compId;
    private CreateTimeInfo createTime;
    private int deptId;
    private String deptName;
    private int disabled;
    private String email;
    private int expired;
    private int gender;
    private int groupId;
    private int id;
    private String idCard;
    private String idCardNum;
    private String lastLoginTime;
    private String lastLogoutTime;
    private int locked;
    private String naming;
    private String password;
    private String phone;
    private String qq;
    private String realname;
    private String roleName;
    private int state;
    private String telephoneNumber;
    private int type;
    private String updateTime;
    private String userPass;
    private String username;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BirthdayInfo getBirthday() {
        return birthday;
    }

    public void setBirthday(BirthdayInfo birthday) {
        this.birthday = birthday;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public CreateTimeInfo getCreateTime() {
        return createTime;
    }

    public void setCreateTime(CreateTimeInfo createTime) {
        this.createTime = createTime;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(String lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
