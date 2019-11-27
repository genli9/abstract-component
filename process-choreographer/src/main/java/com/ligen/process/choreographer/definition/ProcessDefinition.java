package com.ligen.process.choreographer.definition;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessDefinition {

    private String bizCode;

    private String operation;

    @JsonProperty("nodes")
    private List<String> nodeNames;
}
