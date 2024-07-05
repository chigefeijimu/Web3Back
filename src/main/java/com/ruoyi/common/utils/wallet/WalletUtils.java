package com.ruoyi.common.utils.wallet;

import org.p2p.solanaj.core.Account;

public class WalletUtils {

    public static byte[] createSolanaWallet () {
        Account account = new Account();
        return account.getSecretKey();
    }

    public static byte[] createSolanaWalletPretty (String prefix, String suffix) {
        while (true) {
            Account account = new Account();
            String address = account.getPublicKey().toBase58();

            if (address.startsWith(prefix) && address.endsWith(suffix)) {
                return account.getSecretKey();
            }
        }
    }
}
