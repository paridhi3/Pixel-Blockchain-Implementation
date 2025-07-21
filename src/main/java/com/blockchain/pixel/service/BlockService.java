package com.blockchain.pixel.service;

import org.springframework.stereotype.Service;

import com.blockchain.pixel.entity.Block;
import com.blockchain.pixel.repository.BlockRepository;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.util.List;

@Service
public class BlockService {

    private final BlockRepository repository;

    public BlockService(BlockRepository repository) {
        this.repository = repository;
    }

    public List<Block> getAllBlocks() {
        return repository.findAll();
    }

    public Block addBlock(String type, String data) {
        Block lastBlock = repository.findTopByOrderByIndexDesc();

        Block newBlock = new Block();
        newBlock.setIndex(lastBlock == null ? 0 : lastBlock.getIndex() + 1);
        newBlock.setPreviousHash(lastBlock == null ? "0" : lastBlock.getHash());
        newBlock.setTimestamp(LocalDateTime.now());
        newBlock.setData(data);
        newBlock.setType(type);

        int nonce = 0;
        String hash;
        do {
            nonce++;
            hash = calculateHash(newBlock, nonce);
        } while (!hash.startsWith("0000")); // PoW difficulty

        newBlock.setNonce(nonce);
        newBlock.setHash(hash);

        return repository.save(newBlock);
    }

    public boolean isValidChain() {
        List<Block> chain = repository.findAll();
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            String recalculatedHash = calculateHash(current, current.getNonce());
            if (!current.getHash().equals(recalculatedHash)) return false;
            if (!current.getPreviousHash().equals(previous.getHash())) return false;
        }
        return true;
    }

    private String calculateHash(Block block, int nonce) {
        try {
            String input = block.getIndex() + block.getPreviousHash() + block.getData() + block.getType() + nonce;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}