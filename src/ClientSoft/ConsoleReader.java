package ClientSoft;

import Messages.ServerMessage;
import Parser.JsonException;
import AlicePack.*;
import Parser.UrodJsonParser;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConsoleReader {

    private int DEFAULT_PORT;
    private ClientSenderReceiver sender;
    private Boolean workable = false;
    private Scanner scaner;
    private DatagramChannel channel;
    private SocketAddress adress;
    private int time = 0;
    private boolean work = true;

public ConsoleReader() throws IOException {
    scaner = new Scanner(System.in);
    adress = new InetSocketAddress("localhost", DEFAULT_PORT);
    channel = DatagramChannel.open();
}

private void checkConnection() throws InterruptedException {
    System.out.println("===\nДоступные команды: " +
            "\n1. help: показать доступные комманды" +
            "\n2. connect: попытка соединения с сервером" +
            "\n3. exit: выйти из приложения");
    while(true){
        switch (scaner.nextLine()){
            case "help":
                System.out.println("===\n1. help: показать доступные комманды" +
                        "\n2. connect: попытка соединения с сервером" +
                        "\n3. exit: выйти из приложения");
                break;
            case "connect":
                System.out.println("===\nПопытка соединения...");
                new ClientSenderReceiver(channel, adress, "test", null).start();
                while(time<60000&&!workable) {
                    Thread.sleep(1000);
                    System.out.println("Ждём...");
                    time += 1000;
                }
                if(workable){
                    System.out.println("===\nСоединение установлено");
                } else {
                    System.out.println("===\nОтвет от сервера не получен. \nВозможно ответ придёт позже. \nВы можете повторить попытку соединения");
                }
            break;
            case "exit":
                work = false;
                return;
            default:
                System.out.println("===\nНеизвестная команда");
                break;
        }
    }
}

private void shootDown(){
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            new ClientSenderReceiver<Alice>(channel, adress,"exit", null).start();
        }));
}

public void work() throws InterruptedException {
    System.out.println("Hello");
    shootDown();
    while(work){
        if(workable) {
            while (scanAndExecuteCommands()) ;
        }else{
            checkConnection();
        }
    }
}

private void load(){
    try {
        new ClientSenderReceiver<CopyOnWriteArrayList<Alice>>(channel, adress, "load", Reader.justReadFile(scaner));
    } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
    }
}


    private boolean scanAndExecuteCommands() {
            String commands[] = scaner.nextLine().trim().split(" ", 2);
            switch (commands[0].trim()) {
                case "show":
                case "info":
                case "reorder":
                    if (commands.length > 1) {
                        System.out.println("===\nДанная команда не должна содержать аргументов\n===");
                    return true;
                    }
                    new ClientSenderReceiver(channel, adress, commands[0], null).start();
                    return true;
                case "add":
                case "remove_greater":
                case "remove_all":
                case "remove":
                    try {
                        new ClientSenderReceiver<Alice>(channel, adress, commands[0], getElement(commands[1])).start();
                    } catch (JsonException e) {
                        System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
                    }
                    return true;
                case "exit":
                    new ClientSenderReceiver(channel, adress, commands[0], null).start();
                    workable = false;
                    return false;
                case "load":
                    load();
                    return true;
                case "help":
                    help();
                    return true;
                default:
                    System.out.println("===\nНеизвестная команда");
                    return true;
            }
    }

    private void help(){
        System.out.println("===\nСписок доступных команд:\n" +
                "1. help: показать доступные команды\n" +
                "2. load: загрузить коллекцию на сервер\n" +
                "3. reorder: отсортировать коллекцию в порядке, обратном нынешнему\n" +
                "4. add {element}: добавить новый элемент в коллекцию, элемент должен быть введён в формате json\n" +
                "5. remove_greater {element}: удалить из коллекции все элементы, превышающие заданный, элемент должен быть введён в формате json\n" +
                "6. show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "7. info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д., элемент должен быть введён в формате json)\n" +
                "8. remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному, элемент должен быть введён в формате json\n" +
                "9. remove {element}: удалить элемент из коллекции по его значению, элемент должен быть введён в формате json\n" +
                "10. exit: закончить работу с сервером\n" +
                "Пример элемента: {\n" +
                "  \"politeness\": \"RUDE\",\n" +
                "  \"condition\": \"NORMAL\",\n" +
                "  \"cap\": {\n" +
                "  \t\"nameOfUser\": \"Алисt\"\n" +
                "  \t\"fullness\": 122\n" +
                "  },\n" +
                "  \"name\": \"Алисt\",\n" +
                "  \"x\": 10\n" +
                "  }\n===");
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
