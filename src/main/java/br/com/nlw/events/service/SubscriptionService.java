package br.com.nlw.events.service;

import br.com.nlw.events.model.Subscription; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepository;
import br.com.nlw.events.repository.SubscriptionRepository;
import br.com.nlw.events.repository.UserRepository;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId){
        
        // recuperar o evento pelo nome 
        
        Event event = eventRepository.findByPrettyname(eventName);
        if (event == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe." );
        }

        User userRecovered = userRepository.findByEmail(user.getEmail());
        if (userRecovered == null) {
            userRecovered = userRepository.save(user);
        }
        
        User indicator = userRepository.findById(userId).orElse(null);
        if (indicator == null){
            throw new UserIndicatorNotFoundException("Usuário " + userId + " indicador não existe.");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(userRecovered);
        subscription.setIndication(indicator);

        Subscription tmpSubscription = subscriptionRepository.findByEventAndSubscriber(event, userRecovered);
        if (tmpSubscription != null){
            throw new SubscriptionConflictException("Já existe uma inscrição para o usuário " + userRecovered.getName() + " no evento: " + event.getTitle()); 
        }

        Subscription res = subscriptionRepository.save(subscription);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent().getPrettyname() + "/" + res.getSubscriber().getId());
    }
}
