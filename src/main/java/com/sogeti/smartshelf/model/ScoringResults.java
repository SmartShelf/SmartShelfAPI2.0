package com.sogeti.smartshelf.model;

public class ScoringResults {
	Boolean isOutOfStock;
	Double outOfStockProbability;
	
	public ScoringResults() {
		isOutOfStock = false;
		outOfStockProbability = 100.0;
	}
	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}
	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}
	public Double getOutOfStockProbability() {
		return outOfStockProbability;
	}
	public void setOutOfStockProbability(Double outOfStockProbability) {
		this.outOfStockProbability = outOfStockProbability;
	}
	
}
