package com.cts.rabo.rabobackendassignment.utill;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal>{

    @Override
    public String marshal(BigDecimal value) throws Exception 
    {
        if (value!= null)
        {
            return value.toString();
        }
        return null;
    }

    @Override
    public BigDecimal unmarshal(String s) throws Exception 
    {
       return new BigDecimal(s);
    }
}
