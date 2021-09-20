package com.iptq.taskmanager.service;

import com.iptq.taskmanager.domain.Priority;
import com.iptq.taskmanager.domain.Process;
import com.iptq.taskmanager.exception.ProcessNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultTaskManagerTest {

    @Test
    public void policyEviction_whenCapacityIsExceeded() {
        TaskManager taskManager = new DefaultTaskManager(3);

        Process first = new Process(1, Priority.LOW);
        Process second = new Process(2, Priority.MEDIUM);
        Process third = new Process(3, Priority.MEDIUM);

        taskManager.addProcess(first);
        taskManager.addProcess(second);
        taskManager.addProcess(third);

        assertThat(taskManager.list().size()).isEqualTo(3);
        assertThat(taskManager.list().containsAll(Arrays.asList(first, second, third))).isTrue();

        Process fourth = new Process(4, Priority.MEDIUM);
        taskManager.addProcess(fourth);

        assertThat(taskManager.list().size()).isEqualTo(3);
        assertThat(taskManager.list().containsAll(Arrays.asList(first, second, third))).isTrue();

        taskManager.kill(first.getPID());
        taskManager.addProcess(fourth);

        assertThat(taskManager.list().size()).isEqualTo(3);
        assertThat(taskManager.list().containsAll(Arrays.asList(fourth, second, third))).isTrue();
    }

    @Test
    public void processRemovedFromTM_whenKilled() {
        TaskManager taskManager = new DefaultTaskManager(2);

        Process first = new Process(1, Priority.LOW);
        Process second = new Process(2, Priority.MEDIUM);

        taskManager.addProcess(first);
        taskManager.addProcess(second);

        assertThat(taskManager.list().size()).isEqualTo(2);

        taskManager.kill(2);

        assertThat(taskManager.list().size()).isEqualTo(1);

        Process process = taskManager.list().get(0);

        assertThat(process.getPriority()).isEqualTo(Priority.LOW);
        assertThat(process.getPID()).isEqualTo(1);
    }

    @Test
    public void allProcessesRemovedFromTM_whenKillAll() {
        TaskManager taskManager = new DefaultTaskManager(2);

        Process first = new Process(1, Priority.LOW);
        Process second = new Process(2, Priority.MEDIUM);

        taskManager.addProcess(first);
        taskManager.addProcess(second);

        assertThat(taskManager.list().size()).isEqualTo(2);

        taskManager.killAll();

        assertThat(taskManager.list().size()).isEqualTo(0);
    }

    @Test
    public void allProcessesGroupRemovedFromTM_whenKillGroup() {
        TaskManager taskManager = new DefaultTaskManager(4);

        Process first = new Process(1, Priority.LOW);
        Process second = new Process(2, Priority.MEDIUM);
        Process third = new Process(3, Priority.MEDIUM);
        Process fourth = new Process(4, Priority.HIGH);

        taskManager.addProcess(first);
        taskManager.addProcess(second);
        taskManager.addProcess(third);
        taskManager.addProcess(fourth);

        assertThat(taskManager.list().size()).isEqualTo(4);

        taskManager.killGroup(Priority.MEDIUM);

        assertThat(taskManager.list().size()).isEqualTo(2);
        assertThat(taskManager.list().containsAll(Arrays.asList(first, fourth))).isTrue();
    }

    @Test
    public void processNotFoundException_whenKillingUnexistingProcess() {
        TaskManager taskManager = new DefaultTaskManager(4);
        assertThrows(ProcessNotFoundException.class, () -> taskManager.kill(1));
    }

}