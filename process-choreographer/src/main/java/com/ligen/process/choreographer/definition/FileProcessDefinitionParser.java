package com.ligen.process.choreographer.definition;

import com.ligen.process.choreographer.JsonUtils;
import com.ligen.process.choreographer.ProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileProcessDefinitionParser implements ProcessDefinitionParser {

    private static final String Process_Definition_Directory_Prefix = "process/";

    private static final String Process_Definition_File_Suffix = ".json";

    @Override
    public List<ProcessDefinition> parse() {
        List<ProcessDefinition> definitions = new ArrayList<>();
        URL resource = this.getClass().getClassLoader().getResource(Process_Definition_Directory_Prefix);
        if (resource == null) {
            log.info("doesn't find any process definition directories");
            return definitions;
        }
        File processDirectory = new File(resource.getPath());
        File[] definitionFiles = processDirectory.listFiles();
        if (!processDirectory.isDirectory() || definitionFiles == null) {
            log.info("doesn't find any process definition files, check your config");
            return definitions;
        }
        for (File definitionFile : definitionFiles) {
            if (definitionFile.getName().endsWith(Process_Definition_File_Suffix)) {
                try {
                    List<ProcessDefinition> pds = JsonUtils.deSerializeList(definitionFile, ProcessDefinition.class);
                    definitions.addAll(pds);
                } catch (Exception e) {
                    log.error("deSerialize process definition file error, fileName : {}, err : {}", definitionFile.getName(), e);
                    throw new ProcessException("read process definition file error");
                }
            }
        }
        return definitions;
    }
}