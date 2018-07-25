package org.activiti;

import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class MyUnitTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Before
    public void startAync() {
        activitiRule.getProcessEngine().getProcessEngineConfiguration().getAsyncExecutor().start();
    }

    @Test
    @Deployment(resources = {"org/activiti/test/my-process.bpmn20.xml"})
    public void test() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            createAndCompleteProcess(i);
        }

    }

    private void createAndCompleteProcess(int i) throws InterruptedException {
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();

        Map<String, Object> processVariables = Collections.singletonMap("var1", (Object) ("TaskMDCValue" + i));

        activitiRule.getTaskService().complete(task.getId(), processVariables);

        // await async completion
        Thread.sleep(100);
    }

}
