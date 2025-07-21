package com.blockchain.pixel.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blockchain.pixel.entity.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findTopByOrderByIndexDesc(); // Returns the block with the highest index (i.e., the latest block in the chain) from the database.
}