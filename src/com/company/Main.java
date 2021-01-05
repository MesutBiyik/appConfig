package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.*;
import java.awt.Desktop;

public class Main {

    public static void main(String[] args) {

        //File file = new File("C:\\Users\\HISBRO\\Desktop\\wrapper.conf");
        File wrapperFile = new File("C:\\Program Files (x86)\\OrderMate\\Apache Group\\htdocs\\Webstart\\wrapper\\conf\\wrapper.conf");
        //WaiterMate JNLP file
        File waitermateJNLP = new File("C:\\Program Files (x86)\\OrderMate\\Apache Group\\htdocs\\Webstart\\wrapper\\waitermate.jnlp");


        //Find the memory used in wrapper
        List<String> wrapperMemory = readWrapperConfigFile(wrapperFile);
        //Find the memory in the WaiterMate JNLP
        List<String> waitermateMemory = readJNLPFile(waitermateJNLP);
        //test comment
        setupGUI(wrapperMemory);

    }

    //Configure GUI
    private static void setupGUI(List <String> data) {

        JPanel thePanel = new JPanel();
        JFrame frame = new JFrame();

        //Configure Open button
        JButton openDir = new JButton("Open Dir");

        openDir.setSize(20,20);

        //On button click open file
        openDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("C:\\Program Files (x86)\\OrderMate\\Apache Group\\htdocs\\Webstart\\wrapper\\conf\\wrapper.conf"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);

        frame.setTitle("Show Wrapper memory");


        String memory = data.toString();
        //String memory1 = data1.toString();

        JLabel servermateMemory = new JLabel(memory);
        //JLabel waitermateMemory = new JLabel(memory1);

        servermateMemory.setLocation(50,100);
        thePanel.add(servermateMemory);
        //thePanel.add(waitermateMemory);
        thePanel.add(openDir);
        frame.add(thePanel);



    }


    //Read wrapper config file
    private static List<String> readWrapperConfigFile(File file) {
        List<String> allLines = null;
        String findWord;
        List<String> FindMemory = null;

        String filePath = String.valueOf(file);

        try {

            //JDK8 version
            //allLines = Files.readAllLines(Paths.get(String.valueOf(file)));

            //JDK7U17 version to obtain for path
            allLines = Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.UTF_8);


            //Find how much memory is currently allocated
            FindMemory = new ArrayList<String>();
            for (String string : allLines) {
                //Find name using wild card
                if (string.matches(".*(wrapper.java.maxmemory=).*.*.*.*")) {
                    FindMemory.add(string);
                }
            }
            //System.out.println(FindMemory);

            return FindMemory;


            /*// Print out all lines
            for (String line :allLines){

                System.out.println(line);
                        }*/


        } catch (IOException e) {
            e.printStackTrace();
            fileNotFoundGUI();

        }

        return FindMemory;
    }

    //Read JNLP file for Max heap memory
    private static  List<String> readJNLPFile(File file) {

        List<String> allLines = null;
        String findWord;
        List<String> FindMemory = null;
        String filePath = String.valueOf(file);

        try {

            //JDK8 version
            //allLines = Files.readAllLines(Paths.get(String.valueOf(file)));

            //JDK7U17 version to obtain for path
            allLines = Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.UTF_8);


            //Find how much memory is currently allocated
            FindMemory = new ArrayList<String>();
            for (String string : allLines) {
                //Find name using wild card
                if (string.matches(".*(max-heap-size=).*.*.*.*")) {
                    FindMemory.add(string);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

        //Adjust config file from app (Work in progress might not be able to due to Comments being removed.
    public static void config(File file){
        try {

            Object test = "wrapper.java.maxmemory";

            Reader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            Properties properties = new Properties();
            properties.load(reader);
            for (Object o : properties.keySet()){
                //print all properties of config file
                //System.out.println(o.toString());

                //Find wraper config line (Property line)
                /*if(properties.keySet().contains("wrapper.java.maxmemory")){
                    System.out.println("found it");

                }else {
                    System.out.println("Could not find it");
                }*/
            }

            /*//Simple overwrite of all properties without comments
            if(properties.keySet().contains("wrapper.java.maxmemory")){
                System.out.println("found it");
                properties.setProperty("wrapper.java.maxmemory", "120");
                System.out.println(properties.getProperty("wrapper.java.maxmemory"));

                //write the changed property
                //properties.store( new FileOutputStream(file), null);
            }else {
                System.out.println("Could not find it");
            }*/

            if(properties.keySet().contains("#wrapper.java.maxmemory")){
                System.out.println("Found it commented");
            }else if(properties.keySet().contains("wrapper.java.maxmemory")){
                System.out.println("Found it uncommented");
            }


            System.out.println("---------");
            for (Object o :properties.values()){
                //System.out.println(o.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //File not found error GUI
    public static void fileNotFoundGUI(){
        JFrame frame = new JFrame();
        frame.setVisible(true);

        JOptionPane option = new JOptionPane();

        option.showMessageDialog(frame, "File not found :(");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}








