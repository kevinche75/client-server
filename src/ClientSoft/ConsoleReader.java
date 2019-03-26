package ClientSoft;

import Parser.JsonException;
import AlicePack.*;
import Parser.UrodJsonParser;

import java.util.Scanner;

public class ConsoleReader {

    private int port;
    private ClientSenderReceiver sender;

public ConsoleReader(int port){
    this.port = port;
}



    private void scanAndExecuteCommands(Scanner scaner){
            String commands[] = scaner.nextLine().trim().split(" ", 2);
            switch (commands[0].trim()) {
                case "help":
                case "show":
                case "info":
                case "reorder":
                    if (commands.length > 1) {
                        System.out.println("===\nДанная команда не должна содержать аргументов\n===");
                    return;
                    }

                    break;
                case "add":
                case "remove_greater":
                case "remove_all":
                case "remove":
                    try {

                        System.out.println("===");
                    } catch (JsonException e) {
                        System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
                    }
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("===\nНеизвестная команда");
                    break;
            }
    }

    private Alice getElement(String rawjson, Scanner scaner){
        int counterleft = getScobochki1(rawjson,'{');
        int counterright = getScobochki1(rawjson,'}');
        StringBuilder rawjsonBuilder = new StringBuilder(rawjson);
        while(!(counterleft==counterright)){
            String s = scaner.nextLine();
            counterleft += getScobochki1(s,'{');
            counterright += getScobochki1(s,'}');
            rawjsonBuilder.append(s);
        }
        rawjson = rawjsonBuilder.toString();
        rawjson = rawjson.trim();
        UrodJsonParser simpleJsonParser = new UrodJsonParser();
        return simpleJsonParser.simpleParseAliceObjects(rawjson);
    }

    private int getScobochki1(String string,char scobochka){
        int counter = 0;
        for(char c : string.toCharArray()){
            if(c==scobochka) counter++;
        }
        return counter;
    }
}
