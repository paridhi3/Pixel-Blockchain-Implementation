package com.blockchain.pixel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer index;
    private String type;
    
    @Column(columnDefinition = "TEXT")
    private String data;

    private String hash;
    private String previousHash;
    private Integer nonce;
    private LocalDateTime timestamp;    
}