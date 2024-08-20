package com.student.mapper;


import com.student.utils.PageDTO;
import com.student.utils.PaginationDTO;

import org.springframework.data.domain.Page;

public interface PageMapper {

    PageDTO pageDTO(Page<?> page);

    PaginationDTO paginationDTO(Page<?> page);
}
