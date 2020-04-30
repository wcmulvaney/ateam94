package application;

import java.util.ArrayList;

/**
 * This class enables us to add Farm to the Factory using a hashtable and also
 * retrieve data from the factory.
 * 
 * @author Xiaoyang Song
 *
 */
public class Factory {

	private double totalWeight;
	private ArrayList<Farm>[] farmTable;
	private int size;
	private int capacity;
	private double lft;// load factor threshold
	
	
	public String annualReport;
	public ArrayList<String> farmIDListAnnual;
	public ArrayList<Double> totalWeightAnnual;
	public ArrayList<Double> PercentageAnnual;

	public String monthlyReport;
	public ArrayList<String> farmIDListMonthly;
	public ArrayList<Double> totalWeightMonthly;
	public ArrayList<Double> PercentageMonthly;

	/**
	 * Default Constructor
	 */
	public Factory() {
		totalWeight = 0;
		capacity = 10;
		farmTable = new ArrayList[capacity];
		size = 0;
		lft = 0.8;
	}

	/**
	 * return the hashing index
	 * 
	 * @param farmID - key need to be hashed
	 * @return - index
	 */
	private int hashing(String farmID) {
		int hashcode = Math.abs(farmID.hashCode());// call the hashCode() function
																								// to
		// generate hashCode.
		int index = hashcode % this.capacity;// use modulo operation to get the
																					// hash index.
		return index;// return the index.
	}

	/**
	 * Add a new farm to the factory
	 * 
	 * @param farmID - key of the farm that needs to be inserted.
	 */
	public void add(String farmID) {

		Farm farm = new Farm(farmID);

		// check whether the hash table needs to be resize and rehash by checking
		// the value of Load factor.
		if ((this.size / this.capacity) > this.lft) {
			rehashing();
		}

		if (farmTable[hashing(farmID)] != null) {

			farmTable[hashing(farmID)].add(farm);
			size++;// increase the number of elements

		} else {
			farmTable[hashing(farmID)] = new ArrayList<Farm>();
			farmTable[hashing(farmID)].add(farm);
			size++;
		}

	}

	/**
	 * This is a private helper method to resize and rehash the array (hash table)
	 */
	private void rehashing() {

		this.capacity = this.capacity * 2 + 1;// update the capacity

		// create a new array of ArrayList with new capacity
		ArrayList<Farm>[] newht = new ArrayList[capacity];

		for (int i = 0; i < farmTable.length; i++) {

			// if the position i of original array is not null, then we need another
			// for loop to iterate through the ArrayList at that position.
			if (farmTable[i] != null) {
				for (int j = 0; j < farmTable[i].size(); j++) {

					// check whether there is element at the position hashing(key) in the
					// new array, where key is the key of element in original array, which
					// we aim to rehash to new array.
					if (newht[hashing(farmTable[i].get(j).getFarmID())] != null) {
						newht[hashing(farmTable[i].get(j).getFarmID())]
								.add(farmTable[i].get(j));

						// if there is no element at that position, we also need to create a
						// new arrayList.
					} else {
						newht[hashing(
								farmTable[i].get(j).getFarmID())] = new ArrayList<Farm>();
						newht[hashing(farmTable[i].get(j).getFarmID())]
								.add(farmTable[i].get(j));
					}
				}
			}
		}

		farmTable = newht;// after resizing and rehashing, we simply assign this new
											// array
		// to our original one to update the hash table.

	}

	/**
	 * Check whether the farmTable (factory) contains the farm with certain ID.
	 * 
	 * @param ft     - Factory
	 * @param farmID - ID of the farm
	 * @return true if the factory has this farm, false otherwise
	 */
	private boolean contains(ArrayList<Farm>[] ft, String farmID) {

		int startingIndex = hashing(farmID);// create a variable to store the index
		// associated with the key.

		// if there is no element at that position, we are done and we can return
		// false directly. Otherwise, we need to iterate through all the elements in
		// its associated ArrayList.
		if (ft[startingIndex] != null) {
			for (int i = 0; i < ft[startingIndex].size(); i++) {
				if (ft[startingIndex].get(i).getFarmID().equals(farmID)) {
					return true;
				}
			}
			return false;

		} else {
			return false;
		}
	}

	/**
	 * This method gets the reference of certain farm in the factory. Return null
	 * if the farm does not exist.
	 * 
	 * @param farmID - name of the farm
	 * @return - the associated reference of the farm
	 */
	public Farm get(String farmID) {

		if (farmTable[hashing(farmID)] != null
				&& farmTable[hashing(farmID)].size() != 0) {
			for (int j = 0; j < farmTable[hashing(farmID)].size(); j++) {

				if (farmTable[hashing(farmID)].get(j).getFarmID().equals(farmID)) {
					return farmTable[hashing(farmID)].get(j);

				}
			}
		}
		return null;
	}

	/**
	 * Remove a farm from the factory
	 * 
	 * @param farmID - name of the farm that needs to be removed
	 * @return - true if we are able to do this, false otherwise.
	 */
	public boolean delete(String farmID) {
		if (farmTable[hashing(farmID)] != null
				&& farmTable[hashing(farmID)].size() != 0) {

			// use for loop to iterate through the arrayList.
			for (int j = 0; j < farmTable[hashing(farmID)].size(); j++) {

				// if key is found, remove it and decrement the numKeys of hash table.
				if (farmTable[hashing(farmID)].get(j).getFarmID().equals(farmID)) {
					farmTable[hashing(farmID)].remove(j);
					size--;
					return true;
				}
			}
		}
		return false;
	}

