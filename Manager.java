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
  /**
   * Constructor to create a factory object. Takes in a String that can be used to 
   * identify farm.
   * @param farmID
   */
  public Manager(){
    Factory fac  = new Factory(); 
      factory =fac;
      counter =0;
  }
  public Manager(Factory fac){

      factory =fac;
      counter =0;
  }
//Java code to illustrate reading a 
//CSV file line by line 

 public void writeRecord(String year, String month, String day, String farmID, String weight, String filepath) throws Exception {
    String temp = " temp.csv";
    String thisdate = year + "-" + month + "-" + day;
    File Oldfile = new File(filepath);
    File Newfile = new File(temp);
    
    
    try {
      FileWriter fw = new FileWriter(temp,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      Scanner inputStream = new Scanner(Oldfile);
      while(inputStream.hasNext()) {
        String data = inputStream.nextLine();
        String[] values = data.split(",");
     if( counter >0) {
       String[] date = values[0].split("-");
       if(date[0].equals(year)&&date[1].equals(month)&&date[2].equals(day)&&values[1].equals(farmID)) {
         pw.println(thisdate + "," + farmID + "," + weight); 
       }
       else {
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
    throw  e;
  }

    
  }
  public void writeRecord(String year, String month, String day, String farmID, String weight, String filepath) {
    String temp = " temp.csv";
    String thisdate = year + "-" + month + "-" + day;
    File Oldfile = new File(filepath);
    File Newfile = new File(temp);
    
    
    try {
      FileWriter fw = new FileWriter(temp,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      Scanner inputStream = new Scanner(Oldfile);
      while(inputStream.hasNext()) {
        String data = inputStream.nextLine();
        String[] values = data.split(",");
     if( counter >0) {
       String[] date = values[0].split("-");
       if(date[0].equals(year)&&date[1].equals(month)&&date[2].equals(day)&&values[1].equals(farmID)) {
         pw.println(thisdate + "," + farmID + "," + weight); 
       }
       else {
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

  public void outputmonth(String FarmID, String year) {
    String temp = " temp.csv";
    File Newfile = new File(temp);
    try {
      FileWriter fw = new FileWriter(temp,true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      double totalweight = 0.0;
      for ( int i =0; i<12; i++) {
        int inputyear  = Integer.parseInt(year);
       double monthweight=0.0;
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
