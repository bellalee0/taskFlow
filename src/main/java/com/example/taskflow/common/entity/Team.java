package com.example.taskflow.common.entity;

import com.example.taskflow.domain.team.model.request.TeamUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "teams")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateTeam(TeamUpdateRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
    }
}
