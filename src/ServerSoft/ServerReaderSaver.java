package ServerSoft;

import AlicePack.Alice;
import Parser.UrodJsonParser;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerReaderSaver {

    private static final String serverFile = "ServerFile.txt";

    public static void save(CopyOnWriteArrayList<Alice> linkedalices) throws FileNotFoundException {
        File sourcefile = new File(getPath());
        if(!sourcefile.exists()) throw new FileNotFoundException("===\nФайл не существует");
        if(!sourcefile.isFile()) throw new FileNotFoundException("===\nЭто не файл");
        if(!sourcefile.canWrite()) throw new FileNotFoundException("===\nФайл не читается");
        try (PrintWriter printer = new PrintWriter(sourcefile)){
            UrodJsonParser urodJsonParser = new UrodJsonParser();
            printer.write(urodJsonParser.getWrittenAlices(linkedalices));
            System.out.println("===\nКоллекция сохранена в файле: " + sourcefile.getAbsolutePath() + "\n===");
            System.out.println("===\nРабота с коллекцией завершена\n===");
        } catch (FileNotFoundException e) {
            System.out.println("===\nСохранение коллекции не удалось\n===");
            e.getStackTrace();
        }
    }


    static private String getPath(){
        String workdirectory = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        return workdirectory + separator + serverFile;
    }

    static public CopyOnWriteArrayList<Alice> justReadFile()throws FileNotFoundException {
        return readFile(getPath());
    }

    static private void checkFile(File file) throws FileNotFoundException {
        if(!file.exists()) throw new FileNotFoundException("===\nФайл не существует");
        if(!file.isFile()) throw new FileNotFoundException("===\nЭто не файл");
        if(!file.canRead()) throw new SecurityException("===\nНевозможно считать файл");
    }


    static private CopyOnWriteArrayList<Alice> readFile(String path) throws FileNotFoundException{
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
            System.out.println("===\nФайл прочитан");
        } catch (IOException e){
            throw new FileNotFoundException("===\nНепредвиденная ошибка чтения файла");
        }
        return line.toString();
    }

    static private CopyOnWriteArrayList<Alice> jsonToLinkedList(String rawJson){
        UrodJsonParser gson = new UrodJsonParser();
        CopyOnWriteArrayList <Alice> linkedalices = gson.getArraysofAliceObjects(rawJson);
        linkedalices.sort(Comparator.naturalOrder());
        System.out.println("===\nКоличество считанных элементов: "+linkedalices.size());
        return linkedalices;
    }

}
