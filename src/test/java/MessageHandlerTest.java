import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import tk.pintonda.handlers.MessageHandler;

import java.util.ArrayList;

//Testing if input is correctly read
public class MessageHandlerTest {

    static String test = "GENERATE\n" +
            "https://www.sunhome.ru/i/wallpapers/200/planeta-zemlya-kartinka.960x540.jpg\n" +
            "PS4\n" +
            "это земля\n" +
            "картинка\n" +
            "это работает";

    public static void main(String[] args) throws ClientException, ApiException {
        messageNewTest();
    }

    public static void messageNewTest() throws ClientException, ApiException {
        MessageHandler handler = new MessageHandler();
        handler.handle(1111, test,  new ArrayList<>());
    }

}
