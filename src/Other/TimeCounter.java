package Other;

public class TimeCounter {
    private long startTime;
    private int timeInSec = 0;

    public void reset(){
        timeInSec = 0;
    }
    public void Start(){
        startTime = System.currentTimeMillis();
    }
    public void update(){
        long time = System.currentTimeMillis() - startTime;
        timeInSec = (int) time /1000;
    }

    public int getTimeInSec(){
        return timeInSec;
    }
}
