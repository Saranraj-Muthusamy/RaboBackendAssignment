package com.cts.rabo.rabobackendassignment.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.AppConstants;
import com.cts.rabo.rabobackendassignment.AppConstants.AllowedFileFormat;
import com.cts.rabo.rabobackendassignment.model.ResponseModel;
import com.cts.rabo.rabobackendassignment.service.TransactionService;
import com.cts.rabo.rabobackendassignment.utill.TransactionUtility;

@RestController
public class AppController {
	
	private Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping(path= "/upload")
    public ResponseEntity<Object> uploadTransFile(@RequestParam("file") MultipartFile file)  
	{
		JSONObject response = new JSONObject();
		String fileName = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(fileName);
		Boolean isAllowed = TransactionUtility.isAllowedFileType(fileName);
		List<ResponseModel> reportTrans = (isAllowed && AllowedFileFormat.XML.equals(extension))
				? transactionService.processXMLSource(file)
				: (isAllowed && AllowedFileFormat.CSV.equals(extension)) ? transactionService.processCSVSource(file)
						: Collections.emptyList();
		if (!isAllowed) {
			response.put(AppConstants.ResponseStatus, false);
			response.put(AppConstants.ResponseCode, HttpStatus.BAD_REQUEST.value());
			response.put(AppConstants.ResponseMessage, AppConstants.ErrorInvFileFrmt);
			logger.error(AppConstants.ErrorInvFileFrmt);
		} else if (isAllowed && CollectionUtils.isEmpty(reportTrans)) {
			response.put(AppConstants.ResponseStatus, true);
			response.put(AppConstants.ResponseCode, HttpStatus.ACCEPTED.value());
			response.put(AppConstants.ResponseMessage, AppConstants.ResSuccesMsg);
		} else if (isAllowed && !CollectionUtils.isEmpty(reportTrans)) {
			response.put(AppConstants.ResponseStatus, false);
			response.put(AppConstants.ResponseCode, HttpStatus.BAD_REQUEST.value());
			response.put(AppConstants.ResponseMessage, AppConstants.ResFailMsg);
			response.put(AppConstants.ResponseData, reportTrans);
		}
		return new ResponseEntity<>(response.toString(4), HttpStatus.OK);
	}
}
