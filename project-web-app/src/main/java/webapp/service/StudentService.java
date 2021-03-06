package webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
public class StudentService implements StudentServiceInterface{

    private static final Logger logger = LoggerFactory.getLogger(ZadanieService.class);
    private static final String RESOURCE_PATH = "/api/studenci";

    @Value("${rest.server.url}")
    private String serverUrl;

    private RestTemplate restTemplate;

    @Autowired
    public StudentService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Student> getStudent(Integer studentId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(studentId))
                .build()
                .toUri();
        logger.info("REQUEST -> GET {}", url);
        return Optional.ofNullable(restTemplate.getForObject(url, Student.class));
    }

    @Override
    public Student setStudent(Student student) {
        if (student.getStudent_id() != null) { // modyfikacja istniejącego projektu
            String url = getUriStringComponent(student.getStudent_id());
            logger.info("REQUEST -> PUT {}", url);
            restTemplate.put(url, student);
            return student;
        } else {//utworzenie nowego projektu
            // po dodaniu projektu zwracany jest w nagłówku Location - link do utworzonego zasobu
            HttpEntity<Student> request = new HttpEntity<>(student);
            String url = getUriStringComponent();
            logger.info("REQUEST -> POST {}", url);
            URI location = restTemplate.postForLocation(url, request);
            logger.info("REQUEST (location) -> GET {}", location);
            return restTemplate.getForObject(location, Student.class);
            // jeżeli usługa miałaby zwracać utworzony obiekt a nie link to trzeba by użyć
            // return restTemplate.postForObject(url, projekt, Projekt.class);
        }

    }

    @Override
    public void deleteStudent(Integer studentId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(studentId))
                .build()
                .toUri();
        logger.info("REQUEST -> DELETE {}", url);
        restTemplate.delete(url);
    }

    @Override
    public Page<Student> getStudenci(Pageable pageable) {
        URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
        logger.info("REQUEST -> GET {}", url);
        return getPage(url, restTemplate);
    }

    @Override
    public Page<Student> searchByNazwa(String nazwa, Pageable pageable) {
        return null;
    }

    private Page<Student> getPage(URI uri, RestTemplate restTemplate) {
        return ServiceUtil.getPage(uri, restTemplate,
                new ParameterizedTypeReference<RestResponsePage<Student>>() {});
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
