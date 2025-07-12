package de.awtools.registration.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;

import de.awtools.registration.Tags;
import de.awtools.registration.config.RegisterTestConfig;

@RegisterTestConfig
@WebAppConfiguration
@Transactional
@Rollback
class UserRolePrivilegeRepositoryTest {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @DisplayName("Repository test: Find all users")
    @Test
    @Tag(Tags.REPOSITORY)
    void userRolePrvilegeRelation() {
        PrivilegeEntity readPriv = PrivilegeEntity.PrivilegeBuilder.of("READ");
        PrivilegeEntity writePriv = PrivilegeEntity.PrivilegeBuilder.of("WRITE");
        PrivilegeEntity deletePriv = PrivilegeEntity.PrivilegeBuilder.of("DELETE");

        privilegeRepository.saveAll(Set.of(readPriv, writePriv, deletePriv));

        Iterable<PrivilegeEntity> privileges = privilegeRepository.findAll();
        assertThat(privileges).isNotNull();
        assertThat(privileges).hasSize(3);
        assertThat(privileges).contains(readPriv, writePriv, deletePriv);
    }

}
