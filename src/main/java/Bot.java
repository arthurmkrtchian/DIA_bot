import config.Config;
import models.Post;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bot extends TelegramLongPollingBot {
    static long serviceChatID = 512723941L;
    static long channelChatID = Config.getChannelId();
    static int period = Config.getUpdatePeriod();
    static Timer timer = new Timer();
    static LocalTime now;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if (update.getMessage().getChatId() == serviceChatID) {
                if (message.equals("/start")) {
                    start();
                }
            }
            if (update.getMessage().getChatId() == serviceChatID && message.equals("/stop")) {
                timer.cancel();
                sendMessage(update.getMessage().getChatId(), "Bot is having a little nap...");
                System.out.println("Application is stopped");
            }
            if (update.getMessage().getChatId() == serviceChatID && message.equals("/shutdown")) {
                sendMessage(update.getMessage().getChatId(), "The application is off");
                Runtime.getRuntime().exit(0);
            }
        } else {
            sendMessage(update.getMessage().getChatId(), "Access denied.");
        }
    }

    public void start() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                now = LocalTime.now();
                if (now.isAfter(LocalTime.parse("06:00:00")) && now.isBefore(LocalTime.parse("19:00:00"))) {
                    Processor processor = new Processor();
                    List<Post> docs = processor.execute();
                    int counter = 0;
                    for (Post document : docs) {
                        counter++;
                        if (counter > 16) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!Config.isTestLaunch()) {
                            sendMessage(channelChatID, document.toString());
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 0, period);
    }

    @Override
    public String getBotUsername() {
        return Config.getBotName();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return Config.getBotToken();
    }

    public void sendMessage(Long chat_id, String message) {
        SendMessage answer_message = new SendMessage();
        answer_message.setChatId(chat_id);
        answer_message.setText(message);
        try {
            execute(answer_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("The massage is not sent:\n" + answer_message);
            System.out.println("Chat_ID: " + chat_id);
        }
    }
}