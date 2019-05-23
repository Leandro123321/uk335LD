package ch.ny.app.detailsactivity.ui;

public interface DetailsVP {

    interface View {

    }

    interface Presenter {

        void setView(View view);

        int getIconId(String status);

        void getWeeklyInfo(String apiUrl);

    }

}
