/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skystationexampleapp;

import java.util.Random;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import java.io.IOException;

/**
 *
 * @author Declan Easton
 */
public class HTTPInterface {
    
    
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private String root;
    
    public HTTPInterface(String IP) {
        root = "http://" + IP;
    }
    
    public void setIP(String IP) {
        root = "http://" + IP;
    }
    
    public static class SkyStationUrl extends GenericUrl {
        public SkyStationUrl(String encodedUrl) {
            super(encodedUrl);
        }
        @Key
        public String fields;
    }
    
    public SkyStationData getData() throws IOException {
        HttpRequestFactory requestFactory =
        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
        SkyStationUrl url = new SkyStationUrl(root + "/allData");
        url.fields = "id,tags,title,url";
        HttpRequest request = requestFactory.buildGetRequest(url);
        HttpResponse a = request.execute();
        SkyStationData data = a.parseAs(SkyStationData.class);
        return data;
    }
}
