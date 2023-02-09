package ru.itmentor.rest;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		String URL = "http://94.198.50.185:7081/api/users";
		User us3 = new User(5L, "James", "Brown", (byte) 15);
		Gson gson = new Gson();
		String s = gson.toJson(us3);
		User us3ex = new User(5L, "Thomas", "Shelby", (byte) 15);
		String s2 = gson.toJson(us3ex);
		String URL2 = "http://94.198.50.185:7081/api/users/3";
		StringBuilder result = new StringBuilder();
		RestTemplate template = new RestTemplate();
		System.out.println(s);
		ResponseEntity<String> resp = template.getForEntity(URL, String.class);
		System.out.println(resp.getBody());
		HttpHeaders head = resp.getHeaders();
		final List<String> cookie = head.get("Set-Cookie");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Set-Cookie", String.join(";", cookie));
		HttpEntity<String> request = new HttpEntity<>(s, headers);
		HttpEntity<String> putRequest = new HttpEntity<>(s2, headers);
		HttpEntity<String> putRequest2 = new HttpEntity<>(s2, headers);
		ResponseEntity<String> response1 = template.postForEntity(URL, request, String.class);
		ResponseEntity<String> response2 = template.exchange(URL, HttpMethod.POST, putRequest, String.class);
		ResponseEntity<String> response3 = template.exchange(URL2, HttpMethod.DELETE, putRequest2, String.class);
		result.append(response1.getBody());
		result.append(response2.getBody());
		result.append(response3.getBody());
		System.out.println(result);
	}

}
