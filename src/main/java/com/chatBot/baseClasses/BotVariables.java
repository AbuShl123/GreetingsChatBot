package com.chatBot.baseClasses;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class BotVariables {
    public static boolean WELCOME_MESSAGE_IS_CHANGED;
    public static String WELCOMING_MESSAGE = "Kanalımıza hoş geldiniz, - $newMemberName!";
    public static String change_greet_message_text = "Ok, let's change the greet message. \nPlease write down a new welcoming message including $newMemberName word. \n\nFor example: \nWelcome our new chat member - $newMemberName!";


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private static int iLiner = 0;
    public static String openLine() {
        LocalTime time = LocalTime.now();
        return iLiner++ + ": [" + time.format(formatter) + "]: ";
    }
}
