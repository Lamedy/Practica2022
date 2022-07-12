
// ************************************
// Класс для описания красной бактерии
// ************************************

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class RedBacteria extends Creature  {
    private String way = "img/RedBacteria.png";     // Путь к изображению бактерии
    private final double StandardMaxSize = 0.7f;    // Максимальный размер до которого вырастает бактерия
    private boolean full = false;                   // Отоброжает сыта-ли бактерия

    RedBacteria(double x, double y){
        super(x,y);
        try {
            super.bacteria = ImageIO.read(new File(way));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        super.MaxSize = StandardMaxSize;
        super.size = 0.6f;
    }
    // Конструктор с использованием генов другого существа
    protected RedBacteria(double x, double y, Creature creature) {
        this(x,y);
        super.copyCreature(creature);
    }
    public void FindFood(ArrayList<GreenBacteria> info){
        double distance = 99999999; // Переменная для сравнения растояния до еды
        for(GreenBacteria a : info) {
            if(size > a.getSize()) {
                // Проверка съела ли существо еду
                if (size > a.getSize() && !a.getCondition() && (Math.pow(50 * size, 2) > Math.pow(x + 40 * size - a.getX(), 2) + Math.pow(y + 40 * size - a.getY(), 2))) {
                    a.setCondition(true);
                    food += 5f;
                    size += 0.02f;
                }
                // Поиск ближайшей еды и выстовление пути до неё
                double temp = Math.pow(x + 20 - a.getX(), 2) + Math.pow(y + 20 - a.getY(), 2);
                if (getLineOfSight() * getLineOfSight() > temp && distance * distance > temp) {
                    moveX = a.getX() - 20;
                    moveY = a.getY() - 20;
                    distance = sqrt(temp);
                }
            }
        }
    }
    // Возращает значение full
    public boolean getFullInfo(){
        return full;
    }
    // Устанавливает значение full
    public void setFullInfo(boolean full){
           this.full = full;
    }
}
