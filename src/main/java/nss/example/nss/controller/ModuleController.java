package nss.example.nss.controller;

import nss.example.nss.entity.Module;
import nss.example.nss.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        // Add logic for handling parent and sub modules
        if ("sub".equals(module.getModuleType())) {
            // Assuming you have logic to fetch parent module by name
            Module parent = moduleRepository.findByModuleName(module.getParent().getModuleName());
            module.setParent(parent);
        } else {
            module.setParent(null);
        }
        return moduleRepository.save(module);
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Integer id) {
        Optional<Module> module = moduleRepository.findById(id);
        return module.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Integer id, @RequestBody Module moduleDetails) {
        Optional<Module> moduleOptional = moduleRepository.findById(id);
        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();
            module.setModuleName(moduleDetails.getModuleName());
            module.setModuleType(moduleDetails.getModuleType());
            module.setDescription(moduleDetails.getDescription());
            if ("sub".equals(moduleDetails.getModuleType())) {
                Module parent = moduleRepository.findByModuleName(moduleDetails.getParent().getModuleName());
                module.setParent(parent);
            } else {
                module.setParent(null);
            }
            Module updatedModule = moduleRepository.save(module);
            return ResponseEntity.ok(updatedModule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) {
        Optional<Module> module = moduleRepository.findById(id);
        if (module.isPresent()) {
            moduleRepository.delete(module.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
