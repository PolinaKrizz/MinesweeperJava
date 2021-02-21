package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start(){
        flagMap = new Matrix(Box.CLOSED); // сначало всё закрыто
        countOfClosedBoxes = Ranges.getSize().x*Ranges.getSize().y;
    }

    Box get (Coord coord){
        return flagMap.get(coord);
    }

    void setOpened(Coord coord){
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void setFlaged(Coord coord){
        flagMap.set(coord, Box.FLAGED);
    }

    void setClose(Coord coord){
        flagMap.set(coord, Box.CLOSED);
    }

    void setBombed(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void toggleFlaged(Coord coord){
        switch (flagMap.get(coord)){
            case FLAGED: setClose(coord); break;
            case CLOSED: setFlaged(coord); break;
        }
    }

    int getCountOfClosedBoxes(){
        return countOfClosedBoxes;
    }

    void setOpenedToClosedBombBox(Coord coord){
        if (flagMap.get(coord) == Box.CLOSED){
            flagMap.set(coord, Box.OPENED);
        }
    }

    void setNoBomb(Coord coord){
        if (flagMap.get(coord) == Box.FLAGED){
            flagMap.set(coord, Box.NOBOMB);
        }
    }

}
