package webapp.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import webapp.model.Projekt;
import webapp.model.Zadanie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
public class ZadanieService implements ZadanieServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(ZadanieService.class);
    private static final String RESOURCE_PATH = "/api/zadania";

    @Value("${rest.server.url}")
    private String serverUrl;

    private RestTemplate restTemplate;

    public ZadanieService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Projekt> getZadanie(Integer zadanieId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(zadanieId))
                .build()
                .toUri();
        logger.info("REQUEST -> GET {}", url);
        return Optional.ofNullable(restTemplate.getForObject(url, Projekt.class));
    }

    @Override
    public Zadanie setZadanie(Zadanie zadanie) {
        if (zadanie.getZadanieId() != null) { // modyfikacja istniejącego projektu
            String url = getUriStringComponent(zadanie.getZadanieId());
            logger.info("REQUEST -> PUT {}", url);
            restTemplate.put(url, zadanie);
            return zadanie;
        } else {//utworzenie nowego projektu
            // po dodaniu projektu zwracany jest w nagłówku Location - link do utworzonego zasobu
            HttpEntity<Zadanie> request = new HttpEntity<>(zadanie);
            String url = getUriStringComponent();
            logger.info("REQUEST -> POST {}", url);
            URI location = restTemplate.postForLocation(url, request);
            logger.info("REQUEST (location) -> GET {}", location);
            return restTemplate.getForObject(location, Zadanie.class);
            // jeżeli usługa miałaby zwracać utworzony obiekt a nie link to trzeba by użyć
            // return restTemplate.postForObject(url, projekt, Projekt.class);
        }

    }

    @Override
    public void deleteZadanie(Integer zadanieId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(zadanieId))
                .build()
                .toUri();
        logger.info("REQUEST -> DELETE {}", url);
        restTemplate.delete(url);
    }

    @Override
    public Page<Projekt> getZadania(Pageable pageable) {
        URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
        logger.info("REQUEST -> GET {}", url);
        return getPage(url, restTemplate);
    }

    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        return null;
    }

    private Page<Projekt> getPage(URI uri, RestTemplate restTemplate) {
        return ServiceUtil.getPage(uri, restTemplate,
                new ParameterizedTypeReference<RestResponsePage<Projekt>>() {});
    }

    private String getResourcePath() {
        return RESOURCE_PATH;
    }

    private String getResourcePath(Integer id) {
        return RESOURCE_PATH + "/" + id;
    }

    private String getUriStringComponent() {
        return serverUrl + getResourcePath();
    }

    private String getUriStringComponent(Integer id) {
        return serverUrl + getResourcePath(id);
    }

}
