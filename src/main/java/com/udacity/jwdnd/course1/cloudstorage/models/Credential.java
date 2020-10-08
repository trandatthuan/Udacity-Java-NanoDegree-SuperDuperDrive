package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;
    private String decryptPassword;

    public Integer getCredentialId() { return credentialId; }

    public void setCredentialId(Integer credentialId) { this.credentialId = credentialId; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getDecryptPassword() { return decryptPassword; }

    public void setDecryptPassword(String decryptPassword) { this.decryptPassword = decryptPassword; }
}
