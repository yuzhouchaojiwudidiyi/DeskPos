package com.wellsun.deskpos.pboc;

/**
 * date     : 2023-03-13
 * author   : ZhaoZheng
 * describe :
 */

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class PBOCUtil {
    public static String getMac2(String data, String processKey, String icv)
            throws Exception {
        byte[] processKeyBytes = hexString2byte(processKey);
        String mac2Data = data;
        byte[] icvBytes = hexString2byte(icv);
        byte[] mac2DataBytes = hexString2byte(mac2Data);
        byte[] calcPbocdesMACBytes = calculatePbocdesMAC(mac2DataBytes, processKeyBytes, icvBytes);
        String calcPbocdesMac = byte2hexString(calcPbocdesMACBytes);
        String mac2 = calcPbocdesMac.substring(0, 8);
        return mac2;
    }

    //计算过程密匙计算右边密匙
    public static String getDisperseKeyOnce(String data, String key)
            throws Exception {
        byte[] processKeyBytes = hexString2byte(key);
        byte[] randomDataBytes = hexString2byte(data);
        //分散左边8字节
        byte[] calcPbocdesBytesLeft = calculatePbocdes(randomDataBytes, processKeyBytes);
        String calcPbocdesLeft = byte2hexString(calcPbocdesBytesLeft);
        String desLeft = calcPbocdesLeft.substring(0, 16);

        //数据取反
        byte[] byteRandomRight = new byte[8];
        for (int i = 0; i < byteRandomRight.length; i++) {
            byteRandomRight[i] = (byte) ~randomDataBytes[i];
        }

        //分散右边八字节
        byte[] calcPbocdesBytesRight = calculatePbocdes(byteRandomRight, processKeyBytes);
        String calcPbocdesRight = byte2hexString(calcPbocdesBytesRight);
        String desRight = calcPbocdesRight.substring(0, 16);

        return desLeft + desRight;
    }

    private static byte[] calculatePbocdes(byte[] randomDataBytes, byte[] keyBytes) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        cipher.init(1, secretKey, sr);
        return cipher.doFinal(randomDataBytes);
    }


    public static byte[] calculatePbocdesMAC(byte[] data, byte[] key, byte[] icv)
            throws Exception {
        if ((key == null) || (data == null))
            throw new RuntimeException("data or key is null.");
        if (key.length != 8) {
            throw new RuntimeException("key length is not 16 byte.");
        }
        int dataLength = data.length;
        int blockCount = dataLength / 8 + 1;
        int lastBlockLength = dataLength % 8;
        byte[][] dataBlock = new byte[blockCount][8];
        for (int i = 0; i < blockCount; i++) {
            int copyLength = i == blockCount - 1 ? lastBlockLength : 8;
            System.arraycopy(data, i * 8, dataBlock[i], 0, copyLength);
        }
        dataBlock[(blockCount - 1)][lastBlockLength] = -128;
        byte[] desXor = xOr(dataBlock[0], icv);
        for (int i = 1; i < blockCount; i++) {
            byte[] des = encryptByDesCbc(desXor, key);
            desXor = xOr(dataBlock[i], des);
        }
        desXor = encryptByDesCbc(desXor, key);
        return desXor;
    }

    public static byte[] encryptByDesCbc(byte[] content, byte[] key)
            throws GeneralSecurityException {
        byte[] ZERO_IVC = new byte[8];
        return encryptByDesCbc(content, key, ZERO_IVC);
    }

    public static byte[] encryptByDesCbc(byte[] content, byte[] key, byte[] icv)
            throws GeneralSecurityException {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(1, secretKey, iv, sr);
        return cipher.doFinal(content);
    }

    public static byte[] xOr(byte[] b1, byte[] b2) {
        byte[] tXor = new byte[Math.min(b1.length, b2.length)];
        for (int i = 0; i < tXor.length; i++)
            tXor[i] = (byte) (b1[i] ^ b2[i]);
        return tXor;
    }

    public static byte[] hexString2byte(String hexString) {
        if ((hexString == null) || (hexString.length() % 2 != 0) || (hexString.contains("null"))) {
            return null;
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[(i / 2)] = (byte) (Integer.parseInt(hexString.substring(i, i + 2), 16) & 0xFF);
        }
        return bytes;
    }

    public static String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(bytes[i] & 0xFF, 16));
        }
        return buf.toString().toUpperCase();
    }


     //随机数加密 外部验证
    public static String getDes(String data, String key)
            throws Exception {
        byte[] processKeyBytes = hexString2byte(key);
        byte[] randomDataBytes = hexString2byte(data);
        byte[] calcPbocdesBytes = calculatePbocdes(randomDataBytes, processKeyBytes);
        String calcPbocdes = byte2hexString(calcPbocdesBytes);
        String des = calcPbocdes.substring(0, 16);
        return des;
    }


    public static void main(String[] args) {
        try {
            System.out.println(getDes("CDDFE15000008000", "3F013F013F013F013F013F013F013F01"));
            System.out.println(encryptECB3Des("256C888E00008000", "284F0A2B66568DFC52C372EA75D0C439"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 3DES(双倍长) 加密
     *
     * @paramkeybyte
     * @param src
     * @return
     */
    public static String encryptECB3Des(String src, String key) {
        int len = key.length();
        if (key == null || src == null) {
            return null;
        }
        if (src.length() % 16 != 0) {
            return null;
        }
        if (len == 32) {
            String outData = "";
            String str = "";
            for (int i = 0; i < src.length() / 16; i++) {
                str = src.substring(i * 16, (i + 1) * 16);
                outData += encECB3Des(key, str);
            }
            return outData.toUpperCase();
        }
        return null;
    }


    public static String encECB3Des(String key, String src) {
        byte[] temp = null;
        byte[] temp1 = null;
        temp1 = encryptDes(hexStringToBytes(key.substring(0, 16)), hexStringToBytes(src));
        temp = decryptDes(hexStringToBytes(key.substring(16, 32)), temp1);
        temp1 = encryptDes(hexStringToBytes(key.substring(0, 16)), temp);
        return bytesToHexString(temp1);
    }

    public static String decECB3Des(String key, String src) {
        byte[] temp2 = decryptDes(hexStringToBytes(key.substring(0, 16)), hexStringToBytes(src));
        byte[] temp1 = encryptDes(hexStringToBytes(key.substring(16, 32)), temp2);
        byte[] dest = decryptDes(hexStringToBytes(key.substring(0, 16)), temp1);
        return bytesToHexString(dest);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 3DES(双倍长) 解密
     *
     * @paramkeybyte
     * @param src
     * @return
     */
    public static String decryptECB3Des(String key, String src) {
        if (key == null || src == null) {
            return null;
        }
        if (src.length() % 16 != 0) {
            return null;
        }
        if (key.length() == 32) {
            String outData = "";
            String str = "";
            for (int i = 0; i < src.length() / 16; i++) {
                str = src.substring(i * 16, (i + 1) * 16);
                outData += decECB3Des(key, str);
            }
            return outData;
        }
        return null;
    }

    /**
     * DES加密
     *
     */
    public static byte[] encryptDes(byte[] key, byte[] src) {
        try {
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * des解密
     *
     * @param key
     * @param src
     * @return
     */
    public static byte[] decryptDes(byte[] key, byte[] src) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}