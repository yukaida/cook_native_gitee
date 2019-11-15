package com.ebanswers.kitchendiary.network.bean;

public class LoginInfo {

    /**
     * object : {"address":null,"birthday":{"day":19,"eon":null,"eonAndYear":1991,"fractionalSecond":null,"hour":0,"millisecond":-2147483648,"minute":0,"month":3,"second":0,"timezone":480,"valid":true,"xMLSchemaType":{"localPart":"dateTime","namespaceURI":"http://www.w3.org/2001/XMLSchema","prefix":""},"year":1991},"cellphone":"13051666059","cellphoneNumber":null,"compId":62,"createTime":{"day":29,"eon":null,"eonAndYear":2018,"fractionalSecond":0.386,"hour":10,"millisecond":386,"minute":47,"month":10,"second":35,"timezone":480,"valid":true,"xMLSchemaType":{"$ref":"$.object.birthday.xMLSchemaType"},"year":2018},"deptId":63,"deptName":"研发部","disabled":2,"email":null,"expired":2,"gender":1,"groupId":19,"id":590,"idCard":"230921199103191618","idCardNum":null,"lastLoginTime":null,"lastLogoutTime":null,"locked":2,"naming":null,"password":"3a95b65a40e78dce053be1f72eef71b937b649767e514d31de26361a123c820e","phone":"","qq":"0","realname":"姚智荣","roleName":"权限分配,【中科倚天】项目经理,【中科倚天】部门经理","state":1,"telephoneNumber":null,"type":1,"updateTime":null,"userPass":null,"username":"yaozhirong"}
     * success : true
     */

    private LoginObject object;
    private boolean success;

    public LoginObject getObject() {
        return object;
    }

    public void setObject(LoginObject object) {
        this.object = object;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
