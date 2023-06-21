package sample.model;

import java.time.LocalDateTime;

public class Reports {

    private int contactId;
    private int appointmentId;
    private String title;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;

    public Reports(
        int contactId,
        int appointmentId,
        String title,
        String type,
        LocalDateTime start,
        LocalDateTime end,
        int customerId
    ){
        this.contactId = contactId;
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
