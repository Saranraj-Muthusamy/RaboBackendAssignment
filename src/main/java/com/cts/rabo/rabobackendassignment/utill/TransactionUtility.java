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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.cts.rabo.rabobackendassignment.AppConstants;
import com.cts.rabo.rabobackendassignment.model.Record;
import com.cts.rabo.rabobackendassignment.model.Records;
import com.cts.rabo.rabobackendassignment.model.ResponseModel;

public class TransactionUtility {

	private static Logger logger = LoggerFactory.getLogger(TransactionUtility.class);

	public static Pattern pattern;

	static {
		pattern = Pattern.compile("([^\\s]+(\\.(?i)(xml|csv))$)");
	}

	public static Boolean isAllowedFileType(String fileName) {
		return pattern.matcher(fileName).matches();
	}

	public static Records parseXMLSource(MultipartFile file) {
		Records records = new Records();
		JAXBContext jaxbContext;
		try {
			InputStream inStream = file.getInputStream();
			jaxbContext = JAXBContext.newInstance(Records.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Records parsedRecords = (Records) unmarshaller.unmarshal(inStream);
			records.setRecords(parsedRecords.getRecords());
		} catch (JAXBException | IOException e) {
			logger.error(AppConstants.ErrorCorruptedFile, e.getMessage());
		}
		return records;
	}

	public static Records parseCSVSource(MultipartFile file) {
		Records records = new Records();
		try {
			CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
			List<Record> parsedRecord = new ArrayList<Record>();
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVParser parser = new CSVParser(reader, format);
			for (CSVRecord csvRecord : parser) {
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
		} catch (IOException e) {
			logger.error(AppConstants.ErrorCorruptedFile, e.getMessage());
		}
		return records;
	}

	public static List<ResponseModel> collectDuplicateTransaction(Records records) {
		return records.getRecords().stream().collect(Collectors.groupingBy(Record::getReference)).values().stream()
				.filter(duplicates -> duplicates.size() > 1).flatMap(Collection::stream).collect(Collectors.toList())
				.stream().map(r -> new ResponseModel(r.getReference(), r.getDescription()))
				.collect(Collectors.toList());
	}

	public static List<ResponseModel> collectMisledBalTransaction(Records records) {
		return records.getRecords().stream()
				.filter(r -> r.getEndBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN)
						.compareTo(r.getStartBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN)
								.add(r.getMutation().setScale(2, BigDecimal.ROUND_HALF_EVEN))) != 0)
				.map(r -> new ResponseModel(r.getReference(), r.getDescription())).collect(Collectors.toList());
	}
}
