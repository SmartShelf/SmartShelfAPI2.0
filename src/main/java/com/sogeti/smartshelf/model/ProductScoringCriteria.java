package com.sogeti.smartshelf.model;

public class ProductScoringCriteria {

	private Integer finalPercentage;
	private Integer finalWeight;
	private Integer startingWeight;
	private Integer useDays;
	private String productId;
	private Double avgAge;
	private Integer householdSize;
	private Integer zipCode;
	
	public Integer getFinalPercentage() {
		return finalPercentage;
	}
	
	public void setFinalPercentage(Integer finalPercentage) {
		this.finalPercentage = finalPercentage;
	}
	
	public Integer getFinalWeight() {
		return finalWeight;
	}
	
	public void setFinalWeight(Integer finalWeight) {
		this.finalWeight = finalWeight;
	}
	
	public Integer getStartingWeight() {
		return startingWeight;
	}
	
	public void setStartingWeight(Integer startingWeight) {
		this.startingWeight = startingWeight;
	}
	
	public Integer getUseDays() {
		return useDays;
	}
	
	public void setUseDays(Integer useDays) {
		this.useDays = useDays;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public Double getAvgAge() {
		return avgAge;
	}
	
	public void setAvgAge(Double avgAge) {
		this.avgAge = avgAge;
	}
	
	public Integer getHouseholdSize() {
		return householdSize;
	}
	
	public void setHouseholdSize(Integer householdSize) {
		this.householdSize = householdSize;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return "ProductScoringCriteria [finalPercentage=" + finalPercentage
				+ ", finalWeight=" + finalWeight + ", startingWeight="
				+ startingWeight + ", useDays=" + useDays + ", productId="
				+ productId + ", avgAge=" + avgAge + ", householdSize="
				+ householdSize + ", zipCode=" + zipCode + "]";
	}
}