	public double yrWeight(double[][] list) {

		double sum = 0.0;
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[i].length; j++) {
				sum = sum + list[i][j];
			}
		}
		return sum;
	}

	public double totalWeightYr(int yr) {

		double sum = 0.0;
		for (int i = 0; i < farmTable.length; i++) {
			if (farmTable[i] != null) {
				for (int j = 0; j < farmTable[i].size(); j++) {
					double[][] list = farmTable[i].get(j).get(yr);
					sum = sum + yrWeight(list);

				}
			}
		}
		return sum;
	}

	public void getAnnualReport(int yr) {

		String annualReport = "";
		ArrayList<String> farmIDList = new ArrayList<String>();
		ArrayList<Double> totalWeightList = new ArrayList<Double>();
		ArrayList<Double> percentageList = new ArrayList<Double>();
		
		for (int i = 0; i < farmTable.length; i++) {
			if (farmTable[i] != null) {
				for (int j = 0; j < farmTable[i].size(); j++) {
					double[][] list = farmTable[i].get(j).get(yr);
					farmIDList.add(farmTable[i].get(j).getFarmID());
					totalWeightList.add(yrWeight(list));
					percentageList.add(yrWeight(list) / totalWeightYr(yr));
					
					annualReport = annualReport + farmTable[i].get(j).getFarmID() + " "
							+ "TotalWeight: " + yrWeight(list) + "; Percent of Total Weight: "
							+ yrWeight(list) / totalWeightYr(yr) + "\n";

				}
			}
		}

		this.annualReport = annualReport;
		farmIDListAnnual = farmIDList;
		totalWeightAnnual = totalWeightList;
		PercentageAnnual = percentageList;
		

		System.out.println(this.annualReport);

	}

	private double monthWeight(double[] list) {

		double sum = 0.0;
		for (int i = 0; i < list.length; i++) {
			sum = sum + list[i];
		}
		return sum;
	}

	private double totalWeightMonth(int yr, int month) {

		double sum = 0.0;
		for (int i = 0; i < farmTable.length; i++) {
			if (farmTable[i] != null) {
				for (int j = 0; j < farmTable[i].size(); j++) {
					double[] list = farmTable[i].get(j).getMonth(yr, month);
					sum = sum + monthWeight(list);

				}
			}
		}
		return sum;
	}

	public void getMonthlyReport(int yr, int month) {

		ArrayList<String> farmIDList = new ArrayList<String>();
		ArrayList<Double> totalWeightList = new ArrayList<Double>();
		ArrayList<Double> percentageList = new ArrayList<Double>();
		String monthlyReport = "";
		double total = totalWeightMonth(yr, month);

		for (int i = 0; i < farmTable.length; i++) {
			if (farmTable[i] != null) {
				for (int j = 0; j < farmTable[i].size(); j++) {
					double[] list = farmTable[i].get(j).getMonth(yr, month);
					farmIDList.add(farmTable[i].get(j).getFarmID());
					totalWeightList.add((Double) monthWeight(list));
					percentageList.add((Double) monthWeight(list) / total);
					monthlyReport = monthlyReport + farmTable[i].get(j).getFarmID()
							+ " TotalWeight: " + monthWeight(list) + " Percentage: "
							+ monthWeight(list) / total + "\n";

				}
			}
		}
		this.monthlyReport = monthlyReport;
		farmIDListMonthly = farmIDList;
		totalWeightMonthly = totalWeightList;
		PercentageMonthly = percentageList;

	}
	
	
	
	
	
	
	
	
    public void getdaterange(int yr,int month, int date, int month1,int day1) {

      String annualReport = "";
      ArrayList<String> farmIDList = new ArrayList<String>();
      ArrayList<Double> totalWeightList = new ArrayList<Double>();
      ArrayList<Double> percentageList = new ArrayList<Double>();
      for (int i = 0; i < farmTable.length; i++) {
        if (farmTable[i] != null) {
            for (int j = 0; j < farmTable[i].size(); j++) {
              
                double[][] list = farmTable[i].get(j).get(yr);
                System.out.println(list[10][11]);
                System.out.println(list[10][3]);
                for(int k=0;k<month-2;k++) {
                  for( int h =0;h< list[k].length; h++) {
                  
                    list[k][h]=0.0;
                  }
                }
               for(int k=0;k<month-1;k++) {
                 for( int h =0;h< date-1; h++) {
                   System.out.print(h);
                   System.out.print(k);
                   list[k][h]=0.0;
                 }
               }
               for(int k=11;k>month1-1;k--) {
                 for( int h =0;h< list[k].length; h++) {
           
                
                   
                   list[k][h]=0.0;
                 }
               }
               for(int k=11;k>month1-2;k--) {
                 for( int h =list[k].length-1;h> day1-1; h--) {
           
                   
                   
                   list[k][h]=0.0;
                 }
               }
               System.out.println(list[10][11]);
               System.out.println(list[10][3]);
                farmIDList.add(farmTable[i].get(j).getFarmID());
                totalWeightList.add(yrWeight(list));
                percentageList.add(yrWeight(list) / totalWeightYr(yr));
                
                annualReport = annualReport + farmTable[i].get(j).getFarmID() + " "
                        + "TotalWeight: " + yrWeight(list) + "; Percent of Total Weight: "
                        + yrWeight(list) / totalWeightYr(yr) + "\n";

            }
        }
    }


      this.annualReport = annualReport;
      farmIDListAnnual = farmIDList;
      totalWeightAnnual = totalWeightList;
      PercentageAnnual = percentageList;
      

      System.out.println(this.annualReport);

  }
}
