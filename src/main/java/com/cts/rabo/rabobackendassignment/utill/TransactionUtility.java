package com.cts.rabo.rabobackendassignment.utill;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.model.Record;
import com.cts.rabo.rabobackendassignment.model.Records;

public class TransactionUtility {

	private static Logger logger = LoggerFactory.getLogger(TransactionUtility.class);
	
	
	public static Boolean validateFileFormat(String fileName) {
		return Pattern.compile("([^\\s]+(\\.(?i)(xml|csv))$)").matcher(fileName).matches();
	}
 
	public static Boolean parseAndMapping(MultipartFile file,JSONObject response,Records records) throws JSONException {
		try {
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			if("xml".equalsIgnoreCase(ext)) {
				InputStream inStream = file.getInputStream();
				JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				Records parsedRecords = (Records) unmarshaller.unmarshal(inStream);
				records.setRecords(parsedRecords.getRecords());
				return true;
			}else if("csv".equalsIgnoreCase(ext)) {
				CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
				List<Record> parsedRecord = new ArrayList<Record>();
				Reader reader = new InputStreamReader(file.getInputStream());
				CSVParser parser = new CSVParser(reader, format);
				for(CSVRecord csvRecord : parser){
					Record record = new Record();
					record.setReference(Integer.parseInt(csvRecord.get("Reference")));
					record.setAccountNumber(csvRecord.get("AccountNumber"));
					record.setDescription(csvRecord.get("Description"));
					record.setStartBalance(new BigDecimal(csvRecord.get("Start Balance")));
					record.setMutation(new BigDecimal(csvRecord.get("Mutation")));
					record.setEndBalance(new BigDecimal(csvRecord.get("End Balance")));
					parsedRecord.add(record);
				}
				parser.close();
				records.setRecords(parsedRecord);
			    return true;
			}
			} catch (UnknownError | IOException | JAXBException e) {
				response.put("status", false);
				response.put("status_code", HttpStatus.BAD_REQUEST);
		        response.put("data", "corrupted file");
		        logger.error("Error in input parsing"+ e.getLocalizedMessage());
		}
		return false;

	}
	
	public static void validateTransaction(JSONObject response, Records records) throws JSONException {
		List<Record> duplicateRecordList = records.getRecords().stream()
				.collect(Collectors.groupingBy(Record::getReference)).values().stream()
				.filter(duplicates -> duplicates.size() > 1).flatMap(Collection::stream).collect(Collectors.toList());
		if(null!=duplicateRecordList && !duplicateRecordList.isEmpty() ) {
			response.put("status", false);
			response.put("status_code", HttpStatus.EXPECTATION_FAILED);
			response.put("status_message", "There was some misleading transactions");
			response.put("duplicate_transactions", duplicateRecordList);
		}
		List<Record> misleadBalList= new ArrayList<Record>();
		for(Record record : records.getRecords()) {
			BigDecimal startBal = record.getStartBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN);
	        BigDecimal mutation = record.getMutation().setScale(2, BigDecimal.ROUND_HALF_EVEN);
	        BigDecimal calculatedEndBal= startBal.add(mutation).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	        BigDecimal endBal = record.getEndBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN);
	        if(endBal.compareTo(calculatedEndBal)!=0) {
	        	misleadBalList.add(record);
	        }
		}
		if(null!=misleadBalList	&& !misleadBalList.isEmpty()) {
			response.put("status", false);
			response.put("status_code", HttpStatus.EXPECTATION_FAILED);
			response.put("status_message", "There was some misleading transactions");
			response.put("Misleading_Balance_transactions", misleadBalList);
		}
		
	}
}
