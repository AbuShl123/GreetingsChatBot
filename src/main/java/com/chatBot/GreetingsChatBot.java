package com.chatBot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


import java.util.List;
import java.util.Optional;

import static com.chatBot.baseClasses.BotVariables.*;


public class GreetingsChatBot extends TelegramLongPollingBot {
    private static long previous_chat_id;

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
        System.out.println(openLine() + "update received.");
        if (update.hasMessage()) {
            System.out.println(openLine() + "update has message.");
            System.out.println(openLine() + "is from: " + update.getMessage().getFrom().getFirstName() + ".");
            if (update.getMessage().hasText() && update.getMessage().hasEntities()){
                System.out.println(openLine() + "handling the message which is possibly a command.");
                handleCommandMessage(update.getMessage());
            }
            else if (update.getMessage().hasText()) {
                System.out.println(openLine() + "handling the message with text only.");
                handleSimpleMessage(update.getMessage());
            }
            else if (update.getMessage().getNewChatMembers().get(0) != null){
                System.out.println(openLine() + ": " + "Handling newly added user");
                handleChatJoinedUser(update.getMessage());
            }
            System.out.println(openLine() + "handling finished.");
        }
    }

    @SneakyThrows
    private void handleSimpleMessage(Message message) {
        if (WELCOME_MESSAGE_IS_CHANGED && previous_chat_id==message.getChatId()){
            if (!message.getText().contains("$newMemberName")){
                execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text("Incorrect greeting message:( \nYour text have to include $newMemberName variable.")
                        .build());
            } else {
                execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text("Great Job! Operation was done successfully.").build());
                WELCOMING_MESSAGE = message.getText();
                WELCOME_MESSAGE_IS_CHANGED = false;
            }
        }
    }

    @SneakyThrows
    private void handleCommandMessage(Message message) {
        Optional<MessageEntity> messageEntity = message.getEntities().stream()
                .filter(e -> "bot_command".equals(e.getType())).findFirst();
        if (!messageEntity.isPresent()) {
            return;
        }
        WELCOME_MESSAGE_IS_CHANGED = false;
        String command = message.getText().substring(messageEntity.get().getOffset(), messageEntity.get().getLength());
        switch (command){
            case "/change_greet_message":
            case "/change_greet_message@greetings_chat_bot":
                execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(change_greet_message_text)
                        .build());
                previous_chat_id = message.getChatId();
                WELCOME_MESSAGE_IS_CHANGED = true;
                break;
            case "/show_welcome_message":
            case "/show_welcome_message@greetings_chat_bot":
                execute(SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(WELCOMING_MESSAGE)
                        .build());
                break;
        }
    }

    @SneakyThrows
    public void handleChatJoinedUser(Message message) {
        List<User> newUsers = message.getNewChatMembers();
        User user = newUsers.get(0);
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        StringBuilder newMemberName = new StringBuilder();
        newMemberName.append(firstName);
        System.out.println(openLine() + newMemberName + " was added.");
        if (lastName != null) {
            newMemberName.append(" ").append(lastName);
        }
        execute(
                SendMessage.builder()
                .chatId(message.getChatId())
                .text(WELCOMING_MESSAGE.replace("$newMemberName", newMemberName))
                .build()
        );
        System.out.println(openLine());
    }

    @SneakyThrows
    public static void main(String[] args) {
        GreetingsChatBot bot = new GreetingsChatBot();
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }
}
