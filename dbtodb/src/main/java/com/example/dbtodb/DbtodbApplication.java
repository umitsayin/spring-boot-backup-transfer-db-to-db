package com.example.dbtodb;

import com.example.dbtodb.model.User;
import com.example.dbtodb.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.TimeZone;


@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class DbtodbApplication {
    private final JobLauncher jobLauncher;
    private final Job job;
    private final UserRepository userRepository;

    @PostConstruct
    public void test1(){
        this.userRepository.save(new User(100,"John","john.1@gmail.com"));
        this.userRepository.save(new User(191,"Heaties","heat@outlook.com"));
        this.userRepository.save(new User(32,"Fiora","fiora@infotest.com"));
        this.userRepository.save(new User(545,"Gragas","gragas.loq@@gmail.com"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DbtodbApplication.class, args);
    }

    //runs every 1 hour
    @Scheduled(fixedDelay = 1000000,initialDelay = 1000)
    public void testBatch(){
        try{
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Istanbul"));

            String dateString = df.format(date);
            dateString = dateString.replace(" ","+");

            String[] dirBackup = {"zsh", "-c","mkdir ./src/main/resources/backup/backups/" + dateString};
            Process dirProcess = Runtime.getRuntime().exec(dirBackup);

            Thread.sleep(5000);

            String[] backupCommand =  {"zsh", "-c",
                    "docker exec 2aa /usr/bin/mysqldump "
                    +"-udbtodbtest2 -pdbtodbtest2 dbtodbtest2 >"
                    + " ./src/main/resources/backup/backups/"
                    +dateString + "/db.sql"};


            Process backupProcess = Runtime.getRuntime().exec(backupCommand);

            if(backupProcess.waitFor() == 0){ //success
                System.out.println("Process executed!");
            }else{
                System.out.println("Backup process unsuccessed!");
            }

            String[] truncateCommand =  {"zsh", "-c", "docker exec -i 2aa  mysql -udbtodbtest2  -pdbtodbtest2 dbtodbtest2 < ./src/main/resources/backup/config.sql"};
            Process process2 = Runtime.getRuntime().exec(truncateCommand);

            Thread.sleep(50000); //run batch after 5 minutes

        }catch (Exception e){
            System.out.println("Commands line process unsuccessed!");
        }

        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt",System.currentTimeMillis()).toJobParameters();
        try{
            jobLauncher.run(job,jobParameters);
        }catch (Exception e){
            System.out.println("Process unsuccessed!");
        }
    }

}
