package com.cts.rabo.rabobackendassignment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.model.Records;
import com.cts.rabo.rabobackendassignment.model.ResponseModel;
import com.cts.rabo.rabobackendassignment.utill.TransactionUtility;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Override
	public List<ResponseModel> processXMLSource(MultipartFile file) {
		List<ResponseModel> responseModel= new ArrayList<ResponseModel>();
		Records records =TransactionUtility.parseXMLSource(file);
		responseModel.addAll(TransactionUtility.collectDuplicateTransaction(records));
		responseModel.addAll(TransactionUtility.collectMisledBalTransaction(records));
		return responseModel;
	}

	@Override
	public List<ResponseModel> processCSVSource(MultipartFile file) {
		List<ResponseModel> responseModel= new ArrayList<ResponseModel>();
		Records records =TransactionUtility.parseCSVSource(file);
		responseModel.addAll(TransactionUtility.collectDuplicateTransaction(records));
		responseModel.addAll(TransactionUtility.collectMisledBalTransaction(records));
		return responseModel;
	}

}
