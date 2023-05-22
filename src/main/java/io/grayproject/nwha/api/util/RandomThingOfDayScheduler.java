package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.domain.RandomThingOfDay;
import io.grayproject.nwha.api.domain.Thing;
import io.grayproject.nwha.api.repository.RandomThingOfDayRepository;
import io.grayproject.nwha.api.repository.ThingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Component
public class RandomThingOfDayScheduler {
    private Thing randomThingOfDay = null;

    private final ThingRepository thingRepository;
    private final RandomThingOfDayRepository randomThingOfDayRepository;

    @Autowired
    public RandomThingOfDayScheduler(ThingRepository thingRepository,
                                     RandomThingOfDayRepository randomThingOfDayRepository) {
        randomThingOfDayRepository.findRandomThingOfDayToday().ifPresent(r -> {
            randomThingOfDay = r.getThing();
        });
        this.thingRepository = thingRepository;
        this.randomThingOfDayRepository = randomThingOfDayRepository;
    }

    private void forceUpdateRandomThing() {
        List<Thing> allThings = thingRepository
                .findAll()
                .stream()
                .filter(thing -> !thing.isArchived() && !thing.isRemoved())
                .toList();
        Random rand = new Random();
        randomThingOfDay = allThings.get(rand.nextInt(allThings.size()));
        RandomThingOfDay rtd = RandomThingOfDay
                .builder()
                .thing(randomThingOfDay)
                .build();
        randomThingOfDayRepository.save(rtd);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void updateRandomThing() {
        forceUpdateRandomThing();
    }

    public Thing getGetRandomThingOfDay() {
        if (randomThingOfDay == null || randomThingOfDay.isRemoved() || randomThingOfDay.isArchived()) {
            forceUpdateRandomThing();
        }
        return randomThingOfDay;
    }
}