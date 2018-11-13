package io.mosip.registration.processor.scanner.virusscanner.config;

import static org.junit.Assert.assertEquals;


import org.springframework.batch.core.repository.JobRestartException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.mosip.registration.processor.scanner.virusscanner.config.VirusScannerBatchJobConfig;
import io.mosip.registration.processor.scanner.virusscanner.tasklet.VirusScannerTasklet;
import io.mosip.registration.processor.scanner.virusscanner.PacketVirusScannerJobApplication;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PacketVirusScannerJobApplication.class)
@ContextConfiguration(classes = { VirusScannerBatchJobConfig.class })
public class VirusScannerJobTest {

	@Autowired
	private JobLauncher jobLauncher;	
	
	@Autowired
	private Job virusScannerJob;	
	
	@MockBean
	public VirusScannerTasklet ftpScannerTasklet;
	
	@Test
	public void virusScannerJobTest() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		JobExecution jobExecution = jobLauncher.run(virusScannerJob, jobParameters);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
}
