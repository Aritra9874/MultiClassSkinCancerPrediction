package com.enduser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enduser.service.PredictionService;

@RestController
@RequestMapping("/api")
public class LandingController {
	
	@GetMapping("/test")
	public String testOnly() {
		return "Hello world";
	}
	
//	@PostMapping("/api/predict")
//	public ResponseEntity<?> predict(@RequestParam("file") MultipartFile file) {
//	    try {
//	        //String prediction = predictionService.getPrediction(file);
//	    	System.out.println(file.getOriginalFilename());
//	        return ResponseEntity.ok("Image coming");
//	    } catch (Exception e) {
//	        return ResponseEntity.status(500).body("Error: " + e.getMessage());
//	    }
//	}

	    @Autowired
	    private PredictionService predictionService;

	    @PostMapping("/predict")
	    public ResponseEntity<?> predict(@RequestParam("file") MultipartFile file) {
	        try {
	            String prediction = predictionService.getPrediction(file);
	            return ResponseEntity.ok(prediction);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error: " + e.getMessage());
	        }
	    }

}
