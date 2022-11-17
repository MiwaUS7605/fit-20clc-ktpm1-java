import java.io.*;
import java.util.*;

public class SlangDictionary {
    //HashMap implementation
    //Each element is a <key,values> equivalent to <slang word, its meaning>
    private HashMap<String, ArrayList<String>> sDict = new HashMap<String, ArrayList<String>>();
    private HashMap<String, ArrayList<String>> logs = new HashMap<String, ArrayList<String>>();

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
    public Boolean loadSlangDictionary(String filename) throws IOException {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line_info = br.readLine();
            String key = "";
            
            while (line_info != null) {
                String[] info = line_info.split("`");
                key = info[0];
                ArrayList<String> defi = new ArrayList<String>();
                String[] sub_defi = info[1].split("\\| ");
                for (String iter:sub_defi) {
                    defi.add(iter);
                }
                String line_info_next = br.readLine();
                if (line_info_next != null) {
                    String[] info_next = line_info_next.split("`");

                    while (info_next.length == 1) {
                        defi.add(info_next[0]);
                        line_info_next = br.readLine();
                        info_next = line_info_next.split("`");
                    }
                }
                this.sDict.put(key,defi);
                line_info = line_info_next;
            }
            br.close();
            return true;
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

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
