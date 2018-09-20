package tk.pintonda.handlers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import com.vk.api.sdk.objects.photos.Photo;
import tk.pintonda.commands.SendMessage;
import tk.pintonda.commands.SendPicture;
import tk.pintonda.commands.UnknownCommand;
import tk.pintonda.printer.Printer;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    private static MessageHandler instance;
    static List <String> confirmPeng;

    public static MessageHandler getInstance() {
        //Singleton prevents creating new MessageHandler with every recieved message
        if (instance == null) {
            instance = new MessageHandler();

            //Creating list with acceptable commands
            confirmPeng = new ArrayList<>();
            confirmPeng.add("PC");
            confirmPeng.add("PS4");
            confirmPeng.add("PSVR");
            confirmPeng.add("XBOX");
            confirmPeng.add("SWITCH");
            confirmPeng.add("SWITCH2");
        }
        return instance;
    }

    //Getting URL of the widest installment of that picture on VK server (cant get original for some reason)
    public String getURL(Photo photo) {
        if (photo.getWidth() >= 1280) {
            return photo.getPhoto1280();
        } else if (photo.getWidth() >= 807) {
            return photo.getPhoto807();
        } else if (photo.getWidth() >= 604) {
            return photo.getPhoto604();
        } else if (photo.getWidth() >= 130) {
            return photo.getPhoto130();
        }
        return photo.getPhoto75();
    }

    public void handle(int userId, String body, List<MessageAttachment> attachIn) throws ClientException, ApiException {

        //Checking if attached picture is present
        if (attachIn != null && !attachIn.isEmpty() && attachIn.get(0).getType().getValue().equals("photo")){
            String[] lines = body.split("\\r?\\n");
            if (lines.length == 4 && confirmPeng.contains(lines[0].trim())){
                String url = getURL(attachIn.get(0).getPhoto());

                if (url == null){
                    new UnknownCommand(userId);
                }

                Printer printer = new Printer(lines, url);
                new SendPicture(userId, printer.getOutput());
                printer.getOutput().delete();
            } else {
                new UnknownCommand(userId);
            }
            return;
        }

        //Checking conditions for saying hello, if no attach is present
        body = body.toUpperCase();
        if (body.contains("ПРИВЕТ")) {
            SendMessage smg = new SendMessage();
            smg.Send(userId, "Привет, пользователь" + userId);
        } else {
            //If no known command is present, send this
            new UnknownCommand(userId);
        }
    }
}
