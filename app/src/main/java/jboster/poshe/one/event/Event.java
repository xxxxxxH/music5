package jboster.poshe.one.event;

public class Event {

    public final Object[] message;

    public Event(Object... message) {
        this.message = message;
    }

    public Object[] getMessage() {
        return message;
    }
}
