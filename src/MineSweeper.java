import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.*;
import sweeper.Box;

public class MineSweeper extends JFrame {
    private Game game;
    private JPanel panel; // панель
    private JLabel label; // вывод состояния
    private JLabel lastGames; // вывод времени выигрышных партий
    private TableOfLastGames table = new TableOfLastGames(); // время выигрышных партий
    private int lvl = 0; // выбор уровня
    private final int COLS = 5; // количество столбцов
    private final int ROWS = 5; // количество строк
    private final int BOMBS = 10; // процент бомб на поле
    private final int IMAGE_SIZE = 50; // размер картинок
    private int cols = COLS+lvl*5;
    private int rows = ROWS+lvl*5;
    private int bombs = cols*rows*BOMBS/100;
    public static void main(String[] args) {
        new MineSweeper(); // создаём окно для игры
    }

    private MineSweeper(){
        game = new Game(cols, rows, bombs,lvl,table); // инициализация игры //кол-во бомб ломает
        game.start();
        setImages(); // загружаем картинки
        initLabel(); // нижняя строка
        initGameLVL(); // возможность выбирать уровень сложности
        initPanel(); // инициируем игровую панель
        initFrame(); // инициируем части
    }

    private void initGameLVL(){
        JMenuBar bar = new JMenuBar();
        JMenu gameLVL = new JMenu("LVL");
        final JMenuItem begginer = new JMenuItem("begginer");
        final JMenuItem intermediate = new JMenuItem("intermediate");
        final JMenuItem expert = new JMenuItem("expert");
        setJMenuBar(bar);
        gameLVL.add(begginer);
        gameLVL.add(intermediate);
        gameLVL.add(expert);
        bar.add(gameLVL);

        begginer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lvl = 0;
                cols = COLS+lvl*5;
                rows = ROWS+lvl*5;
                bombs = cols*rows*BOMBS/100;
                game = new Game(cols, rows, bombs,lvl,table); // инициализация игры
                panel.repaint();
                game.start();

            }
        });
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lvl = 1;
                cols = COLS+lvl*5;
                rows = ROWS+lvl*5;
                bombs = cols*rows*BOMBS/100;
                game = new Game(cols, rows, bombs,lvl,table); // инициализация игры
                panel.repaint();
                game.start();
            }
        });
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lvl = 2;
                cols = 25;
                rows = 17;
                bombs = cols*rows*BOMBS/100;
                game = new Game(cols, rows, bombs, lvl,table); // инициализация игры
                panel.repaint();
                game.start();
            }
        });
    }

    private void initPanel(){  // панель
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }
            }
        };

        // введение мыши
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord (x,y); // место, где был щелчок мыши
                if (e.getButton() == MouseEvent.BUTTON1) { // левая кнопка мыши
                    game.pressLeftButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) { // правая кнопка мыши
                    game.pressRightButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON2){ // начать играть заново
                    game.start();
                }

                label.setText(getMessage());
                lastGames.setText("<html>Table of last games"+game.getTable());//
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE,Ranges.getSize().y*IMAGE_SIZE)); // установка размера панели
        add(panel);
    }

    private String getMessage(){
        switch (game.getGameState()){
            case PLAY: return "in process...";
            case BOMBED: return "You lose";
            case WIN: {return "You win";}
            default: return "Welcom";
        }
    }

    private void initLabel(){
        label = new JLabel("Welcome");
        add(label, BorderLayout.SOUTH); // располагаем снизу

        lastGames = new JLabel("<html>Table of last games"+game.getTable()); //таблица времени
        add(lastGames, BorderLayout.EAST); // располагаем слева
    }

    private void initFrame(){ // окно приложения
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // остановить работу после закрытия окна
        setTitle("MineSweeper");
        setResizable(true); // можно ли менять размер окна
        setVisible(true);
        setIconImage(getImage("icon")); // икона для окна
        pack();
        setLocationRelativeTo(null); // окно по центру
    }

    private void setImages(){
        for (Box box : Box.values()){
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name){ // получение картинки по ее имени
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

}
