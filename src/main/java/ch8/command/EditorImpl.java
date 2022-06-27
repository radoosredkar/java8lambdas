package ch8.command;

public class EditorImpl implements Editor {
    private String name;

    public EditorImpl(String name){
        this.name = name;
    }
    @Override
    public void save() {
        System.out.println(String.format("Saving %s", this.name));
    }

    @Override
    public void open() {
        System.out.println(String.format("Opening %s", this.name));
    }

    @Override
    public void close() {
        System.out.println(String.format("Closing %s", this.name));
    }
}
