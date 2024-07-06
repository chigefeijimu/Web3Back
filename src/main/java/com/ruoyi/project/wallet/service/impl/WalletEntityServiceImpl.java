package com.ruoyi.project.wallet.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.ruoyi.common.entity.vo.WalletVo;
import com.ruoyi.common.enums.ChainTypeEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.utils.wallet.WalletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.wallet.domain.dto.WalletAddDto;
import org.bitcoinj.core.Base58;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.wallet.mapper.WalletEntityMapper;
import com.ruoyi.project.wallet.domain.WalletEntity;
import com.ruoyi.project.wallet.service.IWalletEntityService;

import javax.annotation.Resource;

/**
 * 钱包管理Service业务层处理
 * 
 * @author junqiao.li
 * @date 2024-06-11
 */
@Service
public class WalletEntityServiceImpl implements IWalletEntityService 
{
    @Resource
    private WalletEntityMapper walletEntityMapper;

    /**
     * 查询钱包管理
     * 
     * @param id 钱包管理主键
     * @return 钱包管理
     */
    @Override
    public WalletEntity selectWalletEntityById(Long id)
    {
        return walletEntityMapper.selectWalletEntityById(id);
    }

    /**
     * 查询钱包管理列表
     * 
     * @param walletEntity 钱包管理
     * @return 钱包管理
     */
    @Override
    public List<WalletEntity> selectWalletEntityList(WalletEntity walletEntity)
    {
        return walletEntityMapper.selectWalletEntityList(walletEntity);
    }

    /**
     * 新增钱包管理
     * 
     * @param walletAddDto 钱包管理
     * @return 结果
     */
    @Override
    public int insertWalletEntity(WalletAddDto walletAddDto)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        WalletEntity walletEntity = WalletEntity.builder()
                .id(IdUtil.getSnowflakeNextId())
                .name(walletAddDto.getName())
                .userId(loginUser.getUserId())
                .chainType(walletAddDto.getChainsValue()).build();

        if (walletAddDto.getChainsValue().equals(ChainTypeEnum.SOLANA.getValue())) {
            WalletVo solanaWallet;
            if (walletAddDto.getIsPretty() == 1) {
                solanaWallet = WalletUtils.createSolanaWalletPretty(walletAddDto.getPrettyPrefix(), walletAddDto.getPrettySuffix());
            } else {
                solanaWallet = WalletUtils.createSolanaWallet();
            }
            walletEntity.setAddress(solanaWallet.getPublicKey());
            walletEntity.setPkey(solanaWallet.getPrivateKey());
        }

        return walletEntityMapper.insertWalletEntity(walletEntity);
    }

    /**
     * 修改钱包管理
     * 
     * @param walletEntity 钱包管理
     * @return 结果
     */
    @Override
    public int updateWalletEntity(WalletEntity walletEntity)
    {
        return walletEntityMapper.updateWalletEntity(walletEntity);
    }

    /**
     * 批量删除钱包管理
     * 
     * @param ids 需要删除的钱包管理主键
     * @return 结果
     */
    @Override
    public int deleteWalletEntityByIds(Long[] ids)
    {
        return walletEntityMapper.deleteWalletEntityByIds(ids);
    }

    /**
     * 删除钱包管理信息
     * 
     * @param id 钱包管理主键
     * @return 结果
     */
    @Override
    public int deleteWalletEntityById(Long id)
    {
        return walletEntityMapper.deleteWalletEntityById(id);
    }
}
