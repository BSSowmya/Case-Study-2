package com.javamaster.transport.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ResponseDto {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;


}
