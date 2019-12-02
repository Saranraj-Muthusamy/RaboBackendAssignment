package com.cts.rabo.rabobackendassignment.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cts.rabo.rabobackendassignment.utill.BigDecimalAdapter;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accountNumber",
    "description",
    "startBalance",
    "mutation",
    "endBalance"
})
public class Record implements Serializable{
	private final static long serialVersionUID = -1L;

	@XmlElement(required = true)
    private String accountNumber;
    @XmlElement(required = true)
    private String description;
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal startBalance;
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal mutation;
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal endBalance;
    @XmlAttribute(name = "reference")
    private Integer reference;
    
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	/**
	 * @return the startBalance
	 */
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	/**
	 * @param startBalance the startBalance to set
	 */
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	/**
	 * @return the mutation
	 */
	public BigDecimal getMutation() {
		return mutation;
	}
	/**
	 * @param mutation the mutation to set
	 */
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	/**
	 * @return the endBalance
	 */
	public BigDecimal getEndBalance() {
		return endBalance;
	}
	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
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
    
    
}
