package com.blockchain.pixel.controller;

import org.springframework.web.bind.annotation.*;

import com.blockchain.pixel.entity.Block;
import com.blockchain.pixel.service.BlockService;

import java.util.List;

@RestController
@RequestMapping("/api/pixel")
@CrossOrigin(origins = "*")
public class BlockController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping
    public List<Block> getAllBlocks() {
        return blockService.getAllBlocks();
    }

    @PostMapping
    public Block addBlock(@RequestBody Block inputBlock) {
        return blockService.addBlock(inputBlock.getType(), inputBlock.getData());
    }

    @GetMapping("/validate")
    public boolean validate() {
        return blockService.isValidChain();
    }
}