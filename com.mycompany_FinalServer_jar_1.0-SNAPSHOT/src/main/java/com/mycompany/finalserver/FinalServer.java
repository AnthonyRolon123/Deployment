package com.mycompany.finalserver;

import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class FinalServer {

    public static void main(String[] args) throws IOException 
    {
        ServerSocket server = null;//creating a server socket
        boolean shutdown = false;//shutdown for while loop
        
        try 
        {
            server = new ServerSocket(1236);//try that port
            System.out.println("Port Bound");
        } catch (IOException e) 
        {
            e.printStackTrace();
            System.exit(-1);
        }
        
        while(!shutdown)
        {
            Socket client = null;//creating sockets and input streams
            InputStream input = null;
            OutputStream output = null;
            
            try 
            {
                client = server.accept();//accepting client connection
                input = client.getInputStream();//IO streams
                output = client.getOutputStream();
                
                int n = input.read();//length of bytes
                byte[] data = new byte[n];//byte array with n length
                input.read(data);//reading the byte array data
                String clientInput = new String(data,StandardCharsets.UTF_8);//changing bytes to strings
                
                if(clientInput.equalsIgnoreCase("shutdown"))//shutdown change
                {
                    System.out.println("Shutting down....");
                    shutdown = true;
                    return;
                }
                
                int userInput = Integer.parseInt(clientInput);//changing string to int for lines
                String response = "";
                
                if(checkIfPrime(userInput))
                {
                    response = userInput + " is prime";
                }
                else
                {
                    response = userInput + " is not prime";
                }
                
                output.write(response.length());//output line to client
                output.write(response.getBytes());
                
                client.close();//close client
                
            } catch (IOException e) 
            {
                e.printStackTrace();
                continue;
            }
        }
    }
    
    public static boolean checkIfPrime(int n)
    {
        if (n <= 1)
        {
            return false;
        }
  
        for (int i = 2; i < n; i++)
        {
            if (n % i == 0)
            {
                return false;
            }
        }
  
        return true; 
    }
}
