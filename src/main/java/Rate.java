/**
 * Created by dingwang on 2016/11/9.
 */
public class Rate {
    private String numRaters;
    private String average;

    public Rate(String numRaters, String average) {
        this.average = average;
        this.numRaters = numRaters;
    }

    public String getNumRaters() {
        return numRaters;
    }

    public String getAverage() {
        return average;
    }

    public void setNumRaters(String numRaters) {
        this.numRaters = numRaters;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
