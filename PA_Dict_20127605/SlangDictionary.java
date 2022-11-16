import java.io.*;
import java.util.*;

public class SlangDictionary {
    //HashMap implementation
    //Each element is a <key,values> equivalent to <slang word, its meaning>
    private HashMap<String, String[]> sDict = new HashMap<String, String[]>();
    private HashMap<String, String[]> logs = new HashMap<String, String[]>();

    //Search by slang word, or by definition
    //Returns a string
    public String searchSlang(String searchBy, String _info) {
        if (searchBy.compareTo("word") == 0) {

        }
        else if (searchBy.compareTo("definition") == 0) {

        }
        return "Error searching";
    }

    //Load the logs file
    public Boolean loadLogs(String filename) {
        return false;
    }

    //Load the slang dictionary file
    public Boolean loadSlangDictionary(String filename) {
        return false;
    }

    //Save the logs file
    public Boolean saveLogs(String filename) {
        return false;
    }

    //Save the slang dictionary file
    //This does not change the original slang dictionary file
    public Boolean saveSlangDictionary(String filename) {
        return false;
    }

    //Show the searching history by user
    public void showHistory() {
        //Do something with the history (this.logs)
    } 

    //Add the slang word to the dictionary
    //isComfirm values: -1: cancel, 0: overwrite, 1: duplicate
    //Return values: true: add success, false: add failure
    public Boolean addSlang(String word, String definition, int isComfirm) {
        if (isComfirm == -1) {
            return false;
        }
        return true;
    }

    //Edit the slang word or its definition
    public Boolean editSlang(String editBy, String word) {
        if (editBy.compareTo("word") == 0) {

        }
        else if (editBy.compareTo("definition") == 0) {

        }
        return false;
    } 

    //Delete the slang word
    public Boolean deleteSlang(String word, Boolean isComfirm) {
        return false;
    }

    //Reset current dictionary to default
    public void resetDictionary() {
        //Finds the original slang dictionary file, update the current ones
    }
    
    //Getting a random slang in dictionary and its definition
    public String getRandomSlang() {
        return "";
    }

}
