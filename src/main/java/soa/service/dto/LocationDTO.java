package soa.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDTO {
    private Integer id;
    private Double x;
    private Integer y;
    private String name;
}
