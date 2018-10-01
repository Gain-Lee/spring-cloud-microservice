package com.gain.organizationservice.services;

import com.gain.organizationservice.model.Organization;
import com.gain.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findByOrganizationId(organizationId);
    }

    public void saveOrg(Organization org){
        org.setOrganizationId( UUID.randomUUID().toString());

        orgRepository.save(org);

    }

    public void updateOrg(Organization org){
        orgRepository.save(org);
    }

    public void deleteOrg(Organization org){
        orgRepository.deleteById( org.getOrganizationId());
    }
}
