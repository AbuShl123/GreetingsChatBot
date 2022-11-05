package com.chatBot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class GreetingsChatBot extends TelegramLongPollingBot {
    static {
        System.out.println("PROJECT STARTED. RUNNING UP CHAT BOT");
    }
    @Override
    public String getBotUsername() {
        return "@greetings_chat_bot";
    }

    @Override
    public String getBotToken() {
        return "5690508685:AAF89b0K1AgKEfTK2mtkrUIICf8QJdWPoZs";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getNewChatMembers().get(0) != null){
            handleChatJoinedUser(update.getMessage());
        }
    }

    @SneakyThrows
    public void handleChatJoinedUser(Message message) {
        System.out.println("handleChatJoinedUser() method started its work");
        List<User> newUsers = message.getNewChatMembers();
        User user = newUsers.get(0);
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        StringBuilder newMemberName = new StringBuilder();
        newMemberName.append(firstName);
        System.out.println(newMemberName + " was added");
        if (lastName != null) {
            newMemberName.append(" ").append(lastName);
        }
        execute(
                SendMessage.builder()
                .chatId(message.getChatId())
                .text("Please, welcome our new chat member -  " + newMemberName + "!")
                .build()
        );
        System.out.println("That was executed after SendMessage");
    }

    @SneakyThrows
    public static void main(String[] args) {
        GreetingsChatBot bot = new GreetingsChatBot();
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }
}
