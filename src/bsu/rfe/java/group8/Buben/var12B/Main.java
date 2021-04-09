package bsu.rfe.java.group8.Buben.var12B;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Main extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    private JCheckBoxMenuItem JCheckBoxMenuItemMagnetizm;

    // поле, по которому движутся шары
    private Field field = new Field();

    public Main() {
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
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            @Override
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);

        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Возобновить движение") {
            @Override
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);


        Action Magnetism = new AbstractAction("Магнетизм") {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.SetShowMagnitism(JCheckBoxMenuItemMagnetizm.isSelected());
            }
        };
        JCheckBoxMenuItemMagnetizm = new JCheckBoxMenuItem(Magnetism);
        controlMenu.add(JCheckBoxMenuItemMagnetizm);
        JCheckBoxMenuItemMagnetizm.setSelected(false);

        JMenu refMenu = new JMenu("Справка");
        menuBar.add(refMenu);

        Action showInformationAction = new AbstractAction("О программе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Main.this, "Бубен Иван ✔ \n8 группа ✔\n2-й курс ✔\n☺☺☺",
                        "Информация о студенте", JOptionPane.PLAIN_MESSAGE);
            }
        };
        JMenuItem showInformationMenuItem = refMenu.add(showInformationAction);
        showInformationMenuItem.setEnabled(true);
        //Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    // Главный метод
    public static void main(String[] args) {
	    Main frame = new Main();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
    }
}
