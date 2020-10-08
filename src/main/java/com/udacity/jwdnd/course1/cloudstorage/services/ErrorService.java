package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

@Service
public class ErrorService {
    public String setUserCustomError(String errorCode) {
        String error;

        switch(errorCode) {
            case "00001":
                error = "Error getting userId...";
                break;
            default:
                error = "Unknown error while getting user information...";
                break;
        }

        return error;
    }

    public String setFileCustomError(String errorCode) {
        String error;

        switch(errorCode) {
            case "10001":
                error = "Error fetching list of files...";
                break;
            case "10002":
                error = "Error deleting file...";
                break;
            case "10003":
                error = "Error uploading file. File size cannot exceed 10MB, please try another one with a smaller size...";
                break;
            default:
                error = "Unknown error while processing your file request...";
                break;
        }

        return error;
    }

    public String setNoteCustomError(String errorCode) {
        String error;

        // Set error message
        switch(errorCode) {
            case "20001":
                error = "Error getting list of notes...";
                break;
            case "20002":
                error = "Error deleting note...";
                break;
            default:
                error = "Unknown error processing note request...";
                break;
        }

        return error;
    }

    public String setCredentialCustomError(String errorCode) {
        String error;

        // Set error message
        switch(errorCode) {
            case "30001":
                error = "Error getting list of credentials...";
                break;
            case "30002":
                error = "Error deleting credential...";
                break;
            default:
                error = "Unknown error processing credential request";
                break;
        }

        return error;
    }

    public String getCustomError(String errorCode) {
        switch(errorCode.charAt(0)) {
            case '0':
                return setUserCustomError(errorCode);
            case '1':
                return setFileCustomError(errorCode);
            case '2':
                return setNoteCustomError(errorCode);
            case '3':
                return setCredentialCustomError(errorCode);
            default:
                return "Unknown error...";
        }
    }
}
