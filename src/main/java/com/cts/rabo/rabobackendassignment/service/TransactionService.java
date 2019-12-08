package com.cts.rabo.rabobackendassignment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.model.ResponseModel;

public interface TransactionService {

	public List<ResponseModel> processXMLSource(MultipartFile file);

	public List<ResponseModel> processCSVSource(MultipartFile file);

}
