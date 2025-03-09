package com.modulith.app.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Results {

    @JsonIgnore
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

}

