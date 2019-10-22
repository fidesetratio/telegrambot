package com.app;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
public class TelegramPooling extends TelegramLongPollingBot {
	private String username;
	private String token;
	private Environment env;
	
	
	
	public TelegramPooling(){
		super();
	}
	public TelegramPooling(String username,String token,Environment env){
		super();
		this.username =username;
		this.token = token;
		this.env = env;
		
	};
	
	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		 if (update.hasMessage() && update.getMessage().hasText()) {
			  long chat_id = update.getMessage().getChatId();
			  String message_text = update.getMessage().getText();
			  SendMessage message = new SendMessage();
			  String messageTxt = "";
			  boolean allowed = false;
			  if(message_text.toLowerCase().contains("help")){
				  allowed = true;
				  messageTxt = "Please use jenkins <command>";
			  }
			  if(message_text.toLowerCase().startsWith("jenkins")){
				  allowed = true;
				  try{
				  String commandline[] = message_text.split(" ");
				  String  jobName = commandline[1];
				  JenkinsCommand jenkinsCommand = new JenkinsCommand(env.getProperty("jenkins.url"), env.getProperty("jenkins.username"), env.getProperty("jenkins.password"), jobName);
				  jenkinsCommand.build();
				  messageTxt=jenkinsCommand.getResponse();
				  
				  if(messageTxt.trim().equals("")){
					  Random rand = new Random(System.currentTimeMillis());
					  long next = rand.nextLong();					  
					  messageTxt="Proses is running (Please check status "+jobName+")"+next;
					  File file = new File(Long.toString(next));
					  FileUtils.writeStringToFile(file, jobName, Charset.forName("utf-8"),true);
				  };
				  
				  
				  }catch(Exception e){
					  e.printStackTrace();
					  messageTxt = "Pastikan command line jenkins ngak ada masalah";
				  }
			  }
			  
			  if(message_text.toLowerCase().startsWith("status")){
				  allowed = true;
				  String commandline[] = message_text.split(" ");
				  String  jobName = commandline[1];
				  JenkinsCommand jenkinsCommand = new JenkinsCommand(env.getProperty("jenkins.url"), env.getProperty("jenkins.username"), env.getProperty("jenkins.password"), jobName);
				  jenkinsCommand.status();
				  messageTxt = jenkinsCommand.getResponse();
				  messageTxt = getLastnCharacters(messageTxt,400);
				  
			  }
			  
			  message.setChatId(chat_id);
              message.setText(messageTxt);
              if(allowed){
		              try {
						this.execute(message);
		              } catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
		              }
		        };
              
			  System.out.println(message_text);
			  
		 }
		
	}
	
	public String getLastnCharacters(String inputString, 
            int subStringLength){
int length = inputString.length();
if(length <= subStringLength){
return inputString;
}
int startIndex = length-subStringLength;
return inputString.substring(startIndex);
}
	
	

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		/*return "590721958:AAE19Qao1N6pgAdr0bc6v4is2zBNj73OLgY";*/
		
		return this.token;
	}

}
