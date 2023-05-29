import config.Config;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            Config.initConfig();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot();
            telegramBotsApi.registerBot(bot);
            if (Config.isAUTOSTART()){
                bot.start();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
