package ch8.command;

public class Save implements Action {
    private Editor editor;
    public Save(Editor editor){
       this.editor = editor;
    }

    @Override
    public void perform() {
        this.editor.save();
    }
}
