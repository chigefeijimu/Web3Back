package com.ruoyi.common.utils.wallet;

import com.ruoyi.common.entity.vo.WalletVo;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;

public class WalletUtils {

    public static WalletVo createSolanaWallet () {
        Account account = new Account();
        return WalletVo.builder()
                .publicKey(account.getPublicKey().toBase58())
                .privateKey(Base58.encode(account.getSecretKey()))
                .build();
    }

    public static WalletVo createSolanaWalletPretty (String prefix, String suffix) {
        while (true) {
            Account account = new Account();
            String address = account.getPublicKey().toBase58();

            if (address.startsWith(prefix) && address.endsWith(suffix)) {
                return WalletVo.builder()
                        .publicKey(account.getPublicKey().toBase58())
                        .privateKey(Base58.encode(account.getSecretKey()))
                        .build();
            }
        }
    }
}
