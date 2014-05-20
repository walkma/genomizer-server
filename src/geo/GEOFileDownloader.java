package geo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GEOFileDownloader {



	public static String getSRAFromDir(String url) throws IOException {
		url = url + "/";
		// This will get the subfolders from the url
		InputStream is = new URL(url).openStream();
		byte[] buffer = new byte[1024];
		int bytesRead = -1;
		StringBuilder page = new StringBuilder(1024);
		while ((bytesRead = is.read(buffer)) != -1) {
			page.append(new String(buffer, 0, bytesRead));
		}
		String fileInfo = page.toString();

		// Pick out the last word in fileInfo, that is the filename that we want
		String[] temp = fileInfo.split(" ");
		// Then paste the string together
		url = url + temp[temp.length - 1].trim() + "/"
				+ temp[temp.length - 1].trim() + ".sra";

		is.close();

		return url;
	}

	public static void downloadFile(String url, String tempFileLoc) throws MalformedURLException, IOException {

        // new HttpClient
        HttpClientBuilder hcBuilder = HttpClients.custom();
        CloseableHttpClient httpClient = hcBuilder.build();

        // post header
		HttpPost httpPost = new HttpPost("scratchy.cs.umu.se:8000/download_from_geo.php?url=" + url + "&path=" + tempFileLoc);

		try {
            HttpResponse response;
            // execute HTTP post request
            response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            System.out.println("Response code: "
                    + response.getStatusLine().getStatusCode());
            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                System.out.println("Response: " + responseStr);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}
}
