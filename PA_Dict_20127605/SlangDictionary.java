import java.io.*;
import java.util.*;

public class SlangDictionary {
    //HashMap implementation
    //Each element is a <key,values> equivalent to <slang word, its meaning>
    private HashMap<String, ArrayList<String>> sDict = new HashMap<String, ArrayList<String>>();
    private HashMap<String, ArrayList<String>> logs = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> keys;
    //Search by slang word, or by definition
    //Returns a string
    public String searchSlang(String searchBy, String _info) {
        String result = "";
        if (searchBy.compareTo("word") == 0) {
            ArrayList<String> defi = this.sDict.get(_info);
            if (defi == null) {
                return "Slang not found in dictionary";
            }
            result += _info;
            for (String iter:defi) {
                result += "|" + iter;
            }
            return result;
        }
        else if (searchBy.compareTo("definition") == 0) {
            Iterator<Map.Entry<String, ArrayList<String>>> iter = this.sDict.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, ArrayList<String>> cur = iter.next();
                String key = cur.getKey();
                ArrayList<String> defi = cur.getValue();
                for (int i = 0; i < defi.size(); i++) {
                    if (defi.get(i).contains(_info)) {
                        result += key + "|" + defi.get(i) + "\n";
                        break;
                    }
                }
            }
            return result;
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

            keys = new ArrayList<String>(this.sDict.keySet());
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
        String BACKTICK = "`", VERTICAL_LINE = "\\| ", NEWLINE = "\n";
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            Iterator<Map.Entry<String, ArrayList<String>>> iter = this.sDict.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, ArrayList<String>> cur = iter.next();
                bw.write(cur.getKey() + BACKTICK);
                ArrayList<String> defi = cur.getValue();
                for (int i = 0; i < defi.size(); i++) {
                    bw.write(defi.get(i));
                    if (i < defi.size() - 1) {bw.write(VERTICAL_LINE);}
                }
                bw.write(NEWLINE);
            }
            bw.close();            
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

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
        Boolean isExist = this.sDict.containsKey(word);
        ArrayList<String> defi = new ArrayList<String>();
        
        if (isComfirm == -1) {
            return false;
        }

        if (!isExist || isComfirm == 0) {
            defi.add(definition);
            this.sDict.put(word,defi);
            return true;
        }
        if (isComfirm == 1) {
            defi.addAll(this.sDict.get(word));
            defi.add(word);
            this.sDict.put(word,defi);
            return true;
        }

        return false;
    }

    //Edit the slang word or its definition
    public Boolean editSlang(String editBy, String _slangWord, String _editText) {
        if (editBy.compareTo("word") == 0) {
            ArrayList<String> defi = this.sDict.get(_slangWord);
            if (defi == null) {
                return false;
            }
            this.deleteSlang(_slangWord, true);
            //This should let the user input the word
            this.sDict.put(_editText,defi);
            //
            return true;
        }
        else if (editBy.compareTo("definition") == 0) {
            //The user choose which definition needs to be edited
        }
        return false;
    } 

    //Delete the slang word
    public Boolean deleteSlang(String word, Boolean isComfirm) {
        if (!isComfirm) {return false;}
        this.sDict.remove(word);
        return true;
    }

    //Reset current dictionary to default
    public void resetDictionary() {
        //Finds the original slang dictionary file, update the current ones
    }
    
    //Getting a random slang in dictionary and its definition
    public ArrayList<String> getRandomSlang() {
        Random rand = new Random();
        String randomKey = keys.get(rand.nextInt(this.sDict.size()));
        ArrayList<String> randomSlang = new ArrayList<String>();
        randomSlang.add(randomKey);
        for (String value:this.sDict.get(randomKey)) {
            randomSlang.add(value);
        }
        return randomSlang;
    }

    //Getting 4 random slang in dictionary and its definition
    public ArrayList<ArrayList<String>> get4Slangs() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<String> slg1 = this.getRandomSlang();
        ArrayList<String> slg2 = this.getRandomSlang();
        ArrayList<String> slg3 = this.getRandomSlang();
        ArrayList<String> slg4 = this.getRandomSlang();
        result.add(slg1);
        result.add(slg2);
        result.add(slg3);
        result.add(slg4);
        return result;
    }

}
