package ClientSoft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import AlicePack.*;

public class Client {

    public static void main(String[] args) {
        try {
            ConsoleReader consoleReader = new ConsoleReader();
            consoleReader.work();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
