package essex.ac.uk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class MainWindow extends JFrame {
    //Constructor that creates the main frame that will display the list and have the options to change the list
    //in the form of buttons
    MainWindow() {
        //Initiation of all buttons
        JButton Add, Specified, Reset ,Remove, Clear;

        //Creates a new button Add to carry put task a by creating a new object btn
        Add = new JButton("Add words");
        Add.addActionListener(
                (ActionEvent e) -> {
                    AddBtn btn = new AddBtn();
                    btn.add(btn.GetTxt());
                }
        );

        //Creates a new button Specified to carry put task b by creating a new object btn
        Specified = new JButton("Specified words");
        Specified.addActionListener(
                (ActionEvent e) -> {
                    SpecifiedBtn btn = new SpecifiedBtn();
                    btn.Specified(btn.GetTxt());
                }
        );

        //Creates a new button Reset which will display the whole defined list
        Reset = new JButton("Reset");
        Reset.addActionListener(
                (ActionEvent e) -> {
                    TxtAreaSet txt = new TxtAreaSet();
                    txt.DisplayTxt();
                    TxtAreaSet.l.setForeground(txt.RGB());

                    JOptionPane.showMessageDialog(null, "Full list displayed");
                }
        );

        //Creates a new button Remove to carry put task c by creating a new object btn
        Remove = new JButton("Remove");
        Remove.addActionListener(
                (ActionEvent e) -> {
                    RemoveBtn btn = new RemoveBtn();
                    btn.Remove(btn.GetTxt());
                }
        );

        //Creates a new button Clear to carry put task d by creating a new object btn
        Clear = new JButton("Clear list");
        Clear.addActionListener(
                (ActionEvent e) -> {
                    ClearBtn btn = new ClearBtn();
                    btn.Clear();
                }
        );

        //Adding all the buttons the JFrame
        JPanel butJPanel = new JPanel();
        butJPanel.add(Add);
        butJPanel.add(Specified);
        butJPanel.add(Reset);
        butJPanel.add(Remove);
        butJPanel.add(Clear);
        add(butJPanel, BorderLayout.NORTH);

        //Adding the JTextField to the JFrame
        TxtAreaSet.inp = new JTextField("", 45);
        JPanel inpJPanel = new JPanel();
        inpJPanel.add(TxtAreaSet.inp);
        add(inpJPanel, BorderLayout.SOUTH);

        //Sets up the RGB text fields and adds them to the JFrame
        TxtAreaSet.Red = new JTextField("0", 5);
        TxtAreaSet.Green = new JTextField("0", 5);
        TxtAreaSet.Blue = new JTextField("0", 5);

        JPanel R = new JPanel();
        JPanel G = new JPanel();
        JPanel B = new JPanel();
        JPanel RGB = new JPanel();

        JLabel Rlabel = new JLabel("Red: ");
        R.add(TxtAreaSet.Red);
        JLabel Glabel = new JLabel("Green: ");
        G.add(TxtAreaSet.Green);
        JLabel Blabel = new JLabel("Blue: ");
        B.add(TxtAreaSet.Blue);

        RGB.setLayout(new BoxLayout(RGB, BoxLayout.Y_AXIS));
        RGB.add(Rlabel);
        RGB.add(R);
        RGB.add(Box.createRigidArea(new Dimension(0,1)));
        RGB.add(Glabel);
        RGB.add(G);
        RGB.add(Box.createRigidArea(new Dimension(0,1)));
        RGB.add(Blabel);
        RGB.add(B);
        add(RGB, BorderLayout.EAST);

        //Sets up the main text area and adds this to the JFrame
        TxtAreaSet.l = new JTextArea("", 24, 45);
        TxtAreaSet.l.setEditable(false);
        JScrollPane txtList = new JScrollPane(TxtAreaSet.l, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        TxtAreaSet txt = new TxtAreaSet();
        txt.DisplayTxt();

        JPanel txtJPanel = new JPanel();
        txtJPanel.add(txtList);
        add(txtJPanel, BorderLayout.CENTER);

        //Formats the main JFrame
        setSize(700, 500);
        setLocation(400, 150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class TxtAreaSet {
    //The list of words stored in the text file given will be placed in this ArrayList
    static ArrayList<String> list = new ArrayList<>();
    //Initiation for the main TextArea
    static JTextArea l;
    //Initiation for the main user input
    static JTextField inp;

    //Initiation of the RGB text fields
    static JTextField Red, Green, Blue;

    //DisplayTxt resets the text field and text area and display the whole defined list stored in list
    void DisplayTxt() {
        l.setText(null);
        inp.setText(null);
        list.clear();

        try {
            File x = new File("listOfWords.txt");
            Scanner sc = new Scanner(x);
            while(sc.hasNext()) {
                list.add(sc.next());
            }
            sc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }

        for (String x : list){
            l.append(x + "\n");
        }
    }

    //This gets the user input from the text field inp
    String GetTxt() {
        return inp.getText();
    }

    //This gets the inputs in the RGB fields and makes sure that they fit within the RGB boundaries,
    //returning the color if the inputted color is valid
    Color RGB() {
        Color txtRGB;
        try {
            int r = Integer.parseInt(Red.getText());
            int g = Integer.parseInt(Green.getText());
            int b = Integer.parseInt(Blue.getText());

            if (((r < 0) || (r > 255) ||
                    (g < 0) || (g > 255) ||
                    (b < 0) || (b > 255))){
                txtRGB = new Color(0,0,0);
                JOptionPane.showMessageDialog(null,
                        "Numbers in the RGB fields are out of bounds");
            } else {
                txtRGB = new Color(r, g, b);

            }
        } catch (Exception e) {
            txtRGB = new Color(0,0,0);
            JOptionPane.showMessageDialog(null, "Only enter numbers in the RGB fields");
        }

        return txtRGB;
    }
}

class AddBtn extends TxtAreaSet {
    //a) Gets the input and first of all makes sure that there is a letter present and the first letter is a character
    //After stores this new letter in the text file, and at the end adding the new word to the list
    void add(String str) {
        if (!str.equals("")) {
            if (!(Character.isLetter(str.charAt(0)))) {
                JOptionPane.showMessageDialog(null,
                        "The string ‘" + str + "’ was not added to the list as it is not a valid word.");
            } else {
                try {
                    FileWriter Wr = new FileWriter("listOfWords.txt", true);
                    PrintWriter Pwr = new PrintWriter(Wr);
                    Pwr.println(str);
                    Pwr.close();
                    JOptionPane.showMessageDialog(null,
                            "Word '" + str + "' has been added to the list");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e);
                }

                DisplayTxt();
                l.setForeground(RGB());
            }
        }
    }
}

class SpecifiedBtn extends TxtAreaSet {
    //b) Gets the input, makes sure the input is a single character, and displays all the words
    // beginning with that character
    void Specified(String str) {
        if (!str.equals("")) {
            if (str.length() == 1 && !str.equals(" ")){
                l.setText(null);

                for (String x : list){
                    if (x.toUpperCase().endsWith(str.toUpperCase())){
                        l.append(x+"\n");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a single character");
            }
        }

        l.setForeground(RGB());
    }
}

class RemoveBtn extends TxtAreaSet {
    //c) Gets the input and compares it to all the words in the list. If they match the word in the list is removed
    // from the text file, and the new list is displayed in the text area
    void Remove(String str) {
        if (!str.equals("")) {
            str = str.toLowerCase();
            int check = list.size();

            for (int x=list.size()-1; x>=0; x--) {
                String letter = list.get(x).toLowerCase();
                if (str.equals(letter)){
                    list.remove(x);
                }
            }

            if (check > list.size()) {
                try {
                    FileWriter Wr = new FileWriter("listOfWords.txt");
                    PrintWriter Pwr = new PrintWriter(Wr);
                    for (String x : list) {
                        Pwr.println(x);
                    }
                    Pwr.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e);
                }

                JOptionPane.showMessageDialog(null, "Word(s) '" + str + "' has been removed");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Word(s) '" + str + "' does not exist so isn't removed");
            }

            DisplayTxt();
        }

        l.setForeground(RGB());
    }
}

class ClearBtn extends TxtAreaSet {
    //d) Removes all stored words from the text documents and displays this on the text area
    void Clear() {
        try {
            FileWriter Wr = new FileWriter("listOfWords.txt");
            PrintWriter Pwr = new PrintWriter(Wr);
            Pwr.println("");
            Pwr.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }

        DisplayTxt();

        l.setForeground(RGB());

        JOptionPane.showMessageDialog(null, "List cleared");
    }
}

public class CE203_2019_Ass1 {
    public static void main(String[] args) {
        //Creates a new instance of MainWindow which will display the Main window
        new MainWindow();
    }
}