package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT key FROM credentials WHERE credentialid = #{credentialId}")
    String getCredentialKeyById(Integer credentialId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid)" +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE credentials " +
            "SET url = #{url}," +
            "username = #{username}," +
            "key = #{key}," +
            "password = #{password}" +
            "WHERE credentialid = #{credentialId};")
    void updateCredentialById(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    void deleteCredentialById(Integer credentialId);
}
