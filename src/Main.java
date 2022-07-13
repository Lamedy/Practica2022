
// ******************************************************
// Основной класс из которого происходит запуск программы
// ******************************************************

public class Main {
    public static void main(String[] args){
        MainForm form = new MainForm();
        new Thread(form).start();
    }
}