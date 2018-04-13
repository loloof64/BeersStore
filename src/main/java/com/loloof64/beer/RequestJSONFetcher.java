package com.loloof64.beer;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestJSONFetcher {

    public JsonStructure apiAdressToJSONStructure(String apiAddress) throws MalformedURLException, IOException {
        InputStream inputStream = null;
        JsonReader jsonReader = null;
        try {
            URL apiUrl = new URL(apiAddress);
            inputStream = apiUrl.openStream();

            jsonReader = Json.createReader(inputStream);
            return jsonReader.read();
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Bad api url : "+apiAddress);
        } catch (IOException e) {
            throw new IOException("A misc. problem occured with server connection : "+e.getMessage());
        } finally {
            try {
                if (inputStream != null) {inputStream.close();}
                if (jsonReader != null) {jsonReader.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
