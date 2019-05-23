package ch.ny.ui.detailsactivity;

import org.junit.Test;

import ch.ny.ui.homeactivity.R;

import static org.junit.Assert.*;

public class DetailsActivityTest {

    @Test
    public void When_getIcon_IsCalled_With_Clear_Returns_CorrectID() {
        // Arrange
        String Clearstatus = "Clear";
        String RainyStatus = "Rain";
        String CloudyStatus = "Clouds";
        // Act
        int id1 = DetailsActivity.getIcon(Clearstatus);
        int id2 = DetailsActivity.getIcon(RainyStatus);
        int id3 = DetailsActivity.getIcon(CloudyStatus);
        // Assert
        assertEquals(R.drawable.ic_wb_sunny_black_24dp, id1);
        assertEquals(R.drawable.ic_rainy_black_24dp, id2);
        assertEquals(R.drawable.ic_cloud_black_24dp, id3);
    }
}