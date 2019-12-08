package com.cts.rabo.rabobackendassignment.model;

public class ResponseModel {
	
	private Integer reference;
	private String description;
	
	/**
	 * @param reference
	 * @param description
	 */
	public ResponseModel(Integer reference, String description) {
		super();
		this.reference = reference;
		this.description = description;
	}
	/**
	 * @return the reference
	 */
	public Integer getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(Integer reference) {
		this.reference = reference;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
