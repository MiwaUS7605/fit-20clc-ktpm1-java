import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Program implements ItemListener {

    private SlangDictionary sd = new SlangDictionary();

    public static void quizBySlangWord(SlangDictionary sd) {
        ArrayList<ArrayList<String>> fourSlang = sd.get4Slangs();
        ArrayList<String> answers = new ArrayList<String>();
        Random rand = new Random();
        int randNum = rand.nextInt(4); //is the answer
        String question = fourSlang.get(randNum).get(0); //get the question
        
        for (int i = 0; i < fourSlang.size(); i++) {
            answers.add(fourSlang.get(i).get(rand.nextInt(fourSlang.get(i).size())));
        }

        //Set the question and answers

        //
        return;
    }

    public static void quizBySlangDefinition(SlangDictionary sd) {
        ArrayList<ArrayList<String>> fourSlang = sd.get4Slangs();
        ArrayList<String> answers = new ArrayList<String>();
        Random rand = new Random();
        int randNum1 = rand.nextInt(4); //is the answer to the question
        int randNum2 = rand.nextInt(fourSlang.get(randNum1).size()); //is the question
        String question = fourSlang.get(randNum1).get(randNum2);
        
        for (int i = 0; i < fourSlang.size(); i++) {
            answers.add(fourSlang.get(i).get(0));
        }

        //Set the question and answers
            
        //
    }

    public void addComponentToPane(Container pane) 
    {      
        JPanel titlePane = new JPanel();
        JPanel resultListPane = new JPanel();
        JPanel centerPane = new JPanel();
        JPanel searchPane = new JPanel();
        JPanel slangInfoPane = new JPanel();
        JPanel functionPane = new JPanel();
        JPanel featurePane = new JPanel();
        
        JLabel title = new JLabel("SLANG DICTIONARY");
        title.setFont(new Font("MV Boli",Font.PLAIN,35));


        ArrayList<String> myList = new ArrayList<String>();
        for (int index = 0; index < 20; index++) {
            myList.add("List Item " + index);
        }

        JLabel slangKey = new JLabel();
        JLabel slangDefinition = new JLabel();
        slangInfoPane.setLayout(new BorderLayout());

        JList<String> list = new JList<String>(myList.toArray(new String[myList.size()]));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setVisibleRowCount(16);
        // list.setFont(new Font("monospace", Font.PLAIN,20));
        list.setLayoutOrientation(JList.VERTICAL);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()){
                    String selected = list.getSelectedValue().toString();
                    slangKey.setText(selected);
                    slangDefinition.setText(selected);
                }
            }
        });
        
        JButton btn_add = new JButton("ADD");
        btn_add.setMaximumSize(new Dimension(100,50));
        JButton btn_edit = new JButton("EDIT");
        btn_edit.setMaximumSize(new Dimension(100,50));
        JButton btn_del = new JButton("DELETE");
        btn_del.setMaximumSize(new Dimension(100,50));
        JButton btn_reset = new JButton("RESET");
        btn_reset.setMaximumSize(new Dimension(100,50));

        JButton btn_random = new JButton("RANDOM");
        btn_random.setMaximumSize(new Dimension(100,50));
        JButton btn_search = new JButton("SEARCH");
        btn_search.setMaximumSize(new Dimension(100,50));
        JButton btn_quiz = new JButton("QUIZ");
        btn_quiz.setMaximumSize(new Dimension(100,50));
        JButton btn_logs = new JButton("HISTORY");
        btn_logs.setMaximumSize(new Dimension(100,50));

        JTextField searchBar = new JTextField(20);
        searchBar.setMaximumSize(new Dimension(300,50));

        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        btn_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                slangKey.setText(searchBar.getText());
                slangDefinition.setText(sd.searchSlang("word",searchBar.getText()));
            }
        });

        btn_random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ArrayList<String> randSlang = sd.getRandomSlang();
                slangKey.setText(randSlang.get(0));
                String defi = "<html>";
                for (int i = 1; i < randSlang.size(); i++) {
                    defi += i + ". " + randSlang.get(i) + "<br/>";
                }
                defi += "</html>";
                slangDefinition.setText(defi);
            }
        });

        
        
        functionPane.add(btn_add);
        functionPane.add(btn_edit);
        functionPane.add(btn_del);
        functionPane.add(btn_reset);
        
        featurePane.setLayout(new GridLayout());
        featurePane.add(btn_random);
        featurePane.add(btn_quiz);
        featurePane.add(btn_logs);

        searchPane.add(searchBar);
        searchPane.add(btn_search);

        slangKey.setFont(new Font("MV Boli",Font.PLAIN,30));

        slangDefinition.setFont(new Font("Arial",Font.PLAIN,15));

        titlePane.add(title);   
        resultListPane.add(scrollPane);
        slangInfoPane.add(slangKey, BorderLayout.NORTH);
        slangInfoPane.add(slangDefinition, BorderLayout.CENTER);

        centerPane.setLayout(new BorderLayout());
        centerPane.add(searchPane, BorderLayout.NORTH);
        centerPane.add(slangInfoPane, BorderLayout.CENTER);
        centerPane.add(functionPane, BorderLayout.SOUTH);
        centerPane.add(featurePane, BorderLayout.EAST);
        featurePane.setLayout(new BoxLayout(featurePane, BoxLayout.Y_AXIS));

        pane.add(titlePane, BorderLayout.NORTH);
        pane.add(resultListPane, BorderLayout.WEST);
        pane.add(centerPane, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("SlangDictionary by 20127605");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Program demo = new Program();
        demo.addComponentToPane(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }

    Program() {
        try {
            this.sd.loadSlangDictionary("slang.txt");
        }
        catch (Exception exc) {
            exc.toString();
        }

    }



    @Override
    public void itemStateChanged(ItemEvent evt) 
    {
        // TODO: quiz page implementation 
    }

    


    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        });
    }
}