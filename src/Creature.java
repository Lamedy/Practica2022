
// ***************************
// Класс для описания существа
// ***************************

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

abstract class Creature {
    protected double x, y;                // Координаты существа
    protected double moveX, moveY;        // Координаты в которые движется существо
    protected boolean isDead = false;     // Флаг смерти существа
    protected double food;                // Количество еды у существа
    protected BufferedImage[] bacteria = new BufferedImage[2];     // Изображение существа
    private int condition = 0;
    protected double LineOfSight;         // поле зрение существа
    protected double size;                // размер существа
    protected double MaxSize;             // Максимальный размер существа
    protected double speed;               // Скорость существа

    protected Creature(double x,double y){
        this.x = x;
        this.y = y;
        this.food = 8;
        this.moveX = x;
        this.moveY = y;
        this.size = 0.5f;
        this.LineOfSight = 500;
        this.speed = (Math.random() * 1f) + 0.1f;
    }
    // Возращает координату x
    public double getX(){
        return x;
    }
    // Возращает координату y
    public double getY(){
        return y;
    }
    // Возращает координату moveX
    public double getMoveX(){
        return moveX;
    }
    // Возращает координату moveY
    public double getMoveY(){
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
    public double getSpeed(){
        return speed;
    }
    // Изменяет координату x
    protected void setX(double x){
        this.x = x;
    }
    // Изменяет координату y
    protected void setY(double y){
        this.y = y;
    }
    // Изменяет координату moveX
    protected void setMoveX(double x){
        this.moveX = x;
    }
    // Изменяет координату moveY
    protected void setMoveY(double y){
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
    public void setSpeed(double speed){
        this.speed = speed;
    }
    // Возращает изображение бактерии
    public BufferedImage getImage(){
        return bacteria[0];
    }
    // Копирвоание характеристик существа
    protected void copyCreature(Creature creature){
        this.x = creature.getX() + 35;
        this.y = creature.getY();
        this.bacteria[0] = creature.getImage();
        this.size = creature.getSize();
        this.speed = creature.getSpeed();
        this.condition = 0;
    }
    // Двигает существо в сторону цели
    public void move(){
        if(x+1 < moveX ){
            x+=speed;
        }
        else if (x-1 > moveX){
            x-=speed;
        }
        if (y+1 < moveY){
            y+=speed;
        }
        else if (y-1 > moveY){
            y-=speed;
        }
    }
    // Нарисовать существо
    public void drawCreature(Graphics2D g2){
        AffineTransform trans = new AffineTransform();
        trans.setTransform(new AffineTransform());
        trans.translate(x, y);
        trans.scale(size, size);
        g2.drawImage(bacteria[condition], trans, null);
    }
    // Логика смены кадров для анимации
    public void Animation(){
        if(condition == 0){condition++;}
        else condition--;
    }
}