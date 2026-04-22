package agh.edu.pl.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "session_id"})
})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int connectionsPoint = 0;
    private int fillInPoints = 0;
    private int wordSearchPoints = 0;
    private int kahootPoints = 0;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setConnectionsPoint(int connectionsPoint) {
        this.connectionsPoint = connectionsPoint;
    }

    public void setFillInPoints(int fillInPoints) {
        this.fillInPoints = fillInPoints;
    }

    public void setWordSearchPoints(int wordSearchPoints) {
        this.wordSearchPoints = wordSearchPoints;
    }

    public void setKahootPoints(int kahootPoints) {
        this.kahootPoints = kahootPoints;
    }

    public String getName() {
        return name;
    }

    public int getConnectionsPoint() {
        return connectionsPoint;
    }

    public int getFillInPoints() {
        return fillInPoints;
    }

    public int getWordSearchPoints() {
        return wordSearchPoints;
    }

    public int getKahootPoints() {
        return kahootPoints;
    }

    public Long getId() {
        return id;
    }

}
