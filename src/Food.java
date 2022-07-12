
// **********************
// Класс для описания еды
// **********************

import java.awt.*;

public class Food {
    private float x, y;                 // Координаты еды
    private boolean isEaten = false;    // Флаг обозночающий съедена ли еда?
    private int color;                  // Цвет еды
    // Доступные цвета еды
    public static Color[] COLOR = {
            new Color(255, 100, 100),
            new Color(200, 200, 100),
            new Color(50, 150, 50),
            new Color(100, 150, 200),
            new Color(50, 50, 255),
            new Color(150, 50, 150)
    };

    Food(int foodX, int foodY){
        this.x = foodX;
        this.y = foodY;
        this.color = (int)(Math.random() * 6);
    }
    // Возращает кординату x
    public float getX(){
        return x;
    }
    // Возращает кординату y
    public float getY(){
        return y;
    }
    // Возращает состояние еды, съедена или нет
    public boolean getCondition(){
        return isEaten;
    }
    // Возращает номер цвета
    public int getColor(){
        return color;
    }
    // Возращает состояние еды, съедена или нет
    public void setCondition(boolean condition){
        isEaten = condition;
    }
}
