package com.boa.jayaram.assignment.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.boa.jayaram.assignment.bean.Employee;

@Configuration
public class SpringBatchConfiguration {

	private static final String SELECT_QUERY = "SELECT FIRST_NAME, LAST_NAME, EMAIL_ID, PHONE_NUMBER FROM EMPLOYEE_DETAILS";

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobExecutionListener completionListener;

	@Autowired
	private DataSource dataSource;

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer()).listener(completionListener)
				.flow(process()).end().build();
	}

	@Bean
	public Step process() {
		return stepBuilderFactory.get("orderStep1").<Employee, Employee>chunk(10).reader(customReader())
				.writer(customWriter()).build();
	}

	public JdbcCursorItemReader<Employee> customReader() {
		JdbcCursorItemReader<Employee> jdbcReader = new JdbcCursorItemReader<>();
		jdbcReader.setDataSource(dataSource);
		jdbcReader.setSql(SELECT_QUERY);
		jdbcReader.setRowMapper((resultSet, rowNum) -> {
			int index = 1;
			Employee employee = new Employee();
			employee.setFirstName(resultSet.getString(index++));
			employee.setLastName(resultSet.getString(index++));
			employee.setEmailId(resultSet.getString(index++));
			employee.setPhoneNumber(resultSet.getLong(index++));
			return employee;
		});

		return jdbcReader;
	}

	public FlatFileItemWriter<Employee> customWriter() {
		FlatFileItemWriter<Employee> csvWriter = new FlatFileItemWriter<>();
		csvWriter.setResource(new FileSystemResource("Employee_batch_output.csv"));

		DelimitedLineAggregator<Employee> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<Employee> wrapper = new BeanWrapperFieldExtractor<>();
		wrapper.setNames(new String[] { "firstName", "lastName", "emailId", "phoneNumber" });

		aggregator.setFieldExtractor(wrapper);
		csvWriter.setLineAggregator(aggregator);
		return csvWriter;
	}

}
