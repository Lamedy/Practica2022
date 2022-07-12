
// **********************************************
// Класс для отрисовки графика популяции бактерий
// **********************************************

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Graph extends JFrame implements Runnable {

    JPanel jPanel = new JPanel();
    int width = 1000;
    int height = 320;
    // Массивы в которых хранится вся информация о бактериях
    ArrayList<GreenBacteria> greenBacteria;
    ArrayList<RedBacteria> redBacteria;
    // Класс для хранения int
    private class SizeGraph {
        public int size;
        SizeGraph(int size){
            this.size = size;
        }
    }
    // Массивы для хранения размеров столбцов графиков
    ArrayList<SizeGraph> GraphGreenBacteria = new ArrayList<>();
    ArrayList<SizeGraph> GraphRedBacteria = new ArrayList<>();

    // Текст
    JLabel countGreen = new JLabel();
    JLabel countRed = new JLabel();

    Graph(ArrayList<GreenBacteria> greenBacteria, ArrayList<RedBacteria> redBacteria){
        // Сохранить данныех от основного окна
        this.greenBacteria = greenBacteria;
        this.redBacteria = redBacteria;
        // Настройки окна
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        super.setBounds(dimension.width - width,32,width,height);
        jPanel.setLayout(null);
        super.add(jPanel);
        super.setTitle("Graph");
        super.setAlwaysOnTop( true );
        try {
            super.setIconImage(ImageIO.read(new File("img/Graph.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        super.setUndecorated(true);
        super.setOpacity(0.9f);
        jPanel.setBackground(new Color(180,180,180));
        // Добавление текста в окно
        countGreen.setBounds(10,284,220,25);
        jPanel.add(countGreen);
        countRed.setBounds(230,284,220,25);
        jPanel.add(countRed);
    }

    // Метод для отрисовки графики
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Изменение текста
        countGreen.setText("Количество зелёных бактерий : " + Integer.toString(greenBacteria.size()));
        countRed.setText("Количество красных бактерий : " + Integer.toString(redBacteria.size()));
        g2.setColor(new Color(0, 0, 0, 255));
        g2.drawLine(0, 282,  3000, 282);
        // Отрисовка графика с зелёными бактериями
        for(int i = 0; i < GraphGreenBacteria.size(); ++i) {
            if (GraphGreenBacteria.get(i).size != 0) {
                g2.setColor(new Color(50, 150, 50, 170));
                g2.drawLine(i + 10, 280, i + 10, 281 - GraphGreenBacteria.get(i).size);
            }
        }
        SizeGraph temp1 = new SizeGraph(greenBacteria.size());
        GraphGreenBacteria.add(temp1);
        // Отрисовка графика с красными бактериями
        for(int i = 0; i < GraphRedBacteria.size(); ++i){
            if(GraphRedBacteria.get(i).size != 0){
                g2.setColor(new Color(255, 100, 100, 170));
                g2.drawLine(i + 10, 280, i + 10, 281 - GraphRedBacteria.get(i).size);
            }
        }
        SizeGraph temp2 = new SizeGraph(redBacteria.size());
        GraphRedBacteria.add(temp2);

        Toolkit.getDefaultToolkit().sync();
    }
    // Метод run который отрисовывает FrameRate кадров в секунду
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.repaint();
        }
    }
    // Открывает окно поверх других
    public void MoveUp(){
        super.setVisible(true);
    }

}
