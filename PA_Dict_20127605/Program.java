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

        JLabel slangKey = new JLabel();
        JLabel slangDefinition = new JLabel();
        slangInfoPane.setLayout(new BorderLayout());


        DefaultListModel<String> model = new DefaultListModel<String>();

        JList<String> list = new JList<String>(model);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setPreferredSize(new Dimension(200,500));
        list.setVisibleRowCount(16);
        list.setLayoutOrientation(JList.VERTICAL);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()){
                    if (list.getSelectedValue() != null) {
                        String selected = list.getSelectedValue().toString();
                        slangKey.setText(selected);
                        ArrayList<String> definition = sd.getDefinitionFromSlang(selected);
                        String defi = "<html>";
                        for (int i = 0; i < definition.size(); i++) {
                            defi += i+1 + ". " + definition.get(i) + "<br/>";
                        }
                        defi += "</html>";
                        slangDefinition.setText(defi);
                    }
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
                JFrame addFrame = new JFrame("Add Slang");
                addFrame.setResizable(false);

                JPanel pane1 = new JPanel();
                JPanel pane2 = new JPanel();
                JPanel pane3 = new JPanel();

                JLabel slangName = new JLabel("Slang name");
                JLabel slangDefinition = new JLabel("Slang definition");
                
                JTextField entry1 = new JTextField(20);
                JTextArea entry2 = new JTextArea(5,20);

                JButton button1 = new JButton("CONFIRM");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        //Input string not empty
                        String input1 = entry1.getText();
                        String input2 = entry2.getText();
                        String outputMessage = "Add slang successfully!";

                        if (!input1.equals("") || !input2.equals("")) {
                            if (sd.isExist(input1) == false) {
                                sd.addSlang(input1, input2, 0);
                            }
                            else {
                                String[] options = {"Overwrite","Duplicate","Cancel"};
                                String note = "Slang is already specified. What will you do?";
                                int result = JOptionPane.showOptionDialog(null, note,"WARNING: SLANG EXIST", 0, 0, null, options,options[0]);

                                if (result == JOptionPane.YES_OPTION) {
                                    sd.addSlang(input1, input2, 0);
                                    outputMessage = "Overwrite slang successfully!";
                                }
                                else if (result == JOptionPane.NO_OPTION) {
                                    sd.addSlang(input1, input2, 1);
                                    outputMessage = "Duplicate slang successfully!";
                                }
                                else {
                                    sd.addSlang(input1, input2, -1);
                                    outputMessage = "Cancel!";
                                }
                            }
                        }
                        else {
                            outputMessage = "ERROR: Invalid/Empty input!";
                        }
                        
                        JOptionPane.showMessageDialog(null, outputMessage, "Notification",JOptionPane.INFORMATION_MESSAGE);
                        addFrame.dispatchEvent(new WindowEvent(addFrame, WindowEvent.WINDOW_CLOSING));
                        
                    }
                });
                pane1.add(slangName);
                pane1.add(entry1);
                pane2.add(slangDefinition);
                pane2.add(entry2);
                pane3.add(button1);

                addFrame.setLayout(new BorderLayout());
                addFrame.add(pane1, BorderLayout.NORTH);
                addFrame.add(pane2, BorderLayout.CENTER);
                addFrame.add(pane3, BorderLayout.SOUTH);

                addFrame.pack();
                addFrame.setLocation(pane.getWidth()/3, pane.getHeight()/3);
                addFrame.setVisible(true);

            }
        });
        
        String[] cbOption = {"Search by word", "Search by definition"};
        JComboBox<String> searchOption = new JComboBox<String>(cbOption);
        
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
                slangKey.setText("");
                slangDefinition.setText("");
                model.clear();

                String method = searchOption.getItemAt(searchOption.getSelectedIndex());
                String input = searchBar.getText();
                ArrayList<String> searchResult = sd.searchSlang(method, input);
                if (searchResult.size() == 0) {
                    slangDefinition.setText("Slang not found in the dictionary");
                }
                for (String iter:searchResult) {
                    model.addElement(iter);      
                }
            }
        });

        btn_random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                model.clear();
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

        searchPane.add(searchOption);
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