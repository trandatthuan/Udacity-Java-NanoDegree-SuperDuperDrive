package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    public List<Credential> getCredentialByUserId(Integer userId) {
        List<Credential> credentialList = credentialMapper.getCredentials(userId);

        for(Credential credential : credentialList) {
            credential.setDecryptPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }

        return credentialList;
    }

    public void addCredential(CredentialForm form, Integer userId) {
        Credential newCredential = new Credential();
        String key = generateRandomKey();

        // Encrypt password
        String encryptPassword = encryptionService.encryptValue(form.getPassword(), key);

        // Package new credential
        newCredential.setUrl(form.getUrl());
        newCredential.setUsername(form.getUsername());
        newCredential.setKey(key);
        newCredential.setPassword(encryptPassword);
        newCredential.setUserId(userId);

        credentialMapper.insertCredential(newCredential);
    }

    public void updateCredential(CredentialForm form) {
        Credential updateCredential = new Credential();
        String key = generateRandomKey();

        // Encrypt password
        String encryptPassword = encryptionService.encryptValue(form.getPassword(), key);

        // Package update credential
        updateCredential.setCredentialId(form.getCredentialId());
        updateCredential.setUrl(form.getUrl());
        updateCredential.setUsername(form.getUsername());
        updateCredential.setKey(key);
        updateCredential.setPassword(encryptPassword);

        credentialMapper.updateCredentialById(updateCredential);
    }

    public void deleteCredential(Integer credentialId) { credentialMapper.deleteCredentialById(credentialId); }

    public String generateRandomKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);

        return Base64.getEncoder().encodeToString(key);
    }
}
