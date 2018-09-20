package tk.pintonda.commands;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import tk.pintonda.Main;

import java.util.Random;

public class SendMessage {

    public void Send(int vkId, String body) throws ClientException, ApiException {
        Main.getVk().messages().send(Main.actor())
                .randomId(new Random().nextInt(10000))
                .message(body)
                .peerId(vkId).execute();
    }

    public void Send(int vkId, String body, String attachId) throws ClientException, ApiException {
        Main.getVk().messages().send(Main.actor())
                .randomId(new Random().nextInt(10000))
                .message(body)
                .attachment(attachId)
                .peerId(vkId).execute();
    }
}
