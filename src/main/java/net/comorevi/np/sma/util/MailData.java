package net.comorevi.np.sma.util;

public class MailData {
    public int databaseId;
    public String subject;
    public String content;
    public String sender;
    public String target;
    public boolean read;

    public MailData(String subject, String content, String sender, String target) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.target = target;
        this.read = false;
    }

    public MailData(String subject, String content, String sender, String target, boolean read) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.target = target;
        this.read = read;
    }

    public MailData(int id, String subject, String content, String sender, String target, boolean read) {
        this.databaseId = id;
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.target = target;
        this.read = read;
    }
}
