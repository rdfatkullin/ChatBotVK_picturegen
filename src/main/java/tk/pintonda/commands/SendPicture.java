package tk.pintonda.commands;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import tk.pintonda.Main;

import java.io.File;
import java.util.List;

public class SendPicture {
    public SendPicture(int userId, File file){
        try {
            PhotoUpload serverResponse = Main.getVk().photos().getMessagesUploadServer(Main.actor()).execute();
            MessageUploadResponse uploadResponce = Main.getVk().upload().photoMessage(serverResponse.getUploadUrl(), file).execute();
            List<Photo> photoList = Main.getVk().photos().saveMessagesPhoto(Main.actor(), uploadResponce.getPhoto())
                    .server(uploadResponce.getServer())
                    .hash(uploadResponce.getHash())
                    .execute();

            Photo photo = photoList.get(0);
            String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();
            new SendMessage().Send(userId, "Вот твоя картинка, наслаждайся", attachId);

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
