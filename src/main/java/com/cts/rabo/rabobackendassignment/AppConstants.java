package com.cts.rabo.rabobackendassignment;

public class AppConstants {

	/* HTTP Response Constants */
	public static String ResponseStatus = "status";
	public static String ResponseCode = "status_code";
	public static String ResponseData = "data";
	public static String ResponseMessage = "Message";

	/* CSV Constants */
	public static String CsvColReference = "Reference";
	public static String CsvColAccountNumber = "AccountNumber";
	public static String CsvColDescription = "Description";
	public static String CsvColStatusBalance = "Start Balance";
	public static String CsvColMutation = "Mutation";
	public static String CsvColEndBalance = "End Balance";

	/* Response Message Constants */
	public static String ResSuccesMsg = "All the transactions are valid & processed";
	public static String ResFailMsg = "Invalid Transactions";
	
	/* Allowed File formats */
	public static enum AllowedFileFormat {
		XML("xml"), CSV("csv");

		private String value;

		AllowedFileFormat(String value) {
			this.value = value;
		}

		public boolean equals(String value) {
			return this.value.equals(value);
		}
	}

	/* Error Messages */
	public static String ErrorInvFileFrmt = "Invalid file format";
	public static String ErrorCorruptedFile = "Error in file parsing corrupted file";

}
