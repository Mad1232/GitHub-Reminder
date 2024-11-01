package com.coms309.demo2.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity // Add this annotation
@Table(name = "events") // Optional: specify the table name
public class Events {
    // relationships
    // one to one b/w events and medication, each event is supposed to have only one medication
    // one to one b/w events and pets, one event will be associated with one pet
    //relate events with medication id and pet id
    // the attributes need to be events_id, reminder_time, reminder_text, pet id and medication id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //had to do this to make it work
    @Column(name = "event_id")
    private Long event_id;

    @Column(name = "reminder_time")
    private LocalDateTime reminderTime;

    @Column(name = "reminder_text")
    private String reminderText;

    //One-to-One relationship to Medications(medicine)
    //@JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "medication_id", referencedColumnName = "id", nullable = false)
    private Medication medication;

    //One-to-One relationship to Pets
   // @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id", nullable = false)
    private Pet pet;

    // Getters and setters
    public Long getEventsId() {
        return event_id;
    }

    public void setEventsId(Long eventsId) {
        this.event_id = eventsId;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderText() {
        return reminderText;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

}
