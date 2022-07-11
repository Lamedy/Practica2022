
// ************************************
// Класс для описания красной бактерии
// ************************************

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class RedBacteria extends Creature  {
    private String way = "img/RedBacteria.png";
    private final double StandardMaxSize = 0.9f;
    RedBacteria(float x, float y){
        super(x,y);
        try {
            super.bacteria = ImageIO.read(new File(way));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        super.MaxSize = StandardMaxSize;
        super.size = 0.6f;
        super.speed = (int)(Math.random() * 4) + 1;
    }
    // Конструктор с использованием генов другого существа
    protected RedBacteria(float x, float y, Creature creature) {
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
                    food += 2f;
                    size += 0.04f;
                }
                // Поиск ближайшей еды и выстовление пути до неё
                double temp = Math.pow(x + 20 - a.getX(), 2) + Math.pow(y + 20 - a.getY(), 2);
                if (getLineOfSight() * getLineOfSight() > temp) {
                    if (distance * distance > temp) {
                        moveX = a.getX() - 20;
                        moveY = a.getY() - 20;
                        distance = sqrt(temp);
                    }
                }
            }
        }
    }
}
