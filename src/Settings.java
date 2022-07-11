import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings {
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    int width = 200;
    int height = 345;
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
        jFrame.setBounds(dimension.width/2 - width/2,dimension.height/2 - height/2,width,height);
        jPanel.setLayout(null);
        jFrame.add(jPanel);


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
                new SpinnerNumberModel(FrameRate/120,  1, 8, 1),        // FrameRate
                new SpinnerNumberModel(FoodSpawnRate,  1, 1000, 1),    // FoodSpawnRate
                new SpinnerNumberModel(ChanceOfMutation / 2,  0, 100, 1),  // ChanceOfMutation
                new SpinnerNumberModel(FastingSpeed,  0, 1f, 0.001f)   // FastingSpeed
        };
        JSpinner[] jSpinner = new JSpinner[4];
        for(int i = 0; i < 4;++i){
            jSpinner[i] = new JSpinner(model[i]);
            jSpinner[i].setBounds(10,40+i*60,160,25);
            jPanel.add(jSpinner[i]);
        }
        // Кнопка
        JButton jButton = new JButton("Обновить настройки");
        jButton.setBounds(10,260,160,30);
        // Событие при нажатии на кнопку
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameRate = (int) jSpinner[0].getValue()*120;
                foodSpawnRate = (int) jSpinner[1].getValue();
                chanceOfMutation = (int) jSpinner[2].getValue() / 2;
                fastingSpeed = (double) jSpinner[3].getValue();
            }
        });
        jPanel.add(jButton);

        jPanel.setBackground(new Color(180,180,180));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
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

