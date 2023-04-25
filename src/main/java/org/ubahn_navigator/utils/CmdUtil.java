package org.ubahn_navigator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//Die Klasse "CmdUtil" bietet die Arbeit mit Cmd.
public class CmdUtil {
    //Liniennamen aus Dateien extrahieren
    public static String[] extractFileNames(){
        //Das Objekt der Klasse ProcessBuilder wird erstellt.
        ProcessBuilder builder = new ProcessBuilder();

        //Die Befehle werden an Methode "command" des Objekts "builder" übergeben;
        builder.command("cmd.exe", "/c", "dir /b /a-d Lines");

        //Die Namen der Dateien werden gelesen.
        String fileNames = "";
        try {
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String fileName;
            while ((fileName = bufferedReader.readLine())!= null){
                fileNames += fileName + "\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Der Array der Namen der Dateien werden zurückgegeben;
        return fileNames.split("\n");
    }
}