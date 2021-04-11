package service;

import org.springframework.beans.factory.annotation.Autowired;
import repository.ActorRepository;

public class ActorFetcher {

    private ActorRepository actorRepository;

    @Autowired
    public ActorFetcher(ActorRepository actorRepository){
        this.actorRepository = actorRepository;
    }



}
