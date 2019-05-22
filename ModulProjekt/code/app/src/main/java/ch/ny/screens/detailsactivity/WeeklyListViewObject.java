package ch.ny.screens.detailsactivity;

class WeeklyListViewObject {

    private String weekday;
    private String minTemp;
    private String maxTemp;

    public WeeklyListViewObject() {

    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String temp) {
        this.minTemp = temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String temp) {
        this.maxTemp = temp;
    }
}
