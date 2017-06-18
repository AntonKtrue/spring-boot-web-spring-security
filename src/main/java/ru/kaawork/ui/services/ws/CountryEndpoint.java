package ru.kaawork.ui.services.ws;


import ru.kaawork.gs_producing_web_service.GetCountryRequest;
import ru.kaawork.gs_producing_web_service.GetCountryResponse;
import ru.kaawork.ui.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by user on 12.06.17.
 */
@Endpoint
public class CountryEndpoint {
    public static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-com.mkyong.service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }

}