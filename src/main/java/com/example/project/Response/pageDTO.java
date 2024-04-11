package com.example.project.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class pageDTO {
    private Object Data;
    private Long totalElement;
    private Integer pageNumber;
    private Integer totalPages;
}
