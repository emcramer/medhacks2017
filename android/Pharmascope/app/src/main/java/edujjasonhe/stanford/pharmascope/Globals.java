package edujjasonhe.stanford.pharmascope;

import android.app.Application;

/**
 * Created by noodles on 9/10/17.
 */

public class Globals extends Application {
    private int historyRow = 1;
    private int orderRow = 1;
    private int ID = 0;
    private String devNum;

    public void setHistoryRow(int hRow) {
        this.historyRow = hRow;
    }

    public int getHistoryRow() {
        return historyRow;
    }

    public int incHistoryRow() {
        historyRow = historyRow + 1;
        return historyRow;
    }

    public int incOrderRow() {
        orderRow = orderRow + 1;
        return orderRow;
    }

    public int incID() {
        ID = ID + 1;
        return ID;
    }

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String dN) {
        this.devNum = dN;
    }
}
