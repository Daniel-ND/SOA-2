package soa.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {
    private Integer id;
    private String name;
    private int height;
    private LocationDTO location;
}
