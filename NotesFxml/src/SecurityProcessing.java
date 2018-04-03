import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Vad Nik.
 * @version dated Apr 1, 2018.
 */
public class SecurityProcessing {
    private static Stage primaryStage;

    static void createWindow() {
        primaryStage = new Stage();
        primaryStage.setTitle("Enter password");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new javafx.scene.image.Image(NotesMain.class.getResourceAsStream("notes.png")));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setOnShowing(event -> Vars.isAlertNPShowing = true);
        primaryStage.initOwner(NotesMain.primaryStage);
        primaryStage.setOnCloseRequest(event -> NotesMain.exit());
        initRootLayout();
    }

    private static void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NotesCreateNote.class.getResource("NSecurityProcessing.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void close() {
        primaryStage.close();
    }

    private static String key = "0000000000000090";

    private static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    private static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder(ba.length * 2);
        String hexNumber;
        for (byte aBa : ba) {
            hexNumber = "0" + Integer.toHexString(0xff & aBa);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        /*
        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
         */
        return sb.toString();
    }

    static String encrypt(String message) throws Exception {
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128);
        // use key coss2
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(message.getBytes());
        return byteArrayToHex(encrypted);
    }

    static String decrypt(String encrypted) throws Exception {
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128);
        // use key coss2
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(hexToByteArray(encrypted));
        //String originalString = new String(original);
        return new String(original);
    }

    static void encryptDb() throws Exception {
        List<String> list = new ArrayList<>();
        for (String o : HibernateProcessing.getStringedNotes()) {
            list.add(String.valueOf(o.split("~~")[0]) + "~~" + encrypt(o.split("~~")[1]) + "~~" + encrypt(o.split("~~")[2]));
        }
        HibernateProcessing.deleteAll();
        for (String o : list)
            HibernateProcessing.insert(Integer.parseInt(o.split("~~")[0]), o.split("~~")[1], o.split("~~")[2]);
    }

    static void decryptDb() throws Exception {
        List<String> list = new ArrayList<>();
        for (String o : HibernateProcessing.getStringedNotes()) {
            list.add(String.valueOf(o.split("~~")[0]) + "~~" + decrypt(o.split("~~")[1]) + "~~" + decrypt(o.split("~~")[2]));
        }
        HibernateProcessing.deleteAll();
        for (String o : list)
            HibernateProcessing.insert(Integer.parseInt(o.split("~~")[0]), o.split("~~")[1], o.split("~~")[2]);
    }

    @NotUsed
    static boolean canProceed() {
        if (Vars.vars.isPasswordEnable) {
            if (SecurityPCtrl.canProceed)
                return true;
        } else
            return true;
        return false;
    }

    static void serialize() {
//        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serv.txt"))) {
//            out.writeObject(Vars.vars);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        try {
            encryptObject(Vars.vars, new FileOutputStream("serv.txt"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static Vars deserialize() throws Exception {
//        Vars vars = null;
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("serv.txt"))) {
//            vars = (Vars) in.readObject();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return vars;

        FileInputStream bais = new FileInputStream("serv.txt");
        return (Vars) decryptObject(bais);
    }

    private static void encryptObject(Serializable object, OutputStream ostream) throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        try {
            // Length is 16 byte
            SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");

            // Create cipher
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            SealedObject sealedObject = new SealedObject(object, cipher);

            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(ostream, cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    private static Object decryptObject(InputStream istream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException {
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, sks);

        CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
        ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
        SealedObject sealedObject;
        try {
            sealedObject = (SealedObject) inputStream.readObject();
            return sealedObject.getObject(cipher);
        } catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}