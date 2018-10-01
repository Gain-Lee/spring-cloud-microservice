package com.handycar.licensingserver.clients;

import com.handycar.licensingserver.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {
    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganizaion(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");

        if (instances.size() == 0)
            return  null;

        String serverUri = instances.get(0).getUri().toString();
        String serviceUri = String.format("%s/v1/organizations/%s", serverUri, organizationId);
        System.out.println("!!!! Service URI : " + serviceUri);

        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
