import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Program {

    private SlangDictionary sd;

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
        // JButton btn_showDict = new JButton("SHOW ALL");
        // btn_showDict.setMaximumSize(new Dimension(100,50));

        JTextField searchBar = new JTextField(20);
        searchBar.setMaximumSize(new Dimension(300,50));
        
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFrame addFrame = new JFrame("ADD SLANG");
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
                JFrame editFrame = new JFrame("EDIT SLANG");
                editFrame.setLayout(new BoxLayout(editFrame.getContentPane(), BoxLayout.Y_AXIS));
                editFrame.setResizable(false);
                
                if (list.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(null, "You must choose the slang from the list!", "ERROR: SLANG NOT FOUND", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DefaultListModel<String> model = new DefaultListModel<String>();
                String slangWord = list.getSelectedValue().toString();
                model.addElement(slangWord);
                for (String iter:sd.getDefinitionFromSlang(slangWord)) {
                    model.addElement(iter);
                }

                JLabel title = new JLabel("Select an info to edit");
                JList<String> infos = new JList<String>(model);
            
                JTextField info_edit = new JTextField(20);
                JButton btn_confirm = new JButton("CONFIRM");

                btn_confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String curSlang = model.get(0);
                        String[] method = {"word","definition"};
                        String msg = "";
                        if (infos.getSelectedValue() != null) {
                            
                            int selected_idx = infos.getSelectedIndex();
                            
                            if (selected_idx == 0) {
                                sd.editSlang(method[0],curSlang,info_edit.getText(),0);
                                msg = "Edit by " + method[0] + " successfully!";
                            }
                            else {
                                sd.editSlang(method[1],curSlang,info_edit.getText(),selected_idx - 1);
                                msg = "Edit by " + method[1] + " successfully!";
                            }
                            model.set(selected_idx, info_edit.getText());
                            JOptionPane.showMessageDialog(null,msg,"NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "You must choose info to edit from the list!", "ERROR: UNKNOWN FIELD", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                
                infos.setLayoutOrientation(JList.VERTICAL);

                editFrame.add(title);
                editFrame.add(infos);
                editFrame.add(info_edit);
                editFrame.add(btn_confirm);

                editFrame.pack();
                editFrame.setVisible(true);
            }
        });

        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (list.getSelectedValue() != null) {
                    String selected = list.getSelectedValue().toString();
                    String outputMessage = "";
                    int result = JOptionPane.showConfirmDialog(null,"Are you sure?" , "WARNING: DELETE SLANG", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        outputMessage = "Delete \"" + selected + "\" successfully!";
                    }
                    else {
                        outputMessage = "Cancel delete slang!";
                    }
                    sd.deleteSlang(selected, result);
                    outputMessage += "\nPress <SHOW ALL> button to show again.";
                    JOptionPane.showMessageDialog(null,outputMessage, "NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
                    model.clear();
                }
            }
        });

        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    sd.resetDictionary();
                    searchBar.setText(null);
                    slangKey.setText(null);
                    slangDefinition.setText(null);
                    JOptionPane.showMessageDialog(null, "RESET TO ORIGINAL DICTIONARY", "NOTIFICATION", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception e) { 
                    JOptionPane.showMessageDialog(null, e.toString(), "ERROR",JOptionPane.ERROR_MESSAGE);
                }
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
                    String msg = "Slang not found in the dictionary";
                    JOptionPane.showMessageDialog(null, msg,"ERROR", JOptionPane.ERROR_MESSAGE);
                }
                for (String iter:searchResult) {
                    model.addElement(iter);      
                }

                String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new java.util.Date());
                sd.writeLogs(timeStamp + ";" + method + ";" + input);
                System.out.println(timeStamp + ";" + method + ";" + input);
                
            }
        });

        btn_random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                model.clear();
                searchBar.setText(null);

                ArrayList<String> randSlang = sd.getRandomSlang();
                slangKey.setText("<html>On this day slang word:<br/>" + randSlang.get(0) + "</html>");
                String defi = "<html>";
                for (int i = 1; i < randSlang.size(); i++) {
                    
                    defi += i + ". " + randSlang.get(i) + "<br/>";
                }
                defi += "</html>";
                slangDefinition.setText(defi);

            }
        });


        // Too slow to display the full slang dictionary
        // btn_showDict.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent evt) {
        //         model.clear();
        //         ArrayList<String> keys = sd.showKeys();
        //         for (String iter:keys) {
        //             model.addElement(iter);
        //         }
        //         slangKey.setText(null);
        //         slangDefinition.setText(null);
        //         searchBar.setText(null);
        //     }
        // });

        btn_logs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFrame logFrame = new JFrame("LOGS");
                
                DefaultListModel<String> model = new DefaultListModel<String>();

                JList<String> logs = new JList<String>(model);

                JScrollPane scrollLogs = new JScrollPane();
                scrollLogs.setViewportView(logs);
                scrollLogs.setPreferredSize(new Dimension(350,500));
                list.setVisibleRowCount(16);
                list.setLayoutOrientation(JList.VERTICAL);

                for (String iter:sd.showHistory()) {
                    model.addElement(iter);
                }
                
                logFrame.add(scrollLogs);
                logFrame.pack();
                logFrame.setVisible(true);
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
        // featurePane.add(btn_showDict);

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

        // frame.addWindowListener(new WindowAdapter() {
        //     @Override
        //     public void windowClosing(WindowEvent evt) {
                
        //     }
        // });
        
        frame.pack();
        frame.setVisible(true);
        
    }

    Program() {
        this.sd = new SlangDictionary();
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