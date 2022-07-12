
// **********************************************
// Класс для отрисовки графика популяции бактерий
// **********************************************

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph extends JFrame implements Runnable {

    JPanel jPanel = new JPanel();
    int width = 800;
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
        super.setBounds(dimension.width - width,0,width,height);
        jPanel.setLayout(null);
        super.add(jPanel);
        super.setTitle("Graph");
        jPanel.setBackground(new Color(180,180,180));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Добавление текста в окно
        countGreen.setBounds(10,250,220,25);
        jPanel.add(countGreen);
        countRed.setBounds(230,250,220,25);
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
