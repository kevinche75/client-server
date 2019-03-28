package ServerSoft;

import AlicePack.Alice;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionPlace {

    private CopyOnWriteArrayList<Alice> collection;
    private boolean reverse = true;
    private Date date;

    public CollectionPlace(CopyOnWriteArrayList<Alice> collection){
        this.collection = collection;
    }

    /**This method makes reverse.
     */
    protected String reorder(){
        if(reverse){
            collection.sort(Comparator.naturalOrder());
            Collections.reverse(collection);
            reverse = false;
        } else {
            collection.sort(Comparator.naturalOrder());
            reverse = true;
        }
        return  "===\nКоллекция отсортирована в обратном порядке\n===";
    }
    /**This method shows information about collection.
     */
    protected String info(){
        return  "===\nИнформация о коллекции:\n" +
                "\tКоллекция типа LinkedList и содержит экземпляры класса Алиса\n" +
                "\tДата: " + date + "\n\tРазмер коллекции: " + collection.size()+"\n===";
    }
    /**This method shows elements in collection.
     */
    protected void show(){
        System.out.println("===\n"+collection+"\n===");
    }
    /**This method add new element in collection.
     * @param aliceforadd - Alice.class, which you want add.
     */
    protected String  add(Alice aliceforadd){
        collection.add(aliceforadd);
        return  "===\nЭлемент добавлен\n===";
    }
    /**This method removes all alices, which greater than parametr.
     * @param primerforevery - Alice.class, which will be compared with alices in Collection.
     */
    protected String remove_greater(Alice primerforevery){
        Iterator<Alice> iterator = collection.iterator();
        Comparator<Alice> comparator = Comparator.naturalOrder();
        int count = 0;
        while(iterator.hasNext()){
            Alice element = iterator.next();
            if(comparator.compare(primerforevery,element)<0) {
                iterator.remove();
                count++;
            }
        }
        return "===\nКоличество удалённых алис: "+count+"\n===";
    }
    /**This method removes all alices, which equal than parametr.
     * @param aliceforcompare - Alice.class, which will be compared with alices in Collection.
     */
    protected String remove_all(Alice aliceforcompare){
        Iterator<Alice> iterator = collection.iterator();
        Comparator<Alice> comparator = Comparator.naturalOrder();
        int count = 0;
        while(iterator.hasNext()){
            Alice element = iterator.next();
            if(comparator.compare(aliceforcompare,element)==0) {
                iterator.remove();
                count++;
            }
        }
        return "===\nКоличество удалённых алис: "+count+"\n===";
    }
    /**This method removes alice in collection equal to parametr.
     * @param aliceforremove - Alice.class, which will be compared with alices in Collection.
     */
    protected String remove(Alice aliceforremove) {
        if (collection.remove(aliceforremove)) return "===\nЭлемент удалён\n===";
        else
            return "===\nТакого элемента и не было\n===";
    }
}
