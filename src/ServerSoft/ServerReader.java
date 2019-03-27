package ServerSoft;

import AlicePack.Alice;
import Parser.UrodJsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerReader {

    private static final String serverFile = "ServerFile.txt";

    static private String getPath(){
        String workdirectory = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        return workdirectory + separator + serverFile;
    }

    static public CopyOnWriteArrayList<Alice> justReadFile()throws FileNotFoundException {
        return toCopyOnWriteArray(readFile(getPath()));
    }

    static private void checkFile(File file) throws FileNotFoundException {
        if(!file.exists()) throw new FileNotFoundException("===\nФайл не существует");
        if(!file.isFile()) throw new FileNotFoundException("===\nЭто не файл");
        if(!file.canRead()) throw new SecurityException("===\nНевозможно считать файл");
    }

    static private LinkedList<Alice> readFile(String path) throws FileNotFoundException{
        File file = new File(path);
        checkFile(file);
        return jsonToLinkedList(readJsonFromFile(file));
    }

    static private String readJsonFromFile(File file) throws FileNotFoundException{
        StringBuilder line = new StringBuilder();
        int stringChar;
        try(FileReader reader = new FileReader(file)){
            while((stringChar=reader.read())!=-1){
                line.append((char) stringChar);
            }
            System.out.println("===\nФайл считан");
        } catch (IOException e){
            throw new FileNotFoundException("===\nНепредвиденная ошибка чтения файла");
        }
        return line.toString();
    }

    static private CopyOnWriteArrayList<Alice> toCopyOnWriteArray(LinkedList<Alice> collection){
        CopyOnWriteArrayList<Alice> array = new CopyOnWriteArrayList<>();
        array.addAll(collection);
        return array;
    }

    static private LinkedList<Alice> jsonToLinkedList(String rawJson){
        UrodJsonParser gson = new UrodJsonParser();
        LinkedList <Alice> linkedalices = gson.getArraysofAliceObjects(rawJson);
        linkedalices.sort(Comparator.naturalOrder());
        System.out.println("===\nЭлементов было считано: "+linkedalices.size());
        return linkedalices;
    }

}
