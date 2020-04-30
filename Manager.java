package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * this class is designed for file io
 * @author jacky
 *
 */
public class Manager {
  private  Factory factory;
  private int counter;
  private int monthcounter ;
  private int yearcounter;
  private boolean revise;
  /**
   * Constructor to create a factory object. Takes in a String that can be used to 
   * identify farm.
   * @param farmID
   */
  public Manager(){
    Factory fac  = new Factory(); 
    monthcounter=0;
    yearcounter=0;
      factory =fac;
      counter =0;
      revise = false;
  }
   /**
   * Constructor to create a factory object. Takes in a String that can be used to 
   * identify farm.
   * @param farmID
   */
  public Manager(Factory fac){
    monthcounter=0;
    yearcounter=0;
      factory =fac;
      counter =0;
      revise = false;
  }

   /**
   *Java code to illustrate reading a 
   * CSV file line by line 
   * @string filepath filepath that get to the file
   */
public void readDataLineByLine(String input) throws Exception   { 
    
   
     try { 
         
         // Create an object of filereader 
         // class with CSV file as a parameter. 
       File file = new File(input);
       Scanner inputStream = new Scanner(file);
       while(inputStream.hasNext()) {
         //  when there are more line
         String data = inputStream.nextLine();
         // get information
         String[] values = data.split(",");
         // split to a string arrry
      if( counter >0) {
        String[] date = values[0].split("-");     
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        if( year>2021|| year <1900|| month>12||month<1||day>31||day<1) {
          // if the formait is invalid break
          System.out.print("error input!"); 
          break;
        }
       if(factory.get(values[1])==null) {
         // if farm hasn't been add to the data add to data
         factory.add(values[1]);
         factory.get(values[1]).add(year,month,day,Double.parseDouble(values[2]));
         
      
       }
       else {
         // if farm id exist add more information
         factory.get(values[1]).add(year,month,day,Double.parseDouble(values[2]));

       }

     }
      counter = counter +1;
      }
    
       inputStream.close();
       counter = 0;
   } 
   catch (NumberFormatException e) { 
     
     System.out.print("error input!");
   }
     catch (Exception e) { 
       
       throw e;
   }
     

  } 
    /**
   *Java code to revise a
   * CSV file line by line 
   * @string year year that need to change
   * @string weight weight that need to changed
   * @string month month that nned to change
   * @string day day that need to change
   * @string filepath filepath that get to the file
   
   */
  public void writeRecord(String year, String month, String day, String farmID, String weight, String filepath) {
    String temp = " temp.csv";
    String thisdate = year + "-" + month + "-" + day;
    File Oldfile = new File(filepath);
    File Newfile = new File(temp);
    //create old file and new file to copy information
    try {
      revise =false;
      FileWriter fw = new FileWriter(temp,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      Scanner inputStream = new Scanner(Oldfile);
      
      while(inputStream.hasNext()) {
        String data = inputStream.nextLine();
        // get each line information with the import
        String[] values = data.split(",");
       {
       String[] date = values[0].split("-");
       if(date[0].equals(year)&&date[1].equals(month)&&date[2].equals(day)&&values[1].equals(farmID)) {
         // if information matched
         pw.println(thisdate + "," + farmID + "," + weight);
         // print revise information
         revise = true;
       }
       else {
         // print origin information
         pw.println(data);
       }
    }
    
      }
    
       inputStream.close();
       
      pw.flush();
      pw.close();

      Oldfile.delete();
      File Oldfile1 = new File(filepath);
      Oldfile1.delete();
      File dump = new File(filepath);
      Newfile.renameTo(dump);

      }
    catch (Exception e) { 
      e.printStackTrace(); 
  }


  }
    /**
   *Java code to revise a
   * CSV file line by line 
   * @string year year that need to change
   * @string weight weight that need to changed
   * @string month month that nned to change
   * @string day day that need to change
   * @string filepath filepath that get to the file
   
   */
  public void outputmonth(String FarmID, String year) {
    String temp = " temp.csv";
    File Newfile = new File(temp);
    try {
      FileWriter fw = new FileWriter(temp,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      // set print io
      double totalweight = 0.0;
      for ( int i =0; i<12; i++) {
        int inputyear  = Integer.parseInt(year);
       double monthweight=0.0;
        // get weight for each month 
       double[][] weight =  factory.get(FarmID).get(inputyear);
       for ( int j=0; j< weight[i].length;j++) {
         double k = weight[i][j];
         monthweight = monthweight +k;
       }
     pw.println(FarmID + "," + monthweight
         
         +","+ totalweight);
      }
      pw.flush();
      pw.close();
      
      
      
      
      }
    catch (Exception e) { 
      e.printStackTrace(); 
  }
  }
  

}
