package ServerSoft;

import AlicePack.Alice;
import Parser.UrodJsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSaver {

    private static LinkedList<Alice> toLinkedList(CopyOnWriteArrayList<Alice> collection){
        LinkedList<Alice> linkedList = new LinkedList<>();
        linkedList.addAll(collection);
        return linkedList;
    }

    public static void save(LinkedList<Alice> linkedalices, File sourcefile){
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
}
