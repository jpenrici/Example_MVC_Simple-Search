package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Util {

    public static final String DELIM = ";";
    private static final boolean ISWIN = isWin();

    // lê arquivo tipo texto e retorna um array de linhas
    public static ArrayList<String> loadFile(String path) {
        ArrayList<String> array = new ArrayList<>();
        try {
            FileReader input = new FileReader(path);
            BufferedReader buffer = new BufferedReader(input);
            String line = buffer.readLine();
            while (line != null) {
                if (ISWIN) {
                    line = utfToIso(line);
                }
                array.add(line);
                line = buffer.readLine();
            }
            input.close();
            System.out.println(path + " ... OK!");
        } catch (IOException ex) {
            System.out.println(path + " ... error!");
        }
        return array;
    }

    // converte utf-8 em ISO-8959-1
    public static String utfToIso(String str) {
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");

        ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());
        // decodifica UTF-8
        CharBuffer data = utf8charset.decode(inputBuffer);
        // codifica ISO-8559-1
        ByteBuffer outputBuffer = iso88591charset.encode(data);
        byte[] outputData = outputBuffer.array();

        return new String(outputData);
    }

    // checa o sistema operacional
    public static boolean isWin() {
        String so = (String) System.getProperties().get("os.name");
        System.out.println("S.O.: " + so);
        return !so.equalsIgnoreCase("Linux");
    }
}
