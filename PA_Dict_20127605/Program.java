public class Program {

    public static void quizBySlangWord() {
        return;
    }

    public static void quizBySlangDefinition() {
        return;
    }

    public static void main(String args[]) throws Exception {
        try {
            SlangDictionary sd = new SlangDictionary();
            System.out.println(sd.loadSlangDictionary("slang.txt"));
            // System.out.println(sd.addSlang("UwU", "Satisfy", 1));
            // System.out.println(sd.editSlang("word", "UwU"));
            // System.out.println(sd.searchSlang("word", "OwO"));
            // System.out.println(sd.searchSlang("word", "UwU"));
            System.out.println(sd.searchSlang("definition", ":)"));

        }
        catch (Exception exc) {
            System.out.println(exc.toString());
        }
    }
}