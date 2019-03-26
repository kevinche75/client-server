package ClientSoft;

import AlicePack.*;
import Parser.UrodJsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Reader {

    static public LinkedList<Alice> justReadFile(Scanner scanner)throws FileNotFoundException{
        return readFile(getFilePath(scanner));
    }

    static private String getFilePath(Scanner scanner) {
        String path = System.getenv("COLLECTION_PATH");
        //System.out.println(System.getProperty("user.dir"));
        if (path == null) {
            System.out.println("===\nПуть через переменную окружения COLLECTION_PATH не указан\nНапишите адрес вручную(в консоль)");
            path = scanner.nextLine();
        }
        while(true) {
            System.out.println("===\nВведеный адрес " + path + ".\nЕсли адрес верный введите \"yes\", иначе \"no\" и повторите ввод: ");
            switch (scanner.nextLine()){
                case "yes":
                    return path;
                case "no":
                    System.out.println("===\n");
                    path = scanner.nextLine();
                    break;
                default:
                    System.out.println("===\nНеизвестный ответ");
                    break;
            }
        }
    }

    static private void checkFile(File file) throws FileNotFoundException {
        if(!file.exists()) throw new FileNotFoundException("Файл не существует");
        if(!file.isFile()) throw new FileNotFoundException("Это не файл");
        if(!file.canRead()) throw new SecurityException("Невозможно считать файл");
    }

   static private LinkedList<Alice> readFile(String path) throws FileNotFoundException{
        StringBuilder readablestring = new StringBuilder();
        int stringChar;
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
            throw new FileNotFoundException("Непредвиденная ошибка чтения файла");
        }
        return line.toString();
    }

   static private LinkedList<Alice> jsonToLinkedList(String rawJson){
        UrodJsonParser gson = new UrodJsonParser();
        LinkedList <Alice> linkedalices = gson.getArraysofAliceObjects(rawJson);
        linkedalices.sort(Comparator.naturalOrder());
        System.out.println("===\nЭлементов было считано: "+linkedalices.size());
        return linkedalices;
    }


}
