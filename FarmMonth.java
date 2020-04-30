package application;

public class FarmMonth {
	public String totalWeight;
	public String farmID;
	public String percentage;
	
	public FarmMonth(double weight, String farmID, double percentage) {
		totalWeight = Double.toString(weight);
		this.farmID = farmID;
		this.percentage = Double.toString(percentage);
		
	}

	public FarmMonth(Double weight, String farmID2, Double percentage2) {
		totalWeight = Double.toString(weight);
		this.farmID = farmID2;
		this.percentage = Double.toString(percentage2);
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
