package soa.service;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import soa.service.dto.Difficulty;
import soa.service.dto.LabWorkDTO;
import soa.service.dto.RequestLabWorkDTO;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class LabworkService {

    private final ArrayList<Difficulty> difficultyList =  new ArrayList<>(Arrays.asList(Difficulty.values()));
    private final String BASE_URL = "https://localhost:8444/rest";

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;

    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
    public LabWorkDTO increaseDifficulty(int labworkId, int stepsCount) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(BASE_URL + "/lab-works/" + labworkId);
        LabWorkDTO labWork = null;
        HttpEntity<LabWorkDTO> response = null;
        try {
            labWork = restTemplate().getForObject(builder.toUriString(), LabWorkDTO.class);
            Difficulty difficulty = labWork.getDifficulty();
            int cur_index = difficultyList.indexOf(difficulty);
            int new_index = Math.min(cur_index + stepsCount, difficultyList.size() - 1);
            labWork.setDifficulty(difficultyList.get(new_index));
            HttpEntity<RequestLabWorkDTO> httpEntity = new HttpEntity<RequestLabWorkDTO>(getRequestLabworkDTO(labWork));
            response = restTemplate().exchange(BASE_URL + "/lab-works/", HttpMethod.PUT, httpEntity, LabWorkDTO.class);
        } catch (Exception e) {
            return null;
        }
        return response.getBody();
    }

    public LabWorkDTO decreaseDifficulty(int labworkId, int stepsCount) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(BASE_URL + "/lab-works/" + labworkId);
        LabWorkDTO labWork = null;
        HttpEntity<LabWorkDTO> response = null;
        try {
            labWork = restTemplate().getForObject(builder.toUriString(), LabWorkDTO.class);
            Difficulty difficulty = labWork.getDifficulty();
            int cur_index = difficultyList.indexOf(difficulty);
            int new_index = Math.max(cur_index - stepsCount, 0);
            labWork.setDifficulty(difficultyList.get(new_index));
            HttpEntity<RequestLabWorkDTO> httpEntity = new HttpEntity<RequestLabWorkDTO>(getRequestLabworkDTO(labWork));
            response = restTemplate().exchange(BASE_URL + "/lab-works/", HttpMethod.PUT, httpEntity, LabWorkDTO.class);
        } catch (Exception e) {
            return null;
        }
        return response.getBody();
    }

    private RequestLabWorkDTO getRequestLabworkDTO(LabWorkDTO labWorkDTO) {
        RequestLabWorkDTO requestLabWorkDTO = new RequestLabWorkDTO();
        requestLabWorkDTO.setId(labWorkDTO.getId());
        requestLabWorkDTO.setDifficulty(labWorkDTO.getDifficulty());
        requestLabWorkDTO.setAuthor(labWorkDTO.getAuthor().getId());
        requestLabWorkDTO.setCoordinates(labWorkDTO.getCoordinates().getId());
        requestLabWorkDTO.setName(labWorkDTO.getName());
        requestLabWorkDTO.setMinimalPoint(labWorkDTO.getMinimalPoint());
        return requestLabWorkDTO;
    }
}
