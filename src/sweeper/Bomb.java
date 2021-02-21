package sweeper;

class Bomb {
    private Matrix bombMap;

    private int totalBombs;

    Bomb (int totalBombs){
        this.totalBombs = totalBombs;
    }

    void start(){
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get (Coord coord){
        return bombMap.get(coord);
    }

    private void placeBomb(){
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombMap.get(coord) == Box.BOMB)
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord){ // увеличивает числа вокруг бомбы
        for (Coord around : Ranges.getCoordsAround(coord)){
            if (bombMap.get(around) != Box.BOMB) {
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
            }
        }
    }

    int getTotalBombs(){
        return totalBombs;
    }

}
