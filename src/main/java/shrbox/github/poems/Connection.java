package shrbox.github.poems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
    public static String getURL(String targeturl) {
        try {
            URL url = new URL(targeturl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) return "";
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String re;
            String json = "";
            while ((re = bufferedReader.readLine()) != null) {
                json = json.concat(re.trim());
            }
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
