package sweeper;

public class Game {

    private Bomb bomb; // матрица с бомбами
    private Flag flag; // матрица с флагами (верхний слой)

    private GameState gameState; // состояние игры

    private Timer time; // время выигрышной партии
    private TableOfLastGames table; // таблица с выигрышным временем

    private String lvl; // уровень

    public GameState getGameState() {
        return gameState;
    }


    public Game(int cols, int rows, int bombs, int lvl_, TableOfLastGames table){
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
        time = new Timer();
        this.table = table;

        switch (lvl_){
            case 0: lvl = "begginer"; break;
            case 1: lvl = "intermediate"; break;
            case 2: lvl = "expert"; break;
        }
    }

    public void start(){
        bomb.start();
        flag.start();
        gameState = GameState.PLAY;
        time.startTimer();
    }

    public Box getBox (Coord coord){ // говорит, что нужно изобразить в данной области экрана
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        }
        else return flag.get(coord);
    }

    public void pressLeftButton(Coord coord){
        if (gameOver()){
            return;
        }
        openBox(coord);
        checkWin();
    }

    private void checkWin(){
        if (gameState == GameState.PLAY){
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()){
                gameState = GameState.WIN;
                time.stopTimer();
                table.addTime(lvl,time.getTime());
            }
        }
    }

    private void openBox(Coord coord){
        switch (flag.get(coord)){
            case OPENED: return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO: openBoxesAround(coord); return;
                    case BOMB: openBombs(coord); return;
                    default: flag.setOpened(coord); return;
                }
        }
    }

    private void openBoxesAround (Coord coord){
        flag.setOpened(coord);
        for (Coord around : Ranges.getCoordsAround(coord)){
            openBox(around);
        }
    }

    private void openBombs(Coord bombed){
        gameState = GameState.BOMBED;
        flag.setBombed(bombed);
        for (Coord coord : Ranges.getAllCoords() ){
            if (bomb.get(coord) == Box.BOMB){
                flag.setOpenedToClosedBombBox(coord); // открывает все бомбы
            }
            else flag.setNoBomb(coord); // показываем, если под флагом не было бомбы
        }
    }

    public void pressRightButton(Coord coord){
        if (gameOver()){
            return;
        }
        flag.toggleFlaged(coord); // ставим или убираем флаг
    }

    private boolean gameOver(){
        return (gameState != GameState.PLAY);
    }

    public String getTable(){
        String fullTable = new String();
        fullTable = "<html><ul><li>"+table.printTime(0)+'\n'
                   +"<li>"+table.printTime(1)+'\n'
                   +"<li>"+table.printTime(2)+'\n'
                   +"<li>"+table.printTime(3)+'\n'
                   +"<li>"+table.printTime(4)+'\n';

        return fullTable;
    }
}
