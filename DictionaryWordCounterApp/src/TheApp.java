import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class TheApp extends Application {
    
    
	@Override
    public void start(Stage primaryStage) {
		
        // The data structure that will be used to store all words in the document
		ArrayList<String> allWordsInDocument = new ArrayList<String>();
		
		
		
		// Load the dictionary words from the file
		HashSet<String> dictWords = new HashSet<String>();
		File dictFile = new File("dict.txt");
        try {
            Scanner input = new Scanner(dictFile); 
            while (input.hasNext()) {
                String word  = input.next();
                dictWords.add(word);
            }
            input.close();
        }
        catch (FileNotFoundException ex) {
        	System.out.println("there was an error reading from the dictionary file");
        } 
        
        
        
    	// Create the user interface
    	primaryStage.setTitle("Dictionary Word Counter");
        FileChooser chooser = new FileChooser();
        Button chooserButton = new Button("Select the .txt file to load");
        Button countButton = new Button("Count the Dictionary Words");
        countButton.setDisable(true);
        Label blankLine = new Label("");
        Label outputMessage = new Label("");
        GridPane pane = new GridPane();
        pane.add(chooserButton, 0, 0);
        pane.add(countButton, 0, 1);
        pane.add(blankLine, 0, 2);
        pane.add(outputMessage, 0, 3);
        Scene scene = new Scene(pane, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
  
        
        
        // When chooser button clicked, open the file and read in all of the words
        chooserButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {  
            	File selectedFile = chooser.showOpenDialog(primaryStage);
                new Thread(){
                	public void run() {
	                	Platform.runLater(() -> outputMessage.setText("Reading the words from the file..."));
	                    try {
	                        Scanner input = new Scanner(selectedFile); 
	                        while (input.hasNext()) {
	                            String word  = input.next();
	                            allWordsInDocument.add(word);
	                        }
	                        input.close();
	                        Platform.runLater(() -> outputMessage.setText("The file has been read"));
	                        Platform.runLater(() -> countButton.setDisable(false));
	                    }
	                    catch (FileNotFoundException ex) {
	                    	System.out.println("There was an error reading from that file");
	                    }
                	}
                }.start();
            }
        } );

       
        
        // When count button clicked, count the dictionary words in the document
        countButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                new Thread(){
                	int numWords = 0;
                    public void run() {
                            Platform.runLater(() -> outputMessage.setText("Counting the dictionary words..."));
                            
                        	// TODO: The following loop counts ALL words in the document, but it should only count words in the Dictionary
                            //       Modify the code inside the loop so that it only counts words that are in the dictionary
                        	//       (in other words, it should only count the word if the dictionary contains that word)
                            // NOTE: this should only require adding 1 or 2 lines of code
                            for (String word: allWordsInDocument) {
                            	numWords++;
                            }
                            
                            Platform.runLater(() -> outputMessage.setText("The document contains " + numWords + " dictionary words"));    
                    }
                }.start();
            }
        } );
        
	}        

    
    
 public static void main(String[] args) {
        launch(args);
    }
}