package com.telkomtasik.arnet.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseMetaData {
    private String totalPages;
    private String totalElements;
    private Integer number;
    private Integer size;
}
