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
        }
        catch (Exception exc) {
            System.out.println(exc.toString());
        }
    }
}