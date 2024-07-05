package com.ruoyi.project.wallet.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WalletAddDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer chainsValue;
    private Integer isPretty;
    private String prettyPrefix;
    private String prettySuffix;
    private String remark;
}
