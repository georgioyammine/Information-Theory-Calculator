package controllers;

import classes.Huffman;
import classes.Trifid;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.controlsfx.control.ToggleSwitch;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class Controller {
    public TextArea entropyText;
    public Label entropy;
    public ToggleSwitch entropyIgnoreCase;
    public ToggleSwitch entropyIgnoreSpace;
    public ToggleSwitch styleSwitch;
    public Label requiredRestartLabel;
    public HBox entropyResult;
    public TextArea huffmanText;
    public HBox entropyResult1;
    public TabPane results;

    public TextArea huffmanResults;
    public TextArea huffmanSymbolsEncoding;
    public TextArea huffmanSymbolsFreq;
    public TextArea huffmanEncoded;
    public Label charsInfo;
    public Button entropyBtn;
    public Button huffmanBtn;

    public TextArea trifidText;
    public TextField key;
    public RadioButton encodeRadio;
    public RadioButton decodeRadio;
    public Button trifidProcess;
    public TabPane trifidResults;
    public TextArea cipherResult;
    public TextArea cipherAlphabet;
    public TextArea cipherProcess;

    boolean isDark = false;

    public void initialize() {
        initializeKey();
        results.setManaged(false);
        trifidResults.setManaged(false);
        ToggleGroup tg = new ToggleGroup();
        encodeRadio.setToggleGroup(tg);
        decodeRadio.setToggleGroup(tg);
        encodeRadio.setSelected(true);
        entropyIgnoreCase.selectedProperty().addListener((observableValue, aBoolean, t1) -> calculateEntropy());
        entropyIgnoreSpace.selectedProperty().addListener((observableValue, aBoolean, t1) -> calculateEntropy());
        Preferences prefs = Preferences.userNodeForPackage(launcher.Main.class);
        if(prefs.get("DARK","0").equals("1")) {
            styleSwitch.setText("DARK");
            styleSwitch.setSelected(true);
            isDark = true;
        }
        styleSwitch.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            Scene scene = this.entropyIgnoreCase.getScene();
            if(styleSwitch.isSelected()){
                prefs.put("DARK", "1");
                styleSwitch.setText("DARK");
                new JMetro(scene, Style.DARK);
//                if(!isDark)
//                    requiredRestartLabel.setVisible(true);
//                else
//                    requiredRestartLabel.setVisible(false);

            }
            else{
                prefs.put("DARK", "0");
                styleSwitch.setText("LIGHT");
                new JMetro(scene, Style.LIGHT);
//                if(!isDark)
//                    requiredRestartLabel.setVisible(false);
//                else
//                    requiredRestartLabel.setVisible(true);
            }
        });
        huffmanBtn.disableProperty().bind(huffmanText.textProperty().isEmpty());
        entropyBtn.disableProperty().bind(entropyText.textProperty().isEmpty());
        trifidProcess.disableProperty().bind(trifidText.textProperty().isEmpty());
    }

    private void initializeKey() {
        key.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([A-Za-z. ]*)")) {
                key.setText(newValue.replaceAll("([^A-Za-z. ]*)", ""));
            }
        });
        trifidText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([A-Za-z. ]*)")) {
                trifidText.setText(newValue.replaceAll("([^A-Za-z. ]*)", ""));
            }
        });
    }

    public void calculateEntropy() {
        String inputText = entropyText.getText();
        if(entropyIgnoreCase.isSelected())
            inputText = inputText.toLowerCase();
        if(entropyIgnoreSpace.isSelected())
            inputText = inputText.replaceAll("\\s+","");
        entropy.setText("Entropy = "
                +String.format("%.2f", getShannonEntropy(inputText))
                +" bits");

    }


    public static double getShannonEntropy(String str) {
        Map<Character, Integer> occurence = new HashMap<>();

        for (char c: str.toCharArray()) {
            if (occurence.containsKey(c)) {
                occurence.put(c, occurence.get(c) + 1);
            } else {
                occurence.put(c, 1);
            }
        }

        double entropy = 0.0;
        for (Map.Entry<Character, Integer> entry : occurence.entrySet()) {
            double p = (double) entry.getValue() / str.length();
            entropy += p * log2(p);
        }
        return -entropy;
    }

    private static double log2(double a) {
        return Math.log(a) / Math.log(2);
    }

    public void testHuffman(String orgStr){
        Huffman h = new Huffman(orgStr);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry: h.hmapWC.entrySet()){
            String key = entry.getKey().toString();
            int val = entry.getValue();
            if (key.equals("\n"))
                key = "\\n";
            sb.append("'").append(key).append("'").append("\u00a0occurs\u00a0").append(val).append("\u00a0times\u00a0\t ");
        }
        huffmanSymbolsFreq.setText(sb.toString());

        int maxLength = 0;
        for(Map.Entry<Character, String> entry: h.hmapCode.entrySet())
            maxLength = Math.max(maxLength, entry.getValue().length());
        StringBuilder map = new StringBuilder();
        for (Map.Entry<Character, String> entry: h.hmapCode.entrySet()){
            String key = entry.getKey().toString();
            String val = entry.getValue();
            if (key.equals("\n"))
                key = "\\n";
            map.append("'").append(key.equals(" ")?"\u00a0":key)
                    .append("'").append(key.equals(" ")?"\u00a0":"")
                    .append("\u00a0:\u00a0").append(rightPadding(val, maxLength)).append("\u00a0\t");
        }
        huffmanSymbolsEncoding.setText(map.toString());

        String encodedMessage = h.encode();
        huffmanEncoded.setText(h.encodeSpace());

        String d = h.decode();
        if(orgStr.equals(d))    // Check if original text and decoded text is exactly same
            System.out.println("ERROR!");
        System.out.println("DONE!");

        double sl = orgStr.length() * Math.ceil(log2(h.hmapCode.entrySet().size())) ;
        double el = encodedMessage.length();
        StringBuilder sbResults = new StringBuilder();

        sbResults.append("Uniform encoding string cost = ").append((int) sl).append(" bits\n");
        sbResults.append("Huffman encoding string cost = ").append((int) el).append(" bits\n\n");

        sbResults.append("Bits per symbol in uniform encoding = ")
                .append(Math.ceil(log2(h.hmapCode.entrySet().size()))).append("\n");
        sbResults.append("Average Bits per symbol in huffman encoding = ")
                .append(String.format("%.2f", el / orgStr.length())).append("\n\n");

        double r = ((el - sl)/sl) * -100 ;
        sbResults.append("% reduction = ").append(String.format("%.2f", r)).append("%");
        charsInfo.setText(orgStr.length()+" characters, "+h.hmapCode.entrySet().size()+" Unique.  -  Entropy= "
                +String.format("%.2f", getShannonEntropy(huffmanText.getText())) +" bits");
        results.setManaged(true);
        results.setVisible(true);
        huffmanResults.setText(sbResults.toString());

    }

    public void encodeHuffman() {
        results.setVisible(true);
        testHuffman(huffmanText.getText());
    }

    public void goToGithub() {
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/georgioyammine/").toURI());
        }catch(Exception ignored){}
    }

    public static String rightPadding(String str, int num) {
        return String.format("%1$-" + num + "s", str).replaceAll(" ","\u00a0");
    }

    public void processTrifid() {
        Trifid.TrifidResult trifidResult = Trifid.solve(encodeRadio.isSelected(), trifidText.getText(), key.getText());
        trifidResults.setVisible(true);
        trifidResults.setManaged(true);
        cipherResult.setText(trifidResult.result);
        cipherAlphabet.setText(trifidResult.alphabet);
        cipherProcess.setText(trifidResult.steps);
    }
}
