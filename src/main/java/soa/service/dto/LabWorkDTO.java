package soa.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class LabWorkDTO {
    private int id;
    private String name;
    private CoordinatesDTO coordinates;
    private String creationDate;
    private Double minimalPoint;
    private Difficulty difficulty;
    private PersonDTO author;
}
