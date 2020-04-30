/*
 * Filename:   Main.java
 * Project:    a2
 * Authors:    William Mulvaney, Xiaoyang Song, Yinzhou Zhang
 * 			   ATeam 94
 * 
 * GUI template for storing and accessing Milk weights. Has multiple pages that are
 * accessible through button objects. 
 * 
 */

package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class that runs the GUI. Uses objects inheriting from JavaFX's node
 * class.
 * 
 * @author williammulvaney
 *
 */
public class Main extends Application {
	private List<String> args;

	private static final int WINDOW_WIDTH = 400;
	private static final int WINDOW_HEIGHT = 300;
	private static final String APP_TITLE = "Milk Weights";

	private static Scene HomeScene;
	private static Scene addFile;
	private static Scene editFile;
	private static Scene farmReport;
	private static Scene getReport;
	private static Scene getDateRange;
	private static Scene getMonthReport;
	private static Scene getAnnualReport;
	private static Scene designer;

	private static Manager manager;
	private static Factory factory;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		factory = new Factory();
		manager = new Manager(factory);

		// Create a vertical box with Hello labels for each args
		VBox vbox = new VBox();

		// Back Button that redirects to home page
		Button backButton1 = new Button("Home");
		backButton1.setOnAction(e -> primaryStage.setScene(HomeScene));

		Button backButton2 = new Button("Home");
		backButton2.setOnAction(e -> primaryStage.setScene(HomeScene));

		Button backButton3 = new Button("Home");
		backButton3.setOnAction(e -> primaryStage.setScene(HomeScene));

		Button backButton4 = new Button("Home");
		backButton4.setOnAction(e -> primaryStage.setScene(HomeScene));

		Button backButton5 = new Button("Get Other Report");
		backButton5.setOnAction(e -> primaryStage.setScene(getReport));

		Button backButton6 = new Button("Get Other Report");
		backButton6.setOnAction(e -> primaryStage.setScene(getReport));

		Button backButton7 = new Button("Get Other Report");
		backButton7.setOnAction(e -> primaryStage.setScene(getReport));

		// Home page buttons
		Button button1 = new Button("Add File");
		button1.setStyle("-fx-font-size: 2em; ");
		button1.setOnAction(e -> primaryStage.setScene(addFile));

		Button button2 = new Button("Get Report");
		button2.setStyle("-fx-font-size: 2em; ");
		button2.setOnAction(e -> primaryStage.setScene(getReport));

		Button button3 = new Button("Edit File");
		button3.setStyle("-fx-font-size: 2em; ");
		button3.setOnAction(e -> primaryStage.setScene(editFile));

		Button button4 = new Button("Designer");
		button4.setStyle("-fx-font-size: 2em; ");
		button4.setOnAction(e -> primaryStage.setScene(designer));

		Button quit = new Button("EXIT");
		quit.setOnAction(e -> Platform.exit());

		vbox.getChildren().addAll(button1, button2, button3, button4, quit);

		// File Path Text Box
		TextField insertFile = new TextField();
		Label filePrompt = new Label("File Path:");
		Button addFileButton = new Button("Add File");
		Label fileNotFound = new Label("File not found");
		VBox fileText = new VBox();
		fileText.getChildren().addAll(filePrompt, insertFile, addFileButton);

		addFileButton.setOnAction(e -> {
			try {
				manager.readDataLineByLine(insertFile.getText());
				if (fileText.getChildren().contains(fileNotFound))
					fileText.getChildren().remove(fileNotFound);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				fileText.getChildren().add(fileNotFound);
			}
		});

		Label fileHeader = new Label("Add File");

		BorderPane fileAdd = new BorderPane();
		fileAdd.setCenter(fileText);
		fileAdd.setTop(fileHeader);
		fileAdd.setBottom(backButton1);

		// Edit File Page
        Label fileEditHeader = new Label("Edit File");
        TextField farmid = new TextField();
        Label farmis = new Label("Farm ID");
        // Year field
   
