package com.mycompany.finalclient;

import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FinalClient 
{
    public static JTextArea resultArea = new JTextArea();
    public static JTextField userNum = new JTextField();
    public static JButton getLines = new JButton("Check Number");
    public static JFrame frame = new JFrame();
    public static String numOfLines = "";
        
    public static void main(String[] args)
    {
        try {
            Socket connection = new Socket("127.0.0.1",1236);//try connecting to server
            
            InputStream input = connection.getInputStream();//IO streams
            OutputStream output = connection.getOutputStream();
            
            Frame(output);//making of JFrame GUI
            
            int n = input.read();
            byte[] data = new byte[n];
            input.read(data);
            
            String serverResponse = new String(data,StandardCharsets.UTF_8);
            
            resultArea.setText(serverResponse);//making the TextArea in JFrame the lines
            
            if(!connection.isClosed())
            {
                connection.close();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void Frame(OutputStream output)//creation of frame
    {
        frame.setTitle("Prime Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3,2));
        frame.add(new JLabel("Number to Check:"));
        frame.add(userNum);
        frame.add(new JLabel("Result:"));
        frame.add(resultArea);
        frame.add(new JLabel(""));
        frame.add(getLines);
        int frameWidth = 800;
        int frameHeight = 600;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int) screenSize.getWidth()-frameWidth,0,frameWidth,frameHeight);
        frame.setVisible(true);
        
        getLines.addActionListener(new ActionListener() { //action listener to get button press and send output
            public void actionPerformed(ActionEvent e) { 
                String nums = action(numOfLines);
                
                try {
                    output.write(nums.length());//outputting the line request to server
                    output.write(nums.getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                } 
            } );
    }
    
    public static String action(String numOfLines)//getting num of lines to a variable
    {
        numOfLines = userNum.getText();
        return numOfLines;
    }
}
