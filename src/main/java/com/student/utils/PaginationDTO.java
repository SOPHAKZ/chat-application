package com.student.utils;

import lombok.Data;

@Data
public class PaginationDTO {
    private int numberOfElements;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean empty;
    private boolean first;
    private boolean last;

}