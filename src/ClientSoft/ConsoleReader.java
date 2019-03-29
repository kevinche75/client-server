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
    private Boolean workable = false;
    private Scanner scaner;

public ConsoleReader(int port){
    this.port = port;
    scaner = new Scanner(System.in);
}


public void testwork()throws IOException {
    sender = new ClientSenderReceiver<>(2000, "здарова", new Alice());
    System.out.println("Hello");
    new Receiver(sender.getChannel(), workable).start();
    //new ClientSenderReceiver<>(2000, "здарова", new Alice()).start();
}

public void checkConnection() throws IOException, ClassNotFoundException {
    sender = new ClientSenderReceiver(port);
//    while(true){
//        if(sender.checkConnection()){
//            System.out.println("===\nСоединение установлено");
//            break;
//        } else {
//            boolean flag = true;
//            while (flag) {
//                System.out.println("===\nСоединение не установлено");
//                System.out.println("===\nПовторить попытку? Введите \"yes\" или \"no\":");
//                switch (scaner.nextLine()) {
//                    case "yes":
//                        flag = false;
//                        break;
//                    case "no":
//                        flag = false;
//                        workable = false;
//                    default:
//                        System.out.println("===\nНеизвестный ответ");
//                        break;
//                }
//            }
//        }
//    }
    System.out.println("===\nДоступные команды: " +
            "\n1. help: показать доступные комманды" +
            "\n2. connect: попытка соединения с сервером" +
            "\n3. exit: выйти из приложения");
    while(true){
        switch (scaner.nextLine()){
            case "help":
                System.out.println("\n===\n1. help: показать доступные комманды" +
                        "\n2. connect: попытка соединения с сервером" +
                        "\n3. exit: выйти из приложения");
                break;
            case "connect":
                System.out.println("===\nПопытка соединения...");
                if(sender.checkConnection()) {
                    workable=true;
                    return;
                } else {
                    workable = false;
                }
            break;
            case "exit":
                return;
            default:
                System.out.println("===\nНеизвестная команда");
                break;
        }
    }
}

public void work() throws IOException, ClassNotFoundException {
    System.out.println("Hello");
    checkConnection();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
            new ClientSenderReceiver<>(2000, "exit", null).start();
        } catch (IOException e) {
            System.out.println("===\nВсё плохо");
        }
    }));
    if(workable) {
        new Receiver(sender.getChannel(), workable).start();
        loadOrImport();
    }
    while(workable){
        scanAndExecuteCommands();
    }
}

private void loadOrImport() throws IOException {
    while (true) {
        System.out.println("===\nИспользовать коллекцию на сервере или загрузить свою? Введите \"yes\" или \"no\":");
        switch (scaner.nextLine()) {
            case "yes":
                try {
                    new ClientSenderReceiver<>(2000, "import", Reader.justReadFile(scaner)).start();
                }catch (FileNotFoundException e){
                    System.out.println(e.getMessage());
                }
            case "no":
                return;
            default:
                System.out.println("===\nНеизвестный ответ");
                break;
        }
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
                    new ClientSenderReceiver<>(2000, commands[0], null).start();
                    break;
                case "add":
                case "remove_greater":
                case "remove_all":
                case "remove":
                    try {
                        new ClientSenderReceiver<>(2000, commands[0], getElement(commands[1])).start();
                    } catch (JsonException e) {
                        System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
                    }
                    break;
                case "exit":
                    new ClientSenderReceiver<>(2000, commands[0], null).start();
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
