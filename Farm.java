
package application;

import java.util.NoSuchElementException;


/**
 * Farm class that is used as an object. Stores weight data based on a date given. 
 * Uses an array indexed by year. Average time complexity for inserting is constant, 
 * and time complexity for retrieving is always constant.
 * 
 * @author williammulvaney
 *
 */
public class Farm {
	
	
	private String farmID; // Stores identification for a farm
	private double hashTable[][][]; // Stores all of the weights by year, month, date
	private double totalWeight; // Combined weight of all inserts
	private int size; // Number of dates that have had weights inserted
	private int earliestYear; // Beginning of the array, the earliest year inserted
	
	/**
	 * Constructor to create a farm object. Takes in a String that can be used to 
	 * identify farm.
	 * @param farmID
	 */
	public Farm(String farmID){
		hashTable = new double[10][12][31];
		size = 0;
		earliestYear = 0;
		
	}
	
	/**
	 * Returns true if weight added without removing another. False
	 * if another weight is replaced (Weight can be effectively removed
	 * by inserting 0.0 at location, although size is not changed)
	 * 
	 * Changing a weight removes the old weight from total weight
	 * 
	 * Months and dates should be added by convention (1-12 for Jan - Dec)
	 * Add accounts for 0 indexing
	 *
	 */
	public boolean add(int year, int month, int date, double weight) {
		
		boolean empty = true; // Boolean used to determine return
		if(earliestYear == 0) earliestYear = year; // If earliest year hasn't been initialized
		
		if(year < earliestYear) shift(year); // Shift down if table doesn't fit to new year
		
		// Resize table if year is too high
		if(year - earliestYear > hashTable.length) {
			double newHash[][][] = new double[hashTable.length + year - earliestYear][12][31];
			for(int i = 0; i < hashTable.length; i++)
				for(int j = 0; j < hashTable[i].length; j++)
					for(int k = 0; k < hashTable[i][j].length; k++)
						newHash[i][j][k] = hashTable[i][j][k];
			hashTable = newHash;
		}
		
		
		// Check if current location already had a weight
		if(hashTable[year - earliestYear][month - 1][date - 1] != 0.0) {
			empty = false;
			totalWeight -= hashTable[year - earliestYear][month - 1][date - 1];
			size--;
		}
		
		// Add weight to the table
		hashTable[year - earliestYear][month - 1][date - 1] = weight;
		// Add weight to total, increment size
		totalWeight += weight;
		size++;
		
		return empty;
	}
	
	/**
	 * Returns a full year worth of weight data. If the data hasn't been inserted,
	 * still returns correctly with all 0.0's.
	 * @param year
	 * @return
	 */
	public double[][] get(int year) {
		if(year < earliestYear || year - earliestYear > hashTable.length)
			return new double[12][31];
		return hashTable[year - earliestYear];
	}
	
	/**
	 * Returns a months worth of weight data. If data hasn't been inserted for the given
	 * month, returns a table full of zeros
	 * @param year
	 * @param month
	 * @return
	 */
	public double[] getMonth(int year, int month) {
		if(year < earliestYear || year - earliestYear > hashTable.length)
			return new double[31];
		return hashTable[year - earliestYear][month - 1];
	}
	
	/**
	 * Returns weight data for a given day. If the data has not been inserted, returns
	 * 0.0
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public double getDate(int year, int month, int date) {
		if(year < earliestYear || year - earliestYear > hashTable.length)
			return 0.0;
		return hashTable[year - earliestYear][month - 1][date - 1];
	}
	
	/**
	 * Shift all elements in the array to suit the new starting year.
	 * Effectively changes the earliest year by altering array
	 * @param newEarliestYear
	 */
	private void shift(int newEarliestYear) {
		double newHash[][][] = new double[hashTable.length + earliestYear - newEarliestYear][12][31];
		
		for(int i = 0; i < hashTable.length; i++) 
			for(int j = 0; j < hashTable[i].length; j++) 
				for(int k = 0; k < hashTable[i][j].length; k++) 
					newHash[i + earliestYear - newEarliestYear][j][k] = hashTable[i][j][k];
				
			
		earliestYear = newEarliestYear;
		hashTable = newHash;
		
	}
	
	/**
	 * Returns total number of dates with weights entered
	 * @return
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns this farms ID
	 * @return
	 */
	public String getFarmID() {
		return farmID;
	}
	
	/**
	 * Returns the total weight
	 * @return
	 */
	public double getTotalWeight() {
		return totalWeight;
	}
}
