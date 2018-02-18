package org.marklar;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    private static String CHARSET = "UTF-8";
    private static String HAPIKEY = "demo";

    public static void main(String[] args) throws Exception {

        // Print account details
        String accountDetails = getAccountDetails();
        System.out.println(accountDetails);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject accountDetailsJson = new JsonParser().parse(accountDetails).getAsJsonObject();
        System.out.println(gson.toJson(accountDetailsJson));


        // Print 20 contacts
//        InputStream contactsResponse = getContacts(hapikey);
//        printApiResponse(contactsResponse);

    }

    private static String getAccountDetails() throws Exception {

        String accountDetailsUrl = "https://api.hubapi.com/integrations/v1/me";
        String params = String.format("hapikey=%s",
                            URLEncoder.encode(HAPIKEY, CHARSET));

        return apiResponseToString(makeApiCallWithResponse(accountDetailsUrl, params));
    }

    private static String getContacts() throws Exception {

        String contactsUrl = "https://api.hubapi.com/contacts/v1/lists/all/contacts/all";
        String params = String.format("hapikey=%s",
            URLEncoder.encode(HAPIKEY, CHARSET));

        return apiResponseToString(makeApiCallWithResponse(contactsUrl, params));

    }



    private static InputStream makeApiCallWithResponse(String url, String params) throws Exception {

        URLConnection connection = new URL(url + "?" + params).openConnection();
        connection.setRequestProperty("Accept-Charset", CHARSET);

        return connection.getInputStream();
    }

    private static String apiResponseToString(InputStream response) {

        String responseString = "";
        try (Scanner scanner = new Scanner(response)) {
            responseString = scanner.useDelimiter("\\A").next();

        }
        return responseString;
    }
}
