/**
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
		
			addFileButton.setOnAction( e->  {
				try {
					manager.readDataLineByLine(insertFile.getText());
					if(fileText.getChildren().contains(fileNotFound)) 
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
		// Year field
		TextField year = new TextField();
		Label yearLabel = new Label("Year:");
		// Create a drop-down ComboBox
		ComboBox<String> month = new ComboBox<String>();
		Label monthLabel = new Label("Month:");
		ComboBox<String> date = new ComboBox<String>();
		Label dateLabel = new Label("Date:");
		Button editFileButton = new Button("Edit");
		// Month and date drop down
		String[] monthArray = new String[12];
		for (int i = 0; i < 12; i++)
			monthArray[i] = "" + (i + 1);
		String[] dateArray = new String[31];
		for (int i = 0; i < 31; i++)
			dateArray[i] = "" + (i + 1);

		month.getItems().addAll(monthArray);
		date.getItems().addAll(dateArray);

		VBox dateInsert = new VBox();
		dateInsert.getChildren().addAll(yearLabel, year, monthLabel, month, dateLabel, date, editFileButton);
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
		vbox1.getChildren().addAll(title1, promptID, farmID, promptYr, yr, buttonFR, checkBox1, backButton3);
		Label invalid = new Label("Invalid farm ID");
		
		TableView table = new TableView();
		
		buttonFR.setOnAction(e->{ 
			try {
				String input = farmData(farmID.getText(), Integer.parseInt(yr.getText()));
				if(input == null) {				
					vbox1.getChildren().add(invalid);
				}
				else if(!checkBox1.isSelected()) {
					TableColumn monthColumn = new TableColumn("Month");	
					monthColumn.setCellValueFactory(new PropertyValueFactory<Month, String>("month"));
					
					TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(new PropertyValueFactory<Month, String>("totalWeight"));
					if(table.getItems().size() == 0) {
						table.getColumns().addAll(monthColumn, totalColumn);
						table.setItems(FXCollections.observableArrayList(new Month(1, 50, 5), new Month(1, 50, 5), new Month(1, 50, 5)));
					}
					vbox1.getChildren().add(table);
				}
				else {
					manager.outputmonth(farmID.getText(), yr.getText());
				}
			
			} catch(Exception f) {
			
		}
		}	);
			
		
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

		vbox2.getChildren().addAll(farmReportButton, annual, monthly, dateRange, backButton4);
		
		// Get Date Range
		Label DateRange = new Label("Date Range");
		TextField insertdat = new TextField();
		Label labeldat = new Label("Enter Starting Date");
		TextField insertenddate = new TextField();
		Label labelenddate = new Label("Enter End Date");
		Button daterangebutton = new Button("Get report!");


		VBox daterange = new VBox();
		daterange.getChildren().addAll(DateRange, labeldat, insertdat, labelenddate, insertenddate, daterangebutton,
				backButton5);

		// Get Month Report
        Label Monthreport = new Label("Month report");
        TextField insertyearep = new TextField();
        Label labelyearep = new Label("Enter Year(int)");
        Label labelmonrep = new Label("Enter Month");
        Button monthReportButton = new Button("Get Report!");
        ComboBox<String> month1 = new ComboBox<String>();
        CheckBox checkBox2 = new CheckBox("Create File");
        month1.getItems().addAll(monthArray);
        VBox monthDisplay= new VBox();
        monthDisplay.getChildren().addAll(Monthreport,labelyearep, insertyearep, labelmonrep, month1,
        		monthReportButton, checkBox2, backButton6);
        
        monthReportButton.setOnAction(e -> 	{
        	try {
        		//Table that will hold month information
			
        		TableView<FarmMonth> table1 = new TableView<FarmMonth>();			
        		// If user has not selected box print table
        		if(!checkBox2.isSelected()) {
        			//First column
        			TableColumn idColumn = new TableColumn("FarmID");	
        			idColumn.setCellValueFactory(new PropertyValueFactory<Month, String>("farmID"));
        			// Second column
        			TableColumn totalColumn = new TableColumn("Total Weight");
					totalColumn.setCellValueFactory(new PropertyValueFactory<Month, String>("totalWeight"));
				
					// Third column
					TableColumn percentageColumn = new TableColumn("Percentage");
					percentageColumn.setCellValueFactory(new PropertyValueFactory<FarmMonth, String>("percentage"));
					//If it has not been added yet
				
					table.getItems().clear();
					table1.getColumns().addAll(idColumn, totalColumn, percentageColumn);
					ArrayList<String> farmIDListMonth = factory.farmIDListMonthly;
					ArrayList<Double> totalWeightMonth = factory.totalWeightMonthly;
					ArrayList<Double> PercentageMonth = factory.PercentageMonthly;
					ObservableList<Object> list = FXCollections.observableArrayList();
					
					// Add all farms monthly reports
					for(int i = 0; i < farmIDListMonth.size(); i++) {
						list.add(new FarmMonth(totalWeightMonth.get(i), farmIDListMonth.get(i), PercentageMonth.get(i)));
					}
					table.setItems(list);
					monthDisplay.getChildren().add(table);
        		}		
				else {
					manager.outputmonth(farmID.getText(), yr.getText());
				}
	} catch(Exception f) {
		
		
		}
        });
        
        

        // Get Annual report
        Label Annualreport = new Label("Annual report");
        TextField insertyearep1 = new TextField();
        Label labelyearep1 = new Label("Enter Years(int)");
        Button annualReportButton = new Button("Get Report!");
       
        VBox yearDisplay = new VBox();
        yearDisplay.getChildren().addAll(Annualreport,labelyearep1, insertyearep1, annualReportButton, backButton7);
		
        

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane

		Label label = new Label("Milk Weight");

		root.setTop(label);

		root.setCenter(vbox);

		// Home page scene
		
		Label designerLabel = new Label("ATEAM 94:\n Xiaoyang Song\n William Mulvaney\n Yinzhou Zhang");
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
		//designer Scene
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
		if(factory.get(farmID) == null)
			return null;
		String output = "Year: " + year + "\n Month    Total weight \n";
		for(int i = 0; i < 12; i++) {
			int sum = 0;
			for(int j = 0; j < 31; j++) {
				
					sum += factory.get(farmID).get(year)[i][j];
			}
			output += (i+1) +  "    : " + sum + '\n';
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
			this.percentage = "";
		
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


