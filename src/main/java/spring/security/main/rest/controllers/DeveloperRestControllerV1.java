package spring.security.main.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.security.main.rest.entities.Developer;

import java.util.ArrayList;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    private ArrayList<Developer> developers = new ArrayList<>(Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2L, "Sergey", "Sergeev"),
            new Developer(3L, "Peter", "Petrov")
    ).toList());


    @GetMapping
    public ArrayList<Developer> getAll(){
        return developers;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getById(@PathVariable Long id){
        return getAll().stream().filter(developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public HttpStatus create(@RequestBody Developer developer){
        try {
            if(developers.stream().anyMatch(dev -> dev.getId().equals(developer.getId())))
                return HttpStatus.CONFLICT;

            this.developers.add(developer);
            return HttpStatus.OK;

        } catch (Exception e){
            return HttpStatus.NOT_ACCEPTABLE;
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:delete')")
    public HttpStatus delete(@PathVariable Long id){
        if(developers.stream().anyMatch(developer -> developer.getId().equals(id))){
            developers.removeIf(developer -> developer.getId().equals(id));
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
