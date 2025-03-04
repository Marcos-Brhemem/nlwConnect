package br.com.nlw.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.dto.ErrorMessage;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.model.User;
import br.com.nlw.events.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    @PostMapping({"/subscription/{prettyName}","/subscription/{prettyName}/{userId}"})
    @Operation(summary = "Create a new subscription", description = "Creates a new subscription for an event. If the user ID is provided, the subscription will be associated with that user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription successfully created"),
        @ApiResponse(responseCode = "404", description = "Event or user not found"),
        @ApiResponse(responseCode = "409", description = "Subscription conflict"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<?> createSubscription(
        @Parameter(description = "Friendly name of the event", example = "nlw-java") 
        @PathVariable String prettyName, 
        
        @RequestBody User subscriber, 
        
        @Parameter(description = "User ID (optional)", example = "123", required = false) 
        @PathVariable(required = false) Integer userId) {

        try {
            SubscriptionResponse res = service.createNewSubscription(prettyName, subscriber, userId);
            if (res != null) {
                return ResponseEntity.ok(res);
            }
        } catch(EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        } catch (SubscriptionConflictException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));
        } catch (UserIndicatorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    @Operation(summary = "Get event ranking", description = "Retrieves the top 3 ranking positions for a given event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ranking retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<?> generateRankingByEvent(
        @Parameter(description = "Friendly name of the event", example = "nlw-java") 
        @PathVariable String prettyName) {
        
        try {
            return ResponseEntity.ok(service.getCompleteRanking(prettyName).subList(0, 3));
        } catch(EventNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    @Operation(summary = "Get event ranking for a specific user", description = "Retrieves the ranking position of a specific user in an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User ranking retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Event or user not found")
    })
    public ResponseEntity<?> generateRankingByEventAndUser(
        @Parameter(description = "Friendly name of the event", example = "nlw-java") 
        @PathVariable String prettyName, 
        
        @Parameter(description = "User ID", example = "123") 
        @PathVariable Integer userId) {
        
        try {
            return ResponseEntity.ok(service.getRakingByUser(prettyName, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }
}
