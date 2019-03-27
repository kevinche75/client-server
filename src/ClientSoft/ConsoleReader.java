package ClientSoft;

import Messages.ServerMessage;
import Parser.JsonException;
import AlicePack.*;
import Parser.UrodJsonParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleReader {

    private int port;
    private ClientSenderReceiver sender;
    private boolean workable = true;
    private Scanner scaner;

public ConsoleReader(int port){
    this.port = port;
    scaner = new Scanner(System.in);
}

public void work() throws IOException, ClassNotFoundException {
    sender = new ClientSenderReceiver(port);
    System.out.println("Hello");
    while(true){
        if(sender.checkConnection()){
            System.out.println("===\nСоединение установлено");
            break;
        } else {
            boolean flag = true;
            while (flag) {
                System.out.println("===\nПовторить попытку? Введите \"yes\" или \"no\":");
                switch (scaner.nextLine()) {
                    case "yes":
                        flag = false;
                        break;
                    case "no":
                        flag = false;
                        workable = false;
                    default:
                        break;
                }
            }
        }
    }
    while(loadOrImport());
    while(workable){
        scanAndExecuteCommands();
    }
}

private boolean loadOrImport() throws IOException, ClassNotFoundException {
    System.out.println("===\nИспользовать коллекцию на сервере или загрузить свою? Введите \"yes\" или \"no\":");
    while (true) {
        switch (scaner.nextLine()) {
            case "yes":
                try {
                    while (checkMessage(sender.sendCollection(Reader.justReadFile(scaner)))) ;
                    return false;
                }catch (FileNotFoundException e){
                    System.out.println(e.getMessage());
                    return true;
                }
            case "no":
                while(!sender.checkConnection()){
                    System.out.println("===\nСервер недоступен");
                    boolean flag = true;
                    while (flag) {
                        System.out.println("===\nПовторить попытку? Введите \"yes\" или \"no\":");
                        switch (scaner.nextLine()) {
                            case "yes":
                                flag = false;
                               break;
                            case "no":
                                return true;
                            default:
                                break;
                        }
                    }
                }
            default:
                break;
        }
    }
}

private boolean checkMessage(ServerMessage message){
    switch (message.getSpecialmessage()){
        case TIMEOUT:
            System.out.println(message.getMessage());
            System.out.println("===\nПовторить попытку? Введите \"yes\" или \"no\":");
            while (true) {
                switch (scaner.nextLine()) {
                    case "yes":
                        return true;
                    case "no":
                        return false;
                    default:
                        break;
                }
            }
        case DONE:
            System.out.println(message.getMessage());
            return false;
        default:
            return false;
    }
}

    private void scanAndExecuteCommands() throws IOException, ClassNotFoundException {
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
                        while(checkMessage(sender.doRequest(commands[0], null)));
                    break;
                case "add":
                case "remove_greater":
                case "remove_all":
                case "remove":
                    try {
                        while(checkMessage(sender.doRequest(commands[0],getElement(commands[1]))));
                        System.out.println("===");
                    } catch (JsonException e) {
                        System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
                    }
                    break;
                case "exit": //FIXME
                    checkMessage(sender.doRequest(commands[0],null));
                    workable = false;
                    break;
                default:
                    System.out.println("===\nНеизвестная команда");
                    break;
            }
    }

    private Alice getElement(String rawjson){
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
