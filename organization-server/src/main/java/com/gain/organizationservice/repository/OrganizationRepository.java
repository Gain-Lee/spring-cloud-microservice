package com.gain.organizationservice.repository;

import com.gain.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    Organization findByOrganizationId(String organizationId);
}
