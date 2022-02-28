package nam.nguyen;

import java.sql.Timestamp;

public class Message {
    int message_id;
    private String sender;
    private String receiver;
    private String content;
    private int parent_message_id;
    private boolean read;
    private Timestamp created_at;

    public Message(int message_id, String sender_id, String receiver_id, String content, int parent_message_id,
            boolean read, Timestamp created_at) {
        this.message_id = message_id;
        this.sender = sender_id;
        this.receiver = receiver_id;
        this.content = content;
        this.parent_message_id = parent_message_id;
        this.read = read;
        this.created_at = created_at;
    }

    public Message(String sender_id, String receiver_id, String content) {
        this.sender = sender_id;
        this.receiver = receiver_id;
        this.content = content;
    }

    public int getMessage_id() {
        return message_id;
    }

    public String getContent() {
        return content;
    }

    public String getSender_id() {
        return sender;
    }

    public String getReceiver_id() {
        return receiver;
    }

    public int getParent_message_id() {
        return parent_message_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public boolean getRead() {
        return read;
    }
}
