package Opgave;

import com.sun.net.httpserver.HttpContext;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.HttpHeaders;
import java.util.Scanner;

public class Webserver {
    static String path =System.getProperty("user.dir"); // henter rod mappen

    public static void main(String[] args) {

        try
        {
            ServerSocket serverSocket= new ServerSocket(1336);
            System.out.println("server er klar");

            Socket socket = serverSocket.accept(); // blokere indtil der kommer en forbindelse
            Scanner scanner = new Scanner(socket.getInputStream());
            String request = scanner.nextLine();
            System.out.println("from client:" + request);

            // vi skal sende til browseren
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // find ud af hvilken protokol tekst som skal sendes FØR man kan sende " hej"
            //dos.writeBytes("GET / HTTP/1.0\r\n");
            dos.writeBytes("HTTP/1,0 200 ok\r\n");

            if(request.equals("GET /about HTTP/1.1")){
             // sende en fil
            File file = new File(path+ "/src/Opgave/file.html");
            FileInputStream fi= new FileInputStream(file);
            int length = (int)file.length();
            byte[] bytes = new byte[length];
            fi.read(bytes); // lægger alle bytes fra filen over i arrayet
            dos.writeBytes("Content-Length:" + length+"\r\n");
            dos.writeBytes("\r\n");
            dos.write(bytes);
            dos.writeBytes("\r\n");
            dos.close();
        }

            else {
            // sende fil til alternativ side / about
            File file1 = new File(path+"/src/Opgave/about.html");
            FileInputStream fi1= new FileInputStream(file1);
            int length1 =(int)file1.length();
            byte[] bytes1 = new byte[length1];
            fi1.read(bytes1);
            dos.writeBytes("Content-Length:" + length1+"\r\n");
            dos.writeBytes("\r\n");
            dos.write(bytes1);
            dos.writeBytes("\r\n");
            dos.close();
        }

            /*
            if(file.isFile()){

            }
*/









        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
/*
// hvis scanner på socket.inputstream == en string så
if(request.equals("GET /about HTTP/1.1")){
        dos.writeBytes("hej fra about"); // skal sige hej fra about
        }
        else{
        dos.writeBytes("hej fra server");
        }

 */