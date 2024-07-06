package com.ruoyi.common.entity.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletVo {
    private String publicKey;
    private String privateKey;
}
