package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

@SpringBootApplication
public class TelegramBots implements CommandLineRunner {
	
	@Autowired
	private Environment env;
	

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		ApiContextInitializer.init();
		String botName = env.getProperty("bot.name");
		String tokenName = env.getProperty("bot.token");
		TelegramBotsApi botsApi = new TelegramBotsApi();
		
		try {
			  botsApi.registerBot(new TelegramPooling(botName,tokenName,env));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	public static void main(String args[]){
		SpringApplication.run(TelegramBots.class, args);
	}
}
