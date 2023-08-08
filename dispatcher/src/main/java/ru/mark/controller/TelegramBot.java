package ru.mark.controller;

import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j
public class TelegramBot extends TelegramWebhookBot {
  @Value("${bot.name}")
  private String botName;
  @Value("${bot.uri}")
  private String botUri;
  @Value("${bot.token}")
  private String botToken;
  private UpdateProcessor updateProcessor;
  public TelegramBot(UpdateProcessor updateProcessor){
    this.updateProcessor = updateProcessor;
  }
  @PostConstruct
  public void init(){
    updateProcessor.registerBot(this);
    try {
      var setWebHook = SetWebhook.builder()
              .url(botUri)
              .build();
      this.setWebhook(setWebHook);
    } catch (TelegramApiException e) {
      log.error(e);
    }
  }
  @Override
  public String getBotUsername() {
    return botName;
  }
  @Override
  public String getBotToken() {
    return botToken;
  }
  @Override
  public String getBotPath() {
    return "/update";
  }

  public void sendAnswerMessage(SendMessage message){
    if(message != null){
      try {
        execute(message);
      } catch (TelegramApiException e){
        log.error(e);
      }
    }
  }

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
    return null;
  }
}
