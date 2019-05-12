package mem;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemBot extends TelegramLongPollingBot {

    private final static String testChatId = "275123378";
    private final static String targetChatId = "-1001403716764";

    private final static String chatId = testChatId;

    @Override
    public void onUpdatesReceived(List<Update> updates) {

        int size = updates.size();

        if (size == 1) {
            System.out.println("one");

            onUpdateReceived(updates.get(0));

        } else if (size > 1) {
            System.out.println("many");

            List<InputMedia> media = new ArrayList<>();

            for (Update update : updates) {

                if (update.hasMessage()) {
                    if (update.getMessage().hasVideo()) {

                        InputMediaVideo inputMediaVideo = new InputMediaVideo()
                                .setMedia(update.getMessage().getVideo().getFileId());

                        media.add(inputMediaVideo);

                    } else if (update.getMessage().hasPhoto()) {

                        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto()
                                .setMedia(update.getMessage().getPhoto().get(0).getFileId());

                        media.add(inputMediaPhoto);

                    }
                }

            }

            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(chatId);
            sendMediaGroup.setMedia(media);

            try {
                execute(sendMediaGroup); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Opa! Unhandled..");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Тык 1 \uD83D\uDC4D");
        inlineKeyboardButton1.setCallbackData("1");
//        inlineKeyboardButton1.set

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Тык 2 \uD83D\uDC4E");
        inlineKeyboardButton2.setCallbackData("0");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton1);
        keyboardButtonsRow.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        if (update.hasMessage()) {

            if (update.getMessage().hasVideo()) {

                SendVideo sendVideo = new SendVideo()
                        .setChatId(chatId)
                        .setVideo(update.getMessage().getVideo().getFileId());

                if (!("".equals(update.getMessage().getCaption()))) {
                    sendVideo.setCaption(update.getMessage().getCaption());
                }

                try {
                    execute(sendVideo.setReplyMarkup(inlineKeyboardMarkup));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasAnimation()) {

                SendAnimation sendAnimation = new SendAnimation()
                        .setChatId(chatId)
                        .setAnimation(update.getMessage().getAnimation().getFileId());

                if (!("".equals(update.getMessage().getCaption()))) {
                    sendAnimation.setCaption(update.getMessage().getCaption());
                }

                try {
                    execute(sendAnimation.setReplyMarkup(inlineKeyboardMarkup));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasPhoto()) {

                SendPhoto sendPhoto = new SendPhoto()
                        .setChatId(chatId)
                        .setPhoto(update.getMessage().getPhoto().get(0).getFileId());

                if (!("".equals(update.getMessage().getCaption()))) {
                    sendPhoto.setCaption(update.getMessage().getCaption());
                }

                try {
                    execute(sendPhoto.setReplyMarkup(inlineKeyboardMarkup));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasText()) {

                // TODO if we have links or entities
                //  like text formatting or link to .mp4

                SendMessage sendMessage = new SendMessage()
                        .setChatId(chatId)
                        .setText(update.getMessage().getText());

                try {
                    execute(sendMessage.setReplyMarkup(inlineKeyboardMarkup));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasInvoice()) {
                System.out.println("invoice");

            } else if (update.getMessage().hasEntities()) {
                System.out.println("entities");

            } else if (update.getMessage().hasVideoNote()) {
                System.out.println("videoNote");

            }

            System.out.println(update.getMessage().getMediaGroupId());
            System.out.println(update.getMessage().getText());
            System.out.println(update.getMessage().getPhoto());
            System.out.println(update.getMessage().getVideo());
            System.out.println(update.getMessage().getVideoNote());
            System.out.println(update.getMessage().getAnimation());
            System.out.println(update.getMessage().getDocument());
            System.out.println(update.getMessage().getSticker());
            System.out.println(update.getMessage().getEntities());
            System.out.println(update.getMessage().getCaption());
            System.out.println(update.getMessage().getInvoice());
            System.out.println(update.getMessage().getReplyToMessage());
            System.out.println(update.getMessage().getVenue());
            System.out.println(update.getMessage().getLocation());
            System.out.println();

        } else if (update.hasCallbackQuery()) {

            System.out.println("callBackQuery");

            // Set variables
            String call_id = update.getCallbackQuery().getId();
            String call_data = update.getCallbackQuery().getData();
            Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            String inline_message_id = update.getCallbackQuery().getInlineMessageId();

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery()
                    .setCallbackQueryId(call_id)
                    .setShowAlert(true)
                    .setText("you voted!");
            try {
                execute(answerCallbackQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


            if (call_data.equals("0")) {
                String answer = "Updated message text";
                EditMessageReplyMarkup new_message = new EditMessageReplyMarkup()
                        .setChatId(chat_id)
                        .setMessageId(message_id)
                        .setInlineMessageId(inline_message_id);
                InlineKeyboardButton dk1 = new InlineKeyboardButton();
                dk1.setText("label1");
                dk1.setCallbackData("change_the_label");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();

                rowInline.add(dk1);

                rowsInline.add(rowInline);

                markupInline.setKeyboard(rowsInline);
                new_message.setReplyMarkup(markupInline);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

//            System.out.println(call_data);
//            System.out.println(message_id);
//            System.out.println(chat_id);
//            System.out.println();

//            if (call_data.equals("update_msg_text")) {
//                String answer = "Updated message text";
//                EditMessageText new_message = new EditMessageText()
//                        .setChatId(chat_id)
//                        .setMessageId(toIntExact(message_id))
//                        .setText(answer);
//                try {
//                    execute(new_message);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }

//            try {
//                execute(new SendMessage()
//                        .setText(update.getCallbackQuery().getData())
//                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
    }

    private <T extends Serializable> void send(BotApiMethod<T> message) {
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // cardan_shaft_bot
        return "Cardan Shaft";
    }

    @Override
    public String getBotToken() {
        return "749396825:AAHRM6562KCQXM7rpC1BL6dShP2YD5-jLTU";
    }
}
