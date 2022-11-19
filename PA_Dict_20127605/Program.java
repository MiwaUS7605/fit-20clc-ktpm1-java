import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Program implements ItemListener {

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
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel();
        JPanel card3 = new JPanel();
        
        JLabel title = new JLabel("SLANG DICTIONARY");
        title.setFont(new Font("MV Boli",Font.PLAIN,40));


        ArrayList<String> myList = new ArrayList<String>();
        for (int index = 0; index < 20; index++) {
            myList.add("List Item " + index);
        }

        JLabel slangKey = new JLabel();
        JLabel slangDefinition = new JLabel();

        JList<String> list = new JList<String>(myList.toArray(new String[myList.size()]));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setVisibleRowCount(15);
        list.setFont(new Font("monospace", Font.PLAIN,20));
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
        JButton btn_edit = new JButton("EDIT");
        JButton btn_del = new JButton("DELETE");
        JButton btn_reset = new JButton("RESET");

        card3.add(btn_add);
        card3.add(btn_edit);
        card3.add(btn_del);
        card3.add(btn_reset);

        btn_add.setBounds(50,400,100,50);
        btn_edit.setBounds(175,400,100,50);
        btn_del.setBounds(300,400,100,50);
        btn_reset.setBounds(425,400,100,50);


        card3.setLayout(null);
        slangKey.setBounds(10,0,300,50);
        slangKey.setFont(new Font("MV Boli",Font.PLAIN,30));
        slangDefinition.setBounds(10,100,300,50);
        slangDefinition.setFont(new Font("MV Boli",Font.PLAIN,20));

        card1.setBackground(new Color(0,0,255));
        card2.setBackground(new Color(0,255,0));
        card3.setBackground(new Color(255,0,0));

        card1.add(title);   
        card2.add(scrollPane);
        card3.add(slangKey);
        card3.add(slangDefinition);

        pane.add(card1, BorderLayout.NORTH);
        pane.add(card2, BorderLayout.WEST);
        pane.add(card3, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("SlangDictionary by 20127605");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Program demo = new Program();
        demo.addComponentToPane(frame.getContentPane());
        
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);
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