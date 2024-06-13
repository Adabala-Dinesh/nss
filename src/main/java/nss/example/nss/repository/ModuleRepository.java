package nss.example.nss.repository;

import nss.example.nss.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Module findByModuleName(String moduleName);
}
