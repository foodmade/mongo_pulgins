
package com.generate.out;

import com.mongodb.DBRef;

/**
 * @author jackies
 * @email  2210465185@qq.com
 * @date   2019-05-21 23:41:03
 */

public class Account {

    private String _class;

    private String userName;

    private String userNick;

    private String password;

    private Integer permissionLevel;

    private DBRef userDetailInfo;

            
    public void set_class(String _class){
        this._class = _class;
    }

    public String get_class(){
        return this._class;
    }
            
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }
            
    public void setUserNick(String userNick){
        this.userNick = userNick;
    }

    public String getUserNick(){
        return this.userNick;
    }
            
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
            
    public void setPermissionLevel(Integer permissionLevel){
        this.permissionLevel = permissionLevel;
    }

    public Integer getPermissionLevel(){
        return this.permissionLevel;
    }
            
    public void setUserDetailInfo(DBRef userDetailInfo){
        this.userDetailInfo = userDetailInfo;
    }

    public DBRef getUserDetailInfo(){
        return this.userDetailInfo;
    }
}