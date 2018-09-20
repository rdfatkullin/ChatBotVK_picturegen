package tk.pintonda;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.groups.CallbackServer;
import com.vk.api.sdk.objects.groups.responses.AddCallbackServerResponse;
import com.vk.api.sdk.objects.groups.responses.GetCallbackServersResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import tk.pintonda.handlers.CallbackRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static TransportClient transportClient;
    private static VkApiClient vk;
    private static GroupActor actor;
    private final static int VK_GROUP_ID = 0; //Your Group_ID here
    private final static String TOKEN = " ";  //Your Token here

    public static void main(String[] args) throws Exception {
        initialise();
        initialiseServer();
    }

    //Creating objects for API, generating actor based on token and group id
    public static void initialise(){
        transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        actor = new GroupActor(VK_GROUP_ID, TOKEN);
        System.out.println(TOKEN);
    }

    //Starting Jetty, adding our server to the list
    public static void initialiseServer() throws Exception {
        Integer port = 80;
        String host = "http://185.185.70.79/";
        HandlerCollection handlers = new HandlerCollection();

        GetCallbackServersResponse getCallbackServersResponse = vk.groups().getCallbackServers(actor).execute();
        CallbackServer callbackServer = isServerExist(getCallbackServersResponse.getItems(), host);
        CallbackRequestHandler callbackRequestHandler = new CallbackRequestHandler();

        handlers.setHandlers(new Handler[]{callbackRequestHandler}); //temp solution

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();

        if (callbackServer == null) {
            AddCallbackServerResponse addServerResponse = vk.groups().addCallbackServer(actor, host, "Pinbot").execute();
            Integer serverId = addServerResponse.getServerId();
            vk.groups().setCallbackSettings(actor, serverId).messageNew(true).execute();
        }

        server.join();
    }

    private static CallbackServer isServerExist(List<CallbackServer> items, String host) {
        for (CallbackServer callbackServer : items) {
            if (callbackServer.getUrl().equals(host)) {
                return callbackServer;
            }
        }

        return null;
    }

    public static VkApiClient getVk() {
        return vk;
    }

    public static GroupActor actor() {
        return actor;
    }
}
