package tk.pintonda.commands;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

//That message will be send if input is incorrect or not supported
public class UnknownCommand {
    public UnknownCommand(int userId) throws ClientException, ApiException {
        SendMessage smg = new SendMessage();
        smg.Send(userId, "Неизвестная комманда. Для генерации изображения прикрепите картинку и отправьте:\n" +
                "PS4, PSVR, PC, XBOX, SWITCH, SWITCH2 - на выбор\n" +
                "Основная надпись\nНадпись выше основной\nНадпись под углом");
    }
}
