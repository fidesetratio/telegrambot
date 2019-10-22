package com.app;
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
					  messageTxt="Proses is running=12344";
				  }
				  }catch(Exception e){
					  e.printStackTrace();
					  messageTxt = "Pastikan command line jenkins ngak ada masalah";
				  }
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

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		/*return "590721958:AAE19Qao1N6pgAdr0bc6v4is2zBNj73OLgY";*/
		
		return this.token;
	}

}
