package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChainTypeEnum {

    ETH("ETH", 1),
    BSC("BSC", 2),
    SOLANA("SOL", 3);

    private final String chain;
    private final Integer value;
}
