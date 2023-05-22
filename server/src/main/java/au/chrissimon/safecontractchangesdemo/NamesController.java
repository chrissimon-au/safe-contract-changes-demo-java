package au.chrissimon.safecontractchangesdemo;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = "*")
public class NamesController {

    private NameRepository nameRepository;

    public NamesController(NameRepository nameRepository) {
        super();
        this.nameRepository = nameRepository;
    }

    @PostMapping("/names")
    public ResponseEntity<NameResponse> addName(@RequestBody AddNameRequest request) {

        FullNameDto fullName = request.getFullName();
        Name name = new Name(new FullName(fullName.getFirstName(), fullName.getLastName()));

        nameRepository.save(name);

        URI newNameUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(name.getId())
            .toUri();

        return ResponseEntity.created(newNameUri).body(name.toResponse());
    }

    @GetMapping("/names/{id}")
    public NameResponse getName(@PathVariable UUID id)
    {
        Name name = nameRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return name.toResponse();
    } 
}