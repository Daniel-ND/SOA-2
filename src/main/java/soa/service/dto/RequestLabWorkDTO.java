package soa.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestLabWorkDTO {
    private int id;
    private String name;
    private Integer coordinates;
    private Double minimalPoint;
    private Difficulty difficulty;
    private Integer author;
}
