package tk.pintonda.handlers;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class CallbackApiHandler extends CallbackApi {

    @Override
    //Extracting message body, userId, attachments and sending it to Message handler
    public void messageNew(Integer groupId, Message message) {
        try {
            MessageHandler.getInstance().handle(message.getUserId(), message.getBody(), message.getAttachments());
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
