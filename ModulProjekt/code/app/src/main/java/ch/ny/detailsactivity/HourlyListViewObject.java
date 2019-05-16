package ch.ny.detailsactivity;

public class HourlyListViewObject {

    private String time;
    private String status;
    private String temp;

    public HourlyListViewObject() {

    }

    public HourlyListViewObject(String time, String status, String temp) {
        this.time = time;
        this.status = status;
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
