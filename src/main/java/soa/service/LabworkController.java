package soa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import soa.service.dto.LabWorkDTO;

@RestController
@RequestMapping(value = "/labwork", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabworkController {
    private final LabworkService labworkService;

    @Autowired
    public LabworkController(LabworkService labworkService) {
        this.labworkService = labworkService;
    }

    @CrossOrigin
    @PutMapping("{labwork-id}/difficulty/increase/{steps-count}")
    public LabWorkDTO increaseDifficulty(
            @PathVariable(name = "labwork-id") int labworkId,
            @PathVariable(name = "steps-count") int stepsCount) throws Exception {
        return labworkService.increaseDifficulty(labworkId, stepsCount);
    }

    @CrossOrigin
    @PutMapping("{labwork-id}/difficulty/decrease/{steps-count}")
    public LabWorkDTO decreaseDifficulty(
            @PathVariable(name = "labwork-id") int labworkId,
            @PathVariable(name = "steps-count") int stepsCount) throws Exception {
        return labworkService.decreaseDifficulty(labworkId, stepsCount);
    }

}
