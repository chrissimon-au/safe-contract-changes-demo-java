package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name, UUID> {
    
}
