
// *****************************
// Класс для настроек приложения
// *****************************

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Settings {
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    int width = 210;
    int height = 305;
    int frameRate;
    int foodSpawnRate;
    int chanceOfMutation;
    double fastingSpeed;

    public Settings(int FrameRate, int FoodSpawnRate, int ChanceOfMutation, double FastingSpeed){
        this.frameRate = FrameRate;
        this.foodSpawnRate = FoodSpawnRate;
        this.chanceOfMutation = ChanceOfMutation;
        this.fastingSpeed = FastingSpeed;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width - width,dimension.height - height-50,width,height);
        jPanel.setLayout(null);
        jFrame.add(jPanel);
        jFrame.setTitle("Settings");
        jFrame.setAlwaysOnTop( true );
        try {
            jFrame.setIconImage(ImageIO.read(new File("img/Settings.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        // Создание интерфейса
        // Текст
        JLabel[] label = {
                new JLabel("Скорость симуляции"),
                new JLabel("Скорость появления еды"),
                new JLabel("Шанс мутации в %"),
                new JLabel("Скорость голодания")
        };
        for(int i = 0; i < 4; ++i){
            label[i].setBounds(10,10+i*60,160,25);
            jPanel.add(label[i]);
        }
        // Спинеры
        SpinnerModel[] model = {
                new SpinnerNumberModel(FrameRate/120,  0, 8, 1),        // FrameRate
                new SpinnerNumberModel(60/FoodSpawnRate,  0, 8, 1),    // FoodSpawnRate
                new SpinnerNumberModel(ChanceOfMutation / 2,  0, 100, 1),  // ChanceOfMutation
                new SpinnerNumberModel(FastingSpeed,  0, 1f, 0.001f)   // FastingSpeed
        };
        JSpinner[] jSpinner = new JSpinner[4];
        for(int i = 0; i < 4;++i){
            jSpinner[i] = new JSpinner(model[i]);
            jSpinner[i].setBounds(10,40+i*60,190,25);
            jPanel.add(jSpinner[i]);
        }
        // Кнопка
        JButton jButton = new JButton("Обновить настройки");
        jButton.setBounds(10,260,190,30);
        // Событие при нажатии на кнопку
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameRate = (int) jSpinner[0].getValue()*120;
                if((int) jSpinner[1].getValue() == 0){
                    foodSpawnRate = 999999999;
                }
                else if((int) jSpinner[1].getValue() == 7){
                    foodSpawnRate = 5;
                }
                else if((int) jSpinner[1].getValue() == 8){
                    foodSpawnRate = 1;
                }
                else{
                    foodSpawnRate =  60/(int)jSpinner[1].getValue();
                }
                chanceOfMutation = (int) jSpinner[2].getValue() / 2;
                fastingSpeed = (double) jSpinner[3].getValue();
            }
        });
        jPanel.add(jButton);

        jFrame.setUndecorated(true);
        jFrame.setOpacity(0.9f);

        jPanel.setBackground(new Color(180,180,180));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // Возращает количество кадров в секунду
    public int getFrameRate(){
        return frameRate;
    }
    // Возращает частоту появления еды
    public int getFoodSpawnRate(){
        return foodSpawnRate;
    }
    // Возращяет шанс мутации
    public int getFChanceOfMutation(){
        return chanceOfMutation;
    }
    // Возращяет скорость голода
    public double getFastingSpeed(){
        return fastingSpeed;
    }
    // Открывает окно поверх других
    public void MoveUp(){
        jFrame.setVisible(true);
    }
}

