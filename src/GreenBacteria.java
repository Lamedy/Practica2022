
// ************************************
// Класс для описания зелёного бактерии
// ************************************

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class GreenBacteria extends Creature {
    // Хранит изображение бактерии
    private String way = "img/bacteria.png";        // Путь к изображению бактерии
    private final double StandardMaxSize = 0.6f;    // Максимальный размер до которого вырастает бактерия
    // Конструктор
    protected GreenBacteria(double x, double y) {
        super(x, y);
        try {
            super.bacteria = ImageIO.read(new File(way));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        super.MaxSize = StandardMaxSize;
    }
    // Конструктор с использованием генов другого существа
    protected GreenBacteria(double x, double y, Creature creature) {
        this(x,y);
        super.copyCreature(creature);
    }
    // Взаимодействие бактерии с едой
    public void FindFood(ArrayList<Food> info){
        double distance = 99999999; // Переменная для сравнения растояния до еды
        for(Food a : info) {
            // Проверка съела ли существо еду
            if (!a.getCondition() && (Math.pow(50*size, 2) > Math.pow(x + 40*size - a.getX(),2) + Math.pow(y + 40*size - a.getY(),2))) {
                a.setCondition(true);
                food += 2;
                size += 0.02f;
            }
            // Поиск ближайшей еды и выстовление пути до неё
            double temp = Math.pow(x + 20 - a.getX(), 2) + Math.pow(y + 20 - a.getY(),2);
            if (getLineOfSight() * getLineOfSight() > temp && distance * distance > temp) {
                moveX = a.getX() - 20;
                moveY = a.getY() - 20;
                distance = sqrt(temp);
            }
        }
    }
}
