package mju.com.iojo.Setting;

/**
 * Created by TaeHoon on 2017-06-04.
 */

public class Bookmark {
    private String top;
    private String bottom;

    public Bookmark(String top, String bottom){
        this.top = top;
        this.bottom = bottom;
    }

    public String getTop() {
        return top;
    }
    public String getBottom() {
        return bottom;
    }

    public void setTop(String t) {
        this.top = t;
    }
    public void setBottom(String t) {
        this.bottom = t;
    }
}
