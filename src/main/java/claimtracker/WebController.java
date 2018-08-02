package claimtracker;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @RequestMapping(value = "/tracker", method = RequestMethod.POST)
    @ResponseBody
    public String process(@RequestBody String payload) {
        VoyaRequestAndResponseBuilder requestAndResponseBuilder = new AlexaRequestAndResponseBuilder();
        VoyaResponse response = new VoyaControllerImpl().getResponse(requestAndResponseBuilder.buildRequest(payload));
        return requestAndResponseBuilder.buildResponse(response);
    }
}
