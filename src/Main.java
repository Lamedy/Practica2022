import javax.swing.*;

public class Main {
    public static void main(String[] args){
        Window form = new Window();
        new Thread(form).start();
    }
}