package org.activiti;

import org.activiti.engine.impl.test.JobTestHelper;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyUnitTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"org/activiti/test/my-process.bpmn20.xml"})
    public void test() throws InterruptedException {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());

        activitiRule.getTaskService().complete(task.getId());

        JobTestHelper.waitForJobExecutorToProcessAllJobs(activitiRule.getProcessEngine().getProcessEngineConfiguration(), activitiRule.getManagementService(), 100000, 25);


    }

}
