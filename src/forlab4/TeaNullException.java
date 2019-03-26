public class TeaNullException extends Exception{
    private String message = "TeaNullException: Саня, всё овно, давай по новой!";
    private String user;
    TeaNullException(String message,String user){
        this.message = message;
        this.user = user;
    }
    @Override
    public String getMessage() {
        return ("TeaNullException: " + message + "\nИсключение вызвал экзмепляр " + user);
    }
}
