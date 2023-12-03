package com.sideagroup.academy.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}
