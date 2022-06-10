package ch4;

public class ChildImpl implements Child {
    private String message;

    @Override
    public void message(String body) {
       this.message = body;
    }

    @Override
    public String getLastMessage() {
        return message;
    }
}
