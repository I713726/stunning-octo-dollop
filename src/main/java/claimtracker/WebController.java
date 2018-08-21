package claimtracker;

import org.springframework.web.bind.annotation.*;

/**
 * This class handles mapping of requests. In the future, if multiple assistants (other than alexa) are implemented,
 * there could be different paths to the same application that would be used by different voice assistants.
 */
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
