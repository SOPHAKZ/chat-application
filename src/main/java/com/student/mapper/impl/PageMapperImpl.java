package com.student.mapper.impl;

import com.student.mapper.PageMapper;
import com.student.utils.PageDTO;
import com.student.utils.PaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageMapperImpl implements PageMapper {

    @Override
    public PageDTO pageDTO(Page<?> page) {
        if (page == null) {
            return null;
        }

        PageDTO pageDTO = new PageDTO();
        pageDTO.setContent(page.getContent());
        pageDTO.setPagination(paginationDTO(page));

        return pageDTO;
    }

    @Override
    public PaginationDTO paginationDTO(Page<?> page) {
        if (page == null) {
            return null;
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setNumberOfElements(page.getNumberOfElements());
        paginationDTO.setSize(page.getSize());
        paginationDTO.setTotalElements(page.getTotalElements());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setEmpty(page.isEmpty());
        paginationDTO.setFirst(page.isFirst());
        paginationDTO.setLast(page.isLast());

        return paginationDTO;
    }
}