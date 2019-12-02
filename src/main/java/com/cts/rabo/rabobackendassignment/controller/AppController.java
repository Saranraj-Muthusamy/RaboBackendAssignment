package com.cts.rabo.rabobackendassignment.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.model.Records;
import com.cts.rabo.rabobackendassignment.utill.TransactionUtility;

@RestController
public class AppController {
	
	private Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@PostMapping(path= "/upload")
    public ResponseEntity<Object> uploadTransFile(@RequestParam("file") MultipartFile file) throws JAXBException, IOException, JSONException 
    {
		JSONObject response = new JSONObject();
		Records records = new Records();
		if(file!=null && TransactionUtility.validateFileFormat(StringUtils.cleanPath(file.getOriginalFilename()))) {
			if(TransactionUtility.parseAndMapping(file,response,records))
				TransactionUtility.validateTransaction(response,records);
		}else {
			response.put("status", false);
			response.put("status_code", HttpStatus.BAD_REQUEST);
	        response.put("data", "Unsupported file type");
	        logger.error("Unsupported file type");
		}
		return new ResponseEntity<>(response.toString(4), HttpStatus.OK);
    }
}
