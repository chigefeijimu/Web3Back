package com.ruoyi.project.wallet.mapper;

import java.util.List;
import com.ruoyi.project.wallet.domain.WalletEntity;

/**
 * 钱包管理Mapper接口
 * 
 * @author junqiao.li
 * @date 2024-06-11
 */
public interface WalletEntityMapper 
{
    /**
     * 查询钱包管理
     * 
     * @param id 钱包管理主键
     * @return 钱包管理
     */
    public WalletEntity selectWalletEntityById(Long id);

    /**
     * 查询钱包管理列表
     * 
     * @param walletEntity 钱包管理
     * @return 钱包管理集合
     */
    public List<WalletEntity> selectWalletEntityList(WalletEntity walletEntity);

    /**
     * 新增钱包管理
     * 
     * @param walletEntity 钱包管理
     * @return 结果
     */
    public int insertWalletEntity(WalletEntity walletEntity);

    /**
     * 修改钱包管理
     * 
     * @param walletEntity 钱包管理
     * @return 结果
     */
    public int updateWalletEntity(WalletEntity walletEntity);

    /**
     * 删除钱包管理
     * 
     * @param id 钱包管理主键
     * @return 结果
     */
    public int deleteWalletEntityById(Long id);

    /**
     * 批量删除钱包管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWalletEntityByIds(Long[] ids);
}
