import java.io.*;
import java.util.*;

public class SlangDictionary {
    //HashMap implementation
    //Each element is a <key,values> equivalent to <slang word, its meaning>
    private HashMap<String, ArrayList<String>> sDict;
    private ArrayList<String> logs;
    private ArrayList<String> keys;

    //Search by slang word, or by definition
    //Returns a string contain keys
    public ArrayList<String> searchSlang(String _method, String _info) {
        ArrayList<String> foundedKeys = new ArrayList<String>();
        if (_method.compareTo("Search by word") == 0) {
            ArrayList<String> defi = this.sDict.get(_info);
            if (defi == null) {
                return foundedKeys;
            }
            foundedKeys.add(_info);
        }
        else if (_method.compareTo("Search by definition") == 0) {
            Iterator<Map.Entry<String, ArrayList<String>>> iter = this.sDict.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, ArrayList<String>> cur = iter.next();
                String key = cur.getKey();
                ArrayList<String> defi = cur.getValue();
                for (int i = 0; i < defi.size(); i++) {
                    if (defi.get(i).contains(_info)) {
                        foundedKeys.add(key);
                        break;
                    }
                }
            }
        }
        return foundedKeys;
    }

    //Load the logs file
    public ArrayList<String> loadLogs(String filename) {
        ArrayList<String> curLogs = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
    
            String line_info = br.readLine();

            while (line_info != null) {
                curLogs.add(line_info);
                line_info = br.readLine();
            }
            br.close();
            fr.close();
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }
        return curLogs;
    }

    //Load the slang dictionary file
    public Boolean loadSlangDictionary(String filename) {
        try {
            this.sDict.clear();
            this.sDict = new HashMap<String, ArrayList<String>>();
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
            fr.close();

            this.keys = new ArrayList<String>(this.sDict.keySet());
            return true;
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

        return false;
    }

    //Save the logs file
    public Boolean saveLogs(String filename, String NEW_CHANGE_LOG) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(NEW_CHANGE_LOG + "\n");
            bw.close();            
            fw.close();
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

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
            fw.close();
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

        return false;
    }

    //Show the searching history by user
    public ArrayList<String> showHistory() {
        return this.logs;
    } 

    public Boolean isExist(String word) {
        return this.sDict.containsKey(word);
    }

    //Add the slang word to the dictionary
    //isComfirm values: -1: cancel, 0: overwrite/normal adding if not exist, 1: duplicate
    //Return values: true: add success, false: add failure
    public Boolean addSlang(String word, String definition, int isComfirm) {
        ArrayList<String> defi = new ArrayList<String>();

        if (isComfirm == -1) {
            return false;
        }

        if (isComfirm == 0) {
            defi.add(definition);
            this.sDict.put(word,defi);
            return true;
        }
        if (isComfirm == 1) {
            defi.addAll(this.sDict.get(word));
            defi.add(definition);
            this.sDict.put(word,defi);
            return true;
        }

        return false;
    }

    //Edit the slang word or its definition
    public Boolean editSlang(String editBy, String _slangWord, String _editText, int _idx) {
        //Save a copy definition
        ArrayList<String> defi = this.sDict.get(_slangWord);
        if (defi == null) {
            return false;
        }

        if (editBy.compareTo("word") == 0) {
            this.deleteSlang(_slangWord, 0);
            this.sDict.put(_editText,defi);
            return true;
        }
        else if (editBy.compareTo("definition") == 0) {
            //The user choose which definition needs to be edited
            this.deleteSlang(_slangWord, 0);

            defi.set(_idx,_editText);
            this.sDict.put(_slangWord,defi);
            return true;

        }
        return false;
    } 

    //Delete the slang word
    public Boolean deleteSlang(String word, int isComfirm) {
        if (isComfirm == 1) {return false;}
        this.sDict.remove(word);
        return true;
    }

    //Reset current dictionary to default
    public void resetDictionary() throws IOException {
        //Finds the original slang dictionary file, update the current ones
        this.loadSlangDictionary("slang.txt");
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
        int i = 0;
        while (i++ < 4) {
            result.add(this.getRandomSlang());
        }
        return result;
    }

    public ArrayList<String> getDefinitionFromSlang(String word) {
        return this.sDict.get(word);
    }

    public ArrayList<String> showKeys() {
        //Refresh the keys list
        this.keys.clear();
        this.keys = new ArrayList<String>(this.sDict.keySet());
        return this.keys;
    }

    public void writeLogs(String CHANGELOG) {
        this.logs.add(CHANGELOG);
        this.saveLogs("logs.txt", CHANGELOG);
    }

    public ArrayList<String> quizBySlangWord() {
        ArrayList<ArrayList<String>> fourSlang = this.get4Slangs();
        ArrayList<String> answers = new ArrayList<String>();
        Random rand = new Random();
        int randNum = rand.nextInt(4); //is the answer
        String question = fourSlang.get(randNum).get(0); //get the question
        
        for (int i = 0; i < fourSlang.size(); i++) {
            answers.add(fourSlang.get(i).get(rand.nextInt(fourSlang.get(i).size())));
        }

        //Set the question and answers
        ArrayList<String> quiz = new ArrayList<String>();
        quiz.add(question);
        quiz.addAll(answers);
        //
        return quiz;
    }

    public ArrayList<String> quizBySlangDefinition() {
        ArrayList<ArrayList<String>> fourSlang = this.get4Slangs();
        ArrayList<String> answers = new ArrayList<String>();
        Random rand = new Random();
        int randNum1 = rand.nextInt(4); //is the answer to the question
        int randNum2 = rand.nextInt(fourSlang.get(randNum1).size()); //is the question
        String question = fourSlang.get(randNum1).get(randNum2);
        
        for (int i = 0; i < fourSlang.size(); i++) {
            answers.add(fourSlang.get(i).get(0));
        }
        //Set the question and answers
        ArrayList<String> quiz = new ArrayList<String>();
        quiz.add(question);
        quiz.addAll(answers);
        //
        return quiz;
    }

    SlangDictionary() {
        this.sDict = new HashMap<String, ArrayList<String>>();
        this.loadSlangDictionary("slang.txt");
        this.logs = this.loadLogs("logs.txt");
        this.keys = new ArrayList<String>(this.sDict.keySet());
    }

}
