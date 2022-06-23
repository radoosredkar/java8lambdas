package ch7.command;

public class Open implements Action {
    private Editor editor;
    public Open(Editor editor){
       this.editor = editor;
    }

    @Override
    public void perform() {
        this.editor.open();
    }
}
