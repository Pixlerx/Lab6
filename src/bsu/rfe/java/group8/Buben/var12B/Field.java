package bsu.rfe.java.group8.Buben.var12B;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel{
    public boolean magnet = false;
    public boolean check = true;
    private boolean showMagnit = false;
    // Флаг приостановленности движения
    private boolean paused;
    // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);

    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
    // При создании его экземпляра используется анонимный класс,
    // реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10,new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            // Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });
    // конструктор
    public Field() {
        setBackground(Color.WHITE);
        // Запуск таймера
        repaintTimer.start();
    }
    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
        // Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        // Последовательно запросить прорисовку от всех мячей из списка
        for(BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }
    // Метод добавляющий новый мячик
    public void addBall() {
        //Заключается в добавлении в список нового экземпляра BouncingBall
        // Всю инициализацию положения, скорости, размера, цвета
        // BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }

    public void setShowMagnit(boolean showMagnit) {
        this.showMagnit = showMagnit;
    }
    public boolean getShowMagnit()
    {
        return showMagnit;
    }
    // Метод синхронизированный, т.е. только один поток может
    // одновременно быть внутри
    public synchronized void pause() {
        paused = true;
    }
    // Метод синхронизированный, т.е. только один поток может
    // одновременно быть внутри
    public synchronized void resume(){
        paused = false;
        notifyAll();
    }
    // Синхронизированный метод проверки, может ли мяч двигаться
    // (не включен ли режим паузы?)
    public synchronized void magnitized(boolean showMagnit) {
        if (showMagnit) {
            magnet = true;
        } else {
            magnet = false;
            notifyAll();
        }
    }
    public synchronized void canMove(BouncingBall ball) throws InterruptedException{
        if(paused)
        {
            wait();
        }
    }
    public synchronized void canStick (BouncingBall ball) throws InterruptedException {
        if (magnet == true)
            wait();
    }
}
