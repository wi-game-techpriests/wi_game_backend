package agh.edu.pl.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "session_id"})
})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String tokenId; // token required in requests

    private int connectionsPoint = 0;
    private int fillInPoints = 0;
    private int wordSearchPoints = 0;
    private int kahootPoints = 0;

    public Player(Session session, String name, String tokenId) {
        this.session = session;
        this.name = name;
        this.tokenId = tokenId;
    }

    public Player(Session session, String name){
        this.session = session;
        this.name = name;
        this.tokenId = UUID.randomUUID().toString();
    }

    public Player() {}

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

    public String getToken() {
        return this.tokenId;
    }

    public void setToken(String tokenId) {
        this.tokenId = tokenId;
    }

}
