package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static String YOURLS_TOKEN;
    private static String YOURLS_URL;
    private static String BOT_TOKEN;
    private static String BOT_NAME;
    private static Long CHANNEL_ID;
    private static int UPDATE_PERIOD;
    private static boolean TEST_LAUNCH;
    private static boolean AUTOSTART;
    private static String DB_HOST;
    private static String DB_USER;
    private static String DB_PASSWORD;

    InputStream getInputStream() {
        return getClass().getResourceAsStream("/config.properties");
    }
    public static void initConfig() {
        try {
            Properties properties = new Properties();
            properties.load(new Config().getInputStream());
            YOURLS_TOKEN = properties.getProperty("YOURLS_TOKEN");
            YOURLS_URL = properties.getProperty("YOURLS_URL");
            BOT_NAME = properties.getProperty("BOT_NAME");
            BOT_TOKEN = properties.getProperty("BOT_TOKEN");
            CHANNEL_ID = Long.parseLong(properties.getProperty("CHANNEL_ID"));
            UPDATE_PERIOD = Integer.parseInt(properties.getProperty("UPDATE_PERIOD")) * 60000;
            TEST_LAUNCH = Boolean.parseBoolean(properties.getProperty("TEST_LAUNCH"));
            AUTOSTART = Boolean.parseBoolean(properties.getProperty("AUTOSTART"));
            DB_HOST = properties.getProperty("DB_HOST");
            DB_USER = properties.getProperty("DB_USER");
            DB_PASSWORD = properties.getProperty("DB_PASSWORD");
            if (isTestLaunch()) {
                System.out.println("Warning! Application is running in test mode.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getYourlsToken() {
        return YOURLS_TOKEN;
    }

    public static boolean isAUTOSTART() {
        return AUTOSTART;
    }

    public static String getYourlsUrl() {
        return YOURLS_URL;
    }

    public static String getBotToken() {
        return BOT_TOKEN;
    }

    public static String getBotName() {
        return BOT_NAME;
    }

    public static Long getChannelId() {
        return CHANNEL_ID;
    }

    public static int getUpdatePeriod() {
        return UPDATE_PERIOD;
    }

    public static String getDbHost() {
        return DB_HOST;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }
    public static boolean isTestLaunch() {
        return TEST_LAUNCH;
    }

}

