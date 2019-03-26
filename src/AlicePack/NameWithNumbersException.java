package AlicePack;

import java.io.Serializable;

public class NameWithNumbersException extends RuntimeException implements Serializable {
    NameWithNumbersException(){
        System.out.println("NameWithNumbersException: Имя не должно содержать цифр");
        System.exit(0);
    }
}