        // Year field
        TextField year = new TextField();
        Label yearLabel = new Label("Year:");
        // Create a drop-down ComboBox
        TextField month = new TextField();;
        Label monthLabel = new Label("Month:");
        TextField date = new TextField();
        Label dateLabel = new Label("Date:");
        TextField weight = new TextField();
        Label weightLabel = new Label("Weight:");
        Button editFileButton = new Button("Edit");
        // Month and date drop down
        String[] monthArray = new String[12];
        for (int i = 0; i < 12; i++)
            monthArray[i] = "" + (i + 1);
        String[] dateArray = new String[31];
        for (int i = 0; i < 31; i++)
            dateArray[i] = "" + (i + 1);
        String filepath = year.getText()+"-"+  month.getText()+".csv";
        editFileButton.setOnAction( e->{
          try { manager.writeRecord(year.getText(),  month.getText(), date.getText(), farmid.getText(),weight.getText(), filepath);
        }
          catch (Exception e1) {
            // TODO Auto-generated catch block
            fileText.getChildren().add(fileNotFound);
        }
        });
        VBox dateInsert = new VBox();
        dateInsert.getChildren().addAll(farmis, farmid,yearLabel, year, monthLabel, month, dateLabel, date, weightLabel, weight,editFileButton);
        BorderPane fileEdit = new BorderPane();
        fileEdit.setTop(fileEditHeader);
        fileEdit.setCenter(dateInsert);
        fileEdit.setBottom(backButton2);

		// Farm Report
		Label title1 = new Label("Get Farm Report");
		TextField farmID = new TextField();
		TextField yr = new TextField();
		Label promptID = new Label("Enter Farm ID(String):");
		Label promptYr = new Label("Enter Years(int):");
		Button buttonFR = new Button("Click here to get the report");
		VBox vbox1 = new VBox();
		CheckBox checkBox1 = new CheckBox("Create File");
		vbox1.getChildren().addAll(title1, promptID, farmID, promptYr, yr, buttonFR,
				checkBox1, backButton3);
		Label invalid = new Label("Invalid farm ID");

		TableView table = new TableView();

