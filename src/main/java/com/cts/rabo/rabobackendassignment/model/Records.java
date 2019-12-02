package com.cts.rabo.rabobackendassignment.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "records"
})
@XmlRootElement(name = "records")
public class Records implements Serializable {
	
    private final static long serialVersionUID = -1L;
    
    @XmlElement(name = "record")
    private List<Record> records;

	/**
	 * @return the records
	 */
	public List<Record> getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(List<Record> records) {
		this.records = records;
	}
    
    

}
