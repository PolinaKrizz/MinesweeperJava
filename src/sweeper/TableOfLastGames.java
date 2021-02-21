package sweeper;

import org.lwjgl.Sys;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class TableOfLastGames {
    private static String[] table;
    private int i;
    private static int size = 5;

    protected class TextFieldTest extends JFrame {
        JTextField name;
        String nickname = new String();
        protected TextFieldTest(String lvl, int time) {
            super("Enter your name");
            name = new JTextField(30);
            name.setToolTipText("Name");

            name.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nickname = name.getText();
                    i++;
                    table[i % 5] = nickname + "\t" + lvl + "\t" + Integer.toString(time) + " ";
                    save("table.txt");
                    dispose();
                }
            });

            JPanel contents = new JPanel(new FlowLayout(FlowLayout.LEFT));
            contents.add(name);
            setContentPane(contents);
            setSize(300, 100);
            setVisible(true);
        }
    }


    public TableOfLastGames(){
        table = new String[size];
        for (int t = 0; t < size; t++) {
            table[t]="---"+"\t"+"---";
        }
        i = size - 1 + load("table.txt") ;
    }

    public void addTime(String lvl, int time){
        TextFieldTest name_ = new TextFieldTest(lvl, time);
    }


    public String printTime(int i){
        return table[i];
    }

    static void save(String filename){
        try {
            FileWriter writer = new FileWriter(filename, false);
            for (int i = 0; i < size; i++){
                writer.write(table[i]);
                writer.append('\n');
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int load(String filename){
        Scanner sc = null;
        int i = 0;
        try {
            sc = new Scanner(new File(filename));

            while(sc.hasNextLine() ){
                String buff =  sc.nextLine();
                if (buff.equals("---"+"\t"+"---"))
                    break;
                table[i] = buff;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }

}
