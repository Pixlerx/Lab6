package bsu.rfe.java.group8.Buben.var12B;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MainFrame extends JPanel {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;

    // поле, по которому движутся шары
    private Field field = new Field();

    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        //отцентрировать окно на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
        // установка нач состояния окна в развернутом виде
        setExtendedState(MAXIMIZED_BOTH);

        // создаем меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new Jmenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem()) {
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu ControleMenu = new JMenu("Управление");
        menuBar.add(ControleMenu);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent e) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controleMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        //Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    // Главный метод
    public static void main(String[] args) {
	    MainFrame frame = new MainFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
    }
}
