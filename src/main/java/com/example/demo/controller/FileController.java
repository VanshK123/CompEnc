package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import org.xerial.snappy.Snappy;  // Import Snappy
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "aesEncryptionKey"; // 16-byte key for AES-128

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        long totalStartTime = System.currentTimeMillis();

        try {
            // Start timing compression
            long compressionStartTime = System.currentTimeMillis();
            // Compress the file using Snappy
            byte[] compressedData = compressFile(file);
            long compressionEndTime = System.currentTimeMillis();
            long compressionTime = compressionEndTime - compressionStartTime;

            // Start timing encryption
            long encryptionStartTime = System.currentTimeMillis();
            // Encrypt the compressed data
            byte[] encryptedData = encryptData(compressedData);
            long encryptionEndTime = System.currentTimeMillis();
            long encryptionTime = encryptionEndTime - encryptionStartTime;

            // Start timing file saving
            long savingStartTime = System.currentTimeMillis();
            // Save the encrypted data to disk
            saveFile(encryptedData, file.getOriginalFilename() + ".enc");
            long savingEndTime = System.currentTimeMillis();
            long savingTime = savingEndTime - savingStartTime;

            long totalEndTime = System.currentTimeMillis();
            long totalTime = totalEndTime - totalStartTime;

            // Build response message with timing information
            String responseMessage = String.format("""
                                                   File uploaded, compressed, and encrypted successfully.
                                                   Compression Time: %d ms
                                                   Encryption Time: %d ms
                                                   Saving Time: %d ms
                                                   Total Processing Time: %d ms""",
                    compressionTime, encryptionTime, savingTime, totalTime);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] compressFile(MultipartFile file) throws IOException {
        // Get the file's byte data
        byte[] fileData = file.getBytes();
        
        // Compress the byte data using Snappy
        return Snappy.compress(fileData);
    }

    private byte[] encryptData(byte[] data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private void saveFile(byte[] data, String fileName) throws IOException {
        File outputFile = new File("uploads/" + fileName);
        outputFile.getParentFile().mkdirs(); // Create directories if they don't exist

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(data);
        }
    }
}
