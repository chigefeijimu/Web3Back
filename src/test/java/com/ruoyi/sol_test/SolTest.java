package com.ruoyi.sol_test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.BIP38PrivateKey;
import org.bitcoinj.net.discovery.SeedPeers;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.types.ProgramAccount;
import org.p2p.solanaj.utils.ByteUtils;
import org.p2p.solanaj.utils.bip32.wallet.DerivableType;
import org.p2p.solanaj.utils.bip32.wallet.SolanaBip44;
import org.p2p.solanaj.utils.bip32.wallet.key.SolanaCurve;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.bouncycastle.asn1.x509.ObjectDigestInfo.publicKey;
import static org.p2p.solanaj.rpc.types.config.RpcSendTransactionConfig.Encoding.base58;

public class SolTest {

    private static final String RPC_01 = "https://cold-hanni-fast-mainnet.helius-rpc.com/";
    public static final RpcClient client = new RpcClient(RPC_01);

    private static final String USER_ID = "manager7922";

    private static final String SECRET = "SECe3383518597e67d871e6b4bff7ed7ca51a0091737d6f610e2d87241a1201ad65";

    private static final String CUSTOM_ROBOT_TOKEN = "ad0c1140785f161bfdc0ee28110b46210fe441f8f48bfdc137acc8a8a70fed8b";

    private static ThreadPoolExecutor threadPoolExecutor;

    @BeforeAll
    public static void beforeTest () {
        threadPoolExecutor = new ThreadPoolExecutor(8, 10, 1, TimeUnit.DAYS, new LinkedBlockingQueue<>());
    }

    @AfterAll
    public static void afterTest () {
        threadPoolExecutor.shutdown();
        try {
            // 等待线程池关闭，超时时间为10秒
            if (!threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                // 超时后线程池仍未关闭，强制关闭
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 线程池关闭时被中断，强制关闭
            threadPoolExecutor.shutdownNow();
        }
    }

    @Test
    public void helloTest () {
        String s = "YkG6zyNJabEjbgMJGCSHAVmn9B4dmwALTdYCS4PjHhv2fNAqRK7qAApyiGbJrEXycD2L6Nro2Lg8YVB2Fr1H6MmTuuJFsg";
        Console.log(s.length());
    }

    @Test
    public void solanaTest () {
        try {
            long balance = client.getApi().getBalance(new PublicKey("94QJgKdvMYhmEvUYX67Y2YLLDVMQFHMzx5hLM5eYAbWN"));
            double balanceOfSol = lamportsToSol(balance);
            Console.log(balanceOfSol);
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
    }

    @Test
    public void createSolTest () {
//        for (int i = 0;i < 4;i++) {
//            threadPoolExecutor.submit(this::createWallet);
//        }
//        try {
//            Thread.sleep(10000000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Account account = new Account();
        byte[] secretKey = account.getSecretKey();
        String privateKey = Base58.encode(secretKey);
        Console.log(account.getPublicKey());
        Console.log(privateKey);
    }

    public void createWallet () {
        while (true) {
            Account account = new Account();
            PublicKey publicKey = account.getPublicKey();
            byte[] secretKey = account.getSecretKey();
            String publicKeyStr = publicKey.toBase58();
            String privateKey = ByteUtils.bytesToHex(secretKey);
            if (publicKeyStr.startsWith("fuck")) {
                this.dingding(publicKeyStr, privateKey);
                break;
            }
        }


    }

    public void dingding (String publicKey, String privateKey) {
        try {
            Long timestamp = System.currentTimeMillis();
            System.out.println(timestamp);
            String secret = SECRET;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            System.out.println(sign);

            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign="+sign+"&timestamp="+timestamp);
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            /**
             * 发送文本消息
             */
            //定义文本内容
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("找到啦，\n地址：" + publicKey + "\n私钥：" + privateKey);
            //定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtUserIds(Arrays.asList(USER_ID));
            //设置消息类型
            req.setMsgtype("text");
            req.setText(text);
//            req.setAt(at);
            client.execute(req, CUSTOM_ROBOT_TOKEN);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPumpKing () {
        String baseUrl = "https://frontend-api.pump.fun/coins/king-of-the-hill?includeNsfw=false";

//        String res = HttpUtil.get(baseUrl);
//        Console.log(res);

        try  {
            //设置系统代理属性（如果需要）
            System.setProperty("proxySet", "true");
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "7890");
            System.setProperty("https.proxyHost",  "127.0.0.1");
            System.setProperty("https.proxyPort",  "7890");

            //创建URL对象
            URL url = new URL(baseUrl);

            //创建URI对象
            URI uri = url.toURI();

            //获取系统默认的ProxySelector
            ProxySelector defaultProxySelector = ProxySelector.getDefault();

            //使用系统代理选择器选择代理服务器
            Proxy proxy = defaultProxySelector.select(uri).iterator().next();

            //创建带有代理的HttpURLConnection连接
            HttpURLConnection connection = (HttpURLConnection)  url.openConnection(proxy);

            //设置请求方法
            connection.setRequestMethod("GET");

            //连接到服务器
            connection.connect();

            //获取响应代码
            //  检查HTTP响应码
            int  responseCode  =  connection.getResponseCode();
            if  (responseCode  ==  HttpURLConnection.HTTP_OK)  {
                //  获取输入流
                BufferedReader  in  =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String  inputLine;
                StringBuilder  response  =  new  StringBuilder();

                //  读取响应内容
                while  ((inputLine  =  in.readLine())  !=  null)  {
                    response.append(inputLine);
                }
                in.close();

                //  输出响应内容
                Console.log(JSONUtil.toJsonPrettyStr(response));
            }  else  {
                System.out.println("GET  request  not  worked,  Response  Code:  "  +  responseCode);
            }

            //关闭连接
            connection.disconnect();
        }  catch(Exception  e) {
            e.printStackTrace();
        }
    }

    public static double lamportsToSol(long lamports) {
        return lamports / 1_000_000_000.0;
    }
}
