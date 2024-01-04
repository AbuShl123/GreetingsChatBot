package com.chatBot.baseClasses;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BotVariables {
    private static final String default_manner = "Welcome our new chat member - $newMemberName!";

    public static boolean WELCOME_MESSAGE_IS_CHANGED;
    public static String WELCOMING_MESSAGE;
    public static String change_greet_message_text;

    static {
        WELCOMING_MESSAGE = default_manner;
        change_greet_message_text = "Ok, let's change the greeting message. \nWrite a new message including $newMemberName variable in place where the user's name should show up. \n\nFor example: \nWelcome our new chat member - $newMemberName!";
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private static int iLiner = 0;
    public static String openLine() {
        LocalTime time = LocalTime.now();
        return iLiner++ + ": [" + time.format(formatter) + "]: ";
    }

    public static void resetToDefault(){
        WELCOMING_MESSAGE = default_manner;
    }
}
