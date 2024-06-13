package nss.example.nss.service;

import nss.example.nss.entity.Module;
import nss.example.nss.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module saveModule(Module module) {
        if ("parent".equalsIgnoreCase(module.getModuleType())) {
            module.setParent(null);
        } else if ("sub".equalsIgnoreCase(module.getModuleType())) {
            Module parentModule = moduleRepository.findByModuleName(module.getParent().getModuleName());
            if (parentModule != null) {
                module.setParent(parentModule);
            } else {
                throw new RuntimeException("Parent module not found");
            }
        }
        return moduleRepository.save(module);
    }

    public Optional<Module> getModuleById(Integer id) {
        return moduleRepository.findById(id);
    }

    public void deleteModule(Integer id) {
        moduleRepository.deleteById(id);
    }
}

