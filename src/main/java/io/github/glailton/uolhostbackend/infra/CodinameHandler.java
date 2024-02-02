package io.github.glailton.uolhostbackend.infra;

import io.github.glailton.uolhostbackend.models.enums.GroupType;
import io.github.glailton.uolhostbackend.services.CodinameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodinameHandler {
    
    private final CodinameService service;

    public CodinameHandler(CodinameService service) {
        this.service = service;
    }

    public String findCodiname(GroupType groupType) {
        String match = "";
        switch (groupType){
            case AVENGERS -> {
                match = service.getAvengersCodinameList().stream().findFirst().orElseThrow();
                service.getAvengersCodinameList().remove(match);
            }
            case JUSTICE_LEAGUE -> {
                match = service.getJusticeLeagueCodinameList().stream().findFirst().orElseThrow();
                service.getJusticeLeagueCodinameList().remove(match);
            }
        }

        return match;
    }
}
