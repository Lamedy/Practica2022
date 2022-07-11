import javax.swing.*;
import java.awt.*;

public class Interface {
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    int width = 1000;
    int height = 800;
    boolean next = true;

    public Interface(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width/2 - width/2,dimension.height/2 - height/2,width,height);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        SpinnerModel model = new SpinnerNumberModel(0,  -100, 100, 5);

        JSpinner jSpinner = new JSpinner(model);
        jSpinner.setBounds(10,10,100,25);
        jPanel.add(jSpinner);


        jFrame.add(jPanel);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        // Пауза чтобы другое окно не открывалось
        while(next){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

