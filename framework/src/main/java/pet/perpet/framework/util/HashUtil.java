package pet.perpet.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil {

    /**
     * Hash Algorithm
     */
    public enum Algorithm {
        MD5,
        SHA,
        SHA1;

        public static final String ALGORITHM_MD5 = "MD5";

        public static final String ALGORITHM_SHA1 = "SHA1";

        public static final String ALGORITHM_SHA = "SHA";

        @Override
        public String toString() {
            switch (this) {
                case MD5:
                    return ALGORITHM_MD5;
                case SHA1:
                    return ALGORITHM_SHA1;
                case SHA:
                    return ALGORITHM_SHA;
            }
            return "";
        }
    }

    /**
     * Hash Algorithm 을 Byte 배열로 변환 한다.
     *
     * @param input     입력값
     * @param algorithm algorithm
     * @return 변환된 Byte 배열 변환이 실패나 에러가 나면 null 로 리턴
     */
    public static byte[] getHash(byte[] input, Algorithm algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm.toString());
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5 Hash Algorithm 을 Byte 배열로 변환 한다.
     *
     * @param input 입력값
     * @return 변환된 Byte 배열 변환이 실패나 에러가 나면 null 로 리턴
     */
    public static byte[] getHashMD5(byte[] input) {
        return getHash(input, Algorithm.MD5);
    }

    /**
     * SHA1 Hash Algorithm 을 Byte 배열로 변환 한다.
     *
     * @param input 입력값
     * @return 변환된 Byte 배열 변환이 실패나 에러가 나면 null 로 리턴
     */
    public static byte[] getHashSHA1(byte[] input) {
        return getHash(input, Algorithm.SHA1);
    }

    /**
     * SHA Hash Algorithm 을 Byte 배열로 변환 한다.
     *
     * @param input 입력값
     * @return 변환된 Byte 배열 변환이 실패나 에러가 나면 null 로 리턴
     */
    public static byte[] getHashSHA(byte[] input) {
        return getHash(input, Algorithm.SHA);
    }

    /**
     * MD5 Hash Algorithm 을 Hex 값으로 변환
     *
     * @param input 입력값
     * @return Hex 값
     */
    public static String getHashHexStringMD5(byte[] input) {
        byte[] hash = getHashMD5(input);
        StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            sb.append(Integer.toString((aHash & 0xf0) >> 4, 16));
            sb.append(Integer.toString(aHash & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * SHA1 Hash Algorithm 을 Hex 값으로 변환
     *
     * @param input 입력값
     * @return Hex 값
     */
    public static String getHashHexStringSHA1(byte[] input) {
        byte[] hash = getHashSHA1(input);
        StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            sb.append(Integer.toString((aHash & 0xf0) >> 4, 16));
            sb.append(Integer.toString(aHash & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * SHA Hash Algorithm 을 Hex 값으로 변환
     *
     * @param input 입력값
     * @return Hex 값
     */
    public static String getHashHexStringSHA(byte[] input) {
        byte[] hash = getHashSHA(input);
        StringBuilder sb = new StringBuilder();
        for (byte aHash : hash) {
            sb.append(Integer.toString((aHash & 0xf0) >> 4, 16));
            sb.append(Integer.toString(aHash & 0x0f, 16));
        }
        return sb.toString();
    }

    /**
     * 앱 인증 값
     *
     * @param context
     * @param algorithm
     * @return
     */
    public static String getApplicationSignatures(Context context, Algorithm algorithm) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance(algorithm.toString());
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String checkSignature(String signature) {
        int ENCRYPT_KEY_LENHTH = 16;
        if (signature.length() > ENCRYPT_KEY_LENHTH) {
            signature = signature.substring(0, ENCRYPT_KEY_LENHTH);
        } else if (signature.length() < ENCRYPT_KEY_LENHTH) {
            int margine = Math.abs(ENCRYPT_KEY_LENHTH - signature.length());
            for (int i = 0; i < margine; i++) {
                signature += "0";
            }
        }
        return signature;
    }
}

