package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT CASE WHEN count(*) > 0 THEN 'Y' ELSE 'N' END AS isDuplicated FROM files " +
            "WHERE userid = #{userId} AND filename = #{fileName}")
    String isFileNameDuplicate(Integer userId, String fileName);

    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM files WHERE fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata)" +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM files WHERE fileid = #{fileId}")
    void deleteFileById(Integer fileId);
}
