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
