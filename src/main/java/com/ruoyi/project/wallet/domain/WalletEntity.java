package com.ruoyi.project.wallet.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 钱包管理对象 wallet_wallet
 * 
 * @author junqiao.li
 * @date 2024-06-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class WalletEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 钱包名称 */
    @Excel(name = "钱包名称")
    private String name;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String address;

    /** 钱包私钥 */
    @Excel(name = "钱包私钥")
    private String pkey;

    /** 钱包归属人 */
    @Excel(name = "钱包归属人")
    private Long userId;

    /** 钱包链类型 */
    @Excel(name = "钱包链类型")
    private Integer chainType;
}
