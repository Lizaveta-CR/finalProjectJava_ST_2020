package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.service.exception.PasswordException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordUtil {
    private static final String SALT = "defaultsalt";
    /**
     * no. of iterations to be done. This value can manage speed of the algorithm.
     */
    private static final int ITERATIONS = 100;
    /**
     * This is the required output length of the hashed function.
     */
    private static final int KEY_LENGTH = 128;
    /**
     * Algorithm to be used in SecretKeyFactory
     */
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * Hashes password using PBKDF2 algorithm
     *
     * @param password - password to be hashed
     * @return hashed password
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String hashPassword(String password) throws PasswordException {
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return ITERATIONS + ":" + toHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PasswordException(e);
        }
    }

    /**
     * Gets salt value that should append to the password
     *
     * @return salt value - byte array
     */
    private static String getSalt() {
        return SALT;
    }

    /**
     * Converts a byte array to a hexadecimal string
     *
     * @param array - array to be converted
     * @return converted string
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}