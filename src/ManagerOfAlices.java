/**
 * @author Dimasik
 * This class automaticly reads information from file, remakes it in linkedlist and has functions for managment.
 * The path of file contains in environment variable with name "COLLECTION_PATH".
*/

import AlicePack.Alice;
import Parser.UrodJsonParser;

import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Date;
import java.io.File;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class ManagerOfAlices {

    private boolean workable = false;
    private LinkedList<Alice> linkedalices;
    private Date date = null;
    private File sourcefile = null;
    private boolean reverse = true;

    protected ManagerOfAlices(File rawfile){

        readFile(rawfile);
        sourcefile = rawfile;
    }

    protected boolean isWorkable() {
        return workable;
    }

    protected void exit(){workable = false;}

    protected String readJsonFromFile(File file){
        String line = "";
        int stringChar;
        try(FileReader reader = new FileReader(file)){
            while((stringChar=reader.read())!=-1){
                line=line+(char)stringChar;
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                saveAndExit();
            }));
            System.out.println("===\nФайл считан\n===");
        } catch (IOException e){
            System.out.println("===\nНепредвиденная ошибка чтения файла.\nДальнейшая работа невозможна.\n===");
            workable = false;
        }
        return line;
    }

    protected void readFile(File importFile) {
        try {
            if ((!(importFile.isFile())))
                throw new FileNotFoundException("===\nПуть ведёт не к файлу.\nДальнейшая работа невозможна.\n===");
            if (!(importFile.exists()))
                throw new FileNotFoundException("===\nПо указанному пути файл не найден.\nДальнейшая работа невозможна.\n===");
            if (!importFile.canRead())
                throw new SecurityException("===\nНет прав на чтение.\nДальнейшая работа невозможна.\n===");
            jsonToLinkedList(readJsonFromFile(importFile));
        } catch (FileNotFoundException | SecurityException ex) {
            System.out.println("===\nНепредвиденная ошибка чтения файла.\n===");
            workable = false;
        }
    }

    protected void jsonToLinkedList(String rawJson){
        UrodJsonParser gson = new UrodJsonParser();
        linkedalices = gson.getArraysofAliceObjects(rawJson);
        linkedalices.sort(Comparator.naturalOrder());
                System.out.println("===\nЭлементов было добавлено: "+linkedalices.size()+" \n===");
                date = new Date();
                workable = true;
    }

    private void save(){
        try (PrintWriter printer = new PrintWriter(sourcefile)){
            if(linkedalices!=null){
                if(linkedalices.size()!=0) {
                    UrodJsonParser urodJsonParser = new UrodJsonParser();
                    printer.write(urodJsonParser.getWrittenAlices(linkedalices));
                    System.out.println("===\nКоллекция сохранена в файле: " + sourcefile.getAbsolutePath() + "\n===");
                }
            } else {
                printer.write("[]");
            }
            System.out.println("===\nРабота с коллекцией завершена\n===");
        } catch (FileNotFoundException e) {
            System.out.println("===\nСохранение коллекции не удалось\n===");
            e.getStackTrace();
        }
    }

    protected void saveAndExit() {
            if(sourcefile==null||!sourcefile.canWrite()){
                System.out.println("===\nФайл не существует или в него невозможно записать\n===");
                String newfilename = "fileforsaving.txt";
                String workdirectory = System.getProperty("user.dir");
                String separator = System.getProperty("file.separator");
                sourcefile = new File(workdirectory + separator + newfilename);
                if(!sourcefile.exists()|!sourcefile.isFile()){
                    try {
                        if (sourcefile.createNewFile()) {
                            System.out.println("===\nСоздан новый файл для записи: " + sourcefile.getAbsolutePath() + "\n===");
                            save();
                        }
                        else System.out.println("===\nОшбика создания файла : " + sourcefile.getAbsolutePath() + "\n===");
                    } catch (IOException e) {
                        System.out.println("===\nСохранение коллекции не удалось\n===");
                    }
                }else{
                    save();
                }
            }else{
                save();
            }
    }

    /**This method makes reverse.
     */
    protected void reorder(){
        if(reverse){
            linkedalices.sort(Comparator.naturalOrder());
            Collections.reverse(linkedalices);
            reverse = false;
        } else {
            linkedalices.sort(Comparator.naturalOrder());
            reverse = true;
        }
        System.out.println("===\nКоллекция отсортирована в обратном порядке\n===");
    }
/**This method shows information about collection.
     */
    protected void info(){
        System.out.println("===\nИнформация о коллекции:\n" +
                "\tКоллекция типа LinkedList и содержит экземпляры класса Алиса\n" +
                "\tДата: " + date + "\n\tРазмер коллекции: " + linkedalices.size()+"\n===");
    }
/**This method shows elements in collection.
     */
    protected void show(){
        System.out.println("===\n"+linkedalices+"\n===");
    }
/**This method add new element in collection.
     * @param aliceforadd - Alice.class, which you want add.
     */
    protected void add(Alice aliceforadd){
        linkedalices.add(aliceforadd);
        System.out.println("===\nЭлемент добавлен\n===");
    }
/**This method removes all alices, which greater than parametr.
     * @param primerforevery - Alice.class, which will be compared with alices in Collection.
     */
    protected void remove_greater(Alice primerforevery){
        Iterator<Alice> iterator = linkedalices.iterator();
        Comparator<Alice> comparator = Comparator.naturalOrder();
        int count = 0;
        while(iterator.hasNext()){
        Alice element = iterator.next();
        if(comparator.compare(primerforevery,element)<0) {
            iterator.remove();
            count++;
            }
        }
        System.out.println("===\nКоличество удалённых алис: "+count+"\n===");
    }
    /**This method removes all alices, which equal than parametr.
     * @param aliceforcompare - Alice.class, which will be compared with alices in Collection.
     */
    protected void remove_all(Alice aliceforcompare){
        Iterator<Alice> iterator = linkedalices.iterator();
        Comparator<Alice> comparator = Comparator.naturalOrder();
        int count = 0;
        while(iterator.hasNext()){
            Alice element = iterator.next();
            if(comparator.compare(aliceforcompare,element)==0) {
                iterator.remove();
                count++;
            }
        }
        System.out.println("===\nКоличество удалённых алис: "+count+"\n===");
    }
    /**This method removes alice in collection equal to parametr.
     * @param aliceforremove - Alice.class, which will be compared with alices in Collection.
     */
    protected void remove(Alice aliceforremove) {
        if (linkedalices.remove(aliceforremove)) System.out.println("===\nЭлемент удалён\n===");
        else
            System.out.println("===\nТакого элемента и не было\n===");
    }
}

