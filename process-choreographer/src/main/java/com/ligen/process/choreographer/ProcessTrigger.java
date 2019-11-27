package com.ligen.process.choreographer;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProcessTrigger {

    @Autowired
    private ProcessRegister processRegister;

    public void fire(String bizCode, String operation, AbstractProcessContext context) throws ProcessException {
        List<Node> nodes = processRegister.findProcess(bizCode, operation);
        if (nodes == null || nodes.size() == 0) {
            log.error("doesn't find any processes by bizCode: {}， operation: {}", bizCode, operation);
            throw new ProcessException("doesn't find any processes by bizCode or operation");
        }
        context.setBizCode(bizCode);
        context.setOperation(operation);
        for (Node n : nodes) {
            try {
                n.execute(context);
            } catch (ProcessException e) {
                throw e;
            } catch (Exception e) {
                log.error("process execution error, biz_code: {}, operation: {}, nodeName: {}, err: {}",
                        bizCode, operation, n.nodeName(), Throwables.getStackTraceAsString(e));
                throw new ProcessException(e);
            }
        }
    }
}
