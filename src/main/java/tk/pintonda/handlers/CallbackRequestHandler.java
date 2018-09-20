package tk.pintonda.handlers;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

//Used for handle Callback Requests and send response "ok"
public class CallbackRequestHandler  extends AbstractHandler {

    private final CallbackApiHandler callbackApiHandler;

    public CallbackRequestHandler() {
        callbackApiHandler = new CallbackApiHandler();
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            setOKResponce(response, baseRequest);
            return;
        }

        //Getting request body, parsing it
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        boolean handled = callbackApiHandler.parse(body);

        if (!handled) {
            setOKResponce(response, baseRequest);
            return;
        }
        setOKResponce(response, baseRequest);
    }

    //It is necessary to send "ok" status 200 after each request from Callback
    public void setOKResponce(HttpServletResponse response, Request baseRequest){
        try {
            response.getWriter().println("ok");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
