package cn.edu.whu.irlab.citation_context_data_analyze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author gcr19
 * @version 1.0
 * @date 2020/1/5 17:48
 * @desc 利用Grobid处理的业务
 **/
@Service
public class GrobidService {

    private static final String urlPrefix = "http://localhost:8070/api/";

    @Autowired
    private RestTemplate restTemplate;

    public String processCitation(String citation) {
        String url = urlPrefix + "processCitation";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("consolidateHeader", "1");
        valueMap.add("citations", citation);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(valueMap, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        return responseEntity.getBody();
    }

}
