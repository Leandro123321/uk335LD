package ch.ny.network;

import okhttp3.OkHttpClient;

public class OkClientFactory {

    static OkHttpClient client;

    public static OkHttpClient getClient() {
        if(client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

}
