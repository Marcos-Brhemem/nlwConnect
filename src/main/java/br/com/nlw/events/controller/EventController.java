package br.com.nlw.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class EventController {
    @Autowired
    private EventService service;

    @PostMapping("/events")
    @Operation(summary = "Create a new event", description = "Adds a new event to the database and returns the created event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event list returned successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Event addNewEvent(@RequestBody Event newEvent){
        return service.addNewEvent(newEvent);
    }

    @GetMapping("/events")
    @Operation(summary = "List all events", description = "List all registered events.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event list all returned successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Event> getAllEvents(){
        return service.getAllEvents();
    }
    @GetMapping("/events/{prettyName}")
    @Operation(summary = "Search for an event by friendly name", description = "Returns the details of an event based on 'prettyName'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event found successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    
    public ResponseEntity<Event> getEventPrettyName(
        @Parameter(description = "Friendly event name", example = "nlw-java") 
        @PathVariable String prettyName) {

        Event evt = service.getByPrettyName(prettyName);

        if (evt != null) {
            return ResponseEntity.ok().body(evt);
        }
        return ResponseEntity.notFound().build();
    }
}
