package com.enduser.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PredictionService {

    private final String FLASK_API_URL = "http://localhost:5000/predict";

    public String getPrediction(MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Save MultipartFile to a temporary file
            File tempFile = convertMultipartFileToFile(file);

            // Prepare the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create the file body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(tempFile));

            // Prepare the HTTP entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send POST request to Flask API
            ResponseEntity<String> response = restTemplate.exchange(
                FLASK_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
            );

            // Delete the temporary file
            tempFile.delete();

            return response.getBody();
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage(), e);
        }
    }

    /**
     * Converts a MultipartFile to a File object.
     *
     * @param file the MultipartFile to convert
     * @return the converted File object
     * @throws IOException if an error occurs during file creation
     */
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }
}
