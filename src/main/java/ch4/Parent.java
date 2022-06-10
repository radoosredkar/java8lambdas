package ch4;

public interface Parent {
    public void message(String body);
    public default void welcome(){
        message("Parent: Hi!");
    }

    public String getLastMessage();
}