		buttonFR.setOnAction(e -> {
			try {
				Farm currentFarm;
				if((currentFarm = factory.get(promptID.getText())) == null) {
					vbox1.getChildren().add(invalid);
				}
				// if they do nort check box
				else if (!checkBox1.isSelected()) {
					table.getItems().clear();
					TableColumn monthColumn = new TableColumn("Month");
					monthColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("month"));

					// Total weight column of table
					TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("totalWeight"));
					
					// Percentage column of table
					TableColumn percentageColumn = new TableColumn("Percentage");
					totalColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("percentage"));
					
					// Find the total of the year (for percentage purposes
					int total = 0;
					for(int i = 0; i < 12; i++) {
						for(int j = 0; j < 31; j++)
							total += currentFarm.get(Integer.parseInt(yr.getText()))[i][j];
					}
					// Add columns to table
						table.getColumns().addAll(monthColumn, totalColumn, percentageColumn);
						// Create new list to be added
						ObservableList<Month> list = FXCollections.observableArrayList();
					//Add all items to list
						for(int i = 0; i < 12; i++) {
							int monthlyTotal = 0;
							for(int j = 0; j < 31; j++) {
								monthlyTotal += currentFarm.get(Integer.parseInt(yr.getText()))[i][j];
							}
							list.add(new Month(i + 1, monthlyTotal,total));
						}
							
						table.setItems(list);
						
						vbox1.getChildren().add(table);
				} else {
						manager.outputmonth(farmID.getText(), yr.getText());
				}

			} catch (Exception f) {

			}
		});

		// Get report
		Label title = new Label("Get Report");

		Label prompt = new Label("Which report do you want?");
		Button annual = new Button("Annual Report");
		annual.setOnAction(e -> primaryStage.setScene(getAnnualReport));
		Button monthly = new Button("Monthly Report");
		monthly.setOnAction(e -> primaryStage.setScene(getMonthReport));

		Button dateRange = new Button("Date Range Report");
		dateRange.setOnAction(e -> primaryStage.setScene(getDateRange));

		Button farmReportButton = new Button("Farm Report");
		farmReportButton.setOnAction(e -> primaryStage.setScene(farmReport));

		VBox vbox2 = new VBox();

		vbox2.getChildren().addAll(farmReportButton, annual, monthly, dateRange,
				backButton4);

		// Get Date Range
		Label DateRange = new Label("Date Range");
		TextField insertdat = new TextField();
		Label labeldat = new Label("Enter Starting Date");
		TextField insertenddate = new TextField();
		Label labelenddate = new Label("Enter End Date");
		Button daterangebutton = new Button("Get report!");
		TableView<FarmAnnual> table3 = new TableView();
		VBox daterange = new VBox();
		daterange.getChildren().addAll(DateRange, labeldat, insertdat, labelenddate,
				insertenddate, daterangebutton, backButton5);
		Label invalid4 = new Label("Invalid farmID or date range");
		
		daterangebutton.setOnAction(e -> { try {

			int d1 = Integer.parseInt(insertdat.getText().substring(0, 2));
			int m1 = Integer.parseInt(insertdat.getText().substring(2, 4));
			int y1 = Integer.parseInt(insertdat.getText().substring(4, 8));
			int d2 = Integer.parseInt(insertenddate.getText().substring(0, 2));
			int m2 = Integer.parseInt(insertenddate.getText().substring(2, 4));
			int y2 = Integer.parseInt(insertenddate.getText().substring(4, 8));

			factory.getdaterange(y1, m1, d1, m2, d2);
			
			ArrayList<String> farmIDListDR = factory.farmIDListDR;
			ArrayList<Double> totalWeightDR = factory.totalWeightDR;
			ArrayList<Double> PercentageDR = factory.PercentageDR;
			
			TableColumn idColumn = new TableColumn("FarmID");
			idColumn.setCellValueFactory(
					new PropertyValueFactory<Month, String>("farmID"));
			// Second column
			TableColumn totalColumn = new TableColumn("Total Weight");
			totalColumn.setCellValueFactory(
					new PropertyValueFactory<Month, String>("totalWeight"));

			// Third column
			TableColumn percentageColumn = new TableColumn("Percentage");
			percentageColumn.setCellValueFactory(
					new PropertyValueFactory<FarmMonth, String>("percentage"));
			
			table3.getItems().clear();
			
			table3.getColumns().addAll(idColumn, totalColumn, percentageColumn);
			
			for(int i = 0; i < farmIDListDR.size(); i++) {
				table3.getItems().add(
						new FarmAnnual(farmIDListDR.get(i), totalWeightDR.get(i), PercentageDR.get(i)));
				
			}
			
			daterange.getChildren().add(table3);
			
		} catch(Exception f) {
			daterange.getChildren().add(invalid4);
		}

		});
		

		// Get Month Report
		Label Monthreport = new Label("Month report");
		TextField insertyearep = new TextField();
		Label labelyearep = new Label("Enter Year(int)");
		Label labelmonrep = new Label("Enter Month");
		Button monthReportButton = new Button("Get Report!");
		ComboBox<String> month1 = new ComboBox<String>();
		CheckBox checkBox2 = new CheckBox("Create File");
		month1.getItems().addAll(monthArray);
		Label invalid2 = new Label("Invalid farm");
		VBox monthDisplay = new VBox();
		monthDisplay.getChildren().addAll(Monthreport, labelyearep, insertyearep,
				labelmonrep, month1, monthReportButton, checkBox2, backButton6);
		TableView<FarmMonth> table1 = new TableView<FarmMonth>();
		monthReportButton.setOnAction(e -> {
			if(monthDisplay.getChildren().contains(invalid2))
				monthDisplay.getChildren().remove(invalid2);
			try {
				// Table that will hold month information
				factory.getMonthlyReport(Integer.parseInt(insertyearep.getText()),
						Integer.parseInt(month1.getValue()));
				
				
				// If user has not selected box print table
				if (!checkBox2.isSelected()) {
					// First column
					TableColumn idColumn = new TableColumn("FarmID");
					idColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("farmID"));
					// Second column
					TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("totalWeight"));

					// Third column
					TableColumn percentageColumn = new TableColumn("Percentage");
					percentageColumn.setCellValueFactory(
							new PropertyValueFactory<FarmMonth, String>("percentage"));
					// If it has not been added yet

					table1.getItems().clear();
					table1.getColumns().addAll(idColumn, totalColumn, percentageColumn);
					ArrayList<String> farmIDListMonth = factory.farmIDListMonthly;
					ArrayList<Double> totalWeightMonth = factory.totalWeightMonthly;
					ArrayList<Double> PercentageMonth = factory.PercentageMonthly;
					ObservableList<FarmMonth> list = FXCollections.observableArrayList();

					// Add all farms monthly reports
					for (int i = 0; i < farmIDListMonth.size(); i++) {
						list.add(new FarmMonth(totalWeightMonth.get(i),
								farmIDListMonth.get(i), PercentageMonth.get(i)));
					}
					table1.setItems(list);
					monthDisplay.getChildren().add(table1);
				} else {
					manager.outputmonth(farmID.getText(), yr.getText());
				}
			} catch (Exception f) {
				monthDisplay.getChildren().add(invalid2);
			}
		});

		// Get Annual report
		Label Annualreport = new Label("Annual report");
		TextField insertyearep1 = new TextField();
		Label labelyearep1 = new Label("Enter Years(int)");
		Button annualReportButton = new Button("Get Report!");
		CheckBox checkBox3 = new CheckBox("Create File");
		VBox yearDisplay = new VBox();
		yearDisplay.getChildren().addAll(Annualreport, labelyearep1, insertyearep1,
				annualReportButton, checkBox3, backButton7);
		
		annualReportButton.setOnAction(e -> {
			try {
				// Table that will hold month information
		//		get Year information
				//
				
				TableView<FarmAnnual> table2 = new TableView<FarmAnnual>();
				// If user has not selected box print table
				if (!checkBox3.isSelected()) {
					// First column
					TableColumn idColumn = new TableColumn("FarmID");
					idColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("farmID"));
					// Second column
					TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("totalWeight"));

					// Third column
					TableColumn percentageColumn = new TableColumn("Percentage");
					percentageColumn.setCellValueFactory(
							new PropertyValueFactory<FarmMonth, String>("percentage"));
					// If it has not been added yet

					table2.getItems().clear();
					table2.getColumns().addAll(idColumn, totalColumn, percentageColumn);
					
					
					//Get list for annual
					factory.getAnnualReport(Integer.parseInt(insertyearep1.getText()));
					ArrayList<String> farmIDListAnnual = factory.farmIDListAnnual;
					ArrayList<Double> totalWeightAnnual = factory.totalWeightAnnual;
					ArrayList<Double> PercentageAnnual = factory.PercentageAnnual;
					ObservableList<FarmAnnual> list = FXCollections.observableArrayList();

					// Add all farms monthly reports
					for (int i = 0; i < farmIDListAnnual.size(); i++) {
						list.add(new FarmAnnual(farmIDListAnnual.get(i),
								PercentageAnnual.get(i), totalWeightAnnual.get(i)));
					}
					table2.setItems(list);
					yearDisplay.getChildren().add(table2);
				} else {
		// 			
		//    Make file instead			
		//			
		//			manager.(farmID.getText(), yr.getText());
				}
			} catch (Exception f) {

			}
		});

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane

		Label label = new Label("Milk Weight");

		root.setTop(label);

		root.setCenter(vbox);

		// Home page scene

		Label designerLabel = new Label(
				"ATEAM 94:\n Xiaoyang Song\n William Mulvaney\n Yinzhou Zhang");
		Button backButton8 = new Button("Home");
		backButton8.setOnAction(e -> primaryStage.setScene(HomeScene));
		VBox designerPage = new VBox();
		designerPage.getChildren().addAll(designerLabel, backButton8);

		HomeScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Add file page scene
		addFile = new Scene(fileAdd, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Edit file page scene
		editFile = new Scene(fileEdit, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Farm report scene
		farmReport = new Scene(vbox1, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Get report scene
		getReport = new Scene(vbox2, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Get date range scene
		getDateRange = new Scene(daterange, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Get month report scene
		getMonthReport = new Scene(monthDisplay, WINDOW_WIDTH, WINDOW_HEIGHT);
		// Get annual report scene
		getAnnualReport = new Scene(yearDisplay, WINDOW_WIDTH, WINDOW_HEIGHT);
		// designer Scene
		designer = new Scene(designerPage, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(HomeScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public static String farmData(String farmID, int year) {
		if (factory.get(farmID) == null)
			return null;
		String output = "Year: " + year + "\n Month    Total weight \n";
		for (int i = 0; i < 12; i++) {
			int sum = 0;
			for (int j = 0; j < 31; j++) {

				sum += factory.get(farmID).get(year)[i][j];
			}
			output += (i + 1) + "    : " + sum + '\n';
		}
		return output;
	}

	public class Month {
		String month;
		String totalWeight;
		String percentage;
		int total;

		public Month(int month, int totalWeight, int total) {
			this.month = Integer.toString(month);
			this.totalWeight = Integer.toString(totalWeight);
			this.total = total;
			this.percentage = Double.toString(1.0 * totalWeight/total);
		}

		/**
		 * @return the month
		 */
		public String getMonth() {
			return month;
		}

		/**
		 * @param month the month to set
		 */
		public void setMonth(String month) {
			this.month = month;
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

		/**
		 * @return the total
		 */
		public int getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(int total) {
			this.total = total;
		}

	}
}