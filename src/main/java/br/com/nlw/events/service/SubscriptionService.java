package br.com.nlw.events.service;

import br.com.nlw.events.model.Subscription;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.dto.SubscriptionRakingByUser;
import br.com.nlw.events.dto.SubscriptionRankingItem;
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
        
        User indicator = null;
        if (userId != null) {
            indicator = userRepository.findById(userId).orElse(null);
        if (indicator == null){
            throw new UserIndicatorNotFoundException("Usuário " + userId + " indicador não existe.");
        }
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

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        Event event = eventRepository.findByPrettyname(prettyName);
        if (event == null){
            throw new EventNotFoundException("Ranking do evento não " + prettyName + "existe.");
        }
        return subscriptionRepository.generateRanking(event.getEventID());
        
    }
    public SubscriptionRakingByUser getRakingByUser(String prettyName, Integer userId){
        List <SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(i->i.userId().equals(userId)).findFirst().orElse(null);
        if (item == null){
            throw new UserIndicatorNotFoundException("Não há inscrições com a indicação do usuário " + userId);
        }
        
        Integer posicao = IntStream.range(0, ranking.size()).filter(pos -> ranking.get(pos).userId().equals(userId)).findFirst().getAsInt();

        return new SubscriptionRakingByUser(item, posicao+1);           
    }
}
