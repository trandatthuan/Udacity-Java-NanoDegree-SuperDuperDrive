package com.udacity.jwdnd.course1.cloudstorage.exceptionHandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeException(MaxUploadSizeExceededException ex) {
        return "redirect:/result?error=true&errorCode=10003";
    }
}
