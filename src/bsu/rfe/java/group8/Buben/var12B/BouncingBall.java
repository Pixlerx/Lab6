package bsu.rfe.java.group8.Buben.var12B;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class BouncingBall implements Runnable  {
        private static final int MAX_RADIUS = 40;
        private static final int MIN_RADIUS = 3;
        private static final int MAX_SPEED = 15;

        private Field field;
        private int radius;
        private Color color;

        private double x;
        private double y;

        private int speed;
        private double speedX;
        private double speedY;

        public BouncingBall(Field field) {
            // Необходимо иметь ссылку на поле, по которому прыгает мяч,
            // чтобы отслеживать выход за его пределы
            // через getWidth(), getHeight()
            this.field = field;
            radius = new Double(Math.random()*(MAX_RADIUS-MIN_RADIUS)).intValue() + MIN_RADIUS;
            speed = new Double(Math.round(5*MAX_SPEED/radius)).intValue();
            if(speed > MAX_SPEED){
                speed = MAX_SPEED;
            }
            // Начальное направление скорости тоже случайно,
            // угол в пределах от 0 до 2PI
            double angle = Math.random()*2*Math.PI;
            // Вычисляются горизонтальная и вертикальная компоненты скорости
            speedX = 3*Math.cos(angle);
            speedY = 3*Math.sin(angle);
            color = new Color((float)Math.random() , (float)Math.random(), (float)Math.random());
            // Начальное положение мяча случайно
            x = Math.random()*(field.getSize().getWidth()-2*radius)+radius;
            y = Math.random()*(field.getSize().getHeight()-2*radius)+radius;
            Thread thisThread = new Thread(this);
            thisThread.start();
        }
    // Метод run() исполняется внутри потока. Когда он завершает работу,
    // то завершается и поток
    public void run(){
            try{
                // Крутим бесконечный цикл, т.е. пока нас не прервут,
                // мы не намерены завершаться
                while(true) {
                    // Синхронизация потоков на самом объекте поля
                    // Если движение разрешено - управление будет
                    // возвращено в метод
                    // В противном случае - активный поток заснѐт
                    field.canMove(this);
                    if(x+speedX <= radius) {
                        field.canStick(this);
                        // Достигли левой стенки, отскакиваем право
                        speedX = -speedX;
                        x = radius;
                    }else if(x+speedX >= field.getWidth() - radius) {
                        field.canStick(this);
                        // Достигли правой стенки, отскок влево
                        speedX = -speedX;
                        x = new Double(field.getWidth()-radius).intValue();
                    }else if(y+speedY <= radius) {
                        field.canStick(this);
                        // верхняя стенка
                        speedY= -speedY;
                        y= radius;
                    }else if(y+speedY >= field.getHeight()- radius) {
                        field.canStick(this);
                        // нижняя стенка
                        speedY = -speedY;
                        y = new Double(field.getHeight() - radius).intValue();
                     }else {
                        //простое смещение
                        x += speedX;
                        y += speedY;
                    }
                        // Засыпаем на X миллисекунд, где X определяется
                        // исходя из скорости
                        // Скорость = 1 (медленно), засыпаем на 15 мс.
                        // Скорость = 15 (быстро), засыпаем на 1 мс.
                        Thread.sleep(16-speed);
                    }
            } catch(InterruptedException ex){
                    // Если нас прервали, то ничего не делаем
                    // и просто выходим (завершаемся)
            }

        }
        // Метод прорисовки самого себя
        public void paint(Graphics2D canvas) {
                canvas.setColor(color);
                canvas.setPaint(color);
                Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius);

                canvas.draw(ball);
                canvas.fill(ball);
            }
        }


