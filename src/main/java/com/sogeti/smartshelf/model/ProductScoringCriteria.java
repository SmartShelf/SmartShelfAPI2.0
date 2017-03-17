package com.sogeti.smartshelf.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductScoringCriteria {

	private Integer finalPercentage;
	private Integer finalWeight;
	private Integer startingWeight;
	private Integer useDays;
	private String productId;
	private Double avgAge;
	private Integer householdSize;
	private Integer zipCode;
	
	private String tablename;
	private List<String> header;
	private List<List<Object>> data;
	
	public ProductScoringCriteria() {
		tablename = "input.csv";
		header = new ArrayList<String>();
		header.add("finalPercentage");
		header.add("finalWeight");
		header.add("startingWeight");
		header.add("useDays");
		header.add("productId");
		header.add("avgAge");
		header.add("householdSize");
		header.add("zipCode");
		data = new ArrayList<>();
	}

//	": [[20.0,7.0,35,4,600,"40.5",2,43235]]
	
//	"tablename" : "input.csv",
//	"header": ["finalPercentage","finalWeight","startingWeight","useDays","productId","avgAge","householdSize","zipCode"]
	
	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

//	public Integer getFinalPercentage() {
//		return finalPercentage;
//	}
//	
//	public void setFinalPercentage(Integer finalPercentage) {
//		this.finalPercentage = finalPercentage;
//	}
//	
//	public Integer getFinalWeight() {
//		return finalWeight;
//	}
//	
//	public void setFinalWeight(Integer finalWeight) {
//		this.finalWeight = finalWeight;
//	}
//	
//	public Integer getStartingWeight() {
//		return startingWeight;
//	}
//	
//	public void setStartingWeight(Integer startingWeight) {
//		this.startingWeight = startingWeight;
//	}
//	
//	public Integer getUseDays() {
//		return useDays;
//	}
//	
//	public void setUseDays(Integer useDays) {
//		this.useDays = useDays;
//	}
//	
//	public String getProductId() {
//		return productId;
//	}
//	
//	public void setProductId(String productId) {
//		this.productId = productId;
//	}
//	
//	public Double getAvgAge() {
//		return avgAge;
//	}
//	
//	public void setAvgAge(Double avgAge) {
//		this.avgAge = avgAge;
//	}
//	
//	public Integer getHouseholdSize() {
//		return householdSize;
//	}
//	
//	public void setHouseholdSize(Integer householdSize) {
//		this.householdSize = householdSize;
//	}
//	
//	public int getZipCode() {
//		return zipCode;
//	}
//	
//	public void setZipCode(Integer zipCode) {
//		this.zipCode = zipCode;
//	}
//	
//	@Override
//	public String toString() {
//		return "ProductScoringCriteria [finalPercentage=" + finalPercentage
//				+ ", finalWeight=" + finalWeight + ", startingWeight="
//				+ startingWeight + ", useDays=" + useDays + ", productId="
//				+ productId + ", avgAge=" + avgAge + ", householdSize="
//				+ householdSize + ", zipCode=" + zipCode + "]";
//	}
}
