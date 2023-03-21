package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);

    Role getRoleById(Long id);
}
