/**
 *
 *  @author Smoczyñski Rafa³ S7973
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

public class Service {
	
	String country = null;
	
	Map<String, String> waluty = new HashMap<String, String>() {	
		{
			put("Poland", "PLN");
			put("Russia", "RUB");
			put("Germany", "EUR");
		}
	};
	
	public Service (String country)
	{
		this.country = country;
	}
	
	
	
	String getWeather (String city)
	{
		String weather = null;
		OpenWeatherMap owm = new OpenWeatherMap("f50b32447ea4ce13fba85fcee5707bb5");
		try {
		CurrentWeather cwd = owm.currentWeatherByCityName(city);
		weather = cwd.getRawResponse();
		}
		catch (IOException | JSONException e)
		{
			e.getMessage();
		}
		return weather;
	}
	
	Double getRateFor (String currency)
	{
		Double d = null;
		String waluta = waluty.get(country);
		try {
			URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + waluta);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String str = "";
			str = br.readLine();
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(str);
				JSONObject emp = (JSONObject) json.get("rates");
				d = (Double) emp.get(currency);
		} catch (Exception ex) 
		{
			ex.getMessage();
		}
		return d;
	}
	
	Double getNBPRate() {
		String[] NBP = {"http://www.nbp.pl/kursy/kursya.html", "http://www.nbp.pl/kursy/kursyb.html"};
		Double currency = null;
		
		for (int i = 0; i < NBP.length; i++) {
			
			HttpGet get = new HttpGet(NBP[i]);
		
			try {
				HttpClient httpClient = HttpClients.createDefault();
				HttpResponse response =  httpClient.execute(get);
				HttpEntity entity = response.getEntity();
				String eRates = EntityUtils.toString(entity);

				int idx = eRates.indexOf(waluty.get(country));
				if (idx != -1 && !country.equals("Poland")) {
					int indeksKursu = eRates.indexOf(">", idx + 10) + 1;
					currency = Double.valueOf(eRates.substring(indeksKursu, indeksKursu + 6).replace(',', '.'));
				} 
			} catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
		
		return currency;
	}
	
	public String getWiki(String city) {
			return "https://en.wikipedia.org/wiki/" + city;
	}
}
