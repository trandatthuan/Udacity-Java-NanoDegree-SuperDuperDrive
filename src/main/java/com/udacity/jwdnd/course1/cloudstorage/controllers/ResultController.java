package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.ErrorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/result")
public class ResultController {
    private ErrorService errorService;

    public ResultController(ErrorService errorService) { this.errorService = errorService; }

    @GetMapping
    public String getResultView(@RequestParam(required = false, value = "success") final String success,
                                @RequestParam(required = false, value = "error") final String error,
                                @RequestParam(required = false, value = "errorCode") final String errorCode,
                                Model model) {
        if (success == null) {
            if (errorCode != null) {
                model.addAttribute("errorMessage", errorService.getCustomError(errorCode));
            }
        }
        return "result";
    }
}
