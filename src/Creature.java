
// ***************************
// Класс для описания существа
// ***************************

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

abstract class Creature {
    protected float x, y;                 // Координаты существа
    protected float moveX, moveY;         // Координаты в которые движется существо
    protected boolean isDead = false;     // Флаг смерти существа
    protected double food;                // Количество еды у существа
    protected BufferedImage bacteria;     // Изображение существа
    protected double LineOfSight;         // поле зрение существа
    protected double size;                // размер существа
    protected double MaxSize;             // Максимальный размер существа
    protected int speed;                  // Скорость существа

    protected Creature(float x,float y){
        this.x = x;
        this.y = y;
        this.food = 8;
        this.moveX = x;
        this.moveY = y;
        this.size = 0.4f;
        this.LineOfSight = 2000;
        this.speed = (int)(Math.random() * 6) + 1;
    }
    // Возращает координату x
    public float getX(){
        return x;
    }
    // Возращает координату y
    public float getY(){
        return y;
    }
    // Возращает координату moveX
    public float getMoveX(){
        return moveX;
    }
    // Возращает координату moveY
    public float getMoveY(){
        return moveY;
    }
    // Возращает состояние существа, живо ли оно или нет
    public boolean getCondition(){
        return isDead;
    }
    // Возращает количество еды
    public double getInfoFood(){
        return food;
    }
    // Возращает дальность обзора
    public double getLineOfSight(){
        return LineOfSight;
    }
    // Возращает дальность размер существа
    public double getSize(){
        return size;
    }
    // Возращает необходимое количество еды
    public double getMaxSize(){
        return MaxSize;
    }
    // Возращает скорость существа
    public int getSpeed(){
        return speed;
    }
    // Изменяет координату x
    protected void setX(float x){
        this.x = x;
    }
    // Изменяет координату y
    protected void setY(float y){
        this.y = y;
    }
    // Изменяет координату moveX
    protected void setMoveX(float x){
        this.moveX = x;
    }
    // Изменяет координату moveY
    protected void setMoveY(float y){
        this.moveY = y;
    }
    // Изменяет состояние существа, живо ли оно или нет
    protected void setCondition(boolean Condition){
        isDead = Condition;
    }
    // Изменяет количество еды
    protected void setInfoFood(double food){
        this.food = food;
    }
    // Изменяет размер существа
    public void setSize(double size){
        this.size = size;
    }
    // Возращает скорость существа
    public void setSpeed(int speed){
        this.speed = speed;
    }
    // Возращает изображение бактерии
    public BufferedImage getImage(){
        return bacteria;
    }
    // Копирвоание характеристик существа
    protected void copyCreature(Creature creature){
        this.x = creature.getX() + 35;
        this.y = creature.getY();
        this.bacteria = creature.getImage();
        this.size = creature.getSize();
        this.speed = creature.getSpeed();
    }
    // Двигает существо в сторону цели
    public void move(){
        if(x < moveX){
            x+=1;
        }
        else if (x > moveX){
            x-=1;
        }
        if (y < moveY){
            y+=1;
        }
        else if (y > moveY){
            y-=1;
        }
    }
    public void drawCreature(Graphics2D g2){
        AffineTransform trans = new AffineTransform();
        trans.setTransform(new AffineTransform());
        trans.translate(x, y);
        trans.scale(size, size);
        g2.drawImage(bacteria, trans, null);
    }
}