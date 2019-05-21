package com.generate.model;

public class SourceProfile {
    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 协议
     */
    private String protocol = "mongo://";
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
