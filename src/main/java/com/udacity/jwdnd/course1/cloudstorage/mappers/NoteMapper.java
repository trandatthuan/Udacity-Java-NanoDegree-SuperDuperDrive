package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotes (Integer userId);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid)" +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE notes " +
            "SET notetitle = #{noteTitle}," +
            "notedescription = #{noteDescription}" +
            "WHERE noteid = #{noteId};")
    void updateNoteById(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteid}")
    void deleteNoteById(Integer noteId);
}
