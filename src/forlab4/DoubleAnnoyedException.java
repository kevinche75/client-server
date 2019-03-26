public class DoubleAnnoyedException extends Exception {
    private String user;
    private String message = "DoubleAnnoyedException: не лезь, она тебя сожрёт";
    DoubleAnnoyedException(String message,String user){
        this.user = user;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return ("DoubleAnnoyedException" + message + "\nИсключение вызвал экземпляр " + user);
    }
}
