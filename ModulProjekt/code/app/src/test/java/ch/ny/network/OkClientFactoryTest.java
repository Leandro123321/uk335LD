package ch.ny.network;

import org.junit.Test;

import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

public class OkClientFactoryTest {

    @Test
    public void When_getClient_IsCalledMultipleTimes_ReturnTheSameObject() {
        //Arrange & Act
        OkHttpClient client1 = OkClientFactory.getClient();
        OkHttpClient client2 = OkClientFactory.getClient();

        //Assert
        assertEquals(client1, client2);
    }
}