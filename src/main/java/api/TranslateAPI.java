package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import utils.LoggerUtil;

public class TranslateAPI {

    public static String translateToEnglish(String spanishText) {

        try {

            String encodedText = URLEncoder.encode(spanishText, "UTF-8");

            String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=es&tl=en&dt=t&q=" + encodedText;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // ✅ FORCE UTF-8 decoding (FIXES Feijóo issue)
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String result = response.toString();

            // Extract translated text
            String translated = result.split("\"")[1];

            // Extra safety for encoding
            try {
                return new String(translated.getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception e) {
               // return translated;
                return new String(translated.getBytes("ISO-8859-1"), "UTF-8");

            }

        } catch (Exception e) {
        	LoggerUtil.log("Translation failed");
            return spanishText;
        }
    }
}

