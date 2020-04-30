/*
 * Filename:   Main.java
 * Project:    aTeam Final Project
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

	private static Scene HomeScene; // Home page
	private static Scene addFile; // Add file page
	private static Scene editFile; // Edit file page
	private static Scene farmReport; // Get farm report page
	private static Scene getReport; // General get report page
	private static Scene getDateRange; // Get date range report page
	private static Scene getMonthReport; // Get month report page
	private static Scene getAnnualReport; // Get annual report page
	private static Scene designer; // Designer information page

	private static Manager manager; // Farm and factory manager that reads in from a file
	private static Factory factory; // Factory used to store farm information
	
	private static TableView<FarmAnnual> table3 = new TableView<FarmAnnual>(); // Table that stores date range
	private static TableView<FarmAnnual> table2 = new TableView<FarmAnnual>(); // Table that shows annual
	private static TableView<FarmMonth> table1 = new TableView<FarmMonth>(); // Table that shows month
	private static TableView<Month> table = new TableView<Month>(); // Table that shows farm report
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		factory = new Factory();
		manager = new Manager(factory);

		// Vbox used for main page
			VBox vbox = new VBox();

		//
		// Back Buttons that redirect to home page
		//
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

		//
		// Home page buttons
		//
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
		// File add button action
		addFileButton.setOnAction(e -> {
			try {
				// Read file in with manager
				manager.readDataLineByLine(insertFile.getText());
				if (fileText.getChildren().contains(fileNotFound))
					fileText.getChildren().remove(fileNotFound);
			} catch (Exception e1) {
				fileText.getChildren().add(fileNotFound);
			}
		});
		
		// Add file page	
			Label fileHeader = new Label("Add File");
			BorderPane fileAdd = new BorderPane();
			fileAdd.setCenter(fileText);
			fileAdd.setTop(fileHeader);
			fileAdd.setBottom(backButton1);
		//
		// Edit File Page
		//
			Label fileEditHeader = new Label("Edit File");
			TextField farmid = new TextField();
			Label farmis = new Label("Farm ID");

			// Year input field 
				TextField year = new TextField();
				Label yearLabel = new Label("Year:");
			// Create a drop-down ComboBox
				TextField month = new TextField();
			// Create labels and text fields
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
		// Edit file buttone action
		editFileButton.setOnAction(e -> {
			try {
				// get file path, rewrite file
				String filepath = year.getText() + "-" + month.getText() + ".csv";
				manager.writeRecord(year.getText(), month.getText(), date.getText(),
						farmid.getText(), weight.getText(), filepath);
			} catch (Exception e1) {
				fileText.getChildren().add(fileNotFound);
			}
		});
		
		// File edit page nodes added
			VBox dateInsert = new VBox();
			dateInsert.getChildren().addAll(farmis, farmid, yearLabel, year, monthLabel,
				month, dateLabel, date, weightLabel, weight, editFileButton);
			BorderPane fileEdit = new BorderPane();
			fileEdit.setTop(fileEditHeader);
			fileEdit.setCenter(dateInsert);
			fileEdit.setBottom(backButton2);

		//
		// Farm Report page
		//
			// Labels
				Label title1 = new Label("Get Farm Report");
				Label promptID = new Label("Enter Farm ID(String):");
				Label promptYr = new Label("Enter Years(int):");
				Label invalid = new Label("Invalid farm ID");
			// TextFields
				TextField farmID = new TextField();
				TextField yr = new TextField();
			
			// button to commit action
				Button buttonFR = new Button("Click here to get the report");
			
			// Other nodes
				VBox vbox1 = new VBox();
				CheckBox checkBox1 = new CheckBox("Create File");
			
			vbox1.getChildren().addAll(title1, promptID, farmID, promptYr, yr, buttonFR,
				checkBox1, backButton3);

		//
		// Button action
		//
		buttonFR.setOnAction(e -> {
			// Remove invalid tag
			if (vbox1.getChildren().contains(invalid)) {
				vbox1.getChildren().remove(invalid);
			}
			// Create a new farm to store farm with user input farmID
			Farm currentFarm;
			try {

				// Check that farm is in factory
				if (!factory.contains(farmID.getText().trim())) {
					vbox1.getChildren().add(invalid);
				}
				// if they do not check box use table
				else if (!checkBox1.isSelected()) {
					// Initialize currentFarm using user input
					currentFarm = factory.get(farmID.getText());
					// Clear out table
					table.getColumns().clear();
					table.getItems().clear();
					
					// Create table columns
					// Month column of table
					TableColumn monthColumn = new TableColumn("Month");
					monthColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("month"));

					// Total weight column of table
					TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("totalWeight"));

					// Percentage column of table
					TableColumn percentageColumn = new TableColumn("Percentage");
					percentageColumn.setCellValueFactory(
							new PropertyValueFactory<Month, String>("percentage"));

					// Find the total of the year (for percentage purposes
					double total = 0;
					for (int i = 0; i < 12; i++) {
						for (int j = 0; j < 31; j++)
							total += currentFarm.getDate(Integer.parseInt(yr.getText()),
									i + 1, j + 1);
					}
					// Add columns to table
					table.getColumns().addAll(monthColumn, totalColumn, percentageColumn);
					// Create new list to be added
					ObservableList<Month> list = FXCollections.observableArrayList();
					// Add all items to list
					
					// Find and add monthly totals to the list
					for (int i = 0; i < 12; i++) {
						double monthlyTotal = 0.0;
						for (int j = 0; j < 31; j++) {
							monthlyTotal += currentFarm
									.getDate(Integer.parseInt(yr.getText()), i + 1, j + 1);
						}
						list.add(new Month(i + 1, monthlyTotal, total));
					}

					// Add the list of Month objects to the table
					table.setItems(list);

					// Add the table to the VBox
					vbox1.getChildren().add(table);
				} 
				// Create file instead
				else {
					manager.outputmonth(farmID.getText(), yr.getText());
				}

			} catch (Exception f) {
				
			}
		});

		//
		// Get report page
		//
			// Labels
			Label title = new Label("Get Report");
			Label prompt = new Label("Which report do you want?");
		
		// Buttons
			Button annual = new Button("Annual Report");
			annual.setOnAction(e -> primaryStage.setScene(getAnnualReport));
			
			Button monthly = new Button("Monthly Report");
			monthly.setOnAction(e -> primaryStage.setScene(getMonthReport));

			Button dateRange = new Button("Date Range Report");
			dateRange.setOnAction(e -> primaryStage.setScene(getDateRange));

			Button farmReportButton = new Button("Farm Report");
			farmReportButton.setOnAction(e -> primaryStage.setScene(farmReport));

		// VBox to add to screen
			VBox vbox2 = new VBox();
			vbox2.getChildren().addAll(farmReportButton, annual, monthly, dateRange,
				backButton4);
		//
		// Get Date Range page
		//
			// Labels
				Label DateRange = new Label("Date Range");
				Label labeldat = new Label("Enter Starting Date");
				Label labelenddate = new Label("Enter End Date");
				Label invalid4 = new Label("Invalid farmID or date range");
				
			// Textfields
				TextField insertdat = new TextField();
				TextField insertenddate = new TextField();
		
			// Buttons
				Button daterangebutton = new Button("Get report!");
		
			// VBox used to display 
				VBox daterange = new VBox();
				daterange.getChildren().addAll(DateRange, labeldat, insertdat, labelenddate,
						insertenddate, daterangebutton, backButton5);
		
			// Observeable list used to store data in table
				ObservableList<FarmAnnual> list1 = FXCollections.observableArrayList();
		
		// Button action
		daterangebutton.setOnAction(e -> {
			try {
				// Clear out all past information
				list1.clear();
				if (daterange.getChildren().contains(invalid4)) {
					daterange.getChildren().remove(invalid4);
				}
				if (daterange.getChildren().contains(table3)) {
					daterange.getChildren().remove(table3);
				}
				
				// Get date from user input
				int d1 = Integer.parseInt(insertdat.getText().substring(0, 2));
				int m1 = Integer.parseInt(insertdat.getText().substring(2, 4));
				int y1 = Integer.parseInt(insertdat.getText().substring(4, 8));
				int d2 = Integer.parseInt(insertenddate.getText().substring(0, 2));
				int m2 = Integer.parseInt(insertenddate.getText().substring(2, 4));
				int y2 = Integer.parseInt(insertenddate.getText().substring(4, 8));
				factory.getdaterange(y1, m1, d1, m2, d2);

				// Get lists that store farmID's, totalWeights, and Percentages
				ArrayList<String> farmIDListDR = factory.farmIDListDR;
				ArrayList<Double> totalWeightDR = factory.totalWeightDR;
				ArrayList<Double> PercentageDR = factory.PercentageDR;

				// Create columns for table
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

				// Clear table
				table3.getItems().clear();
				table3.getColumns().clear();

				// Add columns
				table3.getColumns().addAll(idColumn, totalColumn, percentageColumn);

				// Add all farms date range reports
				for (int i = 0; i < farmIDListDR.size(); i++) {
					list1.add(new FarmAnnual(farmIDListDR.get(i), totalWeightDR.get(i),
							PercentageDR.get(i)));
				}
				// Add information to the table
				table3.setItems(list1);

				// Add the table to the display
				daterange.getChildren().add(table3);

			} catch (Exception f) {
				daterange.getChildren().add(invalid4);
			}

		});

		//
		// Get Month Report page
		//
		
			// Labels
				Label Monthreport = new Label("Month report");
				Label labelyearep = new Label("Enter Year(int)");
				Label labelmonrep = new Label("Enter Month");
				Label invalid2 = new Label("Invalid farm");
			
			// Other nodes
				TextField insertyearep = new TextField();
				Button monthReportButton = new Button("Get Report!");
				ComboBox<String> month1 = new ComboBox<String>();
				CheckBox checkBox2 = new CheckBox("Create File");
				
			// Add the months to the combo box
				month1.getItems().addAll(monthArray);
			// VBox for display
				VBox monthDisplay = new VBox();
				monthDisplay.getChildren().addAll(Monthreport, labelyearep, insertyearep,
						labelmonrep, month1, monthReportButton, checkBox2, backButton6);
		
		// Button action
		monthReportButton.setOnAction(e -> {
			// Remove invalid tag
			if (monthDisplay.getChildren().contains(invalid2))
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
					
					// Clear out table
					table1.getItems().clear();
					table1.getColumns().clear();
					
					// Add columns to table
					table1.getColumns().addAll(idColumn, totalColumn, percentageColumn);
					
					// Get lists of farmID's, total weights, and percentages
					ArrayList<String> farmIDListMonth = factory.farmIDListMonthly;
					ArrayList<Double> totalWeightMonth = factory.totalWeightMonthly;
					ArrayList<Double> PercentageMonth = factory.PercentageMonthly;
					
					// Create observable list that can be added to table
					ObservableList<FarmMonth> list = FXCollections.observableArrayList();

					// Add all farms monthly reports
					for (int i = 0; i < farmIDListMonth.size(); i++) {
						list.add(new FarmMonth(totalWeightMonth.get(i),
								farmIDListMonth.get(i), PercentageMonth.get(i)));
					}
					
					// Add list to table
					table1.setItems(list);
					
					// Add table to display
					monthDisplay.getChildren().add(table1);
					
				} else {
					manager.outputmonth(farmID.getText(), yr.getText());
				}
			} catch (Exception f) {
				monthDisplay.getChildren().add(invalid2);
			}
		});

		
		
		//
		// Get Annual report
		//
			// Labels
				Label Annualreport = new Label("Annual report");
				Label labelyearep1 = new Label("Enter Years(int)");
			// Other nodes
				TextField insertyearep1 = new TextField();
				Button annualReportButton = new Button("Get Report!");
			// VBox to be put to display
				VBox yearDisplay = new VBox();
					yearDisplay.getChildren().addAll(Annualreport, labelyearep1, insertyearep1,
					annualReportButton, backButton7);
		// Button action
		annualReportButton.setOnAction(e -> {
			try {	
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
					
					// Clear table of past elements
					table2.getItems().clear();
					table2.getColumns().clear();
					
					// Add columns to table
					table2.getColumns().addAll(idColumn, totalColumn, percentageColumn);

					// Get list for annual farmID's, totalWeights, and percentages
					factory.getAnnualReport(Integer.parseInt(insertyearep1.getText()));
					ArrayList<String> farmIDListAnnual = factory.farmIDListAnnual;
					ArrayList<Double> totalWeightAnnual = factory.totalWeightAnnual;
					ArrayList<Double> PercentageAnnual = factory.PercentageAnnual;
					
					// Observable list to be added to table
					ObservableList<FarmAnnual> list = FXCollections.observableArrayList();

					// Add all farms annual reports
					for (int i = 0; i < farmIDListAnnual.size(); i++) {
						list.add(new FarmAnnual(farmIDListAnnual.get(i),
								totalWeightAnnual.get(i), PercentageAnnual.get(i)));
					}
					
					// Add the list to the table
					table2.setItems(list);
					
					// Add the table to the display VBox
					yearDisplay.getChildren().add(table2);
				
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
	 *  Main method that starts program. 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	/**
	 * Inner Month class thats instances are used to store data in a TableView object
	 * 
	 * @author William, Xiaoyang, Yinzhou
	 */
	public class Month {
		String month;
		String totalWeight;
		String percentage;
		
		/**
		 * Constructor that takes in values and converts them to string. 
		 * Does a simple percentage calculation
		 * @param month
		 * @param monthlyTotal
		 * @param total
		 */
		public Month(int month, double monthlyTotal, double total) {
			this.month = Integer.toString(month);
			this.totalWeight = Double.toString(monthlyTotal);
			this.percentage = Double.toString(1.0 * monthlyTotal / total);
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


	}
}
