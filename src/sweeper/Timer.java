package sweeper;

class Timer {
    private int start;
    private int finish;
    Timer(){
        start = 0;
        finish = 0;
    }

    public void startTimer(){
        start = (int)System.currentTimeMillis();
    }

    public void stopTimer(){
        finish = (int)System.currentTimeMillis();
    }

    public int getTime(){
        return (finish-start)/1000; //делим на 1000, чтобы получить время в секундах
    }
}
