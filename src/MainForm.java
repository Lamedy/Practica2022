
// *****************************************
// Класс для описания работы окна приложения
// *****************************************

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainForm extends JFrame implements Runnable {
    // **************
    // Настройки окна
    // **************

    // Размер окна
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screen_width = dimension.width+8;
    private final int screen_height = dimension.height-20;
    // Частота обновления экрана (количество кадров в секунду)
    private int FrameRate = 240;
    // Счётчик кадров
    private int FrameCounter = 0;
    // Цвет заднего фона
    private final Color BackGroundColor = new Color(200, 200, 200, 255);

    // ******
    // Данные
    // ******

    // Массив хранящий данные о еде
    public ArrayList<Food> food = new ArrayList<>();
    // Массивы хранящий данные о существах
    public ArrayList<GreenBacteria> greenBacteria = new ArrayList<>();
    public ArrayList<RedBacteria> redBacteria = new ArrayList<>();
    // Размер еды
    private int FoodSize = 8;
    // Скорость появления еды (чем больше значение тем медленее появляется еда)
    private int FoodSpawnRate = 10; // Не ставить ниже 10
    // Вероятность мутации в %
    private int ChanceOfMutation = 40;
    // Скорость голодания
    private double FastingSpeed = 0.035f;
    // Начальное количество зелёных бактерий
    private final int CountOfPopulationGreen = 40;
    // Начальное количество крастных бактерий
    private final int CountOfPopulationRed = 3;
    // Начальное количество еды
    private final int StartFood = 300;

    // Дополнительные окна
    Settings FormSettings = new Settings(FrameRate, FoodSpawnRate, ChanceOfMutation, FastingSpeed);
    Graph graph = new Graph(greenBacteria, redBacteria);

    // Настройки окна
    public MainForm()  {
        for(int i = 0; i < StartFood; ++i){
            Food NewFood = new Food((int)(Math.random() * (screen_width - 100) + 50), (int)(Math.random() * (screen_height - 100) + 50));
            food.add(NewFood);
        }
        // Создаётся CountOfPopulationGreen количество зелёных бактерий
        for(int i = 0; i < CountOfPopulationGreen;i++){
            GreenBacteria NewBacteria = new GreenBacteria((int)(Math.random()*(screen_width - 50)) + 50,(int)(Math.random()*(screen_height - 50)) + 50);
            greenBacteria.add(NewBacteria);
        }
        // Создаётся CountOfPopulationRed количество крастных бактерий
        for(int i = 0; i < CountOfPopulationRed;i++){
            RedBacteria NewBacteria = new RedBacteria((int)(Math.random()*(screen_width - 50)) + 50,(int)(Math.random()*(screen_height - 50)) + 50);
            redBacteria.add(NewBacteria);
        }
        // Параметры окна
        try {
            super.setIconImage(ImageIO.read(new File("img/Bacteria1.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        super.setSize(screen_width, screen_height );
        super.setTitle("Bacteria Simulation");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        // Открывают окна поверх основного окна
        FormSettings.MoveUp();
        graph.MoveUp();
        new Thread(graph).start();

        // Обработка событий мыши
        super.addMouseListener(new MouseListener() {
            // При нажатии на мышь появляется еда на месте курсора
            @Override
            public void mouseClicked(MouseEvent e) {
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
    // Метод для отрисовки графики (вызывается каждый кадр. т.е. FrameRate раз в секунду)
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Toolkit.getDefaultToolkit().sync();
        g2.setColor(BackGroundColor);
        g2.fillRect(0, 0, screen_width, screen_height);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawFood(g2);
        // Отрисовка зелёной бактерии
        for (GreenBacteria creature : greenBacteria) {
            if(FrameCounter % 10 == 0) {
                creature.Animation();
            }
            creature.drawCreature(g2);
        }
        // Отрисовка красной бактерии
        for (RedBacteria creature : redBacteria) {
            if(FrameCounter % 10 == 0) {
                creature.Animation();
            }
            creature.drawCreature(g2);
        }
        logic();
        ChangeSettings();
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
                if(FrameRate != 0){
                    Thread.sleep(1000 / FrameRate);
                }
                else {
                    while(FrameRate == 0){
                        Thread.sleep(1);
                        ChangeSettings();
                    }
                }
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
            creature.move();
            // Поиск еды и проверка если еда съедена
            creature.FindFood(food);
            // Смерть существа
            if(creature.getInfoFood() <= 0){
                creature.setCondition(true);
            }
        }
        // Красная бактерия
        for(RedBacteria creature : redBacteria){
            // Поиск еды и проверка если еда съедена
            if(creature.getFullInfo() == false){
                if(creature.getInfoFood() < 20){
                    creature.FindFood(greenBacteria);
                }
                else{
                    creature.setMoveX(Math.random() * (screen_width - 50) + 50);
                    creature.setMoveY(Math.random() * (screen_height - 50) + 50);
                    creature.setFullInfo(true);
                }
            }
            else if ((creature.getInfoFood() < 20) ) {
                creature.setFullInfo(false);
            }
            if(FrameCounter % 5000 == 0){creature.setFullInfo(false);}
            // Перемещение существа
            creature.move();
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
                if(random > 100 - ChanceOfMutation && NewBacteria.getSpeed() <= 0.9f) {
                    NewBacteria.setSpeed(NewBacteria.getSpeed() + 0.1f);
                }
                else if(random < ChanceOfMutation && NewBacteria.getSpeed() > 0.1f){
                    NewBacteria.setSpeed(NewBacteria.getSpeed() - 0.1f);
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
                if(random > 100 - ChanceOfMutation && NewBacteria.getSpeed() <= 0.9f) {
                    NewBacteria.setSpeed(NewBacteria.getSpeed() + 0.1f);
                }
                else if(random < ChanceOfMutation && NewBacteria.getSpeed() > 0.1f){
                    NewBacteria.setSpeed(NewBacteria.getSpeed() - 0.1f);
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
            for(int i = 0; i < 6; ++i) {
                Food NewFood = new Food((int) (Math.random() * (screen_width - 50) + 50), (int) (Math.random() * (screen_height - 50) + 50));
                food.add(NewFood);
            }
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
        if(FrameCounter > 10001){FrameCounter = 0;}
    }
    public void ChangeSettings(){
        FrameRate = FormSettings.getFrameRate();
        FoodSpawnRate = FormSettings.getFoodSpawnRate();
        ChanceOfMutation = FormSettings.getFChanceOfMutation();
        FastingSpeed = FormSettings.getFastingSpeed();
    }
}