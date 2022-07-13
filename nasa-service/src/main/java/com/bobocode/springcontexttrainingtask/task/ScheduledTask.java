package com.bobocode.springcontexttrainingtask.task;

import com.bobocode.springcontexttrainingtask.service.NasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTask {

    private final NasaService nasaService;

    // every day at 12 am
    @Scheduled(cron = "0 0 0 * * *")
    public void clearCache(){
        nasaService.clearCache();
    }
}
