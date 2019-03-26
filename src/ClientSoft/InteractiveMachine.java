package ClientSoft;

import Parser.JsonException;
import Parser.UrodJsonParser;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

public class InteractiveMachine {

private Scanner scaner = null;
//private ManagerOfAlices manager = null;
private boolean needWork = false;

//public InteractiveMachine(){
//    try{
//    scaner = new Scanner(System.in);
//    String path = getFilePath();
//    if(path==null){
//        System.out.println("===\nПуть не указан.\nДальнейшая работа невозможна.\n===");
//    } else {
//        manager = new ManagerOfAlices(new File(path));
//        if (manager.isWorkable()) {
//            beginWork();
//            if (needWork) {
//                help();
//                System.out.println("===\nВведите команду\n===");
//                scanAndExecuteCommands();
//            }
//        }
//    }
//    }catch(NoSuchElementException e){}
//}
//
//    private String getFilePath() {
//        String path = System.getenv("COLLECTION_PATH");
//        System.out.println(System.getProperty("user.dir"));
//        if (path == null) {
//            System.out.println("===\nПуть через переменную окружения COLLECTION_PATH не указан\nНапишите адрес вручную(в консоль)\n===");
//            return scaner.nextLine();
//        } else {
//            return path;
//        }
//    }
//
//    private void beginWork(){
//        System.out.println("===\nВключить интерактивный режим?\nВвведите \"да\" или \"нет\"\n===");
//            switch (scaner.nextLine().trim()){
//            case "да":
//                needWork = true;
//                System.out.println("===\nЧтобы выйти, напишите: \"exit\"");
//                break;
//            case "нет":
//                break;
//            default:
//                System.out.println("===\nВведите \"да\" или \"нет\"\n===");
//                beginWork();
//        }
//    }
//
//    private void help(){
//        System.out.println("===\nСписок доступных команд:\n1. help: показать доступные команды\n" +
//                "2. reorder: отсортировать коллекцию в порядке, обратном нынешнему\n" +
//                "3. add {element}: добавить новый элемент в коллекцию, элемент должен быть введён в формате json\n" +
//                "4. remove_greater {element}: удалить из коллекции все элементы, превышающие заданный, элемент должен быть введён в формате json\n" +
//                "5. show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
//                "6. info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д., элемент должен быть введён в формате json)\n" +
//                "7. remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному, элемент должен быть введён в формате json\n" +
//                "8. remove {element}: удалить элемент из коллекции по его значению, элемент должен быть введён в формате json\n" +
//                "9. exit: закончить работу\nПример элемента: {\n" +
//                "  \"politeness\": \"RUDE\",\n" +
//                "  \"condition\": \"NORMAL\",\n" +
//                "  \"cap\": {\n" +
//                "  \t\"nameOfUser\": \"Алисt\"\n" +
//                "  \t\"fullness\": 122\n" +
//                "  },\n" +
//                "  \"name\": \"Алисt\",\n" +
//                "  \"x\": 10\n" +
//                "  }\n===");
//    }
//
//    private void scanAndExecuteCommands(){
//            while(needWork) {
//                String commands[] = scaner.nextLine().split(" ", 2);
//                switch (commands[0]) {
//                    case "help":
//                        if (commands.length > 1) {
//                            if (commands[1].matches(" +") | commands[1].matches("")) {
//                                System.out.println("===\nПробелы можно было и не добавлять\n===");
//                                help();
//                            } else {
//                                System.out.println("===\nДанная команда не должна содержать аргументов\n===");
//                            }
//                        } else {
//                            help();
//                        }
//                        break;
//                    case "reorder":
//                        if (commands.length > 1) {
//                            if (commands[1].matches(" +") | commands[1].matches("")) {
//                                System.out.println("===\nПробелы можно было и не добавлять\n===");
//                                manager.reorder();
//                            } else {
//                                System.out.println("===\nДанная команда не должна содержать аргументов\n===");
//                            }
//                        } else {
//                            manager.reorder();
//                        }
//                        break;
//                    case "show":
//                        if (commands.length > 1) {
//                            if (commands[1].matches(" +") | commands[1].matches("")) {
//                                System.out.println("===\nПробелы можно было и не добавлять\n===");
//                                manager.show();
//                            } else {
//                                System.out.println("===\nДанная команда не должна содержать аргументов\n===");
//                            }
//                        } else {
//                            manager.show();
//                        }
//                        break;
//                    case "info":
//                        if (commands.length > 1) {
//                            if (commands[1].matches(" +") | commands[1].matches("")) {
//                                System.out.println("===\nПробелы можно было и не добавлять\n===");
//                                manager.info();
//                            } else {
//                                System.out.println("===\nДанная команда не должна содержать аргументов\n===");
//                            }
//                        } else {
//                            manager.info();
//                        }
//                        break;
//                    case "exit":
//                        needWork = false;
//                        if (commands.length > 1) {
//                            if (commands[1].matches(" +") | commands[1].matches("")) {
//                                System.out.println("===\nПробелы можно было и не добавлять\n===");
//                                manager.exit();
//                            } else {
//                                System.out.println("===\nДанная команда не должна содержать аргументов\n===");
//                            }
//                        } else {
//                            manager.exit();
//                        }
//                        break;
//                    case "add":
//                        try {
//                            manager.add(getElement(commands[1]));
//                            System.out.println("===");
//                        } catch (JsonException e) {
//                            System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
//                        }
//                        break;
//                    case "remove_greater":
//                        try {
//                            manager.remove_greater(getElement(commands[1]));
//                            System.out.println("===");
//                        } catch (JsonException e) {
//                            System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
//                        }
//                        break;
//                    case "remove_all":
//                        try {
//                            manager.remove_all(getElement(commands[1]));
//                            System.out.println("===");
//                        } catch (JsonException e) {
//                            System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
//                        }
//                        break;
//                    case "remove":
//                        try {
//                            manager.remove(getElement(commands[1]));
//                            System.out.println("===");
//                        } catch (JsonException e) {
//                            System.out.println("===\nОбнаружена ошибка при парсинге элемента" + e.getMessage());
//                        }
//                        break;
//                    default:
//                        System.out.println("Неизвестная команда");
//                        System.out.println("===");
//                        break;
//                }
//
//            }
//    }
//
//    private Alice getElement(String rawjson){
//        int counterleft = getScobochki1(rawjson,'{');
//        int counterright = getScobochki1(rawjson,'}');
//        while(!(counterleft==counterright)){
//            String s = scaner.nextLine();
//            counterleft += getScobochki1(s,'{');
//            counterright += getScobochki1(s,'}');
//            rawjson = rawjson + s;
//        }
//        rawjson = rawjson.trim();
//        UrodJsonParser simpleJsonParser = new UrodJsonParser();
//            return simpleJsonParser.simpleParseAliceObjects(rawjson);
//    }
//
//    private int getScobochki1(String string,char scobochka){
//    int counter = 0;
//        for(char c : string.toCharArray()){
//            if(c==scobochka) counter++;
//        }
//        return counter;
//    }
}
