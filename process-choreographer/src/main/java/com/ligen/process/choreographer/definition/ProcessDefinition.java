package com.ligen.process.choreographer.definition;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProcessDefinition {

    private String bizType;

    private String bizCode;

    @JsonProperty("nodes")
    private List<String> nodeNames;
}
