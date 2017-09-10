package edujjasonhe.stanford.pharmascope;

import android.app.Application;

/**
 * Created by noodles on 9/10/17.
 */

public class Globals extends Application {

    private int CNT = 0;

    private int ROW = 2;

    private String devNum;

    public int getCNT() {
        return CNT;
    }

    public void incCNT() {
        CNT += 1;
    }

    public void setCNT(int n) {
        this.CNT = n;
    }

    public int getROW() {
        return ROW;
    }

    public void incROW() {
        ROW += 1;
    }

    public void setROW(int n) {
        this.ROW = n;
    }

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String dN) {
        this.devNum = dN;
    }

}
