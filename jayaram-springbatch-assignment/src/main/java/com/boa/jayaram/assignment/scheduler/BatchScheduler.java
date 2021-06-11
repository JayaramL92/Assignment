package com.boa.jayaram.assignment.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class BatchScheduler {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job processJob;

	@Scheduled(fixedDelayString = "300000")
	public void triggerBatching() {
		log.info("Started Triggering batch!");
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(processJob, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			log.error("Error while triggering batch job :: {} ", e);
		}
		log.info("Completed Triggering batch!");
	}

}
