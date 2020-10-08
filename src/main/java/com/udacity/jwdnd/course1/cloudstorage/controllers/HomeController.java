package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String getHomepage(Principal principal, Model model) throws Exception {
        Integer userId = this.userService.getUserId(principal.getName());

        if (userId == null)  {
            return "redirect:/result?error=true&errorCode=00001";
        }

        // Get file
        try {
            model.addAttribute("userFiles", this.fileService.getFileByUserId(userId));
        } catch (Exception e) { return "redirect:/result?error=true&errorCode=10001"; }

        // Get notes
        try {
            model.addAttribute("userNotes", this.noteService.getNotesByUserId(userId));
        } catch(Exception e) { return "redirect:/result?error=true&errorCode=20001"; }

        // Get credentials
        try {
            model.addAttribute("userCredentials", this.credentialService.getCredentialByUserId(userId));
        } catch(Exception e) { return "redirect:/result?error=true&errorCode=30001"; }


        return "home";
    }

    /**********************************************
     *          File Methods
     ***********************************************/
    // Upload file
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Principal principal, RedirectAttributes attributes) {
        Integer userId = this.userService.getUserId(principal.getName());

        if (userId == null)  {
            return "redirect:/result?error=true&errorCode=00001";
        }

        // Check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/home";
        }

        try {
            // Check if file name is duplicate
            if (fileService.checkFileName(userId, StringUtils.cleanPath(file.getOriginalFilename()))) {
                attributes.addFlashAttribute("message", "Filename already existed. Try another one.");
                return "redirect:/home";
            }

            // Upload file to db
            fileService.insertFile(file, userId);
            return "redirect:/result?success=true";
        } catch(Exception err) {
            return "redirect:/result?error=true";
        }
    }

    // Download file
    @GetMapping("/download-file")
    public HttpServletResponse downloadFile(@RequestParam(name="fileId") String fileId, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(Integer.parseInt(fileId));

        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
        response.getOutputStream().write(file.getFileData());

        return response;
    }

    // Delete file
    @GetMapping("/delete-file")
    public String deleteFile(@RequestParam(name="fileId") String fileId) {
        try {
            this.fileService.deleteFileById(Integer.parseInt(fileId));
            return "redirect:/result?success=true";
        } catch(Exception err) { return "redirect:/result?error=true&errorCode=20002"; }
    }

    /**********************************************
    *           Note Methods
    ***********************************************/
    // Get input form for note
    @ModelAttribute("noteForm")
    public NoteForm getNoteForm() { return new NoteForm(); }

    // Insert/Update note
    @PostMapping("/save-note")
    public String saveNote(Principal principal, NoteForm noteForm) {
        Integer userId = this.userService.getUserId(principal.getName());

        if (userId == null)  {
            return "redirect:/result?error=true&errorCode=00001";
        }

        try {
            // Change if there is note id in the form
            // If id is null, add new note
            // else update note
            if (noteForm.getNoteId() == null) {
                this.noteService.addNote(noteForm, userId);
            } else {
                this.noteService.updateNote(noteForm);
            }
            return "redirect:/result?success=true";
        } catch(Exception err) { return "redirect:/result?error=true"; }
    }

    // Delete note
    @GetMapping("/delete-note")
    public String deleteNote(@RequestParam(name="noteId") String noteId) {
        try {
            this.noteService.deleteNote(Integer.parseInt(noteId));
            return "redirect:/result?success=true";
        } catch(Exception err) {return "redirect:/result?error=true&errorCode=20002"; }
    }

    /**********************************************
     *           Credential Methods
     ***********************************************/
    // Get input form for credential
    @ModelAttribute("credentialForm")
    public CredentialForm getCredentialForm() { return new CredentialForm(); }

    // Insert/update credential
    @PostMapping("/save-credential")
    public String saveCredential(Principal principal, CredentialForm credentialForm) {
        try {
            if (credentialForm.getCredentialId() == null) {
                this.credentialService.addCredential(credentialForm, this.userService.getUserId(principal.getName()));
            } else {
                this.credentialService.updateCredential(credentialForm);
            }
            return "redirect:/result?success=true";
        } catch(Exception err) { return "redirect:/result?error=true"; }
    }

    // Delete credential
    @GetMapping("/delete-credential")
    public String deleteCredential(@RequestParam(name="credentialId") String credentialId) {
        try {
            this.credentialService.deleteCredential(Integer.parseInt(credentialId));
            return "redirect:/result?success=true";
        } catch(Exception err) {return "redirect:/result?error=true&errorCode=20002"; }
    }
}
