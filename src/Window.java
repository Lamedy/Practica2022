
// *************************************
// Класс описания работы окна приложения
// *************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Window extends JFrame implements Runnable {
    // **************
    // Настройки окна
    // **************

    // Размер окна
    private final int screen_wedth = 1000;
    private final int screen_height = 800;
    // Частота обновления экрана (количество кадров в секунду)
    private final int FrameRate = 240;
    // Счётчик кадров
    private int FrameCounter = 0;
    // Цвет заднего фона
    private final Color BackGroundColor = new Color(200, 200, 200, 255);

    JPanel jPanel = new JPanel();

    // ******
    // Данные
    // ******

    // Массив хранящий данные о еде
    public ArrayList<Food> food = new ArrayList<>();
    // Массивы хранящий данные о существах
    public ArrayList<GreenBacteria> greenBacteria = new ArrayList<>();
    public ArrayList<RedBacteria> redBacteria = new ArrayList<>();
    // Размер еды
    private int FoodSize = 10;
    // Скорость появления еды (чем больше значение тем медленее появляется еда)
    private final int FoodSpawnRate = 5;
    // Вероятность мутации в %
    private final int ChanceOfMutation = 5;
    // Скорость голодания
    private final double FastingSpeed = 0.025f;
    // Начальное количество зелёных бактерий
    private final int CountOfPopulationGreen = 20;
    // Начальное количество крастных бактерий
    private final int CountOfPopulationRed = 1;

    // Настройки окна
    public Window()  {
        // Создаётся CountOfPopulationGreen количество зелёных бактерий
        for(int i = 0; i < CountOfPopulationGreen;i++){
            GreenBacteria NewBacteria = new GreenBacteria((int)(Math.random()*(screen_wedth - 50)) + 50,(int)(Math.random()*(screen_height - 50)) + 50);
            greenBacteria.add(NewBacteria);
        }
        // Создаётся CountOfPopulationRed количество крастных бактерий
        for(int i = 0; i < CountOfPopulationRed;i++){
            RedBacteria NewBacteria = new RedBacteria((int)(Math.random()*(screen_wedth - 50)) + 50,(int)(Math.random()*(screen_height - 50)) + 50);
            redBacteria.add(NewBacteria);
        }
        // Параметры окна
        super.setSize(screen_wedth, screen_height );

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        // Обработка событий мыши
        super.addMouseListener(new MouseListener() {
            // При нажатии на мышь появляется еда на месте курсора
            @Override
            public void mouseClicked(MouseEvent e) {
                //if(e.getX() > 200){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        GreenBacteria NewBacteria = new GreenBacteria(e.getX() - 16 ,e.getY() - 18);
                        greenBacteria.add(NewBacteria);
                    }
                    if (e.getButton() == MouseEvent.BUTTON2){
                        Food NewFood = new Food(e.getX() - (FoodSize/2),e.getY()- (FoodSize/2));
                        food.add(NewFood);
                    }
                    if (e.getButton() == MouseEvent.BUTTON3){
                        RedBacteria NewBacteria = new RedBacteria(e.getX() - 16 ,e.getY() - 18);
                        redBacteria.add(NewBacteria);
                    }
                //}
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    // Метод для отрисовки графики (вызывается каждый кадр. т.е. 60 раз в секунду)
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Toolkit.getDefaultToolkit().sync();
        g2.setColor(BackGroundColor);
        g2.fillRect(0, 0, screen_wedth, screen_height);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawFood(g2);
        // Отрисовка зелёной бактерии
        for (GreenBacteria creature : greenBacteria) {
            creature.drawCreature(g2);
        }
        // Отрисовка красной бактерии
        for (RedBacteria creature : redBacteria) {
            creature.drawCreature(g2);
        }
        logic();
        // Отрисовка интерфейса
        //g2.setColor(new Color(180, 180, 180,220));
        //g2.fillRect(0,0,200,screen_height);


    }
    // Метод для отрисовки еды
    private void drawFood(Graphics2D g2){
        for (Food a : food) {
            g2.setColor(Food.COLOR[a.getColor()]);
            g2.fillOval((int) a.getX(), (int) a.getY(), FoodSize, FoodSize);
        }
    }
    // Метод run который отрисовывает FrameRate кадров в секунду
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000 / FrameRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
    // Метод для описания логики симуляции
    private void logic(){
        // Зелёная бактерия
        for(GreenBacteria creature : greenBacteria){
            // Перемещение существа
            if(FrameCounter % creature.getSpeed() == 0){
                creature.move();
            }
            // Поиск еды и проверка если еда съедена
            creature.FindFood(food);
            // Смерть существа
            if(creature.getInfoFood() <= 0){
                creature.setCondition(true);
            }
        }
        // Красная бактерия
        for(RedBacteria creature : redBacteria){
            // Перемещение существа
            if(FrameCounter % creature.getSpeed() == 0){
                creature.move();
            }
            // Поиск еды и проверка если еда съедена
            creature.FindFood(greenBacteria);
            // Смерть существа
            if(creature.getInfoFood() <= 0){
                creature.setCondition(true);
            }
        }
        // Деление существ
        // Зелёная бактерия
        for(int i = 0; i < greenBacteria.size(); ++i){
            if(greenBacteria.get(i).getSize() >= greenBacteria.get(i).getMaxSize()){
                greenBacteria.get(i).setSize(greenBacteria.get(i).getSize() / 2);
                greenBacteria.get(i).setInfoFood(greenBacteria.get(i).getInfoFood() / 2);
                GreenBacteria NewBacteria = new GreenBacteria(greenBacteria.get(i).getX()+20,greenBacteria.get(i).getY(), greenBacteria.get(i));
                greenBacteria.get(i).setX(greenBacteria.get(i).getX()-20);
                // Мутация скорости существа
                int random = (int)(Math.random()*100);
                if(random > 100 - ChanceOfMutation) {
                    NewBacteria.setSpeed(NewBacteria.getSpeed() + 1);
                }
                else if(random < ChanceOfMutation && NewBacteria.getSpeed() > 1){
                    NewBacteria.setSpeed(NewBacteria.getSpeed() - 1);
                }
                greenBacteria.add(NewBacteria);
                ++i;
            }
        }
        // Красная бактерия
        for(int i = 0; i < redBacteria.size(); ++i){
            if(redBacteria.get(i).getSize() >= redBacteria.get(i).getMaxSize()){
                redBacteria.get(i).setSize(redBacteria.get(i).getSize() / 2);
                redBacteria.get(i).setInfoFood(redBacteria.get(i).getInfoFood() / 2);
                RedBacteria NewBacteria = new RedBacteria(redBacteria.get(i).getX()+20,redBacteria.get(i).getY(), redBacteria.get(i));
                redBacteria.get(i).setX(redBacteria.get(i).getX()-20);
                // Мутация скорости существа
                int random = (int)(Math.random()*100);
                if(random > 100 - ChanceOfMutation) {
                    NewBacteria.setSpeed(NewBacteria.getSpeed() + 1);
                }
                else if(random < ChanceOfMutation && NewBacteria.getSpeed() > 1){
                    NewBacteria.setSpeed(NewBacteria.getSpeed() - 1);
                }
                redBacteria.add(NewBacteria);
                ++i;
            }
        }
        // Удаляются все мёртвые существа
        // Зелёные бактерии
        for(int i = 0; i < greenBacteria.size(); ++i){
            if(greenBacteria.get(i).getCondition()){
                greenBacteria.remove(i);
                i--;
            }
        }
        // Красные бактерии
        for(int i = 0; i < redBacteria.size(); ++i){
            if(redBacteria.get(i).getCondition()){
                redBacteria.remove(i);
                i--;
            }
        }
        // Удаляется вся съеденая еда
        for(int i = 0; i < food.size(); ++i){
            if(food.get(i).getCondition()){
                food.remove(i);
                i--;
            }
        }
        // Каждые FoodSpawnRate появляется еда
        if(FrameCounter % FoodSpawnRate == 0) {
            Food NewFood = new Food((int)(Math.random() * (screen_wedth - 100) + 50), (int)(Math.random() * (screen_height - 100) + 50));
            food.add(NewFood);
        }
        // Каждый кадр существа голодает
        // Зелёные бактерии
        for(GreenBacteria creature : greenBacteria){
            creature.setInfoFood(creature.getInfoFood() - ( (FastingSpeed / creature.getSpeed()) * creature.getSize()));
        }
        // Красные бактерии
        for(RedBacteria creature : redBacteria){
            creature.setInfoFood(creature.getInfoFood() - ( (FastingSpeed / creature.getSpeed()) * creature.getSize()));
        }
        ++FrameCounter;
        if(FrameCounter > 10000){FrameCounter = 0;}
    }
}