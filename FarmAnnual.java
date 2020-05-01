//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:Ateam project
// Course: CS400 001 2020 spring (course number, term, and year)
//
// Author:yinzhou zhang (your name)
// xiaoyang song
// William Mulvaney
// Email: yzhang2286@wisc.edu (your @wisc.edu email address)
// description :
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe their assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

public class FarmAnnual {
	String farmID;
	String totalWeight;
	String percentage;
	
	public FarmAnnual(String farmID, double totalWeight, double percentage) {
		this.farmID = farmID;
		this.totalWeight = Double.toString(totalWeight);
		this.percentage = Double.toString(percentage);
	}
	
	public FarmAnnual(String farmID, Double totalWeight, Double percentage) {
		this.farmID = farmID;
		this.totalWeight = Double.toString(totalWeight);
		this.percentage = Double.toString(percentage);
	}

	/**
	 * @return the farmID
	 */
	public String getFarmID() {
		return farmID;
	}

	/**
	 * @param farmID the farmID to set
	 */
	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	/**
	 * @return the totalWeight
	 */
	public String getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return the percentage
	 */
	public String getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}
